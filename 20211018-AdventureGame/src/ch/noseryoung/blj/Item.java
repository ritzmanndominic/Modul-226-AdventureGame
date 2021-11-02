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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
