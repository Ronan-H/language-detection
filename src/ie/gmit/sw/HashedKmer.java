package ie.gmit.sw;

public class HashedKmer {
    private int hashRange;
    private int[] freqs;
    private Language language;
    private int numRecords;

    public HashedKmer(Language language, int hashRange) {
        this.language = language;
        this.hashRange = hashRange;
        freqs = new int[hashRange];
    }

    public void recordKmer(short[] kmer) {
        // hash implementation from https://stackoverflow.com/a/11742634
        int hash = 17;

        for (short c : kmer) {
            hash = hash * 31 + c;
        }

        int index = (hash & 0x7FFFFFFF) % hashRange;
        freqs[index] += 1;

        numRecords++;
    }

    public void recordLine(String line, int k) {
        char[] sample = line.toCharArray();
        short[] kmer = new short[k];

        for (int i = 0; i <= sample.length - k; i++) {
            for (int j = 0; j < k; j++) {
                kmer[j] = (short) sample[i + j];
            }

            recordKmer(kmer);
        }
    }

    public double[] getDistribution() {
        double[] dist = new double[hashRange];

        for (int i = 0; i < hashRange; i++) {
            dist[i] = (double) freqs[i] / numRecords;
        }

        return dist;
    }

    public Language getLanguage() {
        return language;
    }
}
