package ie.gmit.sw.lang_dist;

import ie.gmit.sw.Lang;

public abstract class LangDist {
    private Lang lang;
    private int numRecords;

    public LangDist(Lang lang) {
        this.lang = lang;
    }

    public abstract void recordKmer(char[] kmer);

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

    public abstract double[] getDistribution();

    public Lang getLang() {
        return lang;
    }

    public int getNumRecords() {
        return numRecords;
    }
}
