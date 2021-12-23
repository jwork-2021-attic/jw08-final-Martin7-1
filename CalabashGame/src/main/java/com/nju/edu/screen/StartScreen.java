package com.nju.edu.screen;

import com.nju.edu.util.ReadImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * @author Zyi
 */
public class StartScreen extends JFrame implements ActionListener {

    private JButton startButton;
    private JButton loadButton;
    private static final int WIDTH = 1080;
    private static final int HEIGHT = 680;
    private final String windowTitle = "CalabashGame";
    private BackgroundPanel bg;
    private Container container;

    public StartScreen() {
        bg = new BackgroundPanel();
        bg.setBounds(0, 0, WIDTH, HEIGHT);
        container = this.getContentPane();
        container.add(bg);
        createScreen();
    }

    private void createScreen() {
        setLayout(null);
        startButton = new JButton("Start");
        loadButton = new JButton("Load");

        startButton.setBounds(450, 300, 100, 50);
        container.add(startButton);
        loadButton.setBounds(450, 500, 100, 50);
        container.add(loadButton);
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

    class BackgroundPanel extends JPanel {
        private BufferedImage bg = ReadImage.startBackground;

        public BackgroundPanel() {
            setOpaque(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, StartScreen.WIDTH, StartScreen.HEIGHT, this);
        }
    }
}
