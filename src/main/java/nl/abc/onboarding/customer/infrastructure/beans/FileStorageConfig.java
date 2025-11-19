package nl.abc.onboarding.customer.infrastructure.beans;

import nl.abc.onboarding.customer.infrastructure.filestorage.FileStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfig {

    @Bean
    public FileStorage fileStorage(
            @Value("${filestorage.id.path}") String idPath,
            @Value("${filestorage.pic.path}") String picPath) {
        return new FileStorage(idPath, picPath);
    }
}

