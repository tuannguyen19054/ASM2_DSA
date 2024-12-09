import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

class Student {

    private String studentId;
    private String name;
    private double marks;

    public Student(String studentId, String name, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.marks = marks;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getRank() {
        if (marks >= 0 && marks < 5.0) {
            return "Fail";
        } else if (marks >= 5.0 && marks < 6.5) {
            return "Medium";
        } else if (marks >= 6.5 && marks < 7.5) {
            return "Good";
        } else if (marks >= 7.5 && marks < 9.0) {
            return "Very Good";
        } else if (marks >= 9.0 && marks <= 10.0) {
            return "Excellent";
        } else {
            return "Invalid Marks";
        }
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + ", Name: " + name + ", Marks: " + marks + ", Rank: " + getRank();
    }
}

class StudentManagementSystem {

    private List<Student> students;

    public StudentManagementSystem() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        for (Student s : students) {
            if (s.getStudentId().equals(student.getStudentId())) {
                System.out.println("Error: Student ID already exists.");
                return;
            }
        }
        students.add(student);
        System.out.println("Added: " + student);
    }

    public void editStudent(String studentId, String newName, double newMarks) {
        Optional<Student> optionalStudent = searchStudent(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setName(newName);
            student.setMarks(newMarks);
            System.out.println("Updated: " + student);
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }

    public void deleteStudent(String studentId) {
        boolean removed = students.removeIf(student -> student.getStudentId().equals(studentId));
        if (removed) {
            System.out.println("Deleted student with ID: " + studentId);
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }

    public Optional<Student> searchStudent(String studentId) {
        return students.stream().filter(student -> student.getStudentId().equals(studentId)).findFirst();
    }

    public void BubbleSortStudents() {
        //students.sort(Comparator.comparingDouble(Student::getMarks).reversed());
        //System.out.println("Students sorted by marks in descending order");
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (students.get(j).getMarks() > students.get(j + 1).getMarks()) {
                    // Đổi chỗ hai sinh viên
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));// Đặt sinh viên ở vị trí j+1 vào vị trí j
                    students.set(j + 1, temp);// Đặt sinh viên trong temp vào vị trí j+1
                }
            }
        }  
        //System.out.println("List of students after sorting in ascending order by score:");
        displayStudents();
    }
    public void SelectionSortStudents() {
    int n = students.size();
    for (int i = 0; i < n - 1; i++) {
        // Thiết lập vị trí của sinh viên có điểm nhỏ nhất là i
        int indexMin = i;

        // Tìm vị trí của sinh viên có điểm nhỏ nhất trong phần còn lại của danh sách
        for (int j = i + 1; j < n; j++) {
            if (students.get(j).getMarks() < students.get(indexMin).getMarks()) {
                indexMin = j;
            }
        }

        // Đổi chỗ sinh viên tại i với sinh viên có điểm nhỏ nhất tìm được
        if (indexMin != i) {
            Student temp = students.get(i);
            students.set(i, students.get(indexMin));
            students.set(indexMin, temp);
        }
    }

    System.out.println("Danh sách sinh viên sau khi sắp xếp tăng dần theo điểm:");
    displayStudents();
}

    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
            return;
        }
        System.out.println("Student List:");
        for (Student student : students) {
            System.out.println(student);
        }
    }
}

class Main {

    private static StudentManagementSystem sms = new StudentManagementSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            showMenu();
            int choice = getChoice();
            switch (choice) {
                case 1:
                    addStudentUI();
                    break;
                case 2:
                    editStudentUI();
                    break;
                case 3:
                    deleteStudentUI();
                    break;
                case 4:
                    sms.SelectionSortStudents();
                    break;
                case 5:
                    searchStudentUI();
                    break;
                case 6:
                    sms.displayStudents();
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n=== Student Management System ===");
        System.out.println("1. Add Student");
        System.out.println("2. Edit Student");
        System.out.println("3. Delete Student");
        System.out.println("4. BubbleSortStudents Students by Marks");
        System.out.println("5. SelectionSortStudents Students by Marks");
        System.out.println("6. Search Student by ID");
        System.out.println("7. Display All Students");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            // Invalid input, will be handled in switch-case
        }
        return choice;
    }

    private static void addStudentUI() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine().trim();
        double marks = -1;
        while (true) {
            System.out.print("Enter Student Marks (0 - 10): ");
            try {
                marks = Double.parseDouble(scanner.nextLine());
                if (marks < 0 || marks > 10) {
                    System.out.println("Marks must be between 0 and 10. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for marks. Please enter a number.");
            }
        }
        Student student = new Student(id, name, marks);
        sms.addStudent(student);
    }

    private static void editStudentUI() {
        System.out.print("Enter Student ID to edit: ");
        String id = scanner.nextLine().trim();
        Optional<Student> optionalStudent = sms.searchStudent(id);
        if (optionalStudent.isPresent()) {
            System.out.print("Enter new name (current: " + optionalStudent.get().getName() + "): ");
            String newName = scanner.nextLine().trim();
            double newMarks = -1;
            while (true) {
                System.out.print("Enter new marks (0 - 10) (current: " + optionalStudent.get().getMarks() + "): ");
                try {
                    newMarks = Double.parseDouble(scanner.nextLine());
                    if (newMarks < 0 || newMarks > 10) {
                        System.out.println("Marks must be between 0 and 10. Please try again.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for marks. Please enter a number.");
                }
            }
            sms.editStudent(id, newName, newMarks);
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    private static void deleteStudentUI() {
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine().trim();
        sms.deleteStudent(id);
    }

    private static void searchStudentUI() {
        System.out.print("Enter Student ID to search: ");
        String id = scanner.nextLine().trim();
        Optional<Student> optionalStudent = sms.searchStudent(id);
        if (optionalStudent.isPresent()) {
            System.out.println("Found student: " + optionalStudent.get());
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }
}
