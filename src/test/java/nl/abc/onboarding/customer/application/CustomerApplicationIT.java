package nl.abc.onboarding.customer.application;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.UUID;

import static nl.abc.onboarding.customer.application.response.CustomerResponse.generateInstructions;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerApplicationIT {

    private record FileRecord(Path picDir, Path idDir, Path jsonTemp,
                              Path photo, Path idDoc) implements AutoCloseable {
        @Override
        public void close() throws Exception {
            Files.deleteIfExists(this.photo());
            Files.deleteIfExists(this.idDoc());
            Files.deleteIfExists(this.jsonTemp());
        }
    }

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testOnboardEndpoint_createsFilesAndOnboardCustomer() throws Exception {
        // file location is set in application-test.properties to uploads/pic and uploads/id

        // GIVEN
        try (FileRecord fileRecord = createFileAndReturnAsRecord("/request.json")) {

            // WHEN
            Response response = callOboardEndpoint(
                    fileRecord.jsonTemp(), fileRecord.photo(), fileRecord.idDoc())
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            String identifier = response.jsonPath().getString("identifier");
            String externalIdentifier = response.jsonPath().getString("externalIdentifier");
            String loginInstructions = response.jsonPath().getString("loginInstructions");

            // THEN
            assertNotNull(identifier);
            assertEquals("ext-123", externalIdentifier);
            assertEquals(generateInstructions(UUID.fromString(identifier)), loginInstructions);
            assertFileDirectory(fileRecord);
        }
    }

    @Test
    void testOnboardEndpoint_withInvalidEmailToGetInvalidResponse() throws Exception {
        // GIVEN
        try (FileRecord fileRecord = createFileAndReturnAsRecord(
                "/invalid-email-request.json")) {

            // WHEN
            Response response = callOboardEndpoint(
                    fileRecord.jsonTemp(), fileRecord.photo(),
                    fileRecord.idDoc())
                    .then()
                    .statusCode(400)
                    .extract()
                    .response();

            String message = response.then().extract().jsonPath().getString(
                    "message");
            Assertions.assertEquals("The email 'invalid email' is not valid.", message);
        }
    }

    private static Response callOboardEndpoint(Path jsonTemp, Path photo, Path idDoc) {
        return RestAssured.given()
                .multiPart("data", jsonTemp.toFile(), "application/json")
                .multiPart("photo", photo.toFile(), "image/jpeg")
                .multiPart("id-document", idDoc.toFile(), "image/jpeg")
                .when()
                .post("/onboarding");

    }

    private static void assertFileDirectory(FileRecord fileRecord) throws IOException {
        assertTrue(Files.exists(fileRecord.picDir()), "pic directory should exist");
        try (var s = Files.list(fileRecord.picDir())) {
            assertTrue(s.findAny().isPresent(), "pic directory should contain a file");
        }
        assertTrue(Files.exists(fileRecord.idDir()), "id directory should exist");
        try (var s = Files.list(fileRecord.idDir())) {
            assertTrue(s.findAny().isPresent(), "id directory should contain a file");
        }
    }

    /**
     * This method has been AI generated
     * @return
     * @throws Exception
     */
    private FileRecord createFileAndReturnAsRecord(String jsonPath) throws Exception {
        Path picDir = Path.of("uploads/pic");
        Path idDir = Path.of("uploads/id");
        deleteIfExistsRecursively(picDir);
        deleteIfExistsRecursively(idDir);

        // read json request from test resources
        InputStream jsonStream = getClass().getResourceAsStream(jsonPath);
        assertNotNull(jsonStream, "request.json resource must be present");
        Path jsonTemp = Files.createTempFile("request", ".json");
        Files.copy(jsonStream, jsonTemp, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        // create two tiny in-memory images and save to temp files
        Path photo = Files.createTempFile("photo", ".jpg");
        Path idDoc = Files.createTempFile("id", ".jpg");
        createTestImage(photo);
        createTestImage(idDoc);

        return new FileRecord(picDir, idDir, jsonTemp, photo, idDoc);
    }

    /**
     * This method is AI generated
     * @param target
     * @throws Exception
     */
    private void createTestImage(Path target) throws Exception {
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        try {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, 10, 10);
        } finally {
            g.dispose();
        }
        ImageIO.write(img, "jpg", target.toFile());
    }

    /**
     * This method is AI generated
     * @param path
     * @throws Exception
     */
    private void deleteIfExistsRecursively(Path path) throws Exception {
        if (Files.notExists(path)) return;
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (Exception e) {
                        // ignore
                    }
                });
    }
}
