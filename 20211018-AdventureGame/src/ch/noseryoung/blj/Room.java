package ch.noseryoung.blj;

import java.util.ArrayList;

/**
 * This class creates a template for a room.
 */
public class Room {
    private String name;
    private ArrayList<Item> itemsArrayList = new ArrayList<>();
    private boolean enemy;

    public Room() {
    }

    public Room(String name, boolean enemy) {
        this.name = name;
        this.enemy = enemy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getItemsArrayList() {
        return itemsArrayList;
    }

    public void setItemsArrayList(ArrayList<Item> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }
}