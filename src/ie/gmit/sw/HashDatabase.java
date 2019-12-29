package ie.gmit.sw;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashDatabase {
    private int hashRange;
    private Map<Language, HashedKmer> map;

    public HashDatabase(int hashRange) {
        this.hashRange = hashRange;
        map = new HashMap<>();

        Language[] allLangs = Language.values();
        for (Language lang : allLangs) {
            map.put(lang, new HashedKmer(lang, hashRange));
        }
    }

    public Language findClosestLanguage(HashedKmer unknownKmer) {
        // use frequency analysis
        double[] unknownDist = unknownKmer.getDistribution();
        double lowestDist = Double.MAX_VALUE;
        Language bestFitLang = Language.Unidentified;

        Set<Language> keys = map.keySet();
        for (Language key : keys) {
            HashedKmer ref = map.get(key);
            double[] refDist = ref.getDistribution();
            double d = getDistance(unknownDist, refDist);

            if (d < lowestDist) {
                lowestDist = d;
                bestFitLang = ref.getLanguage();
            }
        }

        return bestFitLang;
    }

    private double getDistance(double[] dist1, double[] dist2) {
        double totalDist = 0;

        for (int i = 0; i < hashRange; i++) {
            totalDist += Math.abs(dist1[i] - dist2[i]);
        }

        return totalDist;
    }

    public HashedKmer getHashedKmer(Language lang) {
        return map.get(lang);
    }

    public void recordKmer(Language lang, short[] kmer) {
        map.get(lang).recordKmer(kmer);
    }
}
