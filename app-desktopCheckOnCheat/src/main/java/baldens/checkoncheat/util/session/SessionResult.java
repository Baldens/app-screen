package baldens.checkoncheat.util.session;

import java.util.ArrayList;
import java.util.List;

public class SessionResult {
    private static SessionResult instance;
    private static StringBuffer result;

    private SessionResult(String result) {
        System.out.println("- " + result);
        if(result == null){
            SessionResult.result = new StringBuffer();
        }
        SessionResult.result.append(result);
    }

    /**
     * @param result
     * @return
     */

    public static SessionResult getInstance(String result) {
        if(instance == null) {
            instance = new SessionResult(result);
            instance = null;
        }
        return instance;
    }

    public static String getResult() {
        return result.toString();
    }
}