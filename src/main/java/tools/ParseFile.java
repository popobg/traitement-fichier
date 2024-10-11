package tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ParseFile {
    public static List<String> parseFile(Path path) throws IOException  {
        List<String> listelignes = new ArrayList<>();

        listelignes = Files.readAllLines(path);


        return listelignes;
    }
}
