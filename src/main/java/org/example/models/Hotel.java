package org.example.models;

public class Hotel {
    private final Player owner;
    private String type;

    public String getType() {
        return type;
    }

    public Hotel(Player owner) {
        this.owner = owner;
        this.type="Silver";
    }

    public void setType(String type) {
        this.type = type;
    }

    public Player getOwner() {
        return owner;
    }

}
