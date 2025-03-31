package Chart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart extends JFrame {

    private JPanel contentPane;
    private JPanel panel;
    private DefaultCategoryDataset lineChartData;
    private JFreeChart lineChart;
    private CategoryPlot linechrt;
    private ChartPanel linePanel;
    private JButton back, saveImg;
    private JFrame frame;
    private String ename;

    public LineChart(String ename, double data[]) {
        this.ename = ename;
        this.frame = this;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        back = new JButton("Back < ");
        back.setBounds(900, 425, 80, 25);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
                // Code to move back
            }
        });

        saveImg = new JButton("Save As Image ");
        saveImg.setBounds(400, 425, 200, 25);
        saveImg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                saveImage(ename);
            }
        });

        contentPane.add(back);
        contentPane.add(saveImg);

        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 10, 1000, 400);
        contentPane.add(panel);
        panel.setLayout(new BorderLayout(0, 0));

        generateLineChart(data);
        frame.setVisible(true);
    }

    private void generateLineChart(double data[]) {
        lineChartData = new DefaultCategoryDataset();

        int range = 100 / data.length;
        for (int i = 0; i < data.length; i++) {
            lineChartData.setValue(data[i], "Percentage Of Students",
                    "" + (range * i) + " - " + (range * (i + 1)) + "%");
        }

        lineChart = ChartFactory.createLineChart(
                "Line Chart : " + ename,
                "Range Of Marks",
                "Percentage Of Students",
                lineChartData,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        lineChart.getTitle().setPaint(Color.BLACK);
        lineChart.setBackgroundPaint(Color.WHITE);

        linechrt = lineChart.getCategoryPlot();
        linechrt.setRangeGridlinePaint(Color.BLUE);

        linePanel = new ChartPanel(lineChart);

        panel.removeAll();
        panel.add(linePanel, BorderLayout.CENTER);
        panel.validate();
    }

    private void saveImage(String ename) {
        try {
            String dirPath = "charts/";
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs(); // Ensure the directory exists
            }

            String filename = dirPath + ename.replaceAll("[^a-zA-Z0-9]", "_") + "_LineChart.png";
            File file = new File(filename);

            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            ChartUtils.saveChartAsPNG(file, lineChart, 1024, 400, info);

            System.out.println("Chart saved successfully at: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving chart: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    double percentage[] = {10, 15.5, 30.6, 25.1, 18.8};
                    LineChart frame = new LineChart("Sample Exam", percentage);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
