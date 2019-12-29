package ie.gmit.sw;

public class LanguageDistribution {
    private int hashRange;
    private int[] freqs;
    private Language language;
    private int numRecords;

    public LanguageDistribution(Language language, int hashRange) {
        this.language = language;
        this.hashRange = hashRange;
        freqs = new int[hashRange];
    }

    public LanguageDistribution(int keyRange) {
        this(Language.Unidentified, keyRange);
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

        numRecords++;
    }

    public void recordLine(String line, int k) {
        char[] sample = line.toCharArray();
        char[] kmer = new char[k];

        for (int i = 0; i <= sample.length - k; i++) {
            for (int j = 0; j < k; j++) {
                kmer[j] = sample[i + j];
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

    public void printFreq() {
        for (int i = 0; i < hashRange; i++) {
            System.out.println(i + ": " + freqs[i]);
        }
    }
}
