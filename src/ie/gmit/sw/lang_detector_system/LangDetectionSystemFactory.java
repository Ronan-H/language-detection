package ie.gmit.sw.lang_detector_system;

import ie.gmit.sw.lang_dist.LangDistStore;
import ie.gmit.sw.lang_dist.LangDistStoreBuilder;
import ie.gmit.sw.sample_parser.FileSampleParser;

/**
 * Responsible for creating language detection systems. These have a lot of different parameters
 * and steps for building them, so it's useful to have this hidden away from the programmer.
 */
public class LangDetectionSystemFactory {
    private static LangDetectionSystemFactory instance;

    private LangDetectionSystemFactory() {}

    public static LangDetectionSystemFactory getInstance() {
        if (instance == null) {
            instance = new LangDetectionSystemFactory();
        }

        return instance;
    }

    /**
     * Builds a standard language detection system with using appropriate parameters. Relies on the existence of
     * a data set file of language samples at a specific path.
     *
     * File: LANGUAGE_DATA_SET from web.xml
     * Mapped store k-mer hash range: 512 values
     * In-queue size: 50
     * Number of asynchronous workers: 4
     *
     * @return Created language detection system.
     */
    public LangDetectionSystem getStandardLangDetectionSystem() {
        // build k-mer distribution for all languages from language dataset
        LangDistStore distStore = new LangDistStoreBuilder()
            .withMappedStore(512)
            .registerParser(
                // TODO replace with languageDataSet file path
                new FileSampleParser("/home/ronan/Downloads/apache-tomcat-9.0.30/bin/data/wili-2018-Edited.txt")
            )
        .build();

        return new LangDetectionSystem(distStore, 50, 4);
    }
}
