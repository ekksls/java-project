package repository;

import mappers.ItemMapper;
import models.Item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class ItemRepository implements Repository<Item> {

    private static ItemRepository INSTANCE = null;

    private ItemRepository() {
    }

    public static ItemRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ItemRepository();
            return INSTANCE;
        }
        return INSTANCE;
    }

    private static final ItemMapper itemMapper = new ItemMapper();

    private static final String ITEMS_FILE_PATH = "C:\\items.txt";

    @Override
    public List<Item> readAllFromFile() {
        List<String> result;

        try (Stream<String> lines = Files.lines(Paths.get(ITEMS_FILE_PATH))) {
            result = lines.collect(Collectors.toList());
        } catch (IOException e) {
            return null;
        }

        return itemMapper.mapToModel(result);
    }

    @Override
    public void saveToFile(Item model) {
        try {
            Files.writeString(
                    Path.of(ITEMS_FILE_PATH),
                    model.toStoredString() + System.lineSeparator(),
                    CREATE, APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFromFile(String title) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        File file = null;
        File temp = null;

        try {
            file = new File(ITEMS_FILE_PATH);
            temp = File.createTempFile("temp", ".txt", file.getParentFile());
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp)));

            for (String line; (line = reader.readLine()) != null; ) {
                if (line.contains(title)) {
                    line = "";
                }
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                reader.close();
                writer.close();
                file.delete();
                temp.renameTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Item findInFile(String title) {
        List<Item> itemList = readAllFromFile();
        for (Item item : itemList) {
            if (item.getTitle().equals(title)) {
                return item;
            }
        }
        return null;
    }
}
