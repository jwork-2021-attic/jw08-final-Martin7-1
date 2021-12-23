package com.nju.edu.screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Zyi
 */
public class StartScreen extends JFrame implements ActionListener {

    private JButton startButton;
    private JButton loadButton;
    private static final int WIDTH = GameScreen.getWid();
    private static final int HEIGHT = GameScreen.getHei();
    private final String windowTitle = "CalabashGame";

    public StartScreen() {
        createScreen();
    }

    private void createScreen() {
        startButton = new JButton("Start");
        loadButton = new JButton("Load");
        this.setLocation(450, 300);
        this.add(startButton);
        this.add(loadButton);
        startButton.addActionListener(this);
        loadButton.addActionListener(this);
        setSize(WIDTH, HEIGHT);
        setTitle(this.windowTitle);
        setLocationRelativeTo(null);
        // setIconImage()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(startButton)) {
            this.dispose();
            new GameScreen("CalabashGame", 30, Color.WHITE);
        } else if (e.getSource().equals(loadButton)) {
            // 加载
            this.dispose();
            new GameScreen("CalabashGame", 30, Color.WHITE);
        }
    }

    public static void main(String[] args) {
        new StartScreen();
    }
}
