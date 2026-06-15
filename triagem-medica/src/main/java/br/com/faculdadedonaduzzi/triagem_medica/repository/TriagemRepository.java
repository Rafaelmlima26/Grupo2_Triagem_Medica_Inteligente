package br.com.faculdadedonaduzzi.triagem_medica.repository;

import br.com.faculdadedonaduzzi.triagem_medica.model.Triagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// interface pra acessar o banco, o spring data ja implementa tudo pra gente
@Repository
public interface TriagemRepository extends JpaRepository<Triagem, Long> {

    // busca triagens pelo nome do paciente (ignora maiusculas/minusculas)
    List<Triagem> findByNomePacienteContainingIgnoreCase(String nome);

    // busca por nivel de prioridade
    List<Triagem> findByPrioridade(String prioridade);
}
