package ie.gmit.sw;

import java.util.Set;

public class LanguageDetector {
    private LanguageDistributionStore store;

    public LanguageDetector(LanguageDistributionStore store) {
        this.store = store;
    }

    public Language findClosestLanguage(LanguageDistribution unidentifiedLang) {
        // use frequency analysis to find the language that matches the closest
        double[] unidentifiedDist = unidentifiedLang.getDistribution();
        double lowestDist = Double.MAX_VALUE;
        Language bestFitLang = Language.Unidentified;

        Set<Language> keys = store.getKeySet();
        for (Language key : keys) {
            LanguageDistribution ref = store.getDistribution(key);
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
}
