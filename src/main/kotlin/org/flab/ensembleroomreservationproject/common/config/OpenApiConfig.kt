package org.flab.ensembleroomreservationproject.common.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("합주실 예약 API")
                .description("앱인토스 합주실 예약 시스템 백엔드 API")
                .version("v1.0.0")
        )
}
