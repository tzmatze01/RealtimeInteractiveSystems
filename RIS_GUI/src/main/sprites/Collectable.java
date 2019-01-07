package main.sprites;

import main.sprites.type.MovingObject;

public class Collectable extends MovingObject {


    public Collectable(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy) {
        super(imgFileName, imageWidth, imageHeight, xPos, yPos, energy);
    }
}
