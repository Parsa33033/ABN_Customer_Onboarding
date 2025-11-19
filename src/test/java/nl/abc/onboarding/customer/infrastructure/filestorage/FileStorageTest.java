package nl.abc.onboarding.customer.infrastructure.filestorage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CompletionException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileStorageTest {

    @Test
    void persistAndDeleteImage_shouldStoreAndRemoveFile(@TempDir Path tempDir) throws Exception {
        String idPath = tempDir.resolve("id").toString();
        String picPath = tempDir.resolve("pic").toString();

        FileStorage storage = new FileStorage(idPath, picPath);

        // create an in-memory PNG image
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        try {
            g.setPaint(Color.BLUE);
            g.fillRect(0, 0, img.getWidth(), img.getHeight());
        } finally {
            g.dispose();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        MockMultipartFile multipart = new MockMultipartFile(
            "file",
            "test.png",
            "image/png",
            imageBytes
        );

        UUID uuid = UUID.randomUUID();
        String returnedPath = storage.persist(multipart, uuid, FileType.PIC).join();
        assertNotNull(returnedPath);
        Path stored = Path.of(returnedPath);
        assertTrue(Files.exists(stored), "Stored image should exist");
        // compare absolute path strings to avoid Path equality quirks across platforms
        assertEquals(
            tempDir.resolve("pic").resolve(uuid.toString() + ".png").toAbsolutePath().toString(),
            stored.toAbsolutePath().toString()
        );

        // use new delete(String fullPath) API
        storage.delete(returnedPath).join();
        assertFalse(Files.exists(stored), "File should be removed after delete");
    }

    @Test
    void persistNonImage_shouldThrow(@TempDir Path tempDir) throws Exception {
        String idPath = tempDir.resolve("id").toString();
        String picPath = tempDir.resolve("pic").toString();

        FileStorage storage = new FileStorage(idPath, picPath);

        MockMultipartFile multipart = new MockMultipartFile(
            "file",
            "not-image.txt",
            "text/plain",
            "this is not an image".getBytes()
        );

        UUID uuid = UUID.randomUUID();
        CompletionException ex = assertThrows(CompletionException.class, () -> storage.persist(multipart, uuid, FileType.ID).join());
        assertTrue(ex.getCause() instanceof IllegalArgumentException);
    }

    @Test
    void deleteNonExisting_shouldThrow(@TempDir Path tempDir) throws Exception {
        String idPath = tempDir.resolve("id").toString();
        String picPath = tempDir.resolve("pic").toString();

        FileStorage storage = new FileStorage(idPath, picPath);

        UUID uuid = UUID.randomUUID();
        String nonExistent = tempDir.resolve("id").resolve(uuid.toString() + ".png").toString();
        CompletionException ex = assertThrows(CompletionException.class, () -> storage.delete(nonExistent).join());
        assertTrue(ex.getCause() instanceof IOException);
    }
}
