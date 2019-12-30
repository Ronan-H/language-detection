package ie.gmit.sw.lang_detector;

import ie.gmit.sw.Lang;
import ie.gmit.sw.lang_dist.LangDist;
import ie.gmit.sw.lang_dist.LangDistStore;

public class LangDetector {
    private LangDetectorStrategy langDetectorStrategy;

    public LangDetector(LangDetectorStrategy langDetectorStrategy) {
        this.langDetectorStrategy = langDetectorStrategy;
    }

    public Lang findClosestLanguage(LangDist unidentifiedLang, LangDistStore store) {
        return langDetectorStrategy.findClosestLanguage(unidentifiedLang, store);
    }
}
