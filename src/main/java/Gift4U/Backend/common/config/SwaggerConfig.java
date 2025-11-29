package Gift4U.Backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI gift4UApi() {

		final String JWT_SCHEME_NAME = "bearerAuth";

		return new OpenAPI()
			.info(new Info()
				.title("Gift4U Server API")
				.description("Gift4U API 명세서")
				.version("1.0.0"))
			.addSecurityItem(new SecurityRequirement().addList(JWT_SCHEME_NAME))
			.components(new Components()
				.addSecuritySchemes(JWT_SCHEME_NAME,
					new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")
				)
			);
	}
}
