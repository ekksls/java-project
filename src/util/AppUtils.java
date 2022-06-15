package util;

import java.util.List;

public class AppUtils {

    public static void showObjectList(List<?> list) {
        int i = 1;
        for (Object o: list) {
            System.out.println(i + ") " + o);
            i++;
        }
    }

    public static String transformObjectListOString(List<?> list) {
        StringBuilder sb = new StringBuilder();
        for (Object o: list) {
            sb.append(o);
            sb.append("/");
        }
        return sb.toString();
    }
}
