package ie.gmit.sw;

public class LanguageDetectorFactory {
    private static LanguageDetectorFactory instance;

    private LanguageDetectorFactory() {}

    public static LanguageDetectorFactory getInstance() {
        if (instance == null) {
            instance = new LanguageDetectorFactory();
        }

        return instance;
    }

    public LanguageDetector getSimpleLanguageDetector() {
        return new LanguageDetector(new SimpleLanguageDetectorStrategy());
    }
}
