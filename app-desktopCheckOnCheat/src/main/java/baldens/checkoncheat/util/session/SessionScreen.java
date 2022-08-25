package baldens.checkoncheat.util.session;

import java.util.ArrayList;
import java.util.List;

public class SessionScreen {
    private static SessionScreen instance;
    private static List<String> listScreenName;

    private SessionScreen(String nameScreen) {
        if(listScreenName == null){
            listScreenName = new ArrayList<>();
        }
        listScreenName.add(nameScreen);
    }

    /**
     * @param nameScreen
     * @return
     */

    public static SessionScreen getInstance(String nameScreen) {
        if(instance == null) {
            instance = new SessionScreen(nameScreen);
            instance = null;
        }
        return instance;
    }

    public static List<String> getListScreenName() {
        return listScreenName;
    }
}
