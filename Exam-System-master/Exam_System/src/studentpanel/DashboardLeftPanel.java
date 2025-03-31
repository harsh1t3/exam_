package studentpanel;

import Login.User_Login;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class DashboardLeftPanel extends JPanel {
    public static JPanel currPanel = null;
    public static DashboardExamShowPanel esp = new DashboardExamShowPanel();
    public static DashboardResultsPanel rsp = new DashboardResultsPanel();

    void createLeftPanel(String options[]) {
        int n = options.length;
        JButton[] buttons = new JButton[n];
        this.setLayout(null);
        
        Rectangle bounds = this.getBounds();
        int x = bounds.x;
        int y = bounds.y;
        int width = bounds.width;
        
        for (int index = 0; index < n; index++) {
            buttons[index] = new JButton(options[index]);
            buttons[index].setBounds(x + 10, y + 10, width - 20, 50);
            buttons[index].addActionListener(new CustomActionListener());
            y += 60;  // Spacing between buttons
            this.add(buttons[index]);
        }
    }

    public static void display(JPanel p) {
        if (currPanel != null) {
            Dashboard.frame.remove(currPanel);
        }
        Dashboard.frame.add(p);
        p.setVisible(true);
        currPanel = p;
    }

    class CustomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int n = 0;
            String query = "SELECT count(*) FROM exam WHERE group_id IN (SELECT group_id FROM student_group WHERE sid=" + Dashboard.sid + ")";
            try {
                ResultSet r = Database.execute(query);
                if (r.next()) {
                    n = r.getInt(1);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            System.out.println("n ---------- " + n);

            String[][] exams = new String[n + 1][5]; // +1 for headers
            exams[0][0] = "Exam ID";
            exams[0][1] = "Exam Title";
            exams[0][2] = "Exam Date & Time";
            exams[0][3] = "Duration";
            exams[0][4] = "Group";

            query = "SELECT eid, ename, edate_time, eduration, group_id FROM exam WHERE group_id IN (SELECT group_id FROM student_group WHERE sid=" + Dashboard.sid + ")";
            try {
                ResultSet r = Database.execute(query);
                int i = 1;
                while (r.next()) {
                    exams[i][0] = r.getString(1);
                    exams[i][1] = r.getString(2);
                    exams[i][2] = r.getString(3);
                    exams[i][3] = r.getString(4);
                    exams[i][4] = r.getString(5);
                    i++;
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            try {
                esp = new DashboardExamShowPanel(exams);
                esp.setBounds(Dashboard.leftPanelWidth, Dashboard.topPanelHeight, Dashboard.rightPanelWidth, Dashboard.bothPanelHeight);
                Dashboard.frame.add(esp);
                esp.setVisible(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            int m = 0;
            String resultQuery = "SELECT count(*) FROM ExamStudentTable WHERE sid = " + Dashboard.sid;
            try {
                ResultSet r = Database.execute(resultQuery);
                if (r.next()) {
                    m = r.getInt(1);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            System.out.println("m ---------- " + m);

            String[][] results = new String[m + 1][3]; // +1 for headers
            results[0][0] = "Exam ID";
            results[0][1] = "Exam Title";
            results[0][2] = "Conducted On";

            String[] estid = new String[m + 1];
            estid[0] = "";

            query = "SELECT est.estid, e.eid, e.ename, est.datetimestamp FROM exam e, examstudenttable est WHERE e.eid = est.eid AND est.sid=" + Dashboard.sid;
            try {
                ResultSet r = Database.execute(query);
                int i = 1;
                while (r.next()) {
                    estid[i] = r.getString(1);
                    results[i][0] = r.getString(2);
                    results[i][1] = r.getString(3);
                    results[i][2] = r.getString(4);
                    i++;
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            try {
                rsp = new DashboardResultsPanel(estid, results);
                rsp.setBounds(Dashboard.leftPanelWidth, Dashboard.topPanelHeight, Dashboard.rightPanelWidth, Dashboard.bothPanelHeight);
                Dashboard.frame.add(rsp);
                rsp.setVisible(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            switch (e.getActionCommand()) {
                case "Dashboard":
                    display(Dashboard.rp);
                    break;
                case "Exams":
                    display(esp);
                    break;
                case "Check Results":
                    display(rsp);
                    break;
                case "Logout":
                    User_Login ob = new User_Login();
                    ob.createGUI();
                    ob.frame.setVisible(true);
                    Dashboard.frame.dispose();
                    break;
            }
        }
    }
}
