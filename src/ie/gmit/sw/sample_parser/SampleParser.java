package ie.gmit.sw.sample_parser;

import ie.gmit.sw.Lang;
import ie.gmit.sw.lang_dist.LangDistStore;

public abstract class SampleParser {
    public abstract void parseAll(LangDistStore store);

    public void parseSample(String lang, String text, int k, LangDistStore store) {
        Lang language = Lang.valueOf(lang);
        store.getDistribution(language).recordSample(text, k);
    }
}
