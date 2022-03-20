package com;

import com.google.common.base.Predicates;
import com.setting.BitostSuiteSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SuchApplication {
    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SuchApplication.class);
        application.run(args);
    }

    @Bean
    public Docket lenderApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(bitostSuiteSetting.getSwagger_is_enable())
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.not(
                        RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Teleport EVM RPC TEST")
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
