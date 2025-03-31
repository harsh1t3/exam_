package SignUp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Student
{
   int sid;
   String first_name;
   String last_name;
   String dob;
   String gender;
   String address;
   String pincode;
   String phonenumber;
   String email;
   String institute_name;
   String branch;
   String semester;
   String year;
   String scholar_no;
   String password;
   String recovery_question;
   String answer;
   byte [] image;
}
class UseStudent
{
    Connection conn=null;
     public void connect()
     {
        try
        {
         Class.forName("com.mysql.cj.jdbc.Driver");
         conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/examsystem","user", "1316");
        }
        catch(Exception e)
        {
          System.out.println(e);
        }
     }
     public Student getStudent(int lid)
     {
          Student s=new Student();
         try
         {
            
            String Query = "SELECT sid, first_name, last_name, dob, gender, address, pincode, phone_no, " +
            "email_id, institute_name, branch, semester, year, scholar_no, password " +
            "FROM student WHERE sid = " + lid;

Statement st = conn.createStatement();
ResultSet rs = st.executeQuery(Query);

if (rs.next()) { // Ensure there's a result
 s.sid = rs.getInt("sid");
 s.first_name = rs.getString("first_name");
 s.last_name = rs.getString("last_name");
 s.dob = rs.getString("dob");
 s.gender = rs.getString("gender");
 s.address = rs.getString("address");
 s.pincode = rs.getString("pincode");
 s.phonenumber = rs.getString("phone_no");
 s.email = rs.getString("email_id");
 s.institute_name = rs.getString("institute_name");
 s.branch = rs.getString("branch");
 s.semester = rs.getString("semester");
 s.year = rs.getString("year");
 s.scholar_no = rs.getString("scholar_no");
 s.password = rs.getString("password"); 
} 
else {
System.out.println("No student found with ID: " + lid);
}
        
        }
         catch(Exception e)
         {
           System.out.println(e);
         }
         return s;
     }
     public void addStudent(Student s)
     {
      System.out.println("Inserting Student Data");
       String Query="insert into student (first_name,last_name,dob,gender,address,pincode,phone_no,email_id,institute_name,branch,semester,year,scholar_no,image) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
       try{
       PreparedStatement pst=conn.prepareStatement(Query);
       //pst.setInt(1,s.s_id); 
       pst.setString(1, s.first_name);
       pst.setString(2, s.last_name);
       pst.setString(3,s.dob);
       pst.setString(4,s.gender);
       pst.setString(5,s.address);
       pst.setString(6,s.pincode);
       pst.setString(7,s.phonenumber);
       pst.setString(8,s.email);
       pst.setString(9,s.institute_name);
       pst.setString(10,s.branch);
       pst.setString(11,s.semester);
       pst.setString(12,s.year);
       pst.setString(13,s.scholar_no);
       pst.setBytes(14,s.image);
       pst.executeUpdate();
       System.out.println("Inserted Student Data");
        }
       catch(Exception e)
       {
          System.out.println(e);
          e.printStackTrace();
          System.out.println("Error Inserting Student Data");
       }
     }
            
}