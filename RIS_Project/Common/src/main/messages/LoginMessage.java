package main.messages;

import main.messages.type.MessageType;

public class LoginMessage extends Message {

    private String userName;
    private String password;

    public LoginMessage(String userName, String password) {

        super(MessageType.LOGIN);
        this.userName = userName;
        this.password = password;
    }


}
