package ie.gmit.sw;

import java.io.*;

public class Parser {
    private String filePath;
    private Database db;

    public Parser(String filePath) {
        this.filePath = filePath;
        db = new Database();
    }

    public void parseFile() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = in.readLine()) != null) {
                String[] parts = line.trim().split("@");
                if (parts.length == 2) {
                    parseLine(parts[1], parts[0], 3);
                }
            }

            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseLine(String lang, String text, int k) {
        Language language = Language.valueOf(lang);

        for (int i = 0; i <= text.length() - k; i++) {
            CharSequence kmer = text.substring(i, i + k);
            db.add(kmer, language);
        }
    }
}
