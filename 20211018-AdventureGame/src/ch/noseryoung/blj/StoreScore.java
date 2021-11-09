package ch.noseryoung.blj;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class StoreScore {

    /**
     * Saves the data of the player into a file
     *
     * @param fileWriteTo in which file it is saved
     * @param player      which player
     * @param game        which game
     */
    public static void saveData(String fileWriteTo, Player player, Game game) {
        ArrayList<String> arrayList = new ArrayList<>();
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());

        arrayList.add(game.getRooms().get(game.getActiveRoom()).getName());
        arrayList.add(String.valueOf(player.getLives()));

        arrayList.add(String.valueOf((currentTime.getTime() - player.getStartTime().getTime() + player.getGameTime())));

        for (int i = 0; i < game.getItems().size(); i++) {
            arrayList.add(game.getItems().get(i).getName() + game.getItems().get(i).getPrice());
        }

        for (int j = 0; j < player.getItemList().size(); j++) {
            arrayList.add(player.getItemList().get(j).getName() + player.getItemList().get(j).getPrice());
        }


        try {
            FileOutputStream fos = new FileOutputStream(fileWriteTo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(arrayList);
            oos.close();
        } catch (IOException e) {
            System.out.println("There was an error, file could not be saved");
        }
    }

    /**
     * Loads data from the file
     *
     * @param fileToReadForm read data from which file
     * @param player         which player
     * @param game           which game
     */
    public static void loadData(String fileToReadForm, Player player, Game game) {
        try {
            FileInputStream fis = new FileInputStream(fileToReadForm);
            ObjectInputStream ois = new ObjectInputStream(fis);

            ArrayList<String> arrayList = (ArrayList<String>) ois.readObject();
            ois.close();

            //set active room
            for (int i = 0; i < game.getRooms().size(); i++) {
                if (arrayList.get(0).equals(game.getRooms().get(i).getName())) {
                    game.setActiveRoom(i);
                }
            }

            //set lives
            player.setLives(Integer.parseInt(arrayList.get(1)));

            //set game time
            player.setGameTime(Long.parseLong(arrayList.get(2)));

            //set empty room
            for (int i = 3; i < 13; i++) {
                if (arrayList.get(i).isEmpty()) {
                    game.getRooms().get(i).setItemsArrayList(null);
                }
            }

            //remove
            for (int i = 0; i < 13; i++) {
                arrayList.remove(0);
            }

            //add to players arraylist
            for (int j = 0; j < game.getItems().size(); j++) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).equals(game.getItems().get(j).getName() + game.getItems().get(j).getPrice())) {
                        player.getItemList().add(game.getItems().get(j));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("There was an error, data could not be loaded");
        }
    }
}
