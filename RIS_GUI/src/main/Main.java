package main;

import main.shapes.Circle;
import main.shapes.Star;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main extends JPanel {

    private List<Object> shapes = new ArrayList<>();
    private Random random = new Random();


    public Main(int i, String shape) {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(400, 400));

        switch (shape) {
            case "Circles":
                for (int j = 0; j < i; j++) {
                    addCircle(390, 390);
                }
                break;
            case "Stars":
                for (int j = 0; j < i; j++) {
                    addStar(380, 380);
                }
                break;
            case "Both":
                int mid = i / 2;
                for (int j = 0; j < mid; j++) {
                    addCircle(390, 390);
                }
                for (int j = mid; j < i; j++) {
                    addStar(380, 380);
                }
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Object s : shapes) {
            if (s instanceof Circle) {
                ((Circle) s).draw(g);
            } else if (s instanceof Star) {
                ((Star) s).draw(g);
            }
        }
    }

    public void addCircle(int maxX, int maxY) {
        shapes.add(new Circle(random.nextInt(maxX), random.nextInt(maxY)));
        repaint();
    }

    public void addStar(int maxX, int maxY) {
        shapes.add(new Star(random.nextInt(maxX), random.nextInt(maxY)));
        repaint();
    }


    public static void main(String[] argv) {

        String shapeAmount = JOptionPane.showInputDialog(null,
                "How many shapes?", "Random Shapes...", JOptionPane.PLAIN_MESSAGE);
        int amount = Integer.parseInt(shapeAmount);

        String shapes[] = {"Stars", "Circles", "Both"};
        String shape = (String) JOptionPane.showInputDialog(null,
                "Pick the shape you want", "Random Shapes...",
                JOptionPane.PLAIN_MESSAGE, null, shapes, shapes[0]);

        JFrame frame = new JFrame();
        frame.add(new Main(amount, shape));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
