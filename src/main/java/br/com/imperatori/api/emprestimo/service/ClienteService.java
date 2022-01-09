package br.com.imperatori.api.emprestimo.service;

import br.com.imperatori.api.emprestimo.dto.ClienteDTO;
import br.com.imperatori.api.emprestimo.model.ClienteModel;
import br.com.imperatori.api.emprestimo.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/* Todas as regras de negócio e validação ficarão concentradas nesta classe. */

@Service
public class ClienteService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    /* Método responsável por salvar um novo cliente no banco de dados. */
    public ClienteDTO salvar(ClienteDTO clienteDTO){
        //certificando que os campos principais foram informados
        if(clienteDTO.getNome().isEmpty() || clienteDTO.getSenha().isEmpty() || clienteDTO.getEmail().isEmpty()){
            return null;
        }

        clienteDTO.setSenha(tokenService.gerarSenha((clienteDTO.getSenha() + clienteDTO.getNome() + clienteDTO.getEmail())));
        ClienteModel cliente = modelMapper.map(clienteDTO,ClienteModel.class);

        /* Por padrão cada cliente ao ser inserido no banco de dados, deve receber o status de ativo
        *  e não bloqueado. Estes atributos serão utilizados em situações onde será necessário inativar
        *  ou bloquear o acesso de um usuário ao sistema. */
        cliente.setAtivo(true);
        cliente.setBloqueado(false);

        cliente = clienteRepository.save(cliente);
       return modelMapper.map(cliente, ClienteDTO.class);
    }

    /* Método responsável por atualizar um cliente, faz uma atualização de todos os atributos do cliente.
    *  Antes de tentar realizar a atualização, ele verifica se foi possível recuperar o cliente conforme o
    *  id passado. */
    public ClienteDTO editar(ClienteDTO clienteDTO){
        ClienteModel cliente = clienteRepository.getById(clienteDTO.getId());

        if(cliente != null) {
            ClienteModel clienteUpdate = modelMapper.map(clienteDTO, ClienteModel.class);
            ClienteModel clienteBd = cliente;

            clienteBd.setNome(clienteUpdate.getNome());
            clienteBd.setEmail(clienteUpdate.getEmail());
            clienteBd.setSenha(clienteUpdate.getSenha());
            clienteBd.setRg(clienteUpdate.getRg());
            clienteBd.setCpf(clienteUpdate.getCpf());
            clienteBd.setRenda(clienteUpdate.getRenda());
            return modelMapper.map(clienteRepository.save(clienteBd), ClienteDTO.class);
        } else {
            return null;
        }
    }

    /* Método responsável por excluir um cliente, dado um id. */
    public Boolean excluir(Integer id){
        clienteRepository.deleteById(id);
        Optional<ClienteModel> cliente = clienteRepository.findById(id);

        if(!cliente.isPresent()) {
            return true;
        } else{
            return false;
        }
    }

    /* Método responsável por listar todos os clientes. */
    public List<ClienteDTO> listarTodos(){
        List<ClienteDTO> listadtos = clienteRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user,ClienteDTO.class))
                .collect(Collectors.toList());

       return  listadtos;
    }

    /* Método responsável por obter um cliente a partir de um id. */
    public ClienteDTO  getClienteId(Integer id){
        Optional<ClienteModel> cliente = clienteRepository.findById(id);

        if(cliente.isPresent()){
            return modelMapper.map(cliente,ClienteDTO.class);
        } else{
            return null;
        }
    }

    /* Método responsável por verificar se o cliente já existe na base de dados antes de tentar fazer sua
    *  inserção/edição. */
    public Boolean clienteJaExiste(String nome){
        ClienteModel tempCliente = clienteRepository.findByNome(nome);
        return (tempCliente != null) ? true : false;
    }
}
