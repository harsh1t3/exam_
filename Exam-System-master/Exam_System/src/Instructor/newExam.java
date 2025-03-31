package Instructor;

import Login.dbConnector;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class newExam extends JPanel {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    int panelwidth = ((int) width) * 3 / 4;
    int panelheight = (int) height;

    JButton addbtn, editbtn, deletebtn, save, viewbtn, back, saveexam, otherdetails;
    JLabel lduration;
    static dbConnector dbc = new dbConnector(); // Ensure dbConnector exists in your project
    boolean backandsave, edit;
    List listOfQuestions;
    JTextArea question, questionMCQ;
    JTextField choice1, choice2, choice3, choice4;
    JLabel lchoice1, lchoice2, lchoice3, lchoice4, lcorrectchoice, lquestion, llistofquestions;
    JLabel viewquestion, viewchoice1, viewchoice2, viewchoice3, viewchoice4, viewcorrectchoice;
    JComboBox<String> correctchoice;
    Question q;
    static Exam exam;
    Instructor instructor;
    int qid = 0;

    public newExam(Instructor a) {
        instructor = a;
        try {
            BufferedImage bi = ImageIO.read(new File("next.jpg"));
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
        setLayout(null);
        setBounds((int) (width / 4), 0, (int) ((width * 3) / 4), (int) height);
        Color c = new Color(205, 205, 245);
        setBackground(c);

        exam = new Exam();
        listOfQuestions = new List();
        JScrollPane p = new JScrollPane(listOfQuestions);
        p.setBounds(60, 40, panelwidth - 100, 2 * panelheight / 3);
        p.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        p.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(p);

        addbtn = new JButton("Add Question");
        editbtn = new JButton("Edit Question");
        deletebtn = new JButton("Delete Question");
        save = new JButton("Save");
        viewbtn = new JButton("View Question");
        back = new JButton("< Back");
        saveexam = new JButton("Save Exam");
        otherdetails = new JButton("Other Details");

        choice1 = new JTextField("");
        choice2 = new JTextField("");
        choice3 = new JTextField("");
        choice4 = new JTextField("");

        lquestion = new JLabel("Question : ");
        lchoice1 = new JLabel("Choice 1 : ");
        lchoice2 = new JLabel("Choice 2 : ");
        lchoice3 = new JLabel("Choice 3 : ");
        lchoice4 = new JLabel("Choice 4 :");
        lcorrectchoice = new JLabel("Correct Choice : ");
        llistofquestions = new JLabel("List Of Questions  : ");
        lduration = new JLabel("Duration  : ");

        correctchoice = new JComboBox<>(new String[]{"1", "2", "3", "4"});

        question = new JTextArea();
        questionMCQ = new JTextArea();

        Font f = new Font("Century Gothic", Font.BOLD, 20);
        llistofquestions.setBounds(60, 10, panelwidth - 100, 30);
        llistofquestions.setFont(f);
        listOfQuestions.setBounds(60, 40, panelwidth - 100, 2 * panelheight / 3);

        f = new Font("Century Gothic", Font.HANGING_BASELINE, 15);
        addbtn.setFont(f);
        editbtn.setFont(f);
        viewbtn.setFont(f);
        deletebtn.setFont(f);
        saveexam.setFont(f);
        otherdetails.setFont(f);

        addbtn.setBounds(150 - 20, 2 * panelheight / 3 + 60, 130 + 30, 30);
        editbtn.setBounds(150 + 200 - 20, 2 * panelheight / 3 + 60, 130 + 30, 30);
        viewbtn.setBounds(150 + 2 * 200 - 20, 2 * panelheight / 3 + 60, 130 + 30, 30);
        deletebtn.setBounds(150 + 3 * 200 - 20, 2 * panelheight / 3 + 60, 130 + 30, 30);
        saveexam.setBounds(150 + 2 * 150 - 20, 2 * panelheight / 3 + 130, 130 + 30, 30);
        otherdetails.setBounds(150 - 20, 2 * panelheight / 3 + 130, 130 + 30, 30);

        question.setBounds(50, 30, (int) (width * 3 / 4) - 100, 500);
        questionMCQ.setBounds(150, 30, panelwidth - 200, panelheight / 3 - 30);
        choice1.setBounds(150, panelheight / 2 - 100, panelwidth - 200, 40);
        choice2.setBounds(150, panelheight / 2 - 100 + 50, panelwidth - 200, 40);
        choice3.setBounds(150, panelheight / 2 - 100 + 2 * 50, panelwidth - 200, 40);
        choice4.setBounds(150, panelheight / 2 - 100 + 3 * 50, panelwidth - 200, 40);
        correctchoice.setBounds(200, panelheight / 2 - 100 + 4 * 50, 50, 30);

        lquestion.setBounds(50, 10, panelwidth - 100, 250);
        lchoice1.setBounds(50, panelheight / 2 - 100, 100, 40);
        lchoice2.setBounds(50, panelheight / 2 - 100 + 50, 100, 40);
        lchoice3.setBounds(50, panelheight / 2 - 100 + 2 * 50, 100, 40);
        lchoice4.setBounds(50, panelheight / 2 - 100 + 3 * 50, 100, 40);
        lcorrectchoice.setBounds(50, panelheight / 2 - 100 + 4 * 50, 150, 40);

        save.setBounds(panelwidth / 2 - 100, panelheight / 2 - 100 + 5 * 50, 130, 30);
        back.setBounds(panelwidth / 2 - 100, panelheight / 2 - 100 + 6 * 50, 130, 30);

        addbtn.addActionListener(new eventAction());
        save.addActionListener(new eventAction());
        deletebtn.addActionListener(new eventAction());
        editbtn.addActionListener(new eventAction());
        viewbtn.addActionListener(new eventAction());
        back.addActionListener(new eventAction());
        saveexam.addActionListener(new eventAction());
        otherdetails.addActionListener(new eventAction());

        listOfQuestions.addItemListener(new eventItem());
        addButtons();
        instructor.setVisible(false);
        instructor.setVisible(true);
        backandsave = false;
        assignId();
        getMinQuestionId();
    }

    public void askExamName() {
        String ename = JOptionPane.showInputDialog(this, "Enter Name Of Examination");
        if (ename != null && !ename.isEmpty()) {
            exam.setName(ename);
            llistofquestions.setText(ename);
        } else {
            JOptionPane.showMessageDialog(this, "Exam name cannot be empty.");
        }
    }

    void assignId() {
        String qry = "SELECT MAX(eid) FROM EXAM";
        ResultSet rs;
        try {
            Statement stmt = dbc.getConnection().createStatement();
            rs = stmt.executeQuery(qry);
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("MAX ID " + id);
            exam.setExamId(id + 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void getMinQuestionId() {
        String qry = "SELECT MAX(qid) FROM QUESTION";
        ResultSet rs;
        try {
            Statement stmt = dbc.getConnection().createStatement();
            rs = stmt.executeQuery(qry);
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("Minimum Question ID " + id);
            qid = id + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void addButtons() {
        add(llistofquestions);
        add(listOfQuestions);
        add(addbtn);
        add(editbtn);
        add(viewbtn);
        add(deletebtn);
        add(saveexam);
        add(otherdetails);
        clearText();
    }

    void clearText() {
        questionMCQ.setText("");
        choice1.setText("");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        correctchoice.setSelectedIndex(0);
    }

    class eventItem implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent arg0) {
            if (listOfQuestions.getSelectedIndex() == -1) {
                editbtn.setEnabled(false);
                deletebtn.setEnabled(false);
                viewbtn.setEnabled(false);
            } else {
                editbtn.setEnabled(true);
                deletebtn.setEnabled(true);
                viewbtn.setEnabled(true);
            }
        }
    }

    void displayQuestion() {
        int type = askType();
        q.setType(type);
        if (type == 0) {
            newMCQ();
        }
    }

    void newMCQ() {
        removeAll();
        add(questionMCQ);
        add(choice1);
        add(choice2);
        add(choice3);
        add(choice4);
        add(correctchoice);
        add(lquestion);
        add(lchoice1);
        add(lchoice2);
        add(lchoice3);
        add(lchoice4);
        add(lcorrectchoice);
        add(save);
        add(back);
        repaint();
        setVisible(false);
        setVisible(true);
    }

    int askType() {
        String[] type = {"Multiple Choice Question", "Theory Question"};
        return JOptionPane.showOptionDialog(this, "Select Type Of Question ", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, type, type[0]);
    }

    void editQuestion() {
        int item = listOfQuestions.getSelectedIndex();
        if (item >= 0) {
            Question ques[] = exam.getQuestions();
            q = new Question();
            q.setQuestionId(ques[item].getQuestionId());
            questionMCQ.setText(ques[item].getQuestion());
            choice1.setText(ques[item].getAnswer1());
            choice2.setText(ques[item].getAnswer2());
            choice3.setText(ques[item].getAnswer3());
            choice4.setText(ques[item].getAnswer4());
            correctchoice.setSelectedIndex(ques[item].getCorrectAns() - 1);
            exam.deleteQuestion(item);
            backandsave = true;
            repaint();
        }
    }

    void viewQuestion() {
        int item = listOfQuestions.getSelectedIndex();
        if (item >= 0) {
            Question q[] = exam.getQuestions();
            questionMCQ.setText(q[item].getQuestion());
            choice1.setText(q[item].getAnswer1());
            choice2.setText(q[item].getAnswer2());
            choice3.setText(q[item].getAnswer3());
            choice4.setText(q[item].getAnswer4());
            questionMCQ.setEditable(false);
            choice1.setEditable(false);
            choice2.setEditable(false);
            choice3.setEditable(false);
            choice4.setEditable(false);

            add(questionMCQ);
            add(choice1);
            add(choice2);
            add(choice3);
            add(choice4);
            add(lquestion);
            add(lchoice1);
            add(lchoice2);
            add(lchoice3);
            add(lchoice4);
            add(back);
            backandsave = false;
            repaint();
        }
    }

    void deleteQuestion() {
        int item = listOfQuestions.getSelectedIndex();
        if (item >= 0) {
            listOfQuestions.remove(item);
            exam.deleteQuestion(item);
            displayList();
            repaint();
        }
    }

    void displayList() {
        listOfQuestions.removeAll();
        Question ques[] = exam.getQuestions();
        for (int i = 0; i < exam.getNumberOfQuestions(); i++) {
            listOfQuestions.add("Q" + ques[i].getQuestionId() + "   " + ques[i].getQuestion());
        }
        repaint();
    }

    void saveQuestion() {
        if (q == null) q = new Question();
        q.setQuestion(questionMCQ.getText());
        q.setAnswer1(choice1.getText());
        q.setAnswer2(choice2.getText());
        q.setAnswer3(choice3.getText());
        q.setAnswer4(choice4.getText());
        q.setCorrectAns(correctchoice.getSelectedIndex() + 1);

        if (!edit) {
            q.setQuestionId(qid++);
        }

        listOfQuestions.add("Q" + q.getQuestionId() + "  " + q.getQuestion());
        exam.addQuestion(q);
        clearText();
    }

    class eventAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addbtn) {
                q = new Question();
                displayQuestion();
            } else if (e.getSource() == save) {
                saveQuestion();
                removeAll();
                addButtons();
                displayList();
            } else if (e.getSource() == deletebtn) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Delete Question", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteQuestion();
                }
            } else if (e.getSource() == editbtn) {
                edit = true;
                editQuestion();
            } else if (e.getSource() == viewbtn) {
                viewQuestion();
            } else if (e.getSource() == back) {
                if (backandsave) saveQuestion();
                removeAll();
                addButtons();
                displayList();
                questionMCQ.setEditable(true);
                choice1.setEditable(true);
                choice2.setEditable(true);
                choice3.setEditable(true);
                choice4.setEditable(true);
                edit = false;
            } else if (e.getSource() == saveexam) {
                saveExamToDatabase();
            } else if (e.getSource() == otherdetails) {
                ExamDetails ob = new ExamDetails();
                instructor.setEnabled(false);
                ob.setVisible(true);
            }
        }

        private void saveExamToDatabase() {
            if (exam == null || exam.getDateTime() == null) {
                JOptionPane.showMessageDialog(null, "Exam details are incomplete!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Convert String to Timestamp
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp timestamp;
                try {
                    java.util.Date parsedDate = format.parse(exam.getDateTime());
                    timestamp = new Timestamp(parsedDate.getTime());
                } catch (ParseException pe) {
                    JOptionPane.showMessageDialog(null, "Invalid date format! Expected: YYYY-MM-DD HH:MM:SS", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String insertExamQuery = "INSERT INTO EXAM (eid, ename, edate_time, duration, estid, group_id) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = dbc.getConnection().prepareStatement(insertExamQuery);
                pst.setInt(1, exam.getExamId());
                pst.setString(2, exam.getName());
                pst.setTimestamp(3, timestamp);
                pst.setInt(4, exam.getDuration());
                pst.setInt(5, Instructor.instructorid);
                pst.setInt(6, exam.getGroup());
                pst.executeUpdate();

                String insertQuestionQuery = "INSERT INTO QUESTION (qid, question_text, qop1, qop2, qop3, qop4, qcorrect_answer) VALUES (?, ?, ?, ?, ?, ?, ?)";
                String insertExamQuestionQuery = "INSERT INTO EXAM_QUESTION (eid, qid) VALUES (?, ?)";
                for (Question que : exam.getQuestions()) {
                    PreparedStatement pstQuestion = dbc.getConnection().prepareStatement(insertQuestionQuery);
                    pstQuestion.setInt(1, que.getQuestionId());
                    pstQuestion.setString(2, que.getQuestion());
                    pstQuestion.setString(3, que.getAnswer1());
                    pstQuestion.setString(4, que.getAnswer2());
                    pstQuestion.setString(5, que.getAnswer3());
                    pstQuestion.setString(6, que.getAnswer4());
                    pstQuestion.setString(7, String.valueOf(que.getCorrectAns())); // Fixed: Convert int to String
                    pstQuestion.executeUpdate();

                    PreparedStatement pstExamQuestion = dbc.getConnection().prepareStatement(insertExamQuestionQuery);
                    pstExamQuestion.setInt(1, exam.getExamId());
                    pstExamQuestion.setInt(2, que.getQuestionId());
                    pstExamQuestion.executeUpdate();
                }
                JOptionPane.showMessageDialog(null, "Exam saved successfully!");
                ExamShowPanel esp = new ExamShowPanel(instructor);
                instructor.addRightPanel(esp);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving exam: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} // Added missing closing brace for the newExam class