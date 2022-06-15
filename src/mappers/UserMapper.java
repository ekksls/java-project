package mappers;

import models.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper implements ObjectMapper<User> {

    @Override
    public List<User> mapToModel(List<String> stringList) {
        List<User> userList = new ArrayList<>();
        for (String s : stringList) {
            String title = s.substring(s.indexOf("username:") + 9, s.lastIndexOf('/'));
            String rentedItems = s.substring(s.indexOf("rentedItems:") + 12);
            userList.add(new User(title, rentedItems));
        }
        return userList;
    }
}
