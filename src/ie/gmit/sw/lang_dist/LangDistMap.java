package ie.gmit.sw.lang_dist;

import ie.gmit.sw.Lang;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LangDistMap implements LangDistStore {
    private Map<Lang, LangDist> map;

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

    public LangDist getDistribution(Lang lang) {
        return map.get(lang);
    }

    @Override
    public Set<Lang> getKeySet() {
        return map.keySet();
    }
}
