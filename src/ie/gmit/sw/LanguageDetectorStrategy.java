package ie.gmit.sw;

public interface LanguageDetectorStrategy {
    Language findClosestLanguage(LanguageDistribution unidentifiedLang, LanguageDistributionStore store);
}
