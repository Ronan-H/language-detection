package ie.gmit.sw;

public class KmerFreq implements Comparable<KmerFreq> {
	private long kmer;
	private int frequency;
	private int rank;

	public KmerFreq(long kmer, int frequency) {
		super();
		this.kmer = kmer;
		this.frequency = frequency;
	}

	public long getKmer() {
		return kmer;
	}

	public void setKmer(long kmer) {
		this.kmer = kmer;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public int compareTo(KmerFreq next) {
		return - Integer.compare(frequency, next.getFrequency());
	}
	
	@Override
	public String toString() {
		return "[" + kmer + "/" + frequency + "/" + rank + "]";
	}
}