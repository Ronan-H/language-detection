package ie.gmit.sw;

import java.io.*;

public class FileSampleParser extends SampleParser {
    private String filePath;

    public FileSampleParser(String filePath, LanguageDistributionStore store) {
        super(store);
        this.filePath = filePath;
    }

    @Override
    public void parseAll() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = in.readLine()) != null) {
                String[] parts = line.trim().split("@");
                if (parts.length == 2) {
                    parseSample(parts[1], parts[0], 3);
                }
            }

            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
