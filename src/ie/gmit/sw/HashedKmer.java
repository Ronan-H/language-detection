package ie.gmit.sw;

public class HashedKmer {
    private int hashRange;
    private int[] freqs;
    private Language language;

    public HashedKmer(Language language, int hashRange) {
        this.hashRange = hashRange;
        freqs = new int[hashRange];
    }

    public void recordKmer(short[] kmer) {
        // hash implementation from https://stackoverflow.com/a/11742634
        int hash = 17;

        for (short c : kmer) {
            hash = hash * 31 + c;
        }

        int index = hash % hashRange;
        freqs[index] += 1;
    }
}
