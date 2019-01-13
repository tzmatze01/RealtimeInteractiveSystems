package main.messages;

import main.messages.type.KeyEventType;
import main.messages.type.MessageType;

public class LoginMessage extends Message {

    private static KeyEventType ket;

    private String userName;
    private String password;

    public LoginMessage(String userName, String password) {

        super(MessageType.LOGIN);

        this.userName = userName;
        this.password = password;
    }


    public String getUserName()
    {
        return this.userName;
    }

    public String getPassword()
    {
        return this.password;
    }



}
