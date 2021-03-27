package reghzy.dailylogreader;

import javax.swing.JFileChooser;
import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting...");

        try {
            JFileChooser inputFileChooser = new JFileChooser();
            inputFileChooser.setPreferredSize(new Dimension(1280, 720));
            inputFileChooser.setMultiSelectionEnabled(true);
            if (inputFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File[] inputFiles = inputFileChooser.getSelectedFiles();
                if (inputFiles == null)
                    return;
                if (inputFiles.length == 0) {
                    System.out.println("You havent provided any files.... hmm");
                    return;
                }

                for(File inputFile : inputFiles) {
                    System.out.println("Processing: " + inputFile.getAbsolutePath());
                    File outputFile = new File(inputFile.getParent(), "processed_" + inputFile.getName());
                    if (!outputFile.createNewFile()) {
                        outputFile.delete();
                        if (!outputFile.createNewFile()) {
                            System.out.println("Failed to create output log file");
                            return;
                        }
                    }
                    System.out.println("Created output log file at " + outputFile.getAbsolutePath());
                    System.out.println("Processing input log file...");

                    FileWriter writer = new FileWriter(outputFile);
                    for (String line : FileHelper.readAllLines(inputFile)) {
                        writer.write(removeColours(line) + '\n');
                    }
                    writer.close();

                    System.out.println("Finished!");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error has occoured... rip");
        }

        System.out.println("Exiting...");
    }

    public static String removeColours(String str) {
        if (str == null || str.isEmpty())
            return "";
        String string = str;
        while (true) {
            int colourStart = string.indexOf("\u001B[");
            if (colourStart == -1)
                return string;
            int colourEnd = string.indexOf("m", colourStart + 2);
            if (colourEnd == -1)
                return string;
            String colour = string.substring(colourStart, colourEnd + 1);
            string = string.replace(colour, "");
        }
    }
}
