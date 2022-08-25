package baldens.checkoncheat.util.dto;

import baldens.checkoncheat.route.http.Route;
import baldens.checkoncheat.util.model.http.Screen;
import baldens.checkoncheat.util.model.http.Token;
import baldens.checkoncheat.util.model.http.User;
import baldens.checkoncheat.util.model.http.UserAuth;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ScriptApi {
    @POST(Route.LINK_REGISTRATION)
    Call<ResponseBody> createUser(@Body User user);

    @POST(Route.LINK_AUTH)
    Call<ResponseBody> getUser(@Body UserAuth userAuth);

    /*@Multipart
    @POST(Route.LINK_SAVE_SCREENSHOT)
    Call<ResponseBody> saveScreenshot(@Body Screen screen);*/

    @Multipart
    @POST(Route.LINK_SAVE_SCREENSHOT)
    Call<ResponseBody> saveScreenshot(@Query("id_user") Integer id_user, @Part MultipartBody.Part image);
}
