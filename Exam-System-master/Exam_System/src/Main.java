import com.formdev.flatlaf.FlatLightLaf;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Failed to initialize FlatLaf: " + e.getMessage());
        }

        // Launch the application
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JFreeChart Example with FlatLaf");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Add the chart panel
            frame.add(new ChartPanel(createChart()));

            // Set frame properties
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true);
        });
    }

    private static JFreeChart createChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(5, "Category", "A");
        dataset.addValue(7, "Category", "B");
        dataset.addValue(3, "Category", "C");

        return ChartFactory.createBarChart(
                "Bar Chart Example",
                "Category",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
    }
}