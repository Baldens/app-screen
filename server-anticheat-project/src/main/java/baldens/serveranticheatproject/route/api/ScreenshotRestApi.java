package baldens.serveranticheatproject.route.api;

import baldens.serveranticheatproject.model.http.Screenshot;
import baldens.serveranticheatproject.repository.http.ScreenshotRepository;
import baldens.serveranticheatproject.repository.util.FileSystemRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("api")
public class ScreenshotRestApi {
    @Autowired
    private ScreenshotRepository screenshotRepository;
    @Autowired
    private FileSystemRepository fileSystemRepository;

    @Transactional
    @PostMapping(path = "/screenshot-save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity saveScreenshot(
            @RequestParam("id_user") Integer id_user,
            @RequestParam("image") MultipartFile screen)
    {
        try{
            try (InputStream inputStream = screen.getInputStream()){
                String hashNameFile = getHashNameText("png");

                Screenshot screenshot = new Screenshot();
                screenshot.setId_user(id_user);
                screenshot.setName_file(hashNameFile);

//                screenshotRepository.save(screenshot);
                createFileInPackage(screen, hashNameFile);

                return ResponseEntity.ok("Ok");
            }catch (IOException exception){
                return ResponseEntity.badRequest().body("Error");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    private void createFileInPackage(MultipartFile file, String nameFile) throws Exception {
        fileSystemRepository.save(file.getBytes(), nameFile);
    }

    private static String getHashNameText(String format){
        return String.format("%s.%s", RandomStringUtils.randomAlphanumeric(20), format);
    }
}
