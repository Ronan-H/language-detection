package ie.gmit.sw.lang_detector;

import ie.gmit.sw.Lang;
import ie.gmit.sw.lang_dist.LangDist;
import ie.gmit.sw.lang_dist.LangDistStore;

import java.util.*;

public class OutOfPlaceStrategy implements LangDetectorStrategy {
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
        Map<Lang, Map<Integer, Double>> map = new TreeMap<>();
        Set<Lang> keys = store.getKeySet();
        for (Lang key : keys) {
            Integer[] refRanking = getLangRanking(store.getDistribution(key));
            double oopm = getOutOfPlaceMetric(unidentifiedRanking, refRanking);
            if (oopm < smallestOopm) {
                closestLang = key;
                smallestOopm = oopm;
            }
        }

        return closestLang;
    }

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

    private Integer[] swapIndicesForValues(Integer[] arr) {
        Integer[] swapped = new Integer[arr.length];

        for (int i = 0; i < arr.length; i++) {
            swapped[arr[i]] = i;
        }

        return swapped;
    }

    private double getOutOfPlaceMetric(Integer[] langARanking, Integer[] langBRanking) {
        double totalDistance = 0;

        for (int i = 0; i < langARanking.length; i++) {
            totalDistance += Math.abs(langARanking[i] - langBRanking[i]);
        }

        return totalDistance;
    }
}

// loosely based on https://stackoverflow.com/a/4859279
class DoubleArrayIndexComparator implements Comparator<Integer> {
    private final Double[] array;

    public DoubleArrayIndexComparator(Double[] array) {
        this.array = array;
    }

    public Integer[] getIndexArray() {
        Integer[] indexes = new Integer[array.length];

        for (int i = 0; i < array.length; i++) {
            indexes[i] = i;
        }

        return indexes;
    }

    @Override
    public int compare(Integer index1, Integer index2) {
        return - array[index1].compareTo(array[index2]);
    }
}