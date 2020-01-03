package ie.gmit.sw.lang_detector;

import ie.gmit.sw.Lang;
import ie.gmit.sw.lang_dist.LangDist;
import ie.gmit.sw.lang_dist.LangDistStore;

import java.util.Set;

/**
 * A simple strategy for detecting an unknown language: finds the language in a LangDistStore which has
 * the smallest total difference for each k-mer frequency value.
 */
public class DistanceStrategy implements LangDetectorStrategy {
    /**
     * Performs this strategy on an unidentified language distribution and a store of known language k-mers.
     *
     * @param unidentifiedLang K-mer distribution of the unidentified language sample.
     * @param store K-mer distribution store for all known languages
     * @return Closest known language using this strategy.
     */
    @Override
    public Lang findClosestLanguage(LangDist unidentifiedLang, LangDistStore store) {
        // use frequency analysis to find the language that matches the closest
        double[] unidentifiedDist = unidentifiedLang.getFrequencies();
        double lowestDist = Double.MAX_VALUE;
        Lang bestFitLang = Lang.Unidentified;

        Set<Lang> keys = store.getKeySet();
        for (Lang key : keys) {
            LangDist ref = store.getDistribution(key);
            double[] refDist = ref.getFrequencies();
            double d = getDistance(unidentifiedDist, refDist);

            if (d < lowestDist) {
                lowestDist = d;
                bestFitLang = ref.getLang();
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
