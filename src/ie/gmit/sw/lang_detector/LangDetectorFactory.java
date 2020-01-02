package ie.gmit.sw.lang_detector;

/**
 * Responsible for creating different types of LangDetector. Hides the complexity of creating these objects.
 */
public class LangDetectorFactory {
    private static LangDetectorFactory instance;

    private LangDetectorFactory() {}

    public static LangDetectorFactory getInstance() {
        if (instance == null) {
            instance = new LangDetectorFactory();
        }

        return instance;
    }

    public LangDetector getOutOfPlaceLanguageDetector() {
        return new LangDetector(new OutOfPlaceStrategy());
    }

    public LangDetector getSmallestDistanceLanguageDetector() {
        return new LangDetector(new DistanceStrategy());
    }
}
