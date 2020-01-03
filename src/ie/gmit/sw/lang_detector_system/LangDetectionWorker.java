package ie.gmit.sw.lang_detector_system;

import ie.gmit.sw.Lang;
import ie.gmit.sw.lang_detector.LangDetector;
import ie.gmit.sw.lang_dist.HashedLangDist;
import ie.gmit.sw.lang_dist.LangDist;
import ie.gmit.sw.lang_dist.LangDistStore;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * Language detection worker capable of running asynchronously on a different thread.
 * Continuously takes jobs off of a queue and processes them before adding the result to a result map.
 */
public class LangDetectionWorker implements Runnable {
    private LangDistStore distStore;
    private BlockingQueue<LangDetectionJob> inQueue;
    private ConcurrentMap<String, LangDetectionJob> outMap;
    private LangDetector langDetector;
    private boolean running;

    /**
     * Creates a new worker using pre-existing shared objects.
     *
     * @param distStore Store of language distribution frequencies
     * @param inQueue Job input queue (blocking)
     * @param outMap Language detection result map
     * @param langDetector Language detector to use (contains an underlying strategy for detecting languages)
     */
    public LangDetectionWorker(LangDistStore distStore, BlockingQueue<LangDetectionJob> inQueue, ConcurrentMap<String, LangDetectionJob> outMap, LangDetector langDetector) {
        this.distStore = distStore;
        this.inQueue = inQueue;
        this.outMap = outMap;
        this.langDetector = langDetector;
    }

    /**
     * Continuously takes jobs off of a queue and processes them before adding the result to a result map.
     */
    @Override
    public void run() {
        running = true;

        while (running) {
            try {
                // take job from in-queue (blocking)
                // (there may be multiple workers waiting for a job)
                LangDetectionJob currentJob = inQueue.take();

                // TODO key range and k-mer size shouldn't be specified here, they have to match the store and parser values
                // create language distribution for the user's query and record the k-mer values to it
                LangDist testDist = new HashedLangDist(512);
                testDist.recordSample(currentJob.getSampleText(), 3);

                // find the closest language from the store of known language distributions, using whichever
                // LangDetectorStrategy was given to this worker through the LangDetector
                Lang closest = langDetector.findClosestLanguage(testDist, distStore);
                // update job result with detected language
                currentJob.setResult(closest);

                // put the job result out onto the out map to be later viewed by the user
                outMap.put(currentJob.getId(), currentJob);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stop thread (may not stop immediately)
     */
    public void stop() {
        running = false;
    }
}
