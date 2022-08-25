package baldens.checkoncheat.controller.login;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Handler;

import baldens.checkoncheat.route.util.ResourceView;
import baldens.checkoncheat.util.model.http.Status;
import baldens.checkoncheat.util.model.http.Token;
import baldens.checkoncheat.util.model.http.User;
import baldens.checkoncheat.util.model.http.UserAuth;
import baldens.checkoncheat.util.parser.JsonParser;
import baldens.checkoncheat.util.service.ReplacePageService;
import baldens.checkoncheat.util.service.RetrofitService;
import baldens.checkoncheat.util.session.SessionOperation;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;

import static java.lang.Thread.sleep;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private TextField inputLogin;

    @FXML
    private Button buttonLogin;

    @FXML
    private Hyperlink urlText;

    @FXML
    private AnchorPane layoutReplacePage;

    private UserAuth userAuth;
    private final JsonParser jsonParser = new JsonParser();
    private Integer countError = 0;

    @FXML
    void initialize() {
        urlText.setOnAction(event -> {
            ReplacePageService.getInstance().clickReplacePage(ResourceView.LINK_REGISTRATION_VIEW, layoutReplacePage);
        });

        buttonLogin.setOnAction(event -> {
            initUiWithBD();
            try {
                restApiLogin();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void initUiWithBD(){
        jsonParser.setFilename("logs.json");
        userAuth = new UserAuth();

        userAuth.setLogin(inputLogin.getText());
        userAuth.setPassword(inputPassword.getText());
    }

    private void restApiLogin() throws InterruptedException {
        RetrofitService<ResponseBody> retrofitService = new RetrofitService<>();
        retrofitService.setResponse(retrofitService.getScriptApi().getUser(userAuth));
        retrofitService.configTest();

        checkFinishOperation();
    }

    private void createAlertDialog(String strDialog){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, strDialog, ButtonType.OK);
        alert.showAndWait();
    }

    private void checkFinishOperation() throws InterruptedException {
        if(countError > 6){
            createAlertDialog("Нет обратного ответа");
            throw new IllegalAccessError("Error send login");
        }

        if(SessionOperation.isOperation()){
            if(SessionOperation.getStatus().equals("Error")){
                createAlertDialog("Авторизация не прошла успешно!");
                SessionOperation.getInstance().setOperation(false);
            }else{
                createAlertDialog("Авторизация прошла успешно!");
                SessionOperation.getInstance().setOperation(false);
                createNewSceneProfile();
            }
        }else{
            sleep(1000);
            checkFinishOperation();
            countError++;
        }
    }

    private void createNewSceneProfile(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(ResourceView.LINK_GAME_VIEW));

        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }

        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setMaximized(false);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
