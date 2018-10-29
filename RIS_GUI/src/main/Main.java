package main;

import main.shapes.*;
import main.shapes.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main extends JPanel {

    private List<Object> shapes = new ArrayList<>();
    private Random random = new Random();


    public Main(int i, String shape) {
        setBackground(Color.WHITE);
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


    // this overrideed methoded draws the Graphics2D Objects in shapes
    // g is pane itself?
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Object s : shapes) {
            if (s instanceof Circle) {
                ((Circle) s).draw(g);
            } else if (s instanceof Star) {
                ((Star) s).draw(g);
            } else if (s instanceof Image) {
                ((Image) s).draw(g);
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

    public void addImage(int x, int y, String path)
    {
        shapes.add(new Image(x, y, path));
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

        Main main = new Main(amount, shape);
        main.addImage(50, 50, "src/main/resources/player.png");


        frame.add(main);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
