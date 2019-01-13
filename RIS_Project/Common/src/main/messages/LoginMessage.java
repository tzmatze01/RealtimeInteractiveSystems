package main.messages;

import main.messages.type.KeyEventType;
import main.messages.type.MessageType;

public class LoginMessage extends Message {

    private static KeyEventType ket;

    private String userName;
    private String password;

    private boolean loggedIn;

    public LoginMessage(String userName, String password)
    {
        super(MessageType.LOGIN);

        this.userName = userName;
        this.password = password;
        this.loggedIn = false;
    }

    public LoginMessage(String username, boolean loggedIn)
    {
        super(MessageType.LOGIN);

        this.userName = username;
        this.password = "";
        this.loggedIn = loggedIn;
    }


    public String getUserName()
    {
        return this.userName;
    }

    public String getPassword()
    {
        return this.password;
    }

    public boolean isLoggedIn()
    {
        return this.loggedIn;
    }



}
