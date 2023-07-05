import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Ball extends JPanel {
    public static final int width = 20;
    public static final int speed = 10;
    public static Dimension window_size;
    public int x, y;
    public Color color;

    public Ball(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public static Color make_random_color() {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        Color color = new Color(r, g, b);
        return color;
    }

    public Ball() {
        int width = (int) window_size.getWidth();
        int height = (int) window_size.getHeight();

        this.x = (int) (Math.random() * width);
        this.y = (int) (Math.random() * height);
        this.color = make_random_color();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, width);

    }
}

class DisplayPanel extends JPanel {
    private ArrayList<Ball> balls;
    public int x_velocity, y_velocity;
    private Timer timer;
    private JButton button;

    private class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key_code = e.getKeyCode();
            if (key_code == KeyEvent.VK_A) {
                x_velocity = -1;
            } else if (key_code == KeyEvent.VK_D) {
                x_velocity = 1;
            } else if (key_code == KeyEvent.VK_W) {
                y_velocity = -1;
            } else if (key_code == KeyEvent.VK_S) {
                y_velocity = 1;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key_code = e.getKeyCode();
            if (key_code == KeyEvent.VK_A || key_code == KeyEvent.VK_D) {
                x_velocity = 0;
            } else if (key_code == KeyEvent.VK_W || key_code == KeyEvent.VK_S) {
                y_velocity = 0;
            }
        }
    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            for (Ball ball : balls) {
                ball.color = Ball.make_random_color();
            }
            repaint();
        }
    }

    private class MyTimerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (Ball ball : balls) {
                ball.x += Ball.speed * x_velocity;
                ball.y += Ball.speed * y_velocity;
            }
            repaint();
        }
    }

    private class MyButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            balls.add(new Ball());
            requestFocus();
        }
    }

    public DisplayPanel(Dimension window_size) {
        Ball.window_size = window_size;
        balls = new ArrayList<>();

        button = new JButton("Click Me for More Balls :)");
        button.addActionListener(new MyButtonActionListener());
        add(button);

        addKeyListener(new MyKeyListener());
        addMouseListener(new MyMouseListener());
        timer = new Timer(100, new MyTimerActionListener());
        timer.start();

        x_velocity = 0;
        y_velocity = 0;

        setFocusable(true);
        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 1000, 1000);
        for (Ball ball : balls) {
            ball.paintComponent(g);
        }
    }

}

public class EventLoopDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Event Loop Demo");
        frame.setSize(600, 400);
        frame.setLocation(150, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new DisplayPanel(frame.getSize()));
        frame.setVisible(true);
    }

}
