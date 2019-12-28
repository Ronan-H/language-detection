package ie.gmit.sw;

import java.util.HashMap;
import java.util.Map;

public class HashDatabase {
    private Map<Language, HashedKmer> map;

    public HashDatabase(int hashRange) {
        map = new HashMap<>();

        Language[] allLangs = Language.values();
        for (Language lang : allLangs) {
            map.put(lang, new HashedKmer(lang, hashRange));
        }
    }


}
