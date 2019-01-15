package main.game.sprites.type;

public enum ObjectType {

    METEORITE(0),
    PLAYER_BEAM(1),
    ENEMY_BEAM(2),
    COLLECTABLE(3),
    PLAYER(4),
    ENEMY(5);
    private int type;

    ObjectType(int type)
    {
        this.type = type;
    }

}
