package task1;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Task1 {


    public static int countWords(String fileName) throws IOException {
        // Implement the logic to count words in the file
        if (fileName == null || fileName.isEmpty()) {
            return 0;
        }
        int wordCount = 0; // Initialize the word count variable

        try (FileReader fileReader = new FileReader(fileName); // Create a FileReader for the specified file
             BufferedReader bufferedReader = new BufferedReader(fileReader)) { // Create a BufferedReader for the FileReader
        
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] words = line.trim().split("\\s+"); // Split the line into words based on whitespace
            wordCount += words.length; // Increment the word count by the number of words in the line
        }
        return wordCount;
        } // End of try-with-resources block
    }

    public static void cleanFile(String fileName, String outputFileName) throws IOException {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Input file name cannot be null or empty");
        }

        if (outputFileName == null || outputFileName.isEmpty()) {
            throw new IllegalArgumentException("Output file name cannot be null or empty");
        }

        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName))
    ) {
   
             String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] words = line.trim().split("\\s+"); // Split the line into words based on whitespace
            if (words.length > 0) {
                bufferedWriter.write(String.join(" ", words));
                bufferedWriter.newLine();
            }
        }

        bufferedWriter.flush(); // Ensure all data is written to the output file
    }
}


    public static void main(String[] args) {
        System.out.println("Task1 executed");
        try {
            int wordCount = countWords("input.txt"); // Replace "example.txt" with your file name
            System.out.println("Word count: " + wordCount);
            cleanFile("input.txt", "output.txt"); // Replace with your input and output file names
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

    }

}
