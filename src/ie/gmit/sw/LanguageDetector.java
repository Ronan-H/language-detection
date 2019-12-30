package ie.gmit.sw;

public class LanguageDetector {
    private LanguageDetectorStrategy languageDetectorStrategy;

    public LanguageDetector(LanguageDetectorStrategy languageDetectorStrategy) {
        this.languageDetectorStrategy = languageDetectorStrategy;
    }

    public Language findClosestLanguage(LanguageDistribution unidentifiedLang, LanguageDistributionStore store) {
        return languageDetectorStrategy.findClosestLanguage(unidentifiedLang, store);
    }
}
