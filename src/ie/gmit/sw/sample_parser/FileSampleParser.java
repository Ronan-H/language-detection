package ie.gmit.sw.sample_parser;

import ie.gmit.sw.lang_dist.LangDistStore;

import java.io.*;

public class FileSampleParser extends SampleParser {
    private String filePath;

    public FileSampleParser(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void parseAll(LangDistStore store) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = in.readLine()) != null) {
                String[] parts = line.trim().split("@");
                if (parts.length == 2) {
                    parseSample(parts[1], parts[0], 3, store);
                }
            }

            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
