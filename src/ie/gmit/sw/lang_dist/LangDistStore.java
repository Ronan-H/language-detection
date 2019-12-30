package ie.gmit.sw.lang_dist;

import ie.gmit.sw.Lang;

import java.util.Set;

public interface LangDistStore {
    // TODO replace with iterator?
    LangDist getDistribution(Lang lang);
    Set<Lang> getKeySet();
}
