package baldens.checkoncheat.controller.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import baldens.checkoncheat.util.model.http.Screen;
import baldens.checkoncheat.util.service.RetrofitService;
import baldens.checkoncheat.util.service.ScreenService;
import baldens.checkoncheat.util.session.SessionScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class GameController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text textNamePerson;

    @FXML
    private Text textStatusGame;

    @FXML
    private Button buttonFinishGame;

    @FXML
    void initialize() {
        buttonFinishGame.setOnAction(event -> {
            saveScreenInCloud();
        });
    }

    private void saveScreenInCloud(){
        try {
//            ScreenService.getInstance().createScreenshot();
//            SessionScreen.getListScreenName();
            File file = new File(ScreenService.getDirectoryProject() + "screen.png");

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);

            RetrofitService<ResponseBody> retrofitService = new RetrofitService<>();
            retrofitService.setResponse(retrofitService.getScriptApi().saveScreenshot(1, body));
            retrofitService.configTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
