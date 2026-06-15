package br.com.faculdadedonaduzzi.triagem_medica.controller;

import br.com.faculdadedonaduzzi.triagem_medica.dto.TriagemRequest;
import br.com.faculdadedonaduzzi.triagem_medica.model.Triagem;
import br.com.faculdadedonaduzzi.triagem_medica.service.TriagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/triagem")
@Tag(name = "Triagem", description = "Endpoints para triagem médica inteligente com classificação por ML")
public class TriagemController {

    private final TriagemService service;

    public TriagemController(TriagemService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
        summary = "Realizar triagem",
        description = "Recebe os sinais vitais do paciente, envia ao modelo de ML e retorna a classificação de prioridade (Protocolo de Manchester)",
        responses = {
            @ApiResponse(responseCode = "201", description = "Triagem realizada com sucesso",
                content = @Content(schema = @Schema(implementation = Triagem.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno ou ML indisponível")
        }
    )
    public ResponseEntity<Triagem> criar(@Valid @RequestBody TriagemRequest request) {
        Triagem triagem = service.criarTriagem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(triagem);
    }

    @GetMapping
    @Operation(
        summary = "Listar todas as triagens",
        description = "Retorna o histórico completo de triagens salvas no banco de dados"
    )
    public ResponseEntity<List<Triagem>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar triagem por ID",
        description = "Retorna uma triagem específica pelo seu identificador"
    )
    public ResponseEntity<Triagem> buscarPorId(
            @Parameter(description = "ID da triagem", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/buscar")
    @Operation(
        summary = "Buscar triagens por nome do paciente",
        description = "Retorna todas as triagens que correspondam ao nome informado (busca parcial)"
    )
    public ResponseEntity<List<Triagem>> buscarPorNome(
            @Parameter(description = "Nome do paciente (parcial)", example = "João")
            @RequestParam String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }
}
