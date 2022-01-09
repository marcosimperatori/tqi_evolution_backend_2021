package br.com.imperatori.api.emprestimo.repository;

import br.com.imperatori.api.emprestimo.model.EmprestimoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<EmprestimoModel,Integer> {

}
