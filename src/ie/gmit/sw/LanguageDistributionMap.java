package ie.gmit.sw;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LanguageDistributionMap implements LanguageDistributionStore {
    private Map<Language, LanguageDistribution> map;

    public LanguageDistributionMap(int hashRange) {
        map = new HashMap<>();

        Language[] allLangs = Language.values();
        for (Language lang : allLangs) {
            // skip Unidentified language (used in LanguageDistribution when the language isn't specified)
            if (lang != Language.Unidentified) {
                map.put(lang, new HashedLanguageDistribution(lang, hashRange));
            }
        }
    }

    public Language findClosestLanguage(LanguageDistribution unidentifiedLang) {
        // use frequency analysis to find the language that matches the closest
        double[] unidentifiedDist = unidentifiedLang.getDistribution();
        double lowestDist = Double.MAX_VALUE;
        Language bestFitLang = Language.Unidentified;

        Set<Language> keys = map.keySet();
        for (Language key : keys) {
            LanguageDistribution ref = getDistribution(key);
            double[] refDist = ref.getDistribution();
            double d = getDistance(unidentifiedDist, refDist);

            if (d < lowestDist) {
                lowestDist = d;
                bestFitLang = ref.getLanguage();
            }
        }

        return bestFitLang;
    }

    private double getDistance(double[] dist1, double[] dist2) {
        double totalDist = 0;

        for (int i = 0; i < dist1.length; i++) {
            totalDist += Math.abs(dist1[i] - dist2[i]);
        }

        return totalDist;
    }

    public LanguageDistribution getDistribution(Language lang) {
        return map.get(lang);
    }
}
