package br.com.faculdadedonaduzzi.triagem_medica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// configuração da documentação OpenAPI / Swagger
// acesse em: http://localhost:8080/swagger-ui/index.html
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Triagem Médica Inteligente")
                        .version("1.0.0")
                        .description("""
                                Sistema de triagem médica que utiliza Machine Learning (Random Forest) \
                                para classificar a prioridade de atendimento dos pacientes com base \
                                no Protocolo de Manchester.

                                **Níveis de Prioridade:**
                                - 🔴 Vermelho — Emergência (atendimento imediato)
                                - 🟠 Laranja — Muito Urgente (até 10 min)
                                - 🟡 Amarelo — Urgente (até 60 min)
                                - 🟢 Verde — Pouco Urgente (até 120 min)
                                - 🔵 Azul — Não Urgente (até 240 min)
                                """)
                        .contact(new Contact()
                                .name("Grupo 2 — Faculdade Donà Duzzi")
                                .email("grupo2@faculdadedonaduzzi.com.br")));
    }
}
