package mappers;

import models.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper implements ObjectMapper<Item> {

    @Override
    public List<Item> mapToModel(List<String> stringList) {
        List<Item> itemList = new ArrayList<>();
        for (String s : stringList) {
            String title = s.substring(s.indexOf("title:") + 6);
            itemList.add(new Item(title));
        }
        return itemList;
    }
}
