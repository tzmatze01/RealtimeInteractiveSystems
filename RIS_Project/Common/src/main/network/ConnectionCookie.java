package main.network;


import java.util.HashMap;
import java.util.Map;


public class ConnectionCookie {


    // TODO timer refresh for painting

    private static int SCREENWIDTH = 1200;
    private static int SCREENHEIGHT = 800;


    private Map<String, Integer> userNameID;
    private Map<Integer, Boolean> isPlaying;

    private int userID; // has to be set by client, after successful login by server
    private String userName;
    private boolean isLoggedIn;

    /*
    private Map<String, Player> players;
    private Map<Integer, Enemy> enemies;
    private List<MovingObject> movingObjects;
    */

    public ConnectionCookie() {

        // Server Cookie
        this.userNameID = new HashMap<>();
        this.isPlaying = new HashMap<>();

        this.userID = 0;
        this.userName = "";

    }


    public ConnectionCookie(int userID, String userName)
    {
        // Client Cookie
        this.userID = userID;
        this.userName = userName;
        this.isLoggedIn = true;

        this.userNameID = null;
    }



    // SERVER METHODS
    public void addUserPlaying(int userID,String userName)
    {
        this.userName = userName;
        this.isPlaying.put(userID, true);
        this.isLoggedIn = true;
        this.userID = userID;
    }

    public void setUserPlaying(int userID, boolean isPlaying) {
        this.isPlaying.put(userID, isPlaying);
    }

    public boolean isUserPlaying(int userID)
    {
        return this.isPlaying.get(userID);
    }

    public boolean isUserLoggedIn(String username)
    {
        return this.userNameID.containsKey(username);
    }

    public void addUser(String userName, int userID)
    {
        this.userNameID.put(userName, userID);
    }

    public int getUserID(String username)
    {
        return this.userNameID.get(username);
    }

    public int countRegisteredUsers()
    {
        return this.userNameID.size();
    }


    // CLIENT METHODS
    public int getOwnUserID() {
        return userID;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }


    // OTHER METHODS
    public static int getScreenWidth() {
        return SCREENWIDTH;
    }

    public static int getScreenHeight() {
        return SCREENHEIGHT;
    }

    public void setUserID(int userID) {
        this.userID = userID;
        this.isLoggedIn = true;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    /*PLAYER METHODS

    public void addPlayer(String playerName, Player player)
    {
        this.players.put(playerName, player);
    }

    public void getPlayer(String playerID)
    {
        this.players.get(playerID);
    }


    // ENEMY METHODS

    public void addEnemy(Enemy enemy)
    {
        this.enemies.put(enemy.getEnemyID(), enemy);
    }

    public List<Enemy> getAllEnemies()
    {
        return new ArrayList<>(enemies.values());
    }

    public void removeEnemy(int enemyID)
    {
        this.enemies.remove(enemyID);
    }


    // MOVING OBJECTS METHODS

    public void addMovingObject(MovingObject mo)
    {
        this.movingObjects.add(mo);
    }

    public void removeMovingObject(MovingObject mo)
    {
        this.movingObjects.remove(mo);
    }

    public List<MovingObject> getAllMovingObjects()
    {
        return this.movingObjects;
    }



    */
}
