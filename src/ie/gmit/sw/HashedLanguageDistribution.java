package ie.gmit.sw;

public class HashedLanguageDistribution extends LanguageDistribution {
    private int hashRange;
    private int[] freqs;

    public HashedLanguageDistribution(Language language, int hashRange) {
        super(language);
        this.hashRange = hashRange;
        freqs = new int[hashRange];
    }

    public HashedLanguageDistribution(int keyRange) {
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
