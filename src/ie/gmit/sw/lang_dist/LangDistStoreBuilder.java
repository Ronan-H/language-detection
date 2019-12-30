package ie.gmit.sw.lang_dist;

import ie.gmit.sw.sample_parser.SampleParser;

import java.util.ArrayList;
import java.util.List;

public class LangDistStoreBuilder {
    private List<SampleParser> parsers;
    private LangDistStore store;

    public LangDistStoreBuilder() {
        parsers = new ArrayList<>();
    }

    public LangDistStoreBuilder withMappedStore(int hashRange) {
        store = new LangDistMap(hashRange);

        return this;
    }

    public LangDistStoreBuilder registerParser(SampleParser parser) {
        parsers.add(parser);

        return this;
    }

    public LangDistStore build() {
        for (SampleParser parser : parsers) {
            parser.parseAll(store);
        }

        return store;
    }
}
