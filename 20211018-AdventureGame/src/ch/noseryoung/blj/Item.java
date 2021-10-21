package ch.noseryoung.blj;

/**
 * This class creates a template for an Item.
 */
public class Item {
    private String name;
    private boolean alarm;

    public Item() {
    }

    public Item(String name, boolean alarm) {
        this.name = name;
        this.alarm = alarm;
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
}
