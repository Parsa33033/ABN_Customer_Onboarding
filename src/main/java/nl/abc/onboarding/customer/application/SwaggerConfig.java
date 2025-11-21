package nl.abc.onboarding.customer.application;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Customer Onboarding API")
                        .version("0.0.1")
                        .description("API for onboarding customers with file uploads"));
    }

    @Bean
    public GroupedOpenApi onboardingApi() {
        return GroupedOpenApi.builder()
                .group("onboarding")
                .packagesToScan("nl.abc.onboarding.customer.application")
                .pathsToMatch("/onboarding", "/onboarding/**")
                .build();
    }
}
