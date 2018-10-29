package main.shapes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {


    private int x;
    private int y;
    private BufferedImage img;

    public Image(int x, int y, String path) {
        this.x = x;
        this.y = y;

        // add image
        try {
            this.img = ImageIO.read(new File("src/main/resources/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics g)
    {
        g.drawImage(this.img, this.x, this.y, null);
    }
}
