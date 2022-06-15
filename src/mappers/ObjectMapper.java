package mappers;

import java.util.List;

public interface ObjectMapper<T> {
    List<T> mapToModel(List<String> stringList);
}
