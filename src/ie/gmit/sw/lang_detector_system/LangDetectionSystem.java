package ie.gmit.sw.lang_detector_system;

import ie.gmit.sw.lang_detector.LangDetectorFactory;
import ie.gmit.sw.lang_dist.LangDistStore;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LangDetectionSystem {
    private LangDetectionWorker[] workers;
    private BlockingQueue<LangDetectionJob> inQueue;
    private ConcurrentMap<String, LangDetectionJob> outMap;

    public LangDetectionSystem(LangDistStore distStore, int inQueueCap, int numWorkers) {
        inQueue = new ArrayBlockingQueue<>(inQueueCap);
        outMap = new ConcurrentHashMap<>();
        workers = new LangDetectionWorker[numWorkers];

        for (int i = 0; i < workers.length; i++) {
            workers[i] = new LangDetectionWorker(distStore, inQueue, outMap, LangDetectorFactory.getInstance().getOutOfPlaceLanguageDetector());
        }
    }

    public void go() {
        for (int i = 0; i < workers.length; i++) {
            new Thread(workers[i]).start();
        }
    }

    public void stop() {
        for (int i = 0; i < workers.length; i++) {
            workers[i].stop();
        }
    }

    public void submitJob(String id, String sampleText) {
        try {
            inQueue.put(new LangDetectionJob(id, sampleText));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isJobFinished(String id) {
        return outMap.containsKey(id);
    }

    public String getLanguageResult(String id) {
        return outMap.get(id).getResult().name();
    }
}
