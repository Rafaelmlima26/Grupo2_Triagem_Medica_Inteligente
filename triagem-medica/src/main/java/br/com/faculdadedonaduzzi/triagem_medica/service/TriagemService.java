package br.com.faculdadedonaduzzi.triagem_medica.service;

import br.com.faculdadedonaduzzi.triagem_medica.dto.MlResponse;
import br.com.faculdadedonaduzzi.triagem_medica.dto.TriagemRequest;
import br.com.faculdadedonaduzzi.triagem_medica.model.Triagem;
import br.com.faculdadedonaduzzi.triagem_medica.repository.TriagemRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class TriagemService {

    private final TriagemRepository repository;
    private final RestClient mlRestClient;

    // injecao via construtor, mais limpo que usar @Autowired direto
    public TriagemService(TriagemRepository repository, RestClient mlRestClient) {
        this.repository = repository;
        this.mlRestClient = mlRestClient;
    }

    // cria a triagem, manda os sinais vitais pro ML e salva com o resultado
    public Triagem criarTriagem(TriagemRequest request) {
        // montando o body pra mandar pro servico python
        Map<String, Object> body = Map.of(
            "temperatura", request.temperatura(),
            "freq_cardiaca", request.freqCardiaca(),
            "pressao_sistolica", request.pressaoSistolica(),
            "saturacao_o2", request.saturacaoO2(),
            "nivel_dor", request.nivelDor(),
            "idade", request.idade()
        );

        // chamando o endpoint de predicao do servico de ML
        MlResponse mlResponse = mlRestClient.post()
                .uri("/predict")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(MlResponse.class);

        // preenchendo a entidade com os dados do paciente e o resultado do ML
        Triagem triagem = new Triagem();
        triagem.setNomePaciente(request.nomePaciente());
        triagem.setIdade(request.idade());
        triagem.setTemperatura(request.temperatura());
        triagem.setFreqCardiaca(request.freqCardiaca());
        triagem.setPressaoSistolica(request.pressaoSistolica());
        triagem.setSaturacaoO2(request.saturacaoO2());
        triagem.setNivelDor(request.nivelDor());

        // dados que vieram do modelo
        triagem.setPrioridade(mlResponse.prioridade());
        triagem.setDescricaoPrioridade(mlResponse.descricao());
        triagem.setCodigoPrioridade(mlResponse.codigo());

        return repository.save(triagem);
    }

    // lista todas as triagens do historico
    public List<Triagem> listarTodas() {
        return repository.findAll();
    }

    // busca uma triagem especifica pelo id
    public Triagem buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Triagem nao encontrada com id: " + id));
    }

    // busca triagens pelo nome do paciente
    public List<Triagem> buscarPorNome(String nome) {
        return repository.findByNomePacienteContainingIgnoreCase(nome);
    }
}
