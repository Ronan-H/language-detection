package ie.gmit.sw.lang_detector_system;

import ie.gmit.sw.lang_dist.LangDistStore;
import ie.gmit.sw.lang_dist.LangDistStoreBuilder;
import ie.gmit.sw.sample_parser.FileSampleParser;

public class LangDetectionSystemFactory {
    private static LangDetectionSystemFactory instance;

    private LangDetectionSystemFactory() {}

    public static LangDetectionSystemFactory getInstance() {
        if (instance == null) {
            instance = new LangDetectionSystemFactory();
        }

        return instance;
    }

    public LangDetectionSystem getStandardLangDetectionSystem() {
        // build kmer distribution for all languages from language dataset
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
