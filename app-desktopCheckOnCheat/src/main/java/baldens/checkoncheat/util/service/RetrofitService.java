package baldens.checkoncheat.util.service;

import baldens.checkoncheat.route.http.Route;
import baldens.checkoncheat.util.dto.ScriptApi;
import baldens.checkoncheat.util.model.http.Token;
import baldens.checkoncheat.util.parser.JsonParser;
import baldens.checkoncheat.util.session.SessionOperation;
import com.google.gson.Gson;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RetrofitService <T> {
    private boolean isToken;
    private Call<T> response;
    private String tokenOut;
    private final JsonParser jsonParser = new JsonParser();

    public void configTest(){
        response.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                assert response.body() != null;
                instanceOfClass(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable throwable) {
                System.out.println("Error: " + throwable.getMessage());
            }
        });
        isToken = false;
    }

    private void instanceOfClass(Response<T> response)  {
        if(response.body() instanceof ResponseBody){
            String res;
            try {
                res = ((ResponseBody) response.body()).string();
                if(res.equals("Ok")){
                    jsonScriptWriteLogs(res);
                    SessionOperation.getInstance().setStatus("Ok");
                }else if(res.equals("Error")){
                    jsonScriptWriteLogs(res);
                }else{
                    jsonScriptWriteLogs("Ok");
                    jsonScriptWriteToken(res);
                    SessionOperation.getInstance().setStatus("Ok");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            jsonScriptWriteLogs("Error");
            SessionOperation.getInstance().setStatus("Error");
        }
        SessionOperation.getInstance().setOperation(true);
    }

    private void jsonScriptWriteLogs(String status){
        jsonParser.setFilename("logs.json");
        jsonParser.setStatus(status);
        jsonParser.build(true);
    }

    private void jsonScriptWriteToken(String status){
        Gson gson = new Gson();
        Token token = gson.fromJson(status, Token.class);
        jsonParser.setFilename("token.json");
        jsonParser.setId(token.getId());
        jsonParser.setToken(token.getToken());
        jsonParser.build(false);
    }

    public ScriptApi getScriptApi(){
        return getRetrofitJson().create(ScriptApi.class);
    }

    public void setResponse(Call<T> tCall){
        this.response = tCall;
    }

    private OkHttpClient okHttpClient(){
        OkHttpClient client = new OkHttpClient();
        if (isToken){
            client.interceptors().add(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder()
                            .header("Authorization", "Bearer " + tokenOut);

                    Request request = builder.build();

                    return chain.proceed(request);
                }
            });
        }
        return client;
    }

    public void setOriginalBuilderToken(String tokenIn){
        isToken = true;
        tokenOut = tokenIn;
    }

    public Retrofit getRetrofitJson(){
        return new Retrofit.Builder()
                .baseUrl(Route.basicUrlDomen)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient())
                .build();
    }
}
