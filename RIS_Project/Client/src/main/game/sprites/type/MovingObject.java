package main.game.sprites.type;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.util.HashSet;
import java.util.Set;


public abstract class MovingObject {

    private int id;

    private ObjectType type;
    private Image image;
    //private Image hb_image;

    private int w;
    private int h;

    protected double xPos;
    protected double yPos;

    private int energy;

    private boolean toDelete;
    protected int gamePoints;

    private Set<Point> relevantPoints;


    public MovingObject(int id, ObjectType type, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy)
    {
        this.id = id;
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

        //calcRelevantPixels(hb_image);
    }

    private void loadImage(String imgFileName)
    {
        //String hitboxFileName = imgFileName + ".jpg";


        ImageIcon ii = new ImageIcon("Client/src/main/resources/"+imgFileName+".png");
        //ImageIcon hb_ii = new ImageIcon("Server/src/main/game/resources/"+hitboxFileName);

        image = ii.getImage().getScaledInstance(w, h, 0);
        //hb_image = hb_ii.getImage().getScaledInstance(w, h, 0);
    }

    public void move() { }


    public Image getImage() {
        return image;
    }

    public int getX()
    {
        return (int) this.xPos;
    }

    public int getY()
    {
        return (int) this.yPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public int getWidth()
    {
        return this.w;
    }

    public int getHeight()
    {
        return this.h;
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

    /*
    public void calcRelevantPixels(Image hb_image)
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


        // check if last pixel was black, or end of row, to only store 'outline' pixels
        boolean lastPixelBlack = false;
        boolean currentPixelBlack = false;

        relevantPoints = new HashSet<>();

        for(int height = 0; height < h; height++)
        {
            for(int width = 0; width < w; width++)
            {
                int pos = (height * w) + width;

                //if pixel is black -> in hitbox
                if (!pixelIsWhite(pixels, pos))
                {
                    currentPixelBlack = true;

                    // if pixel in row to top or bottom is white -> add pixel
                    if(pixelIsWhite(pixels, pos - w) || pixelIsWhite(pixels, pos + w))
                        relevantPoints.add(new Point(width, height));
                        // first or last black pixel in row
                    else if(width == 0 || width == (w - 1))
                        relevantPoints.add(new Point(width, height));
                    if(!lastPixelBlack)
                        relevantPoints.add(new Point(width, height));
                }
                else
                    currentPixelBlack = false;

                lastPixelBlack = currentPixelBlack;
            }
        }
    }

    // white is better, because some pixels might be grey-ish
    private boolean pixelIsWhite(int[] pixels, int pos)
    {
        if(pos < 0 || pos > pixels.length)
            return true;

        int blue = pixels[pos] & 0xff;
        int green = (pixels[pos] & 0xff00) >> 8;
        int red = (pixels[pos] & 0xff0000) >> 16;
        //int alpha = (pixels[pos] & 0x000000FF) >> 24;

        if(blue == 255 && green == 255 && red == 255)
            return true;

        return false;
    }

    public Set<Point> getHitboxPoints() {

        // TODO rotation for player

        // add current position to points
        Set<Point> hitboxPoints = new HashSet<>();

        for(Point p : relevantPoints)
        {
            int currX = (int)(p.x + xPos - (getWidth() / 2));
            int currY = (int)(p.y + yPos - (getHeight() / 2));

            hitboxPoints.add(new Point(currX, currY));
        }

        return hitboxPoints;
    }

    public boolean detectCollision(Set<Point> otherHitboxPoints)
    {
        for(Point ownHitboxPoint : getHitboxPoints())
        {
            for(Point otherHitboxPoint : otherHitboxPoints)
            {
                if(ownHitboxPoint.x == otherHitboxPoint.x && ownHitboxPoint.y == otherHitboxPoint.y)
                {
                    return true;
                }
            }
        }

        return false;
    }
    */

    public int getId() {
        return id;
    }
}
