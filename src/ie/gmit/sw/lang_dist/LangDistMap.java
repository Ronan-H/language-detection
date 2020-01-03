package ie.gmit.sw.lang_dist;

import ie.gmit.sw.Lang;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a store of language distribution objects using a Map.
 */
public class LangDistMap implements LangDistStore {
    private Map<Lang, LangDist> map;

    /**
     * Constructs a new language distribution map with a given hash range.
     * (the hash range is needed for the created language distribution objects, not this store)
     *
     * @param hashRange Hash range to use for this mapped store.
     */
    public LangDistMap(int hashRange) {
        map = new HashMap<>();

        Lang[] allLangs = Lang.values();
        for (Lang lang : allLangs) {
            // skip Unidentified language (used in LanguageDistribution when the language isn't specified)
            if (lang != Lang.Unidentified) {
                map.put(lang, new HashedLangDist(lang, hashRange));
            }
        }
    }

    @Override
    public LangDist getDistribution(Lang lang) {
        return map.get(lang);
    }

    @Override
    public Set<Lang> getKeySet() {
        return map.keySet();
    }
}
