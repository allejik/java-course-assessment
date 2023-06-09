package org.bundestag;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class VotesViewerApp extends ApplicationFrame {

    private JPanel chartsPanel;

    private GridLayout chartsLayout;

    /**
     * Constructs a new application frame.
     *
     * @param title the frame title.
     */
    public VotesViewerApp(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUI();
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
    }

    private PieDataset createVotesDataSet(Stats statistics) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        if (statistics.getVotes() != null) {
            for (String candidateName : statistics.getVotes().keySet()) {
                Integer votes = statistics.getVotes().get(candidateName);
                dataset.setValue(candidateName, votes);
            }
        }
        return dataset;
    }

    private JFreeChart createVoteChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart("Votes", dataset, true, true, false);
        StandardPieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", NumberFormat.getInstance(), NumberFormat.getPercentInstance());
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(labelGenerator);
        return chart;
    }

    private JPanel createVoteChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        return chartPanel;
    }

    private JPanel createGenderChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        return chartPanel;
    }


    private CategoryDataset createGenderDataSet(Stats statistics) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (statistics.getGender() != null) {
            for (String voterGender : statistics.getGender().keySet()) {
                Integer amount = statistics.getGender().get(voterGender);
                dataset.setValue(amount, "Gender", voterGender);
            }
        }
        return dataset;
    }


    private JFreeChart createGenderChart(CategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Voters genders",
                "",
                "Voters genders",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }

    private CategoryDataset createAgeDataSet(Stats statistics) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, Integer> data = new LinkedHashMap<>();

        data.put("<20", 0);

        if (statistics.getAge() != null) {
            int step = 10;
            for (Integer voterAge : statistics.getAge().keySet()) {
                Integer amount = statistics.getAge().get(voterAge);
                String categoryName;
                if (voterAge < 20) {
                    categoryName = "<20";
                } else {
                    int low = voterAge / step * step; //round
                    int high = ((voterAge / step) + 1) * step;
                    categoryName = low + " - " + high;
                }
                data.put(categoryName, data.getOrDefault(categoryName, 0) + amount);
            }
        }

        for (String category : data.keySet()) {
            Integer amount = data.get(category);
            dataset.setValue(amount, "Age", category);
        }

        return dataset;
    }

    private JFreeChart createAgeChart(CategoryDataset dataset) {
         JFreeChart barChart = ChartFactory.createBarChart("Age",
                "Age", "Amount", dataset);
        return barChart;
    }

    private JPanel createAgeChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        return chartPanel;
    }

    private CategoryDataset createResidenceDataset(Stats statistics) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (String residence : statistics.getResidence().keySet()) {
            Integer amount = statistics.getResidence().get(residence);
            dataset.addValue(amount, "Residence", residence);
        }

        return dataset;
    }

    private JFreeChart createResidenceChart(CategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart("Residence",
                "Residence", "Amount", dataset);
        barChart.getCategoryPlot().getDomainAxis().setTickLabelFont(new Font("Dialog", Font.BOLD, 13));
        barChart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        return barChart;
    }

    private JPanel createResidenceChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        return chartPanel;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu jMenu = new JMenu("File");

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener((event) -> System.exit(0));

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener((event) -> openFileCommand());

        jMenu.add(openItem);
        jMenu.add(quitItem);

        menuBar.add(jMenu);

        return menuBar;
    }


    private void createUI() {
        setJMenuBar(createMenuBar());
        setLocationByPlatform(true);

        chartsPanel = new JPanel();
        chartsLayout = new GridLayout(0, 1);
        chartsPanel.setLayout(chartsLayout);

        getContentPane().add(chartsPanel, BorderLayout.CENTER);

        setDefaultView();

    }

    private void openFileCommand() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {


                chartsLayout.setColumns(2);
                chartsLayout.setRows(2);
                chartsPanel.removeAll();
                pack();

                Stats statistics = readStatistics(file);

                JPanel voteChartPanel = createVoteChartPanel(createVoteChart(createVotesDataSet(statistics)));

                JPanel genderChartPanel = createGenderChartPanel(createGenderChart(createGenderDataSet(statistics)));

                JPanel ageChartPanel = createAgeChartPanel(createAgeChart(createAgeDataSet(statistics)));

                JPanel populationChartPanel = createResidenceChartPanel(createResidenceChart(createResidenceDataset(statistics)));

                chartsPanel.add(voteChartPanel);
                chartsPanel.add(genderChartPanel);
                chartsPanel.add(ageChartPanel);
                chartsPanel.add(populationChartPanel);


                chartsPanel.setPreferredSize(new Dimension(1000, 800));

                pack();


            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        e.getMessage(),
                        "Opening error",
                        JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
                setDefaultView();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "File not selected",
                    "File not selected",
                    JOptionPane.WARNING_MESSAGE);
            setDefaultView();
        }

    }

    private void setDefaultView() {
        int width = getWidth();
        int height = getHeight();
        FlowLayout layout = new FlowLayout();
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(layout);
        logoPanel.add(new JLabel("Open a stats.json file from the menu..."));
        chartsLayout.setColumns(1);
        chartsLayout.setRows(0);
        chartsPanel.removeAll();
        chartsPanel.add(logoPanel);
        setSize(width, height);
        pack();
    }

    public Stats readStatistics(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper.readValue(new FileInputStream(file), Stats.class);
    }

    public static void main(String[] args) {
        VotesViewerApp app = new VotesViewerApp("Votes Viewer");
        app.setVisible(true);
    }

    private static class Stats {
        private Map<String, Integer> votes = new HashMap<>();

        private Map<Integer, Integer> age = new HashMap<>();

        private Map<String, Integer> gender = new HashMap<>();

        private Map<String, Integer> residence = new HashMap<>();


        @JsonGetter("candidateVotes")
        public Map<String, Integer> getVotes() {
            return votes;
        }

        @JsonGetter("voterAge")
        public Map<Integer, Integer> getAge() {
            return age;
        }

        @JsonGetter("voterGender")
        public Map<String, Integer> getGender() {
            return gender;
        }

        @JsonGetter("voterResidence")
        public Map<String, Integer> getResidence() {
            return residence;
        }
    }
}
