package outsera.goldenraspberry.adapter.in.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Golden Raspberry API",
                version = "1.0.0",
                description = "REST API to calculate producers' minimum and maximum award intervals."
        )
)
public class OpenApiConfig { }
