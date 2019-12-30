package ie.gmit.sw;

public abstract class SampleParser {
    public abstract void parseAll(LanguageDistributionStore store);

    public void parseSample(String lang, String text, int k, LanguageDistributionStore store) {
        Language language = Language.valueOf(lang);
        store.getDistribution(language).recordSample(text, k);
    }
}
