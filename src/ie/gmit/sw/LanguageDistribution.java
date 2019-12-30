package ie.gmit.sw;

public abstract class LanguageDistribution {
    private Language language;
    private int numRecords;

    public LanguageDistribution(Language language) {
        this.language = language;
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

    public Language getLanguage() {
        return language;
    }

    public int getNumRecords() {
        return numRecords;
    }
}
