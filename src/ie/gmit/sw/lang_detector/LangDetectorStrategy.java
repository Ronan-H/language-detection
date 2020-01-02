package ie.gmit.sw.lang_detector;

import ie.gmit.sw.Lang;
import ie.gmit.sw.lang_dist.LangDist;
import ie.gmit.sw.lang_dist.LangDistStore;

public interface LangDetectorStrategy {
    /**
     * Find the closest language based on a k-mer distribution and a LangDistStore.
     */
    Lang findClosestLanguage(LangDist unidentifiedLang, LangDistStore store);
}
