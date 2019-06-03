/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmpe331_project;

import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author selinyesilselve
 */
public class MyFunctions {
    
    public static String studentName(int id) throws SQLException{
        Connection con = My_Connection.getConnection();
        Statement st;
        st = con.createStatement();
        String student_name = "";
        ResultSet rs = st.executeQuery("SELECT * FROM student");
        while(rs.next()){
         if(id == rs.getInt(1)){
         student_name = rs.getString(3) +" " + rs.getString(4);
    }}
        return student_name;
    }
    
   
    
    
    public static String studentClass(int id) throws SQLException{
        Connection con = My_Connection.getConnection();
        Statement st;
        st = con.createStatement();
        String student_class = "";
        ResultSet rs = st.executeQuery("SELECT * FROM student");
        while(rs.next()){
         if(id == rs.getInt(1)){
         student_class = rs.getString(5);
         }
        }
        return student_class;
    } 
    
   
    
     public static String parentName(int id) throws SQLException{
        Connection con = My_Connection.getConnection();
        Statement st;
        st = con.createStatement();
        String parent_name = "";
        ResultSet rs = st.executeQuery("SELECT * FROM parent");
        while(rs.next()){
         if(id == rs.getInt(1)){
         parent_name = rs.getString(5)+" " + rs.getString(7);
    }}
        return parent_name ;
    }
     
     
     public static String teacherName(int id) throws SQLException{
        Connection con = My_Connection.getConnection();
        Statement st;
        st = con.createStatement();
        String teacher_name = "";
        String teacher_lastname = "";
        ResultSet rs = st.executeQuery("SELECT * FROM teacher");
        while(rs.next()){
         if(id == rs.getInt(3)){
         teacher_name = rs.getString(1);
         teacher_lastname = rs.getString(2);
         }
        }
        return teacher_name + "  " + teacher_lastname;

    }


    public static String teacherType(int id) throws SQLException{
       Connection con = My_Connection.getConnection();
       Statement st;
       st = con.createStatement();
       String teacherTpe = "";
       ResultSet rs = st.executeQuery("SELECT * FROM teacher");
       while(rs.next()){
           if(id == rs.getInt(3)){
               teacherTpe = rs.getString(5);
           }
       }

        return teacherTpe;

    }
   public static ImageIcon tecaherPhoto(int id, JLabel label) throws SQLException{
         Icon icon = null;
         Connection con = My_Connection.getConnection();
        Statement st;
        st = con.createStatement();
        ImageIcon newImage = null;
        ResultSet rs = st.executeQuery("SELECT * FROM teacher");
        while(rs.next()){
         if(id == rs.getInt(3)){
             byte [] img = rs.getBytes(6);
             ImageIcon image = new ImageIcon(img);
             Image im = image.getImage();
             Image myImg = im.getScaledInstance(label.getWidth(),label.getHeight(),Image.SCALE_SMOOTH);
             newImage = new ImageIcon(myImg);
         }
        }

        return newImage;

   }
   
    public static ImageIcon studentPhoto(int id , JLabel label) throws SQLException {
        Icon icon = null;
         Connection con = My_Connection.getConnection();
        Statement st;
        st = con.createStatement();
        ImageIcon newImage = null;
        ResultSet rs = st.executeQuery("SELECT * FROM student");
        while(rs.next()){
         if(id == rs.getInt(1)){
             byte [] img = rs.getBytes(8);
             ImageIcon image = new ImageIcon(img);
             Image im = image.getImage();
             Image myImg = im.getScaledInstance(label.getWidth(),label.getHeight(),Image.SCALE_SMOOTH);
             newImage = new ImageIcon(myImg);
         }
        }

        return newImage;
    }
    
     public static void fillStudentGradebookTable(JTable table,int id) throws SQLException{
        Connection con = My_Connection.getConnection();
        Statement st;
        st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM mathematics WHERE student_id =" + id+" UNION SELECT*FROM science WHERE student_id =" +id+" UNION SELECT*FROM literature WHERE student_id =" +id+" UNION SELECT*FROM english WHERE student_id =" +id+" UNION SELECT*FROM physical_education WHERE student_id =" + id  );
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object[] row;
        
        while(rs.next()){
         if(id == rs.getInt(1)){
         row = new Object[7];
         row[0] = rs.getString(6);
         row[1] = rs.getInt(2);
         row[2] = rs.getInt(3);
         row[3] = rs.getInt(4);
         
         
         model.addRow(row);
         
         
    }}
     
    }
     
     
     static String [] columnNames = new String[1];
     static Object [][] data  = null;
     public static JTable createTeacherAnnouncement(JTable table) throws SQLException{
        columnNames = new String[]{"Announcement","Teacher Name"};
        data = new Object[1][2];
        data[0][0] = JOptionPane.showInputDialog("Enter an Announcement");
        data[0][1] = JOptionPane.showInputDialog("Enter your Teacher Name");
        table.setModel(new DefaultTableModel(data,columnNames));
        Connection con = My_Connection.getConnection();
        String sql = "INSERT INTO announcement(announcement,teacher_name) values(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, (String) data[0][0]);
        ps.setString(2, (String) data[0][1]);
        
        
        ps.execute();
        JOptionPane.showMessageDialog(null, "Success !");
      
            return table;
     
     }
     
     
     
     public static JTable addTeacherAnnouncement(JTable table) throws SQLException{
        Object[][] temp = new Object[data.length+1][2];
        for(int i = 0;i<data.length;i++){
            for (int j=0; j<2 ; j++){
                temp[i][j] = data[i][j];   
            }
        }
        temp[data.length][0] = JOptionPane.showInputDialog("Enter an Announcement");
        temp[data.length][1] = JOptionPane.showInputDialog("Enter your Teacher Name");
        data = temp;
        table.setModel(new DefaultTableModel(temp,columnNames));
        
        Connection con = My_Connection.getConnection();
        String sql = "INSERT INTO announcement(announcement,teacher_name) values(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
       for(int i= 1; i<data.length;i++){
        ps.setString(1, (String) data[i][0]);
        ps.setString(2, (String) data[i][1]);
        ps.execute();
       
        JOptionPane.showMessageDialog(null, "Success !");
        
         }
        
        return table;
     }
     
     
     
     public static void studentAnnouncement(JTable table) throws SQLException{
        Connection con = My_Connection.getConnection();
        Statement st;
        st = con.createStatement();
       
        ResultSet rs = st.executeQuery("SELECT * FROM announcement");         
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object[] row;
        
        while(rs.next()){
         
             
         row = new Object[2];
         row[0] = rs.getString(1);
         row[1] = rs.getString(2);
       
         
         model.addRow(row);
        
        }
         
       
    }
     public static void deleteAnnouncement(JTable table) throws SQLException{
        
         int column = 0;
         int SelectedRowIndex = table.getSelectedRow();
         if(table.getSelectionModel().isSelectionEmpty()){
         Teacher teacher = new Teacher();
             JOptionPane.showMessageDialog(teacher.Homework, "Select a row to delete");
        
         }
         else{
            
              String value = table.getModel().getValueAt(SelectedRowIndex, column).toString();
         String value2 = table.getModel().getValueAt(SelectedRowIndex, 1).toString();
         DefaultTableModel model = (DefaultTableModel) table.getModel();
         model.removeRow(SelectedRowIndex);
         
         Connection con = My_Connection.getConnection();
         PreparedStatement ps;
         String deleteQuery = "DELETE from announcement WHERE announcement = '" + value + "' AND teacher_name = '" + value2 + "'"; 
         ps = con.prepareStatement(deleteQuery);
         System.out.println(ps);
         ps.executeUpdate(deleteQuery);
         }
     }
     
     static String [] columnNames2 = new String[3];
     static Object [][] data2  = null;
     public static JTable createTeacherHomework(JTable table) throws SQLException{
         columnNames2 = new String[]{"Homework","Deadline","Lecture Name","Class"};
         data2 = new Object[1][4];
         data2[0][0] = JOptionPane.showInputDialog("Enter the homework");
         data2[0][1] = JOptionPane.showInputDialog("Enter deadline of the homework");
         data2[0][2] = JOptionPane.showInputDialog("Enter the lecture name");
         data2[0][3] = JOptionPane.showInputDialog("Enter the class. \nPlease write the class like: 4/A or 4/B");
        
        table.setModel(new DefaultTableModel(data2,columnNames2));
        Connection con = My_Connection.getConnection();
        String sql = "INSERT INTO homework(homework,deadline,lecture_name,class) VALUES (?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, (String) data2[0][0]);
        ps.setString(2, (String) data2[0][1]);
        ps.setString(3, (String) data2[0][2]);
        ps.setString(4, (String) data2[0][3]);
         System.out.print(data2.length);
        
        
        ps.execute();
        JOptionPane.showMessageDialog(null, "Success !");
       
         return table;   
     }
     
     public static JTable addTeacherHomework(JTable table) throws SQLException{
         Object[][] temp2 = new Object[data2.length+1][4];
        
        for(int i = 0;i<data2.length;i++){
            for (int j=0; j<4 ; j++){
                temp2[i][j] = data2[i][j];   
            }
        }
        temp2[data2.length][0] = JOptionPane.showInputDialog("Enter homework");
        temp2[data2.length][1] = JOptionPane.showInputDialog("Enter deadline");
        temp2[data2.length][2] = JOptionPane.showInputDialog("Enter lecture name");
        temp2[data2.length][3] = JOptionPane.showInputDialog("Enter class name.\nPlease write the class like: 4/A or 4/B");
        data2 = temp2;
        table.setModel(new DefaultTableModel(temp2,columnNames2));
        
        Connection con = My_Connection.getConnection();
        String sql = "INSERT INTO homework(homework,deadline,lecture_name,class) VALUES (?,?,?,?)";
        
        PreparedStatement ps = con.prepareStatement(sql);
        for(int i= 1; i<data2.length;i++){
        ps.setString(1, (String) data2[i][0]);
        ps.setString(2, (String) data2[i][1]);
        ps.setString(3, (String) data2[i][2]);
        ps.setString(4, (String) data2[i][3]);
        ps.execute();
       
        JOptionPane.showMessageDialog(null, "Success !");
        
         }
        
         return table;
     }
     

     
     
     public static void homeworkShow(JTable table) throws SQLException{
         
         Connection con = My_Connection.getConnection();
         Statement st ;
         st = con.createStatement();
         String sql = "SELECT * FROM homework";
         ResultSet rs = st.executeQuery(sql);
        
         DefaultTableModel model = (DefaultTableModel) table.getModel();
         Object [] row;
       
         
         while(rs.next()){
           
             row = new Object[4];
             row[0] = rs.getString(1);
             row[1] = rs.getString(2);
             row[2] = rs.getString(3);
             row[3] = rs.getString(4);
             
             model.addRow(row);
       
          }
    
     }
     
     
     public static void deleteHomework(JTable table) throws SQLException{
        
         if(table.getSelectionModel().isSelectionEmpty()){
         Teacher teacher = new Teacher();
             JOptionPane.showMessageDialog(teacher.Homework, "Select a row to delete");
             
         }
         else{
         
         int SelectedRowIndex = table.getSelectedRow();
         String value0 = table.getModel().getValueAt(SelectedRowIndex, 0).toString();
         String value1 = table.getModel().getValueAt(SelectedRowIndex, 1).toString();
         String value2 = table.getModel().getValueAt(SelectedRowIndex, 2).toString();
         String value3 = table.getModel().getValueAt(SelectedRowIndex, 3).toString();
         
         DefaultTableModel model = (DefaultTableModel) table.getModel();
         
         model.removeRow(SelectedRowIndex);
         
         Connection con = My_Connection.getConnection();
         PreparedStatement ps;
         String deleteQuery = "DELETE from homework WHERE homework = '" + value0 +
                 "' AND deadline = '" + value1 + 
                 "' AND lecture_name = '" + value2 +
                 "' AND class = '" + value3 + "'"; 
       
         ps = con.prepareStatement(deleteQuery);
         System.out.println(ps);
         ps.executeUpdate(deleteQuery);
         

         }
     }
     
     public static void studentHomework(JTable table,int id) throws SQLException{
         Connection con = My_Connection.getConnection();
         Statement st;
         st = con.createStatement();
         ResultSet rs;
         rs = st.executeQuery("SELECT student_id,homework.homework,homework.deadline,homework.lecture_name FROM homework JOIN student ON homework.class = student_class WHERE student_id = "+id);
         Object[] row ; 
          row = new Object[3]; 
          DefaultTableModel model = (DefaultTableModel) table.getModel();
         
          while(rs.next()){
         if(rs.getInt(1)== id) {        
         row[0]=rs.getString(2);
         row[1]=rs.getString(3);
         row[2]=rs.getString(4);
         model.addRow(row);
         }
     }
     }
     
     
    public static void teacherComboBoxLecture(int id, JComboBox lecture ) throws SQLException{
  
    Connection con = My_Connection.getConnection();
    Statement st;
    st = con.createStatement();
    ResultSet rs;
    rs = st.executeQuery("SELECT teacher_id,teacher_type FROM `teacher` WHERE teacher_id= " + id);
    while(rs.next()){
        if(rs.getString(2).equals("Class")){
            lecture.addItem("Mathematics");
            lecture.addItem("Science");
            lecture.addItem("Literature");
        }
        if(rs.getString(2).equals("English")){
            lecture.addItem("English");
        }
        if(rs.getString(2).equals("Physical_education")){
            lecture.addItem("Physical Education");
        }
    }
}
     
     public static void studentGradebookInformation(JTable table, JComboBox comboboxlecture ,JComboBox comboboxclass) throws SQLException{
    Connection con = My_Connection.getConnection();
    Statement st;
    st = con.createStatement();
    ResultSet rs;
    Object selectedlecture = comboboxlecture.getSelectedItem();
    Object selectedclass = comboboxclass.getSelectedItem();
    
    if(selectedlecture.toString().equals("Mathematics")&& selectedclass.toString().equals("4/A")){
   
    rs = st.executeQuery("SELECT student.student_id,student_name,student_lastname,mathematics.math_midterm,mathematics.math_final,mathematics.math_project FROM student JOIN mathematics ON student.student_id=mathematics.student_id WHERE student.student_class =\"4/A\"" );
      
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);
        Object[] row;
        while(rs.next()){
         row = new Object[7];
         
         row[0] = rs.getString(1);
         row[1] = rs.getString(2);
         row[2] = rs.getString(3);
         row[3] = rs.getInt(4);
         row[4] = rs.getInt(5);
         row[5] = rs.getInt(6);
         row[6] = (rs.getInt(4)+ rs.getInt(5)+rs.getInt(6))/3;
         
         model.addRow(row);
   
        }
}
    
    if(selectedlecture.toString().equals("Mathematics")&& selectedclass.toString().equals("4/B")){
    rs = st.executeQuery("SELECT student.student_id,student_name,student_lastname,mathematics.math_midterm,mathematics.math_final,mathematics.math_project FROM student JOIN mathematics ON student.student_id=mathematics.student_id WHERE student.student_class =\"4/B\"" );
        Object[] row;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        while(rs.next()){
         row = new Object[7];
         row[0] = rs.getString(1);
         row[1] = rs.getString(2);
         row[2] = rs.getString(3);
         row[3] = rs.getInt(4);
         row[4] = rs.getInt(5);
         row[5] = rs.getInt(6);
         row[6] = (rs.getInt(4)+ rs.getInt(5)+rs.getInt(6))/3;
         
         model.addRow(row);
    
}
}
    
    if(selectedlecture.toString().equals("Science")&& selectedclass.toString().equals("4/A")){
    rs = st.executeQuery("SELECT student.student_id,student_name,student_lastname,science.science_midterm,science.science_final,science.science_project FROM student JOIN science ON student.student_id = science.student_id WHERE student.student_class = \"4/A\"" );
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);
        Object[] row;
        while(rs.next()){
         row = new Object[7];
         row[0] = rs.getString(1);
         row[1] = rs.getString(2);
         row[2] = rs.getString(3);
         row[3] = rs.getInt(4);
         row[4] = rs.getInt(5);
         row[5] = rs.getInt(6);
         row[6] = (rs.getInt(4)+ rs.getInt(5)+rs.getInt(6))/3;
         
         model.addRow(row);
         
    
        }
}
    
    if(selectedlecture.toString().equals("Science")&& selectedclass.toString().equals("4/B")){
    rs = st.executeQuery("SELECT student.student_id,student_name,student_lastname,science.science_midterm,science.science_final,science.science_project FROM student JOIN science ON student.student_id = science.student_id WHERE student.student_class = \"4/B\"" );
        Object[] row;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        while(rs.next()){
         row = new Object[7];
         row[0] = rs.getString(1);
         row[1] = rs.getString(2);
         row[2] = rs.getString(3);
         row[3] = rs.getInt(4);
         row[4] = rs.getInt(5);
         row[5] = rs.getInt(6);
         row[6] = rs.getInt(4)+ rs.getInt(5)+rs.getInt(6)/3;
         
         model.addRow(row);
    
}
}
    if(selectedlecture.toString().equals("Literature")&& selectedclass.toString().equals("4/A")){
    rs = st.executeQuery("SELECT student.student_id,student_name,student_lastname,literature.literature_midterm,literature.literature_final,literature.literature_project FROM student JOIN literature ON student.student_id = literature.student_id WHERE student.student_class = \"4/A\"" );
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);
        Object[] row;
        while(rs.next()){
         row = new Object[7];
         row[0] = rs.getString(1);
         row[1] = rs.getString(2);
         row[2] = rs.getString(3);
         row[3] = rs.getInt(4);
         row[4] = rs.getInt(5);
         row[5] = rs.getInt(6);
         row[6] = rs.getInt(4)+ rs.getInt(5)+rs.getInt(6)/3;
         
         model.addRow(row);
         
    
        }
}
    
    if(selectedlecture.toString().equals("Literature")&& selectedclass.toString().equals("4/B")){
        rs = st.executeQuery("SELECT student.student_id,student_name,student_lastname,literature.literature_midterm,literature.literature_final,literature.literature_project FROM student JOIN literature ON student.student_id = literature.student_id WHERE student.student_class = \"4/B\"" );
        Object[] row;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        while(rs.next()){
            row = new Object[7];
            row[0] = rs.getString(1);
            row[1] = rs.getString(2);
            row[2] = rs.getString(3);
            row[3] = rs.getInt(4);
            row[4] = rs.getInt(5);
            row[5] = rs.getInt(6);
            row[6] = rs.getInt(4)+ rs.getInt(5)+rs.getInt(6)/3;
         
            model.addRow(row);
    
        }
    }
    
    if(selectedlecture.toString().equals("English")&& selectedclass.toString().equals("4/A")){
        rs = st.executeQuery("SELECT student.student_id,student_name,student_lastname,english.english_midterm,english.english_final,english.english_project FROM student JOIN english ON student.student_id = english.student_id WHERE student.student_class = \"4/A\"" );
        Object[] row;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        while(rs.next()){
            row = new Object[7];
            row[0] = rs.getString(1);
            row[1] = rs.getString(2);
            row[2] = rs.getString(3);
            row[3] = rs.getInt(4);
            row[4] = rs.getInt(5);
            row[5] = rs.getInt(6);
            row[6] = rs.getInt(4)+ rs.getInt(5)+rs.getInt(6)/3;
         
            model.addRow(row);
    
        }
    }
    
    
    if(selectedlecture.toString().equals("English")&& selectedclass.toString().equals("4/B")){
        rs = st.executeQuery("SELECT student.student_id,student_name,student_lastname,english.english_midterm,english.english_final,english.english_project FROM student JOIN english ON student.student_id = english.student_id WHERE student.student_class = \"4/B\"" );
        Object[] row;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        while(rs.next()){
            row = new Object[7];
            row[0] = rs.getString(1);
            row[1] = rs.getString(2);
            row[2] = rs.getString(3);
            row[3] = rs.getInt(4);
            row[4] = rs.getInt(5);
            row[5] = rs.getInt(6);
            row[6] = rs.getInt(4)+ rs.getInt(5)+rs.getInt(6)/3;
         
            model.addRow(row);
    
        }
    }
    
     if(selectedlecture.toString().equals("Physical Education")&& selectedclass.toString().equals("4/A")){
        rs = st.executeQuery("SELECT student.student_id,student_name,student_lastname,physical_education.pe_midterm,physical_education.pe_final,physical_education.pe_project FROM student JOIN physical_education ON student.student_id = physical_education.student_id WHERE student.student_class = \"4/A\"" );
        Object[] row;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        while(rs.next()){
            row = new Object[7];
            row[0] = rs.getString(1);
            row[1] = rs.getString(2);
            row[2] = rs.getString(3);
            row[3] = rs.getInt(4);
            row[4] = rs.getInt(5);
            row[5] = rs.getInt(6);
            row[6] = rs.getInt(4)+ rs.getInt(5)+rs.getInt(6)/3;
         
            model.addRow(row);
    
        }
    }
    
     if(selectedlecture.toString().equals("Physical Education")&& selectedclass.toString().equals("4/B")){
        rs = st.executeQuery("SELECT student.student_id,student_name,student_lastname,physical_education.pe_midterm,physical_education.pe_final,physical_education.pe_project FROM student JOIN physical_education ON student.student_id = physical_education.student_id WHERE student.student_class = \"4/B\"" );
        Object[] row;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        while(rs.next()){
            row = new Object[7];
            row[0] = rs.getString(1);
            row[1] = rs.getString(2);
            row[2] = rs.getString(3);
            row[3] = rs.getInt(4);
            row[4] = rs.getInt(5);
            row[5] = rs.getInt(6);
            row[6] = rs.getInt(4)+ rs.getInt(5)+rs.getInt(6)/3;
         
            model.addRow(row);
    
        }
    }
    
}
    
     public static void studentList(JTable table,JComboBox classComboBox) throws SQLException{
         Connection con = My_Connection.getConnection();
         Statement st;
         st = con.createStatement();
         ResultSet rs;
         Object  selectedClass = classComboBox.getSelectedItem();
         
         if(selectedClass.toString().equals("4/A")){
             
             rs = st.executeQuery("SELECT student_id,student_name,student_lastname FROM student WHERE student_class =\"4/A\"" );
             
             DefaultTableModel model = (DefaultTableModel)table.getModel();
             Object[] row;
             model.setRowCount(0);
             while(rs.next()){
              row = new Object[3];
              row[0] = rs.getString(1);
              row[1] = rs.getString(2);
              row[2] = rs.getString(3);     
              model.addRow(row);
         
             }
           
         }
          if(selectedClass.toString().equals("4/B")){
             
             rs = st.executeQuery("SELECT student_id,student_name,student_lastname FROM student WHERE student_class =\"4/B\"" );
             
             DefaultTableModel model = (DefaultTableModel)table.getModel();
             Object[] row;
             model.setRowCount(0);
             while(rs.next()){
              row = new Object[3];
              row[0] = rs.getString(1);
              row[1] = rs.getString(2);
              row[2] = rs.getString(3);     
              model.addRow(row);
           
             }
          
         }
      
     }
    
    
    public static void studentInfo(JTable table) throws SQLException{
         int selectedRow = table.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
       String id = model.getValueAt(selectedRow, 0).toString();
        Connection con = My_Connection.getConnection();
        Statement st;
         st = con.createStatement();
        ResultSet rs;
        String sql= "SELECT * from student WHERE student_id = "+id ;
        rs = st.executeQuery(sql);
        String student_id = "";
        String student_name = "";
        String student_lastname = "";
        String student_class = "";
        String student_address = "";
        int student_identity=0;
        
        
         while(rs.next()){
             
             student_id = rs.getString(1);
             student_name = rs.getString(3);
             student_lastname = rs.getString(4);
             student_class = rs.getString(5);
             student_address = rs.getString(6);
             student_identity = rs.getInt(7);
          
         }
      
        JOptionPane.showMessageDialog(null, "Student id: "+student_id+"\nStudent name: "+student_name+"\nStudent last name: "+student_lastname+
                "\nStudent class: "+student_class+"\nStudent address: "+student_address+"\nStudent identity: "+student_identity,"Student Information",JOptionPane.PLAIN_MESSAGE);
        
    }
    
    
    
    public static void gradeInfoUpdate(JTable table, JComboBox comboboxlecture ) throws SQLException{
       
        Teacher teacher = new Teacher();
        Connection con = My_Connection.getConnection();
        Statement st;
        st = con.createStatement();
        PreparedStatement ps;
        Object selectedlecture = comboboxlecture.getSelectedItem();
        if(selectedlecture.toString().equals("Mathematics")){
            String sql = "UPDATE mathematics SET  math_midterm = ?, math_final = ?, math_project = ? WHERE student_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,teacher.a_midterm);
            ps.setInt(2,teacher.a_final);
            ps.setInt(3,teacher.a_project);
            ps.setString(4, teacher.a_id);
            ps.executeUpdate(); 
          
        }
        if(selectedlecture.toString().equals("Science")){
            String sql = "UPDATE science SET  science_midterm = ?, science_final = ?, science_project = ? WHERE student_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,teacher.a_midterm);
            ps.setInt(2,teacher.a_final);
            ps.setInt(3,teacher.a_project);
            ps.setString(4, teacher.a_id);
            ps.executeUpdate(); 
          
        }
        if(selectedlecture.toString().equals("Literature")){
            String sql = "UPDATE literature SET  literature_midterm = ?, literature_final = ?, literature_project = ? WHERE student_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,teacher.a_midterm);
            ps.setInt(2,teacher.a_final);
            ps.setInt(3,teacher.a_project);
            ps.setString(4, teacher.a_id);
            ps.executeUpdate(); 
          
        }
        if(selectedlecture.toString().equals("English")){
            String sql = "UPDATE english SET  english_midterm = ?, english_final = ?, english_project = ? WHERE student_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,teacher.a_midterm);
            ps.setInt(2,teacher.a_final);
            ps.setInt(3,teacher.a_project);
            ps.setString(4, teacher.a_id);
            ps.executeUpdate(); 
          
        }
        if(selectedlecture.toString().equals("Physical Education")){
            String sql = "UPDATE physical_education SET  pe_midterm = ?, pe_final = ?, pe_project = ? WHERE student_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,teacher.a_midterm);
            ps.setInt(2,teacher.a_final);
            ps.setInt(3,teacher.a_project);
            ps.setString(4, teacher.a_id);
            ps.executeUpdate(); 
          
        }
    }
        
    
     static String [] columnNames3 = new String[2];
     static Object [][] data3  = null;
         
     public static JTable createExamSchedule(JTable table) throws SQLException{
         columnNames3 = new String[]{"Lecture Name","Date of exam"};
         data3 = new Object[1][4];
         data3[0][0] = JOptionPane.showInputDialog("Enter the lecture name");
         data3[0][1] = JOptionPane.showInputDialog("Enter date of the exam");
         
         table.setModel(new DefaultTableModel(data3,columnNames3));
         Connection con = My_Connection.getConnection();
         String sql = "INSERT INTO exam_schedule(lecture_name,exam_date) VALUES (?,?)";
         PreparedStatement ps = con.prepareStatement(sql);
         ps.setString(1, (String) data3[0][0]);
         ps.setString(2, (String) data3[0][1]);
        
        ps.execute();
        JOptionPane.showMessageDialog(null, "Success !");
      
        return table;
     }
     
     
    public static JTable addExamDate(JTable table) throws SQLException{
         Object[][] temp3 = new Object[data3.length+1][2];
        
        for(int i = 0;i<data3.length;i++){
            for (int j=0; j<2 ; j++){
                temp3[i][j] = data3[i][j];   
            }
        }
        temp3[data3.length][0] = JOptionPane.showInputDialog("Enter lecture name");
        temp3[data3.length][1] = JOptionPane.showInputDialog("Enter exam date");
        data3=temp3;
        table.setModel(new DefaultTableModel(temp3,columnNames3));
        
        Connection con = My_Connection.getConnection();
        String sql= "INSERT INTO exam_schedule(lecture_name,exam_date) VALUES (?,?)";
        
        PreparedStatement ps = con.prepareStatement(sql);
        for(int i= 1; i<data3.length;i++){
        ps.setString(1, (String) data3[i][0]);
        ps.setString(2, (String) data3[i][1]);
       
        ps.execute();
        JOptionPane.showMessageDialog(null, "Success !");
        
         }
        
        return table;
    }
    
    public static void scheduleShow(JTable table) throws SQLException{
        Connection con = My_Connection.getConnection();
         Statement st ;
         st = con.createStatement();
         String sql = "SELECT * FROM exam_schedule";
         ResultSet rs = st.executeQuery(sql);
         DefaultTableModel model = (DefaultTableModel) table.getModel();
         Object [] row;
       
         
         while(rs.next()){
           
             row = new Object[2];
             row[0] = rs.getString(1);
             row[1] = rs.getString(2);
            
             
             model.addRow(row);
       
          }
         
    }
    
    
    public static void deleteSchedule(JTable table) throws SQLException{
       if(table.getSelectionModel().isSelectionEmpty()){
           Teacher teacher = new Teacher();
           JOptionPane.showMessageDialog(null, "Select a row to delete");
           
       }
       else{
         int SelectedRowIndex = table.getSelectedRow();
         String value0 = table.getModel().getValueAt(SelectedRowIndex, 0).toString();
         String value1 = table.getModel().getValueAt(SelectedRowIndex, 1).toString();
         DefaultTableModel model = (DefaultTableModel) table.getModel();
         
         model.removeRow(SelectedRowIndex);
         
         Connection con = My_Connection.getConnection();
         PreparedStatement ps;
         String deleteQuery = "DELETE from exam_schedule WHERE lecture_name = '" + value0 +"' AND exam_date = '" + value1 +"'" ;
         ps = con.prepareStatement(deleteQuery);
         ps.executeUpdate(deleteQuery);
         
       }
        
    }
    
    
    public static JTable studentSchedule(JTable table) throws SQLException{
        Connection con = My_Connection.getConnection();
        Statement st;
        st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM exam_schedule");         
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object[] row;
        
        while(rs.next()){
         row = new Object[2];
         row[0] = rs.getString(1);
         row[1] = rs.getString(2);
       
         model.addRow(row);
        
        }
        
        return table;
    }
    
    
    
    public MyFunctions() {
          
    }

    




}
   

