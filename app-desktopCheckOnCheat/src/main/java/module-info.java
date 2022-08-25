module baldens.checkoncheat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;
    requires jdk.httpserver;
    requires java.sql;
    requires retrofit2;
    requires okhttp3;
    requires io.reactivex.rxjava3;
    requires okhttp3.logging;
    requires converter.gson;
    requires gson;
    requires json.simple;

    opens baldens.checkoncheat to javafx.fxml;
    exports baldens.checkoncheat;
    exports baldens.checkoncheat.controller;
    exports baldens.checkoncheat.controller.login;

    opens baldens.checkoncheat.controller.login to javafx.fxml;
    opens baldens.checkoncheat.controller.game to javafx.fxml;
    opens baldens.checkoncheat.controller to javafx.fxml;

    opens baldens.checkoncheat.util.service to gson;
    opens baldens.checkoncheat.util.model.http to gson;
}