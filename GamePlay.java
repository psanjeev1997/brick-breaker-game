package com.mycompany.brick_breaker_game;
/**
 *
 * @author Pattamu Sanjeev
 */
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Gameplay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer Timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2; //because it's a rectangle and ballPosX is 120 and ballPosY is 350 intially
    private MapGenerator map;
    
    public Gameplay(){
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer = new Timer(delay, this);//to start a timer with a particular friction of value delay (ms)
        Timer.start();
    }

    public void paint(Graphics g){
        //for whole rectangle frame
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        
        map.draw((Graphics2D) g);
        
        //for boarders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(684, 0, 3, 592);
        
        //for score display
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 570, 37);
        
        //for slider
        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100, 8);
        
        //for ball
        g.setColor(Color.white);
        g.fillOval(ballPosX, ballPosY, 20, 20);
        
        if(ballPosY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("Monospaced", Font.BOLD, 30));
            g.drawString("Score:" + score, 254, 300);
            g.drawString("GAME OVER", 245, 340);
            
            g.setFont(new Font("Monospaced", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 140, 380);
        }
        
        if(totalBricks == 0){
            play = false;
            ballYdir = -2;
            ballXdir = -1;
            g.setColor(Color.white);
            g.setFont(new Font("Monospaced",Font.BOLD,30));
            g.drawString("Score:" + score, 250, 300);
            g.drawString("GAME FINISHED", 230, 340);

            g.setFont(new Font("Monospaced", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 150, 380);
        }
        
        g.dispose();
        
    } 
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();

        if (play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            A: //to break from all loops
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int bricksWidth = map.brickWidth;
                        int bricksHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickrect = rect;

                        if (ballrect.intersects(brickrect)) {
                            map.setBricksValue(0, i, j);
                            totalBricks--;
                            score += 5;
                            if (ballPosX + 19 <= brickrect.x || ballPosX + 1 >= brickrect.x + bricksWidth) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                    
                }
            }

            ballPosX += ballXdir;
            ballPosY += ballYdir;
            if (ballPosX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballPosY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballPosX > 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 310;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }

    }

    public void moveRight(){
        play = true;
        playerX += 20;
    }
    
    public void moveLeft(){
        play = true;
        playerX -= 20;
    }
    
}
