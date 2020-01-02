package ie.gmit.sw.lang_detector;

import ie.gmit.sw.Lang;
import ie.gmit.sw.lang_dist.LangDist;
import ie.gmit.sw.lang_dist.LangDistStore;

/**
 * Used to detect an unknown language based on a LangDistStore. Encapsulates a LangDetectorStrategy,
 * which defines the algorithm for how the language should be detected.
 */
public class LangDetector {
    private LangDetectorStrategy langDetectorStrategy;

    /**
     * Creates a LangDetector using an underlying LangDetectorStrategy.
     */
    public LangDetector(LangDetectorStrategy langDetectorStrategy) {
        this.langDetectorStrategy = langDetectorStrategy;
    }

    /**
     * Finds the closest language based on a k-mer distribution and a LangDistStore.
     */
    public Lang findClosestLanguage(LangDist unidentifiedLang, LangDistStore store) {
        return langDetectorStrategy.findClosestLanguage(unidentifiedLang, store);
    }
}
