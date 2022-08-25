package baldens.serveranticheatproject.repository.util;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
public class FileSystemRepository {
    public String save(byte[] content, String imageName) throws Exception {
        Path newFile = Paths.get(getResourcesDir() + imageName);
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, content);

        return newFile.toAbsolutePath()
                .toString();
    }

    private static String getResourcesDir() throws IOException {
        return new File(".").getCanonicalFile().toString() + "\\screenshot\\";
    }
}