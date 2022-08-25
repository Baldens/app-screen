package baldens.checkoncheat.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import baldens.checkoncheat.route.http.Route;
import baldens.checkoncheat.route.util.ResourceView;
import baldens.checkoncheat.util.dto.ScriptApi;
import baldens.checkoncheat.util.model.http.User;
import baldens.checkoncheat.util.parser.JsonParser;
import baldens.checkoncheat.util.service.ReplacePageService;
import baldens.checkoncheat.util.service.RetrofitService;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label welcomeText;

    @FXML
    private PasswordField inputPassord;

    @FXML
    private TextField inputLogin;

    @FXML
    private Button buttonLogin;

    @FXML
    private AnchorPane layoutReplacePage;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @FXML
    void initialize() {
        ReplacePageService.getInstance().clickReplacePage(ResourceView.LINK_LOGIN_VIEW, layoutReplacePage);

        JsonParser jsonParser = new JsonParser();
        jsonParser.setFilename("logs.json");
        jsonParser.clear();
        jsonParser.setFilename("token.json");
        jsonParser.clear();
    }
}
