package serialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;   

public class SerializationTask {
    public static void main(String[] args) {
        // Example serialization and deserialization code can be added here
        Institute institute = new Institute("My Institute");
        Faculty faculty1 = new Faculty("Mexmat");
        Faculty faculty2 = new Faculty("Cybernetics");
        Faculty faculty3 = new Faculty("Mathematics");
        institute.addFaculty(faculty1);
        institute.addFaculty(faculty2);
        institute.addFaculty(faculty3);

        // Print the faculty members of the institute
        for (Faculty faculty : institute.getFaculty()) {
            System.out.println(faculty.getName());
        }
        System.out.println("Total faculty members: " + institute.getFaculty().length);

        // Serialize the institute object   
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("institute.ser"))) {
            oos.writeObject(institute);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Deserialization can be added here if needed
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("institute.ser"))) { 
            Institute deserializedInstitute = (Institute) ois.readObject();
            for (Faculty faculty : deserializedInstitute.getFaculty()) {
                System.out.println(faculty.getName());
            }
            System.out.println("Total faculty members: " + deserializedInstitute.getFaculty().length);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
