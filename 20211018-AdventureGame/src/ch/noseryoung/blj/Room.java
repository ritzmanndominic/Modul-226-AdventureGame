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

    /**
     * getter for the room name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the item List
     *
     * @return itemsArrayList
     */
    public ArrayList<Item> getItemsArrayList() {
        return itemsArrayList;
    }

    /**
     * setter for the item list
     *
     * @param itemsArrayList
     */
    public void setItemsArrayList(ArrayList<Item> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }

    /**
     * getter if there is an enemy in the room
     *
     * @return enemy
     */
    public boolean isEnemy() {
        return enemy;
    }

    /**
     * setter to set if there is an enemy in the room
     *
     * @param enemy
     */
    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }
}