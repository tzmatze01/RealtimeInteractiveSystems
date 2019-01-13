package main.messages.type;


public enum KeyEventType {

    KEY_PRESSED(0),
    KEY_RELEASED(1);

    private int eventType;

    KeyEventType(int eventType) {
        this.eventType = eventType;
    }
}
