package baldens.checkoncheat.route.util;

public class ResourceView extends LinkPage {

    private static ResourceView INSTANCE;

    private ResourceView() {
    }

    public static ResourceView getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResourceView();
        }
        return INSTANCE;
    }
}
