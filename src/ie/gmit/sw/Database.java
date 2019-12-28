package ie.gmit.sw;

import java.util.*;

public class Database {
	private Map<Language, Map<Long, KmerFreq>> db = new TreeMap<>();

	public void add(short[] kmer, Language lang) {
		long key = 0;

		for (int i = 0; i  < kmer.length; i++) {
			key <<= 16;
			key |= kmer[i];
		}

		Map<Long, KmerFreq> langDb = getLanguageEntries(lang);

		int frequency = 1;
		if (langDb.containsKey(key)) {
			frequency += langDb.get(key).getFrequency();
		}
		langDb.put(key, new KmerFreq(key, frequency));
	}
	
	private Map<Long, KmerFreq> getLanguageEntries(Language lang){
		Map<Long, KmerFreq> langDb = null;
		if (db.containsKey(lang)) {
			langDb = db.get(lang);
		}else {
			langDb = new TreeMap<Long, KmerFreq>();
			db.put(lang, langDb);
		}
		return langDb;
	}
	
	public void resize(int max) {
		Set<Language> keys = db.keySet();
		for (Language lang : keys) {
			Map<Long, KmerFreq> top = getTop(max, lang);
			db.put(lang, top);
		}
	}
	
	public Map<Long, KmerFreq> getTop(int max, Language lang) {
		Map<Long, KmerFreq> temp = new TreeMap<>();
		List<KmerFreq> les = new ArrayList<>(db.get(lang).values());
		Collections.sort(les);
		
		int rank = 1;
		for (KmerFreq le : les) {
			le.setRank(rank);
			temp.put(le.getKmer(), le);			
			if (rank == max) break;
			rank++;
		}
		
		return temp;
	}
	
	public Language getLanguage(Map<Long, KmerFreq> query) {
		TreeSet<OutOfPlaceMetric> oopm = new TreeSet<>();
		
		Set<Language> langs = db.keySet();
		for (Language lang : langs) {
			oopm.add(new OutOfPlaceMetric(lang, getOutOfPlaceDistance(query, db.get(lang))));
		}
		return oopm.first().getLanguage();
	}
	
	private int getOutOfPlaceDistance(Map<Long, KmerFreq> query, Map<Long, KmerFreq> subject) {
		int distance = 0;
		
		Set<KmerFreq> les = new TreeSet<>(query.values());
		for (KmerFreq q : les) {
			KmerFreq s = subject.get(q.getKmer());
			if (s == null) {
				distance += subject.size() + 1;
			}else {
				distance += s.getRank() - q.getRank();
			}
		}
		return distance;
	}
	
	private class OutOfPlaceMetric implements Comparable<OutOfPlaceMetric>{
		private Language lang;
		private int distance;
		
		public OutOfPlaceMetric(Language lang, int distance) {
			super();
			this.lang = lang;
			this.distance = distance;
		}

		public Language getLanguage() {
			return lang;
		}

		public int getAbsoluteDistance() {
			return Math.abs(distance);
		}

		@Override
		public int compareTo(OutOfPlaceMetric o) {
			return Integer.compare(this.getAbsoluteDistance(), o.getAbsoluteDistance());
		}

		@Override
		public String toString() {
			return "[lang=" + lang + ", distance=" + getAbsoluteDistance() + "]";
		}
		
		
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		int langCount = 0;
		int kmerCount = 0;
		Set<Language> keys = db.keySet();
		for (Language lang : keys) {
			langCount++;
			sb.append(lang.name() + "->\n");
			 
			 Collection<KmerFreq> m = new TreeSet<>(db.get(lang).values());
			 kmerCount += m.size();
			 for (KmerFreq le : m) {
				 sb.append("\t" + le + "\n");
			 }
		}
		sb.append(kmerCount + " total k-mers in " + langCount + " languages"); 
		return sb.toString();
	}
}