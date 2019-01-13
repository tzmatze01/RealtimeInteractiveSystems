package main.handler.cookie;

public class ConnectionCookie {

    private boolean loggedIn;

    public ConnectionCookie(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }


}
