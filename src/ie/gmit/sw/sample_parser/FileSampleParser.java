package ie.gmit.sw.sample_parser;

import ie.gmit.sw.lang_dist.LangDistStore;

import java.io.*;

/**
 * Allows the parsing of language sample lines from a file into a language distribution store.
 */
public class FileSampleParser extends SampleParser {
    private String filePath;

    /**
     * Creates a new file parser at the given path.
     *
     * @param filePath Path of file to parse.
     */
    public FileSampleParser(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Parses all lines of the file into a given language distribution store.
     * Expects each line to have a language sample, followed by an "@" symbol,
     * followed by the language of the sample.
     *
     * @param store Store to record the resulting k-mers into.
     */
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
