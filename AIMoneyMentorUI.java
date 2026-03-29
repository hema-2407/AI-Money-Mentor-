package com.example;

import javax.swing.*;
import java.awt.*;

public class AIMoneyMentorUI {

    public static void main(String[] args) {
        createUI();
    }

    public static void createUI() {

        JFrame frame = new JFrame("AI Money Mentor");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Input fields
        JTextField incomeField = new JTextField(10);
        JTextField expenseField = new JTextField(10);
        JTextField savingsField = new JTextField(10);
        JTextField debtField = new JTextField(10);

        JButton button = new JButton("Calculate");

        JTextArea resultArea = new JTextArea(8, 50);
        resultArea.setEditable(false);

        JProgressBar scoreBar = new JProgressBar(0, 100);
        scoreBar.setStringPainted(true);

        // Add components
        frame.add(new JLabel("Income:")); frame.add(incomeField);
        frame.add(new JLabel("Expenses:")); frame.add(expenseField);
        frame.add(new JLabel("Savings:")); frame.add(savingsField);
        frame.add(new JLabel("Debt:")); frame.add(debtField);

        frame.add(button);
        frame.add(scoreBar);
        frame.add(resultArea);

        button.addActionListener(e -> {
            try {
                double income = Double.parseDouble(incomeField.getText());
                double expenses = Double.parseDouble(expenseField.getText());
                double savings = Double.parseDouble(savingsField.getText());
                double debt = Double.parseDouble(debtField.getText());

                int score = calculateScore(income, expenses, savings, debt);
                scoreBar.setValue(score);

                String suggestions = getSuggestions(income, expenses, savings, debt, score);

                resultArea.setText("\nScore: " + score + "/100\n\n" + suggestions);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid numbers!");
            }
        });

        frame.setVisible(true);
    }

    public static int calculateScore(double income, double expenses, double savings, double debt) {

        int score = 0;

        double savingsRatio = savings / income;
        if (savingsRatio >= 0.3) score += 40;
        else if (savingsRatio >= 0.2) score += 30;
        else if (savingsRatio >= 0.1) score += 20;
        else score += 10;

        double expenseRatio = expenses / income;
        if (expenseRatio <= 0.5) score += 30;
        else if (expenseRatio <= 0.7) score += 20;
        else score += 10;

        double debtRatio = debt / income;
        if (debtRatio <= 0.5) score += 30;
        else if (debtRatio <= 1.0) score += 20;
        else score += 10;

        return score;
    }

    public static String getSuggestions(double income, double expenses, double savings, double debt, int score) {

        String s = " ";

        if (score >= 80) s += "Excellent financial health!\n";
        else if (score >= 60) s += "Good, but can improve.\n";
        else s += "Needs improvement.\n";

        if (expenses > 0.7 * income) s += "- Reduce expenses\n";
        if (savings < 0.2 * income) s += "- Increase savings\n";
        if (debt > income) s += "- Reduce debt\n";

        s += "- Start investments\n- Build emergency fund";

        return s;
    }
}