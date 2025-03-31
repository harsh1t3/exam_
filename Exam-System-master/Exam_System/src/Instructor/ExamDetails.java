package Instructor;

import Login.dbConnector;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ExamDetails extends JFrame {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JButton date, group, save;
    static dbConnector dbc = new dbConnector();
    JLabel ltime, lduration, lgroup;
    static JLabel ldate;
    JSpinner duration, hh, mm;
    public static JFrame f;

    public ExamDetails() {
        f = this;
        setLayout(null);
        setSize(500, 500);
        setLocationRelativeTo(null);

        duration = new JSpinner(new SpinnerNumberModel(15, 15, 180, 15));
        hh = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        mm = new JSpinner(new SpinnerNumberModel(0, 0, 59, 15));

        lduration = new JLabel("Duration:");
        ldate = new JLabel("Date: Not Selected"); 
        date = new JButton("Select Date");
        lgroup = new JLabel("Group: Not Selected");
        group = new JButton("Select Group");
        save = new JButton("Save");
        ltime = new JLabel("Time:");

        duration.setBounds(100, 10, 150, 30);
        lduration.setBounds(10, 10, 150, 30);
        date.setBounds(100, 50, 150, 30);
        ldate.setBounds(10, 50, 150, 30);
        group.setBounds(100, 100, 150, 30);
        lgroup.setBounds(10, 100, 150, 30);
        hh.setBounds(60, 150, 50, 30);
        mm.setBounds(150, 150, 50, 30);
        ltime.setBounds(10, 150, 100, 30);
        save.setBounds(50, 250, 80, 30);

        add(lduration);
        add(ldate);
        add(date);
        add(lgroup);
        add(group);
        add(duration);
        add(save);
        add(hh);
        add(mm);
        add(ltime);

        date.addActionListener(new eventAction());
        save.addActionListener(new eventAction());
        group.addActionListener(new eventAction());
    }

    int fetchGroups() {
        String countQuery = "SELECT COUNT(DISTINCT sg.group_id) FROM STUDENT_GROUP sg "
                + "JOIN INSTRUCTOR_GROUP ig ON sg.group_id = ig.group_id WHERE ig.iid = ?";
        String groupQuery = "SELECT DISTINCT sg.group_id FROM STUDENT_GROUP sg "
                + "JOIN INSTRUCTOR_GROUP ig ON sg.group_id = ig.group_id WHERE ig.iid = ?";

        try (PreparedStatement countStmt = dbc.getConnection().prepareStatement(countQuery);
             PreparedStatement groupStmt = dbc.getConnection().prepareStatement(groupQuery)) {

            countStmt.setInt(1, Instructor.instructorid);
            groupStmt.setInt(1, Instructor.instructorid);

            ResultSet rs = countStmt.executeQuery();
            int num = 0;
            if (rs.next()) {
                num = rs.getInt(1);
                if (num == 0) {
                    JOptionPane.showMessageDialog(this, "No groups found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            }

            rs = groupStmt.executeQuery();
            Object[] gid = new Object[num];
            int i = 0;
            while (rs.next()) {
                gid[i] = "G" + rs.getInt(1);
                i++;
            }

            String selectedGroup = (String) JOptionPane.showInputDialog(this, "Select Group:", "", JOptionPane.PLAIN_MESSAGE, null, gid, "");
            if (selectedGroup == null || !selectedGroup.startsWith("G")) {
                JOptionPane.showMessageDialog(this, "Invalid group selection.", "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }

            return Integer.parseInt(selectedGroup.substring(1));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error while fetching groups.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    class eventAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == date) {
                f.setEnabled(false);
                new ShowCalendar();
                f.setEnabled(true);
                repaint();
            } else if (e.getSource() == group) {
                int groupId = fetchGroups();
                if (groupId != -1) {
                    lgroup.setText("Group: G" + groupId);
                }
            } else if (e.getSource() == save) {
                if (ldate.getText().equals("Date: Not Selected")) {
                    JOptionPane.showMessageDialog(f, "Please select a date.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (lgroup.getText().equals("Group: Not Selected")) {
                    JOptionPane.showMessageDialog(f, "Please select a group.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int groupId = Integer.parseInt(lgroup.getText().substring(8));
                    int durationValue = (Integer) duration.getValue();
                    String examDateTime = ldate.getText().substring(6) + " " + hh.getValue() + ":" + mm.getValue() + ":00";

                    String insertExamQuery = "INSERT INTO EXAM (ename, edate_time, duration, estid, group_id) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement stmt = dbc.getConnection().prepareStatement(insertExamQuery, Statement.RETURN_GENERATED_KEYS)) {
                        stmt.setString(1, "New Exam");
                        stmt.setString(2, examDateTime);
                        stmt.setInt(3, durationValue);
                        stmt.setInt(4, Instructor.instructorid);
                        stmt.setInt(5, groupId);

                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(f, "Exam successfully scheduled!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            ResultSet generatedKeys = stmt.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                int examId = generatedKeys.getInt(1);
                                System.out.println("Exam created with ID: " + examId);
                            }
                        } else {
                            JOptionPane.showMessageDialog(f, "Failed to schedule exam.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(f, "Error saving exam details.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                f.dispose();
                Instructor.frame.setEnabled(true);
            }
        }
    }
}
