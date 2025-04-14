import java.sql.*;
import java.util.ArrayList;

public class SMS {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: MySQL Driver not found.");
            return;
        }
        String url = "jdbc:mysql://localhost:3306/sms2";
        String user = "root";
        String password = "";
        
        Reports("enrollment", url, user, password);   
    }
    
    public static void Reports(String reportName, String url, String user, String password) {
        ArrayList<Student> students = new ArrayList<>();
        
        if (reportName.toLowerCase().equals("enrollment")) {
            String sqlcommand = "SELECT student_id, firstname, lastname, classify, major " + 
                                "FROM students " +
                                "ORDER BY student_id; ";
                                
            try (Connection myConn = DriverManager.getConnection(url, user, password)) {
                Statement myStmt = myConn.createStatement();
                ResultSet myRS = myStmt.executeQuery(sqlcommand);
                
                while (myRS.next()) {
                    Student temp = new Student();
                    temp.setStudent_id(myRS.getInt("student_id"));
                    temp.setFname(myRS.getString("firstname"));
                    temp.setLname(myRS.getString("lastname"));
                    temp.setClassify(myRS.getString("classify"));
                    temp.setMajor(myRS.getString("major"));
                    temp.setEnrolled_courses(); 
                    students.add(temp);
                }
                myConn.close();
            } catch (Exception e) {
                System.out.println("ERROR " + e.getLocalizedMessage());
            }
            
            PrintEnrollments(students); 
        } else {
            System.out.println("\n\n****ERROR**** Invalid report requested\n\n");
        }
    }
    

    public static void PrintEnrollments(ArrayList<Student> myStudents) {
        System.out.println("\n\n\nStudent Enrollment Report by Fuhad Bailey\n");
        System.out.printf("%-4s %-10s %-8s %-22s %s\n", 
                          "ID", "CLASS", "MAJOR", "NAME", "Enrollments");
    
        for (Student s : myStudents) {
            
            System.out.printf("%-4d %-10s %-8s %-22s\n",
                    s.getStudent_id(),
                    s.getClassify(),
                    s.getMajor(),
                    s.getFname() + " " + s.getLname());
    
                    
            int count = 1;
            for (Enrollments e : s.getEnrolled_courses()) {
                System.out.printf("%45s%d) %s-%d, HRS:%d, %s\n", 
                        "", 
                        count++, 
                        e.getDeptID(), 
                        e.getCourseID(), 
                        e.getCourseHours(), 
                        e.getCourseName());
            }
    
            
            System.out.println("     ================================================================");
        }
    }
    
}