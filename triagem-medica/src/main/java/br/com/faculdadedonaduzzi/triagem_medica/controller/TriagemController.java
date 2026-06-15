package br.com.faculdadedonaduzzi.triagem_medica.controller;

import br.com.faculdadedonaduzzi.triagem_medica.dto.TriagemRequest;
import br.com.faculdadedonaduzzi.triagem_medica.model.Triagem;
import br.com.faculdadedonaduzzi.triagem_medica.service.TriagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller principal da API de triagem
@RestController
@RequestMapping("/api/triagem")
public class TriagemController {

    private final TriagemService service;

    public TriagemController(TriagemService service) {
        this.service = service;
    }

    // endpoint pra criar uma nova triagem
    // recebe os dados do paciente, chama o ML e salva no banco
    @PostMapping
    public ResponseEntity<Triagem> criar(@RequestBody TriagemRequest request) {
        Triagem triagem = service.criarTriagem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(triagem);
    }

    // lista todo o historico de triagens
    @GetMapping
    public ResponseEntity<List<Triagem>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // busca uma triagem pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Triagem> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // busca triagens pelo nome do paciente
    @GetMapping("/buscar")
    public ResponseEntity<List<Triagem>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }
}
