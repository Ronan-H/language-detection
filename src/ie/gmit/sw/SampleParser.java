package ie.gmit.sw;

public abstract class SampleParser {
    private LanguageDistributionStore store;

    public SampleParser(LanguageDistributionStore store) {
        this.store = store;
    }

    public abstract void parseAll();

    public void parseSample(String lang, String text, int k) {
        Language language = Language.valueOf(lang);
        store.getDistribution(language).recordSample(text, k);
    }
}
