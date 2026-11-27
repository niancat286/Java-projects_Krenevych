import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

enum Criterion{
    NAME,
    AVERAGE_MARK
}

class Student implements Serializable, Comparable<Student>{
    String name;
    int course;
    String group;
    ArrayList<Integer> subjects_marks;

    Student() {
    }
    Student(String name) {
        this.name = name;
    }
    Student(String name, int course, String group, ArrayList<Integer> subjects_marks) {
        this.name = name;
        this.course = course;
        this.group = group;
        this.subjects_marks = subjects_marks;
    }

    void printStudent() {
        System.out.println("Name: " + name);
        System.out.println("Course: " + course);
        System.out.println("Group: " + group);
        System.out.println("Subjects Marks: " + subjects_marks);
    }

    double getAverageMark() {
        if (subjects_marks == null || subjects_marks.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (int mark : subjects_marks) {
            sum += mark;
        }
        return (double) sum / subjects_marks.size();
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Course: " + course + ", Group: " + group + ", Subjects Marks: " + subjects_marks;
    }
    
    static Criterion criterion = Criterion.NAME;

    static void setCriterion(Criterion criterion) {
        Student.criterion = criterion;
    }

    @Override
    public int compareTo(Student other) {
        switch (criterion) {
            case NAME:
                return this.name.compareTo(other.name);
            case AVERAGE_MARK:
                return Double.compare(this.getAverageMark(), other.getAverageMark());
            default:
                return 0;
        }
    }

}

public class StudentLists {

    static final String fileName = "students.bin";

    List<Student> createStudentList() {
        // Implementation for creating a student list
        List<Student> students = new ArrayList<Student>();
        final int numberOfStudents = 5;
        for (int i = 0; i < numberOfStudents; i++) {
            ArrayList<Integer> subjects_marks = new ArrayList<Integer>();
            int k = (int)(Math.random() * 10);
            for (int j = 0; j < k; j++) {
                subjects_marks.add((int)(60 + Math.random() * 40));
            }
            
            students.add(new Student("Student " + (i + 1), 1, "A", subjects_marks));
        }

        return students;
    }

    boolean saveStudentList(List<Student> students, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    List<Student> loadStudentList(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<Student>();
        }
    }

    void printStudentList(List<Student> students) {
        for (Student student : students) {
            student.printStudent();
            System.out.println();
        }
    }

    public static void main(String[] args) {
        StudentLists studentLists = new StudentLists();
        List<Student> students = studentLists.createStudentList();
        studentLists.printStudentList(students);

        studentLists.saveStudentList(students, fileName);
        List<Student> loadedStudents = studentLists.loadStudentList(fileName);

        //llections.sort(loadedStudents, (s1, s2) -> s1.name.compareTo(s2.name));
        Collections.sort(loadedStudents);

        System.out.println("Sorted by name:");
        studentLists.printStudentList(loadedStudents);


        //llections.sort(loadedStudents, (s1, s2) -> Double.compare(s1.getAverageMark(), s2.getAverageMark()));

        // Collections.sort(loadedStudents, new Comparator<Student>() {
        //     @Override
        //     public int compare(Student s1, Student s2) {
        //         return Double.compare(s1.getAverageMark(), s2.getAverageMark());
        //     }
        // }  );

        Student.setCriterion(Criterion.AVERAGE_MARK);
        Collections.sort(loadedStudents);

        System.out.println("Sorted by average mark:");
        studentLists.printStudentList(loadedStudents);


    }

    
}
