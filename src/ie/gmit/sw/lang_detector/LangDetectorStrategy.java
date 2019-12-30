package ie.gmit.sw.lang_detector;

import ie.gmit.sw.Lang;
import ie.gmit.sw.lang_dist.LangDist;
import ie.gmit.sw.lang_dist.LangDistStore;

public interface LangDetectorStrategy {
    Lang findClosestLanguage(LangDist unidentifiedLang, LangDistStore store);
}
