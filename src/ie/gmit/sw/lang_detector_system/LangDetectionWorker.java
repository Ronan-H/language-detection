package ie.gmit.sw.lang_detector_system;

import ie.gmit.sw.Lang;
import ie.gmit.sw.lang_detector.LangDetector;
import ie.gmit.sw.lang_dist.HashedLangDist;
import ie.gmit.sw.lang_dist.LangDist;
import ie.gmit.sw.lang_dist.LangDistStore;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

public class LangDetectionWorker implements Runnable {
    private LangDistStore distStore;
    private BlockingQueue<LangDetectionJob> inQueue;
    private ConcurrentMap<String, LangDetectionJob> outMap;
    private LangDetector langDetector;
    private boolean running;

    public LangDetectionWorker(LangDistStore distStore, BlockingQueue<LangDetectionJob> inQueue, ConcurrentMap<String, LangDetectionJob> outMap, LangDetector langDetector) {
        this.distStore = distStore;
        this.inQueue = inQueue;
        this.outMap = outMap;
        this.langDetector = langDetector;
    }

    @Override
    public void run() {
        running = true;

        while (running) {
            try {
                LangDetectionJob currentJob = inQueue.take();

                LangDist testDist = new HashedLangDist(512);
                testDist.recordSample(currentJob.getSampleText(), 3);

                Lang closest = langDetector.findClosestLanguage(testDist, distStore);
                currentJob.setResult(closest);

                outMap.put(currentJob.getId(), currentJob);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }
}
