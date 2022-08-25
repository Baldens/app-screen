package baldens.checkoncheat.util.model.http;

import okhttp3.MultipartBody;
import retrofit2.http.Part;

import java.io.File;

public class Screen {
    private Integer id_user;
    private MultipartBody.Part image;

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public MultipartBody.Part getImage() {
        return image;
    }

    public void setImage(MultipartBody.Part image) {
        this.image = image;
    }
}
