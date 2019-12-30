package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;

public class LanguageDistributionStoreBuilder {
    private List<SampleParser> parsers;
    private LanguageDistributionStore store;

    public LanguageDistributionStoreBuilder() {
        parsers = new ArrayList<>();
    }

    public LanguageDistributionStoreBuilder withMappedStore(int hashRange) {
        store = new LanguageDistributionMap(hashRange);

        return this;
    }

    public LanguageDistributionStoreBuilder registerParser(SampleParser parser) {
        parsers.add(parser);

        return this;
    }

    public LanguageDistributionStore build() {
        for (SampleParser parser : parsers) {
            parser.parseAll(store);
        }

        return store;
    }
}
