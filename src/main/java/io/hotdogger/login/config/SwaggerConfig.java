package io.hotdogger.login.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false) //get rid of default response codes
                .pathMapping("/").enableUrlTemplating(true) //allow multiple swagger docs on a single path
//        .ignoredParameterTypes(Throwable.class,
//            StackTraceElement.class) //ignore these types in Models, but adding it in generates errors
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex(("/error")))) //exclude Spring error controllers
                .build();
    }
}