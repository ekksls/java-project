package repository;

import mappers.UserMapper;
import models.User;

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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class UserRepository implements Repository<User> {

    private static UserRepository INSTANCE = null;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
            return INSTANCE;
        }
        return INSTANCE;
    }

    private static final UserMapper userMapper = new UserMapper();

    private static final String USERS_FILE_PATH = "C:\\users.txt";

    @Override
    public List<User> readAllFromFile() {
        List<String> result;

        System.getProperty("user.dir");
        try (Stream<String> lines = Files.lines(Paths.get(USERS_FILE_PATH))) {
            result = lines.collect(Collectors.toList());
        } catch (IOException e) {
            return null;
        }

        return userMapper.mapToModel(result);
    }

    @Override
    public User findInFile(String username) {
        List<User> userList = readAllFromFile();
        for (User user: userList) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void saveToFile(User model) {
        try {
            Files.writeString(
                    Path.of(USERS_FILE_PATH),
                    model.toStoredString() + System.lineSeparator(),
                    CREATE, APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFromFile(String username) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        File file = null;
        File temp = null;

        try {
            file = new File(USERS_FILE_PATH);
            temp = File.createTempFile("temp", ".txt", file.getParentFile());
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp)));

            for (String line; (line = reader.readLine()) != null; ) {
                String trimmedLine = line.trim();
                if (trimmedLine.contains(username)) {
                    continue;
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
}
