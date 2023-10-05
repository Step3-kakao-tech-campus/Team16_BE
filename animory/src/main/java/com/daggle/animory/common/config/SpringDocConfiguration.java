package com.daggle.animory.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI openAPI() {

        final String jwtSchemeName = "jwtAuth";

        // API 요청헤더에 인증정보 포함
        final SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        // SecuritySchemes 등록
        final Components components = new Components()
            .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                .name(jwtSchemeName)
                .type(SecurityScheme.Type.HTTP) // HTTP 방식
                .scheme("Bearer")
                .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

        return new OpenAPI()
            .info(
                new Info()
                    .title("애니모리 API 문서")
                    .description("프로젝트 애니모리의 서버 API 문서 입니다.")
                    .version("v1.0.0")
            )
            .addSecurityItem(securityRequirement)
            .components(components)
            .externalDocs(
                new ExternalDocumentation()
                    .description("애니모리 BE Wiki")
                    .url("https://github.com/Step3-kakao-tech-campus/Team16_BE/wiki")
            )
            .servers(
                List.of(
                    new Server()
                        .url("http://ec2-3-37-14-140.ap-northeast-2.compute.amazonaws.com/api")
                        .description("클라우드에 배포되어 있는 테스트 서버입니다."),
                    new Server()
                        .url("http://localhost:8080")
                        .description("BE 팀에서 사용할 로컬 환경 테스트를 위한 서버입니다.")
                )
            );


    }
}
