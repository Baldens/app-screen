package baldens.checkoncheat.controller.login;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpRequest;
import java.util.ResourceBundle;

import baldens.checkoncheat.route.http.Route;
import baldens.checkoncheat.route.util.ResourceView;
import baldens.checkoncheat.util.model.http.User;
import baldens.checkoncheat.util.parser.JsonParser;
import baldens.checkoncheat.util.service.ReplacePageService;
import baldens.checkoncheat.util.service.RetrofitService;
import baldens.checkoncheat.util.session.SessionOperation;
import baldens.checkoncheat.util.session.SessionResult;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import okhttp3.ResponseBody;

import static java.lang.Thread.sleep;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField inputPassord;

    @FXML
    private TextField inputLogin;

    @FXML
    private TextField inputName;

    @FXML
    private Button buttonLogin;

    @FXML
    private Hyperlink urlText;

    @FXML
    private AnchorPane layoutReplacePage;

    private User user;
    private static SessionResult sessionResult;
    private JsonParser jsonParser;
    private Integer countError = 0;

    @FXML
    void initialize() {
        urlText.setOnAction(event -> {
            ReplacePageService.getInstance().clickReplacePage(ResourceView.LINK_LOGIN_VIEW, layoutReplacePage);
        });

        buttonLogin.setOnAction(event -> {
            initUiWithBD();
            try {
                restApiRegistration();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void initUiWithBD(){
        user = new User();

        user.setLogin(inputLogin.getText());
        user.setPassword(inputPassord.getText());
        user.setName(inputName.getText());
        user.setId_role(1);
    }

    private void restApiRegistration() throws InterruptedException {
        RetrofitService<ResponseBody> retrofitService = new RetrofitService<>();
        retrofitService.setResponse(retrofitService.getScriptApi().createUser(user));
        retrofitService.configTest();

        checkFinishOperation();
    }

    private void checkFinishOperation() throws InterruptedException {
        if(countError > 6){
            createAlertDialog("Нет обратного ответа");
            throw new IllegalAccessError("Error send registration");
        }

        if(SessionOperation.isOperation()){
            if(SessionOperation.getStatus().equals("Error")){
                createAlertDialog("Регистрация не прошла успешно!");
                SessionOperation.getInstance().setOperation(false);
            }else{
                createAlertDialog("Регистрация прошла успешно, данные были отправлены!");
                SessionOperation.getInstance().setOperation(false);
            }
        }else{
            sleep(1000);
            checkFinishOperation();
            countError++;
        }
    }

    private void createAlertDialog(String strDialog){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, strDialog, ButtonType.OK);
        alert.showAndWait();
    }
}
