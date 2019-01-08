package main.sprites.type;

import javafx.scene.image.PixelFormat;
import main.sprites.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashSet;
import java.util.Set;

import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;

public abstract class MovingObject {

    private ObjectType type;
    private Image image;
    private Image hb_image;

    private int w;
    private int h;

    // moving object has two y Pos, which are randomly generated, to simulate a non straight moving object
    protected double xPos;
    protected double yPos;

    private int energy;

    private boolean toDelete;
    protected int gamePoints;

    private Set<Point> relevantPoints;


    public MovingObject(ObjectType type, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy, int gamePoints)
    {
        this.type = type;

        this.w = imageWidth;
        this.h = imageHeight;

        // objects always start on right side of screen
        this.xPos = xPos;
        this.yPos = yPos;

        this.energy = energy;

        this.toDelete = false;

        this.gamePoints = gamePoints;

        loadImage(imgFileName);

        getRelevantPixels();
    }

    private void loadImage(String imgFileName)
    {
        String hitboxFileName = imgFileName + ".jpg";

        // players share same hitbox
        if(imgFileName.contains("player"))
            hitboxFileName = "player.jpg";

        ImageIcon ii = new ImageIcon("src/main/resources/img/display/"+imgFileName+".png");
        ImageIcon hb_ii = new ImageIcon("src/main/resources/img/hitbox/"+hitboxFileName);

        image = ii.getImage().getScaledInstance(w, h, 0);
        hb_image = hb_ii.getImage().getScaledInstance(w, h, 0);
    }

    public void move() { }


    public int getX()
    {
        return (int) this.xPos;
    }

    public int getY()
    {
        return (int) this.yPos;
    }

    public int getWidth()
    {
        return this.w;
    }

    public int getHeight()
    {
        return this.h;
    }

    public Image getImage()
    {
        return this.image;
    }

    public void addEnergy(int amount)
    {
        this.energy += amount;
    }

    public void reduceEnergy(int amount)
    {
        this.energy -= amount;
    }

    public int getEnergy() {
        return energy;
    }

    public ObjectType getType() {
        return type;
    }

    public int getGamePoints() {
        return gamePoints;
    }

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    // for collision detection
    public Rectangle getRectangleBounds() {

        // TODO http://zetcode.com/tutorials/javagamestutorial/collision/

        // TODO with intersects https://docs.oracle.com/javase/7/docs/api/java/awt/Polygon.html

        return new Rectangle((int) xPos - getWidth() / 2, (int) yPos - getHeight() / 2, w, h);
    }


    private void getRelevantPixels()
    {
        int[] pixels = new int[w * h];

        PixelGrabber pg = new PixelGrabber(hb_image, 0, 0, w, h, pixels, 0, w);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            throw new IllegalStateException("Error: Interrupted Waiting for Pixels");
        }
        if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
            throw new IllegalStateException("Error: Image Fetch Aborted");
        }

        relevantPoints = new HashSet<>();

        for(int height = 0; height < h; height++)
        {
            for(int width = 0; width < w; width++)
            {
                int pos = (height * w) + width;

                int blue = pixels[pos] & 0xff;
                int green = (pixels[pos] & 0xff00) >> 8;
                int red = (pixels[pos] & 0xff0000) >> 16;
                //int alpha = (pixels[pos] & 0x000000FF) >> 24;

                //if pixel is white - out of hitbox
                if (blue == 0 && green == 0 && red == 0)
                {
                    relevantPoints.add(new Point(width, height));
                }

            }
        }
    }
}
