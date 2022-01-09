package br.com.imperatori.api.emprestimo.repository;

import br.com.imperatori.api.emprestimo.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Integer> {

    ClienteModel findByNome(String nome);

    @Query("select c from ClienteModel c where c.email=:email")
    ClienteModel findByEmail(String email);

}
