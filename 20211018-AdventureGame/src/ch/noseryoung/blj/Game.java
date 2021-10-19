package ch.noseryoung.blj;

import java.util.ArrayList;
import java.util.Random;
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

        createRooms(rooms);
        createDoors(rooms, doors);
    }

    /**
     * This methode creates the doors between the rooms
     *
     * @param rooms Arraylist to get the room
     * @param doors Arraylist where the doors will be added
     *              index 0 -> Office
     *              index 1 -> Kitchen
     *              index 2 -> Bedroom
     *              index 3 -> Toilet
     *              index 4 -> Bathroom
     *              index 5 -> Balcony
     *              index 6 -> Storeroom
     *              index 7 -> Gym
     *              index 8 -> Livingroom
     *              index 9 -> Secretroom
     */
    public void createDoors(ArrayList<Room> rooms, ArrayList<Door> doors) {
        doors.add(new Door(new Room[]{rooms.get(0), rooms.get(1)}, false));
        doors.add(new Door(new Room[]{rooms.get(0), rooms.get(9)}, true));
        doors.add(new Door(new Room[]{rooms.get(1), rooms.get(2)}, false));
        doors.add(new Door(new Room[]{rooms.get(1), rooms.get(7)}, false));
        doors.add(new Door(new Room[]{rooms.get(7), rooms.get(8)}, false));
        doors.add(new Door(new Room[]{rooms.get(7), rooms.get(5)}, false));
        doors.add(new Door(new Room[]{rooms.get(5), rooms.get(8)}, false));
        doors.add(new Door(new Room[]{rooms.get(8), rooms.get(4)}, false));
        doors.add(new Door(new Room[]{rooms.get(8), rooms.get(2)}, false));
        doors.add(new Door(new Room[]{rooms.get(2), rooms.get(3)}, false));
        doors.add(new Door(new Room[]{rooms.get(2), rooms.get(6)}, false));
        doors.add(new Door(new Room[]{rooms.get(6), rooms.get(4)}, false));
    }

    public void createRooms(ArrayList<Room> rooms) {
        Random randomNumber = new Random();
        String[] RoomNames = {"Office", "Kitchen", "Bedroom", "Toilet", "Bathroom", "Balcony", "Storeroom", "Gym",
                "Livingroom", "Secretroom"};

        for (String roomName : RoomNames) {
            Room room = new Room(roomName, false);
            if (randomNumber.nextInt(2) == 1) {
                room.setEnemy(true);
            }
            rooms.add(room);
        }
    }


    public void move(Player player, Game game) {
        boolean validMove = false;
        String newRoom;
        String cancel = "x";
        do {
            System.out.print("\nType in the room you want to go in: ");
            newRoom = IO.scn.nextLine();
            for (Door door : getDoors()) {
                //validMove = !door.isLocked();
                if (door.getConnector()[0].getName().toLowerCase().equals(newRoom.toLowerCase()) ||
                        door.getConnector()[1].getName().toLowerCase().equals(newRoom.toLowerCase())) {
                    if (door.getConnector()[0].getName().toLowerCase().equals(getRooms().get(getActiveRoom()).getName().toLowerCase()) ||
                            door.getConnector()[1].getName().toLowerCase().equals(getRooms().get(getActiveRoom()).getName().toLowerCase())) {
                        validMove = true;
                        for (int j = 0; j < getRooms().size(); j++) {
                            if (newRoom.toLowerCase().equals(getRooms().get(j).getName().toLowerCase())) {
                                setActiveRoom(j);
                                getLastRoom().push(getActiveRoom());
                            }
                        }
                    }
                }
            }
            if (newRoom.equals(cancel)) {
                validMove = true;
            } else {
                if (validMove) {
                    System.out.println("You entered the " + newRoom);
                    //enemyManager(player, game);
                } else {
                    System.out.print("The room could not be found, try again or press \"x\" to cancel");
                }
            }
        } while (!validMove);
    }

    public void inspectRoom(Player player) {
        //counts the amount of items in the current room
        int amountItems = 0;
        for (Item itemInRoom : getRooms().get(getActiveRoom()).getItemsArrayList()) {
            if (!itemInRoom.getName().isEmpty()) {
                amountItems++;
            }
        }
        IO io = new IO();
        //checks if room has items
        if (amountItems > 0) {
            //a random number will be generated, until a valid item could be found
            Random random = new Random();
            int randomNumber;
            do {
                randomNumber = random.nextInt(getRooms().get(getActiveRoom()).getItemsArrayList().size());
            } while (getRooms().get(getActiveRoom()).getItemsArrayList().get(randomNumber).getName().isEmpty());
            System.out.println("You have found " +
                    getRooms().get(getActiveRoom()).getItemsArrayList().get(randomNumber).getName());
            //if the item has an alarm player will get damage
            if (getRooms().get(getActiveRoom()).getItemsArrayList().get(randomNumber).isAlarm()) {
                System.out.println("Oh no I shouldn't have taken that!");
                System.out.println("\u001B[31myou lost a life\u001B[0m");
                player.setLives(player.getLives() - 1);
                io.printHeart(player.getLives(), "red");
            }
            //if player has not the max amount of lives a live will be added
            else if (player.getLives() < player.getMaxLives()) {
                player.setLives(player.getLives() + 1);
                System.out.println("\u001B[32mnice! I got an extra life\u001B[0m");
                io.printHeart(player.getLives(), "green");
            } else {
                io.printHeart(player.getLives(), "normal");
            }
            //the item will be added to player inventory and removed from current room
            player.getItemList().add(getRooms().get(getActiveRoom()).getItemsArrayList().get(randomNumber));
            getRooms().get(getActiveRoom()).getItemsArrayList().remove(randomNumber);
        } else {
            System.out.println("There seems to be no item in this room");
        }
        //checks if player died
        if (player.getLives() == 0) {
            System.out.println("\u001B[31myou have died\u001B[0m");
            System.exit(0);
        }
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
