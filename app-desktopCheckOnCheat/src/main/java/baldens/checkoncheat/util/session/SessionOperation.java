package baldens.checkoncheat.util.session;

public class SessionOperation {
    private static SessionOperation instance;

    private static boolean operation = false;
    private static String status;

    private SessionOperation() {
    }

    public static SessionOperation getInstance() {
        if(instance == null) {
            instance = new SessionOperation();
        }
        return instance;
    }

    public void setOperation(boolean operation) {
        SessionOperation.operation = operation;
        instance = null;
    }

    public static boolean isOperation() {
        return operation;
    }

    public static String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        SessionOperation.status = status;
        instance = null;
    }
}
