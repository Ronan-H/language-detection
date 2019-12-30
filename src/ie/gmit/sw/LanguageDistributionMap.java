package ie.gmit.sw;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LanguageDistributionMap implements LanguageDistributionStore {
    private Map<Language, LanguageDistribution> map;

    public LanguageDistributionMap(int hashRange) {
        map = new HashMap<>();

        Language[] allLangs = Language.values();
        for (Language lang : allLangs) {
            // skip Unidentified language (used in LanguageDistribution when the language isn't specified)
            if (lang != Language.Unidentified) {
                map.put(lang, new HashedLanguageDistribution(lang, hashRange));
            }
        }
    }

    public LanguageDistribution getDistribution(Language lang) {
        return map.get(lang);
    }

    @Override
    public Set<Language> getKeySet() {
        return map.keySet();
    }
}
