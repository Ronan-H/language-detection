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

    /**
     * Creates a language detector that uses the out-of-place metric for comparing language distributions.
     *
     * @return Created language detector.
     */
    public LangDetector getOutOfPlaceLanguageDetector() {
        return new LangDetector(new OutOfPlaceStrategy());
    }

    /**
     * Creates a language detector that compares language distributions based on the total distance between all
     * k-mer frequency values.
     *
     * @return Created language detector.
     */
    public LangDetector getSmallestDistanceLanguageDetector() {
        return new LangDetector(new DistanceStrategy());
    }
}
