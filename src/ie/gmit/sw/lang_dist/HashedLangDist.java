package ie.gmit.sw.lang_dist;

import ie.gmit.sw.Lang;
import ie.gmit.sw.lang_dist.LangDist;

/**
 * Hashed language distribution; a language distribution where each k-mer is hashed into
 * an array (there WILL be collisions, but since other language distributions of the same
 * language would have the same collisions and so record frequencies for the same index,
 * it should still work OK and be much more efficient than using Maps).
 */
public class HashedLangDist extends LangDist {
    private int hashRange;
    private int[] freqs;

    /**
     * Creates a new hashed language distribution
     *
     * @param lang Language of this distribution
     * @param hashRange Range of the hash function
     */
    public HashedLangDist(Lang lang, int hashRange) {
        super(lang);
        this.hashRange = hashRange;
        freqs = new int[hashRange];
    }

    /**
     * Creates a new hashed language distribution, where the language is not known
     * (used for the user's query)
     *
     * @param keyRange Range of the hash function
     */
    public HashedLangDist(int keyRange) {
        this(Lang.Unidentified, keyRange);
    }

    /**
     * TODO continue javadoccing here
     * @param kmer
     */
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
