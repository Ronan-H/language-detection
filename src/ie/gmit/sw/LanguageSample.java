package ie.gmit.sw;

public class LanguageSample {
    private Language language;
    private String sample;

    public LanguageSample(Language language, String sample) {
        this.language = language;
        this.sample = sample;
    }

    public LanguageSample(String language, String sample) {
        this(Language.valueOf(language), sample);
    }

    public LanguageSample() {
        language = Language.Unidentified;
        sample = "";
    }

    public Language getLanguage() {
        return language;
    }

    public String getSample() {
        return sample;
    }
}

class Poison extends LanguageSample {}
