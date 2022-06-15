package models;

public class User {

    private final String username;
    private String borrowedItems;

    public User(String username, String rentedItems) {
        this.username = username;
        this.borrowedItems = rentedItems;
    }

    public String getUsername() {
        return username;
    }

    public String getBorrowedItems() {
        return borrowedItems;
    }

    public void setRentedItems(String rentedItems) {
        this.borrowedItems += rentedItems + ", ";
    }

    @Override
    public String toString() {
        return "Username: " + username + " Rented items: " + borrowedItems.substring(0, borrowedItems.length() - 2);
    }

    public String toStoredString() {
        return "User/username:" + username + "/rentedItems:" + borrowedItems;
    }
}
