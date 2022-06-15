package menu;

import models.Item;
import models.User;
import repository.ItemRepository;
import repository.UserRepository;

import java.util.List;
import java.util.Scanner;

import static util.AppUtils.showObjectList;

public class MainMenu implements Menu {

    private static final UserRepository userRepository = UserRepository.getInstance();
    private static final ItemRepository itemRepository = ItemRepository.getInstance();

    @Override
    public void start() {
        show();
        handleSelection();
    }

    @Override
    public void show() {
        System.out.println("""
                1. Add user
                2. Delete user
                3. Show all users
                4. Add item
                5. Delete item
                6. Show all items
                7. Login as user
                0. Exit
                """
        );
    }

    @Override
    public void handleSelection() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            switch (scanner.nextInt()) {
                case 1 -> {
                    System.out.println("Enter username: ");
                    String username = scanner.next();
                    userRepository.saveToFile(new User(username, ""));
                    System.out.println("User has been successfully added!\n");
                }
                case 2 -> {
                    System.out.println("Enter username: ");
                    String username = scanner.next();
                    userRepository.deleteFromFile(username);
                    System.out.println("User has been successfully deleted!\n");
                }
                case 3 -> {
                    List<User> userList = userRepository.readAllFromFile();
                    if (userList == null) {
                        System.out.println("List of users is empty! Please, add users");
                    } else {
                        showObjectList(userList);
                    }
                }
                case 4 -> {
                    System.out.println("Enter item title: ");
                    String title = scanner.next();
                    itemRepository.saveToFile(new Item(title));
                    System.out.println("Item has been successfully added!\n");
                }
                case 5 -> {
                    System.out.println("Enter item title: ");
                    String title = scanner.next();
                    itemRepository.deleteFromFile(title);
                    System.out.println("Item has been successfully deleted!\n");
                }
                case 6 -> {
                    List<Item> itemList = itemRepository.readAllFromFile();
                    if (itemList == null) {
                        System.out.println("List of items is empty! Please, add items");
                    } else {
                        showObjectList(itemList);
                    }
                }
                case 7 -> {
                    System.out.println("Enter username: ");
                    User user = userRepository.findInFile(scanner.next());
                    if (user == null) {
                        System.out.println("\nUser does not exist!\n");
                        this.start();
                        break;
                    }
                    System.out.println("""
                            1. Borrow an item
                            2. Show borrowed items
                            3. Return to main menu
                            """);

                    boolean isActive = true;
                    while(isActive) {
                        switch (scanner.nextInt()) {
                            case 1 -> {
                                System.out.println("Enter item title you want to borrow: ");
                                String title = scanner.next();
                                Item item = itemRepository.findInFile(title);
                                if (item == null) {
                                    System.out.println("\nItem does not exist!\n");
                                    this.start();
                                    break;
                                }
                                userRepository.deleteFromFile(user.getUsername());
                                user.setRentedItems(title);
                                userRepository.saveToFile(user);
                                System.out.println("\nItem has been successfully borrowed!\n");
                            }
                            case 2 -> {
                                String borrowedItems = user.getBorrowedItems();
                                if (borrowedItems.isEmpty()) {
                                    System.out.println("\nUser did not borrowed any items.\n");
                                    this.start();
                                    break;
                                }
                                System.out.println("Borrowed items: " + borrowedItems.substring(0, borrowedItems.length() - 2));
                            }
                            default -> {
                                this.start();
                                isActive = false;
                            }
                        }
                    }
                }
                default -> System.exit(0);
            }
        }
    }
}
