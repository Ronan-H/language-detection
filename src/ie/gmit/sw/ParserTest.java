package ie.gmit.sw;

public class ParserTest {
    public static void main(String[] args) {
        Parser parser = new Parser("/home/ronan/Downloads/apache-tomcat-9.0.30/bin/data/wili-2018-Edited.txt");
        parser.parseFile();
    }
}
