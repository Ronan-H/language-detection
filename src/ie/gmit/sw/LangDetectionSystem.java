package ie.gmit.sw;

import ie.gmit.sw.lang_dist.LangDistStore;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LangDetectionSystem {
    private LangDetectionWorker worker;
    private BlockingQueue<LangDetectionJob> inQueue;
    private ConcurrentMap<String, LangDetectionJob> outMap;

    public LangDetectionSystem(LangDistStore distStore, int inQueueCap) {
        inQueue = new ArrayBlockingQueue<>(inQueueCap);
        outMap = new ConcurrentHashMap<>();
        worker = new LangDetectionWorker(distStore, inQueue, outMap);
    }

    public void go() {
        new Thread(worker).start();
    }

    public void stop() {
        worker.stop();
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
