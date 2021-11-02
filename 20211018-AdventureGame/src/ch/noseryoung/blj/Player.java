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

    /**
     * getter for the start time when the game has begun.
     *
     * @return startTime
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * setter for the startTime
     *
     * @param startTime
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * getter for the whole gameTime
     *
     * @return gameTime
     */
    public long getGameTime() {
        return gameTime;
    }

    /**
     * setter for the whole gameTime
     *
     * @param gameTime
     */
    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    /**
     * getter for how much lives the player has
     *
     * @return lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * setter for the lives
     *
     * @param lives
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * getter for the itemList
     *
     * @return itemList
     */
    public ArrayList<Item> getItemList() {
        return itemList;
    }

    /**
     * getter for the max lives the player can have
     *
     * @return maxLives
     */
    public int getMaxLives() {
        return maxLives;
    }
}