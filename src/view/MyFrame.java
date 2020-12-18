package view;

import logic.Algorithm;
import logic.Rice;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFrame extends JFrame {
    private static final Font DEFAULT_FONT = new Font("Dialog", Font.BOLD, 18);
    private static final List<String> labelNames = Arrays.asList("Area", "Perimeter",
            "Majoraxis", "Minoraxis", "Eccentricity", "Convex area", "Extent");
    private static final List<String> classNames = Arrays.asList("Cammeo", "Osmancik");

    public MyFrame(Algorithm algorithm) {
        super("lab 2");
        setBounds(200, 200, 500, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel(new GridLayout(8, 2));
        JLabel[] jLabels = new JLabel[labelNames.size()];
        JTextField[] jTextFields = new JTextField[labelNames.size()];
        for (int i = 0; i < labelNames.size(); i++) {
            jTextFields[i] = new JTextField();
            jTextFields[i].setFont(DEFAULT_FONT);
            jLabels[i] = new JLabel(labelNames.get(i));
            jLabels[i].setFont(DEFAULT_FONT);
            jPanel.add(jLabels[i]);
            jPanel.add(jTextFields[i]);
        }
        JButton jButton = new JButton("Predict");
        jPanel.add(jButton);
        jButton.addActionListener(e -> {
            if (Arrays.stream(jTextFields).anyMatch(jTextField -> jTextField.getText().isEmpty())) {
                showJOptionPane("Some fields are empty", "Error");
                return;
            }
            List<Double> features = new ArrayList<>();
            try {
                for (JTextField jTextField : jTextFields) {
                    features.add(Double.valueOf(jTextField.getText()));
                }
            } catch (NumberFormatException exception) {
                showJOptionPane("NumberFormatException", "Error");
                return;
            }
            Rice rice = new Rice(features, -1);
            showJOptionPane(classNames.get(algorithm.predict(rice)), "Prediction");
        });
        add(jPanel);
        setVisible(true);
    }

    void showJOptionPane(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
