package ie.gmit.sw.lang_dist;

import ie.gmit.sw.Lang;

import java.util.Set;

/**
 * Represents a store of language distributions (LangDist objects) which are accessible
 * through a set of Lang keys.
 */
public interface LangDistStore {
    /**
     * Finds the k-mer distribution belonging to a given language
     */
    LangDist getDistribution(Lang lang);

    /**
     * Retrieves the set of language keys
     */
    Set<Lang> getKeySet();
}
