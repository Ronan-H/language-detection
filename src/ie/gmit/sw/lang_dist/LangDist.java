package ie.gmit.sw.lang_dist;

import ie.gmit.sw.Lang;

/**
 * Abstract definition for a language distribution, involving recording k-mer occurrences from
 * language samples and creating a frequency distribution array.
 */
public abstract class LangDist {
    private Lang lang;
    private int numRecords;

    /**
     * Create a language distribution for a specified language.
     * @param lang Language of this distribution
     */
    public LangDist(Lang lang) {
        this.lang = lang;
    }

    /**
     * Record a k-mer to this distribution
     */
    public abstract void recordKmer(char[] kmer);

    /**
     * Split a language sample line into k-mers and record each k-mer
     *
     * @param line language sample line
     * @param k size of k-mer
     */
    public void recordSample(String line, int k) {
        char[] sample = line.toCharArray();
        char[] kmer = new char[k];

        for (int i = 0; i <= sample.length - k; i++) {
            for (int j = 0; j < k; j++) {
                kmer[j] = sample[i + j];
            }

            recordKmer(kmer);
            numRecords++;
        }
    }

    /**
     * Generate a k-mer distribution, where each k-mer is given a frequency value
     * between 0 and 1.
     * @return
     */
    public abstract double[] getDistribution();

    public Lang getLang() {
        return lang;
    }

    public int getNumRecords() {
        return numRecords;
    }
}
