package ie.gmit.sw;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class KmerMapper implements Runnable {
    private Database db;
    private BlockingQueue<LanguageSample> queue;
    private int k;

    public KmerMapper(BlockingQueue<LanguageSample> queue, int k) {
        this.queue = queue;
        this.k = k;
        db = new Database();
    }

    @Override
    public void run() {
        LanguageSample languageSample;
        short[] kmer = new short[k];
        char[] sample;

        try {
            while (!((languageSample = queue.take()) instanceof Poison)) {
                sample = languageSample.getSample().toCharArray();

                for (int i = 0; i <= sample.length - k; i++) {
                    for (int j = 0; j < k; j++) {
                        kmer[j] = (short) sample[i + j];
                    }

                    db.add(kmer, languageSample.getLanguage());
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
