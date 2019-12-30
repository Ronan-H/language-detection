package ie.gmit.sw.lang_dist;

import ie.gmit.sw.Lang;
import ie.gmit.sw.lang_dist.LangDist;

public class HashedLangDist extends LangDist {
    private int hashRange;
    private int[] freqs;

    public HashedLangDist(Lang lang, int hashRange) {
        super(lang);
        this.hashRange = hashRange;
        freqs = new int[hashRange];
    }

    public HashedLangDist(int keyRange) {
        this(Lang.Unidentified, keyRange);
    }

    public void recordKmer(char[] kmer) {
        // hash implementation based on String.hashcode()
        int hash = 17;

        for (int i = 0; i < kmer.length; i++) {
            hash = 31 * hash + kmer[i];
        }

        // compute array index (ignoring sign bit)
        int index = (hash & 0x7FFFFFFF) % hashRange;
        freqs[index]++;
        freqs[kmer[0] % hashRange]++;
    }

    public double[] getDistribution() {
        double[] dist = new double[hashRange];

        for (int i = 0; i < hashRange; i++) {
            dist[i] = (double) freqs[i] / getNumRecords();
        }

        return dist;
    }

    public void printFreq() {
        for (int i = 0; i < hashRange; i++) {
            System.out.println(i + ": " + freqs[i]);
        }
    }
}
