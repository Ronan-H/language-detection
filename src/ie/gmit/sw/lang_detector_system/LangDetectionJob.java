package ie.gmit.sw.lang_detector_system;

import ie.gmit.sw.Lang;

public class LangDetectionJob {
    private String id;
    private String sampleText;
    private Lang result;

    public LangDetectionJob(String id, String sampleText) {
        this.id = id;
        this.sampleText = sampleText;
    }

    public String getId() {
        return id;
    }

    public String getSampleText() {
        return sampleText;
    }

    public Lang getResult() {
        return result;
    }

    public void setResult(Lang result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object otherObj) {
        // (this covers otherObj == null)
        if (!(otherObj instanceof LangDetectionJob)) {
            return false;
        }

        LangDetectionJob otherJob = (LangDetectionJob) otherObj;

        return id.equals(otherJob.getId());
    }
}
