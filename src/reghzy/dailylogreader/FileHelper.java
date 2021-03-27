package reghzy.dailylogreader;

import java.io.*;
import java.util.ArrayList;

public class FileHelper {
    public static String[] readAllLines(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<String> array = new ArrayList<String>(32);
        String line = reader.readLine();
        while (line != null) {
            array.add(line);
            line = reader.readLine();
        }
        return array.toArray(new String[32]);
    }
}
