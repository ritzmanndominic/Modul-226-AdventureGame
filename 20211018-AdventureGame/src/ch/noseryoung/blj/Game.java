package ch.noseryoung.blj;

import java.util.ArrayList;
import java.util.Stack;

public class Game {

    private final ArrayList<Room> rooms;
    private final ArrayList<Item> items;
    private final ArrayList<Door> doors;
    private int activeRoom = 5;
    private Stack<Integer> lastRoom = new Stack<>();
    IO io = new IO();

    public Game() {
        rooms = new ArrayList<>();
        items = new ArrayList<>();
        doors = new ArrayList<>();
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public int getActiveRoom() {
        return activeRoom;
    }

    public void setActiveRoom(int activeRoom) {
        this.activeRoom = activeRoom;
    }

    public Stack<Integer> getLastRoom() {
        return lastRoom;
    }

    public void setLastRoom(Stack<Integer> lastRoom) {
        this.lastRoom = lastRoom;
    }

    public IO getIo() {
        return io;
    }

    public void setIo(IO io) {
        this.io = io;
    }
}
