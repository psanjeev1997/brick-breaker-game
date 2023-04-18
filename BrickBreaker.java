package com.mycompany.brick_breaker_game;

/**
 *
 * @author Pattamu Sanjeev
 */
import javax.swing.JFrame;
public class BrickBreaker {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Gameplay gameplay = new Gameplay();
        frame.setBounds(10, 10, 700, 600);
        frame.setTitle("Brick Breaker");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gameplay);
    }
}
