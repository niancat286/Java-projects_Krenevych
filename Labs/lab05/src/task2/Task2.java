package task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Task2 {


    public static boolean createFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        File file = new File(fileName);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        File file = new File(fileName);
        return file.delete();
    }

    
    public static boolean createFileNio(String fileName) {  
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        Path path = Paths.get(fileName);
        try {
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteFileNio(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        Path path = Paths.get(fileName);
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean fileExists(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        File file = new File(fileName);
        return file.exists();
    }

    public static boolean fileExistsNio(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        Path path = Paths.get(fileName);
        return Files.exists(path);
    }

    public static void writeFile(String fileName, String content) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        Path path = Paths.get(fileName);
        try {
            Files.writeString(path, content, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void replaceZeroWithOne(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        Path path = Paths.get(fileName);
        try {
            String content = Files.readString(path);
            content = content.replace('0', '1');
            Files.writeString(path, content, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void replaceOneWithZero(String fileInputName, String fileOutputName) {
        if (fileInputName == null || fileInputName.isEmpty()) {
            throw new IllegalArgumentException("Input file name cannot be null or empty");
        }
        if (fileOutputName == null || fileOutputName.isEmpty()) {
            throw new IllegalArgumentException("Output file name cannot be null or empty");
        }
        Path inputPath = Paths.get(fileInputName);
        Path outputPath = Paths.get(fileOutputName);
        try {
            if (!Files.exists(inputPath)) {
                throw new IllegalArgumentException("Input file does not exist");
            }
            if (!Files.exists(outputPath)) {
                throw new IllegalArgumentException("Output file does not exist");
            }
            String[] lines = Files.readAllLines(inputPath).toArray(new String[0]);
            for(String line : lines) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '1') {
                        sb.append('0');
                    }
                    else if (line.charAt(i) == '0') {
                        sb.append('1');
                    }
                    else {
                        sb.append(line.charAt(i));
                    }
                }
                sb.append(System.lineSeparator());
                Files.writeString(outputPath, sb.toString(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            }

            // content = content.replace('1', '0');
            // Files.writeString(path, content, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Task2 executed");
        writeFile("F.txt", "000101dfdsafg0011\n0101.");
        System.out.println("File exists (NIO): " + fileExistsNio("F.txt"));

        replaceOneWithZero("F.txt", "G.txt");

        deleteFile("F.txt");
        System.out.println("File exists after deletion (NIO): " + fileExistsNio("F.txt"));
    }
}
