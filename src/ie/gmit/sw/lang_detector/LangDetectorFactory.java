package ie.gmit.sw.lang_detector;

public class LangDetectorFactory {
    private static LangDetectorFactory instance;

    private LangDetectorFactory() {}

    public static LangDetectorFactory getInstance() {
        if (instance == null) {
            instance = new LangDetectorFactory();
        }

        return instance;
    }

    public LangDetector getSimpleLanguageDetector() {
        return new LangDetector(new SimpleLangDetectorStrategy());
    }
}
