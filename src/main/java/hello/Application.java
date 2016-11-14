package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.base.Predicate;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.ImplicitGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import hello.GreetingController;

import static springfox.documentation.builders.RequestHandlerSelectors.any;
import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = {
    GreetingController.class
  })
  
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  // This is from the CORS configuration example on spring site.
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8080");
        registry.addMapping("/v2/api-docs").allowedOrigins("*");
      }
    };
  }

  // This appears to generate output on the /swagger-resources
  // endpoint by default.
  @Bean
  public Docket greetingApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("Greeting API")
      .apiInfo(apiInfo())
      .select()
      .paths(regex("/greeting.*"))
      .build();
  }
  
  // This seems to get setup and served as part of
  // the v2/api-docs?group=Greeting%20API
  // Which has the swagger.json for stuff.
  
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Springfox Greeting")
      .description("Hopefully this will work.")
      .termsOfServiceUrl("http://springfox.io")
                .contact("springfox")
      .license("Apache License Version 2.0")
      .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
      .version("2.0")
      .build();
  }
}
