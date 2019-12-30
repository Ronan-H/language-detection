package ie.gmit.sw;

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
        if (otherObj == null || !(otherObj instanceof LangDetectionJob)) {
            return false;
        }

        LangDetectionJob otherJob = (LangDetectionJob) otherObj;

        return id.equals(otherJob.getId());
    }
}
