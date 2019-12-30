package ie.gmit.sw;

import java.util.Set;

public interface LanguageDistributionStore {
    // TODO replace with iterator?
    LanguageDistribution getDistribution(Language lang);
    Set<Language> getKeySet();
}
