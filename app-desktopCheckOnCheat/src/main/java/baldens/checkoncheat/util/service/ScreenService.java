package baldens.checkoncheat.util.service;

import baldens.checkoncheat.util.session.SessionScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenService {
    private static ScreenService instance;
    private static String nameScreen = "";
    private ScreenService(){
    }

    public static ScreenService getInstance() {
        if(instance == null){
            instance = new ScreenService();
        }
        return instance;
    }

    public void createScreenshot() throws IOException {
        try {
//            timeEventStart();
            checkIsScreenAndNextName("screen.png");
            ImageIO.write(Objects.requireNonNull(grabScreen()), "png", new File(getDirectoryProject(), nameScreen));
            System.out.println(SessionScreen.getListScreenName());
        } catch (IOException e) {
            System.out.println("IO exception: " + e);
        }
    }

    private static void checkIsScreenAndNextName(String screenName) throws IOException {
        File file = new File(getDirectoryProject() + screenName);
        if(file.exists() && !file.isDirectory()) {
            String screenNameReplace = screenName.replaceAll("\\D", "");
            if(screenNameReplace.equals("")){
                screenNameReplace = "screen0";
            }else{
                screenNameReplace = "screen" + (Integer.parseInt(screenNameReplace) + 1);
            }
            checkIsScreenAndNextName(screenNameReplace + ".png");
        }
        else {
            SessionScreen.getInstance(screenName);
            nameScreen = screenName;
        }
    }

    public static String getDirectoryProject() throws IOException {
        StringBuilder route = new StringBuilder(new String(new File(".").getCanonicalPath().getBytes(StandardCharsets.UTF_8)));
        route.append("\\src\\main\\java\\asset\\");
        return route.toString();
    }

    private static BufferedImage grabScreen() {
        try {
            return new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())) ;
        } catch (SecurityException | AWTException e) {
            return null;
        }
    }

    //Срабатывание события через 45 секунд
    private void timeEventStart() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        service.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    createScreenshot();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 45, TimeUnit.SECONDS);
    }
}
