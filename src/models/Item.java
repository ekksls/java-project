package models;

public class Item {

    private final String title;
    public Item(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Item title: " + title;
    }

    public String toStoredString() {
        return "Item/title:" + title;
    }
}
