package baldens.checkoncheat.util.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class ReplacePageService {
    private static ReplacePageService instance;

    private ReplacePageService(){
    }

    public static ReplacePageService getInstance(){
        if(instance == null){
            instance = new ReplacePageService();
        }
        return instance;
    }

    public void clickReplacePage(String url, AnchorPane layoutReplacePage) {
        try {
            AnchorPane layoutLoadPage = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource(url)));
            layoutReplacePage.getChildren().setAll(layoutLoadPage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
