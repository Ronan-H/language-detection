package ie.gmit.sw.lang_detector;

import ie.gmit.sw.Lang;
import ie.gmit.sw.lang_dist.LangDist;
import ie.gmit.sw.lang_dist.LangDistStore;

import java.util.*;

/**
 * Defines a strategy for identifying an unknown language by using an "out-of-place" metric.
 */
public class OutOfPlaceStrategy implements LangDetectorStrategy {
    /**
     * Finds the closest language to an unknown language, using the "out-of-place" metric.
     */
    public Lang findClosestLanguage(LangDist unidentifiedLang, LangDistStore store) {
        System.out.println("-- Distribution --");
        for (int i = 0; i < unidentifiedLang.getDistribution().length; i++) {
            System.out.println(i + ": " + unidentifiedLang.getDistribution()[i]);
        }

        Integer[] unidentifiedRanking = getLangRanking(unidentifiedLang);

        System.out.println("-- Unidentified ranking --");
        for (int i = 0; i < unidentifiedRanking.length; i++) {
            System.out.println(i + ": " + unidentifiedRanking[i]);
        }

        double smallestOopm = Double.MAX_VALUE;
        Lang closestLang = Lang.Unidentified;
        Set<Lang> keys = store.getKeySet();
        for (Lang key : keys) {
            Integer[] refRanking = getLangRanking(store.getDistribution(key));
            double oopm = getOutOfPlaceMetric(unidentifiedRanking, refRanking, 100);
            if (oopm < smallestOopm) {
                closestLang = key;
                smallestOopm = oopm;
            }
        }

        return closestLang;
    }

    /**
     * Converts an array of k-mer frequency values to an array that defines their
     * ranking. I.e.,
     *
     * Frequency values (k-mer -> frequency):
     * 0: 0.4
     * 1: 0.1
     * 2: 0.7
     *
     * Ranking (k-mer -> rank):
     * 0: 2
     * 1: 0
     * 2: 1
     */
    private Integer[] getLangRanking(LangDist langDist) {
        Double[] dist = Arrays.stream(langDist.getDistribution())
                .boxed()
                .toArray(Double[]::new);
        DoubleArrayIndexComparator comparator = new DoubleArrayIndexComparator(dist);
        Integer[] indices = comparator.getIndexArray();
        Arrays.sort(indices, comparator);
        Integer[] ranking = swapIndicesForValues(indices);
        return ranking;
    }

    /**
     * Inverts an Integer array so that indices and values are swapped.
     * (used as part of converting an array of k-mer frequencies to their corresponding ranking)
     */
    private Integer[] swapIndicesForValues(Integer[] arr) {
        Integer[] swapped = new Integer[arr.length];

        for (int i = 0; i < arr.length; i++) {
            swapped[arr[i]] = i;
        }

        return swapped;
    }

    /**
     * Calculates the out-of-place metric for two arrays of k-mer ranks.
     *
     * @param limit Excludes past this limit (if both lang A AND lang B's k-mer rank are below this)
     */
    private double getOutOfPlaceMetric(Integer[] langARanking, Integer[] langBRanking, int limit) {
        double totalDistance = 0;

        for (int i = 0; i < langARanking.length; i++) {
            int aRank = langARanking[i];
            int bRank = langBRanking[i];
            if (aRank <= limit || bRank <= limit) {
                totalDistance += Math.abs(aRank - bRank);
            }
        }

        return totalDistance;
    }
}

/**
 * Sorts an array of Integers (indexes) based on an array of doubles.
 *
 * Loosely based on https://stackoverflow.com/a/4859279
 */
class DoubleArrayIndexComparator implements Comparator<Integer> {
    private final Double[] array;

    /**
     * Creates a new comparator based on a Double array.
     */
    public DoubleArrayIndexComparator(Double[] array) {
        this.array = array;
    }

    /**
     * Generate an array of indexes (where index = value)
     */
    public Integer[] getIndexArray() {
        Integer[] indexes = new Integer[array.length];

        for (int i = 0; i < array.length; i++) {
            indexes[i] = i;
        }

        return indexes;
    }

    /**
     * Compares two Integer indexes, based on the underlying array of Double values.
     */
    @Override
    public int compare(Integer index1, Integer index2) {
        return - array[index1].compareTo(array[index2]);
    }
}