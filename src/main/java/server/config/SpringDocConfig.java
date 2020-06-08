package server.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Info customInfo = new Info()
                .title("ADN Project's backend API")
                .description("Assignment for Mobile Development (CO3043-192) course");
        return new OpenAPI().info(customInfo);
    }
}
