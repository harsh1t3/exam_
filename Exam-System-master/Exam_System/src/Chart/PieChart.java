package Chart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
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
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart extends JFrame {
    private JPanel contentPane;
    private JPanel panel = new JPanel();
    private DefaultPieDataset pieChartData;
    private JFreeChart pieChart;
    private PiePlot piechrt;
    private ChartPanel piePanel;
    private JButton back, saveimg;
    private JFrame frame;
    private String ename;

    public PieChart(String ename, double data[]) {
        this.ename = ename;
        frame = this;
        pieChartData = new DefaultPieDataset();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        back = new JButton("Back < ");
        back.setBounds(500, 425, 80, 25);
        back.addActionListener(e -> frame.dispose());
        contentPane.add(back);

        saveimg = new JButton("Save As Image ");
        saveimg.setBounds(200, 425, 200, 25);
        saveimg.addActionListener(e -> saveImage(ename));
        contentPane.add(saveimg);

        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 10, 600, 400);
        contentPane.add(panel);
        panel.setLayout(new BorderLayout(0, 0));
        generatePieChart(data);
        frame.setVisible(true);
    }

    private void generatePieChart(double data[]) {
        int range = 100 / data.length;
        for (int i = 0; i < data.length; i++) {
            pieChartData.setValue("Range " + (range * i) + " - " + (range * (i + 1)) + "%", data[i]);
        }

        pieChart = ChartFactory.createPieChart("Pie Chart: " + ename, pieChartData, true, true, false);
        pieChart.getTitle().setPaint(Color.BLACK);
        pieChart.setBackgroundPaint(Color.WHITE);
        piechrt = (PiePlot) pieChart.getPlot();

        piePanel = new ChartPanel(pieChart);
        panel.removeAll();
        panel.add(piePanel, BorderLayout.CENTER);
        panel.validate();
    }

    private void saveImage(String ename) {
        try {
            File chartsDir = new File("charts");
            if (!chartsDir.exists()) {
                chartsDir.mkdirs();
            }
            String filename = "charts/" + ename.replaceAll("[^a-zA-Z0-9]", "_").substring(0, Math.min(20, ename.length())) + "_PieChart.png";
            File file = new File(filename);
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            ChartUtils.saveChartAsPNG(file, pieChart, 600, 400, info);
            System.out.println("Chart saved as: " + filename);
        } catch (Exception e) {
            System.err.println("Error saving chart: " + e.getMessage());
        }
    }
}
