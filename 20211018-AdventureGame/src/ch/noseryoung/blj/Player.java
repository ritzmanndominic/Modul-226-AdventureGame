package ch.noseryoung.blj;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class creates a template for a Player.
 */
public class Player {
    private ArrayList<Item> itemList = new ArrayList<>();
    private int lives;
    private int maxLives;
    private long gameTime;
    private Timestamp startTime;

    public Player() {
    }

    public Player(ArrayList<Item> itemList, int lives) {
        this.itemList = itemList;
        this.lives = lives;
        this.maxLives = lives;

        gameTime = 0;
        Date date = new Date();
        startTime = new Timestamp(date.getTime());
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public long getGameTime() {
        return gameTime;
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public int getMaxLives() {
        return maxLives;
    }
}