package ch.noseryoung.blj;

/**
 * This class creates a template for an Item.
 */
public class Item {
    private String name;
    private boolean alarm;
    private int price;

    public Item() {
    }

    public Item(String name, boolean alarm, int price) {
        this.name = name;
        this.alarm = alarm;
        this.price = price;
    }

    /**
     * Getter for the item name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter to check if the item has an alarm
     *
     * @return alarn
     */
    public boolean isAlarm() {
        return alarm;
    }

    /**
     * Getter to get the price of the item
     *
     * @return price
     */
    public int getPrice() {
        return price;
    }
}
