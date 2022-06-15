package repository;

import java.util.List;

public interface Repository<T> {

    List<T> readAllFromFile();

    void saveToFile(T model);

    void deleteFromFile(String name);

    T findInFile(String name);
}
