package Instructor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import Login.dbConnector;

public class ExamShowPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Instructor instructor;
    private static dbConnector dbc = new dbConnector();
    private JPanel[] examListItem;
    private JLabel[] label1, label2, label3;
    private JButton[] chooseExamButton, deleteExamButton;
    private JButton addExamButton;
    private String[][] exams;
    private int examCount;
    
    public ExamShowPanel(Instructor instructor) {
        this.instructor = instructor;
        setLayout(null);
        setBackground(Color.YELLOW);
        
        fetchExamsFromDatabase();
        initializeComponents();
        displayExams();
    }

    private void initializeComponents() {
        addExamButton = new JButton("Add Exam");
        addExamButton.setBounds(300, 500, 130, 40);
        addExamButton.addActionListener(new AddExamAction());
        add(addExamButton);
        
        JLabel title = new JLabel("Exams");
        title.setFont(new Font("Century Gothic", Font.BOLD, 37));
        title.setBounds(50, 20, 200, 50);
        add(title);
    }

    private void fetchExamsFromDatabase() {
        String query = "SELECT eid, ename, edate_time FROM exam WHERE iid = ?";
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(query)) {
            stmt.setInt(1, Instructor.instructorid);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String[]> examList = new ArrayList<>();
            while (rs.next()) {
                examList.add(new String[]{"E" + rs.getInt("eid"), rs.getString("ename"), rs.getString("edate_time")});
            }
            exams = examList.toArray(new String[0][]);
            examCount = exams.length;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayExams() {
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(examCount, 1));
        
        examListItem = new JPanel[examCount];
        label1 = new JLabel[examCount];
        label2 = new JLabel[examCount];
        label3 = new JLabel[examCount];
        chooseExamButton = new JButton[examCount];
        deleteExamButton = new JButton[examCount];
        
        for (int i = 0; i < examCount; i++) {
            examListItem[i] = new JPanel();
            examListItem[i].setLayout(new FlowLayout(FlowLayout.LEFT));
            examListItem[i].setBackground(i % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
            
            label1[i] = new JLabel(exams[i][0]);
            label2[i] = new JLabel(exams[i][1]);
            label3[i] = new JLabel(exams[i][2]);
            chooseExamButton[i] = createButton("next.jpg");
            deleteExamButton[i] = createButton("delete.png");
            
            deleteExamButton[i].addActionListener(new DeleteExamAction(i));
            chooseExamButton[i].addActionListener(new ViewExamAction(i));
            
            examListItem[i].add(label1[i]);
            examListItem[i].add(label2[i]);
            examListItem[i].add(label3[i]);
            examListItem[i].add(chooseExamButton[i]);
            examListItem[i].add(deleteExamButton[i]);
            container.add(examListItem[i]);
        }
        
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBounds(50, 80, 800, 400);
        add(scrollPane);
    }
    
    private JButton createButton(String imagePath) {
        JButton button = new JButton();
        button.setBackground(Color.WHITE);
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            button.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
        return button;
    }
    
    private class AddExamAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            instructor.addRightPanel(new newExam(instructor));
        }
    }

    private class DeleteExamAction implements ActionListener {
        private int index;

        public DeleteExamAction(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this exam?");
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    String examId = exams[index][0].substring(1);
                    String deleteQuery = "DELETE FROM exam WHERE eid = ?";
                    try (PreparedStatement stmt = dbc.getConnection().prepareStatement(deleteQuery)) {
                        stmt.setInt(1, Integer.parseInt(examId));
                        stmt.executeUpdate();
                    }
                    instructor.addRightPanel(new ExamShowPanel(instructor));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class ViewExamAction implements ActionListener {
        private int index;

        public ViewExamAction(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Exam exam = new Exam();
            exam.fetchFromDatabase(Integer.parseInt(exams[index][0].substring(1)));
            instructor.addRightPanel(new newExam(instructor, exam));
        }
    }
}
