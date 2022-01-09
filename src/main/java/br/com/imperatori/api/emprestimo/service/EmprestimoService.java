package br.com.imperatori.api.emprestimo.service;

import br.com.imperatori.api.emprestimo.dto.EmprestimoDTO;
import br.com.imperatori.api.emprestimo.dto.EmprestimoDetalhadoDTO;
import br.com.imperatori.api.emprestimo.dto.ListaEmprestimoDTO;
import br.com.imperatori.api.emprestimo.model.ClienteModel;
import br.com.imperatori.api.emprestimo.model.EmprestimoModel;
import br.com.imperatori.api.emprestimo.repository.ClienteRepository;
import br.com.imperatori.api.emprestimo.repository.EmprestimoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    @Autowired
    CredencialService credencialService;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<String> solicitarEmprestimo(EmprestimoDTO emprestimoDTO, HttpServletRequest request){

        if (!checarVectoPrimeiraParcela(emprestimoDTO.getVectoPrimeiraParcela())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).
                    body("O vencimento ultrapassa 03 meses a partir desta data.  " +
                            " Vencimento primeira parcela: " + emprestimoDTO.getVectoPrimeiraParcela());
        }

        if(checarTotalParcelas(emprestimoDTO.getNumeroPrestacoes())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).
                    body("A quantidade de parcelas ultrapassa o total permitido. " +
                            " Permitido: 60, solicitado: " + emprestimoDTO.getNumeroPrestacoes());
        }

        ClienteModel cliente = clienteRepository.findByNome(credencialService.getUsuarioLogado(request));

        if (cliente == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).
                    body("Não foi possível localizar o cliente para dar continuidade a sua solicitação.");
        }

        EmprestimoModel emprestimo = new EmprestimoModel();
        emprestimo.setNumeroPrestacoes(stringToInteger(emprestimoDTO.getNumeroPrestacoes()));
        emprestimo.setVectoPrimeiraParcela(stringToDate(emprestimoDTO.getVectoPrimeiraParcela()));
        emprestimo.setValorTotal(stringToDouble(emprestimoDTO.getValorTotal()));
        emprestimo.setStatus("Pendente");
        emprestimo.setCliente(cliente);

        emprestimo = emprestimoRepository.save(emprestimo);
        return ResponseEntity.status(HttpStatus.OK).body(emprestimo.toString());
    }

    public ResponseEntity<?> consultarEmprestimo(Integer idEmprestimo){
        EmprestimoModel emprestimo = emprestimoRepository.getById(idEmprestimo);


        if (emprestimo == null){
            return null;
        }

        EmprestimoDetalhadoDTO emprestimoDetalhado = modelMapper.map(emprestimo, EmprestimoDetalhadoDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(filtrarDadosEmprestimos(emprestimoDetalhado));
    }

    public List<ListaEmprestimoDTO> consultarTodosEmprestimos(Integer idCliente){
        ClienteModel cliente = clienteRepository.getById(idCliente);

        if (cliente == null){
            return null;
        }

        List<ListaEmprestimoDTO> listaEmprestimos = cliente.getEmprestimos()
                .stream()
                .map(emp -> modelMapper.map(emp,ListaEmprestimoDTO.class))
                .collect(Collectors.toList());

        return listaEmprestimos;
    }

    private String filtrarDadosEmprestimos(EmprestimoDetalhadoDTO emprestimo){
        String dados = "Dados do emprestimos: { ";

        dados += " código: " + emprestimo.getId() + ", Valor total: " + emprestimo.getValorTotal() +
                 ", Prestações: " + emprestimo.getNumeroPrestacoes() + ", Vencimento primeira parcela: " +
                  emprestimo.getVectoPrimeiraParcela() + ", Email: " + emprestimo.getCliente().getEmail() +
                  ", Renda: " + emprestimo.getCliente().getRenda() + " }";

        return dados;
    }

    /* Método responsável por verificar se a data de vencimento da primeira parcela, solicitada pelo cliente
    *  está  com vencimento para no máximo 03 meses, contados a partir da data da solicitação. */
    private Boolean checarVectoPrimeiraParcela(String vectoPrimeiraParcela){
        Boolean dataOK = false;
        try {
            Date vecto = stringToDate(vectoPrimeiraParcela);

            /* obtém a data atual, na sequencia seta com a data de vencimento definida pelo cliente. */
            Calendar dataVecto = Calendar.getInstance();
            dataVecto.setTime(vecto);

            /* como a data de vencimento da primeira parcela deve no máximo de 03 meses a partir da data de solicitação do
             *  empréstimo, ou seja, data atual, vou capturar a data atual e acrescentar 03 meses nela. */
            Calendar dataVectoProjetada = Calendar.getInstance();
            dataVectoProjetada.add(Calendar.MONTH, 3);

            /* agora vou comparar se a data de vencimento da primeira parcela que o cliente solicitou está dentro de
             *  03 meses. */
            switch (dataVectoProjetada.compareTo(dataVecto)) {
                case -1:         // projeção do vecto primeira parcela é superior a 03 meses
                    dataOK = false;
                break;
                case 0:          // as datas são iguais, logo, estão dentro dos 03 meses
                    dataOK = true;
                break;
                case 1:         // a data está dentro dos 03 meses, é até que menor que 03 meses
                    dataOK = true;
                break;
            }
        }catch (Exception e){
        }
        return dataOK;
    }

    /* Este método é responsável por verificar se a quantidade de parcelas solicitadas é de ate 60, pois este é
    *  o máximo permitido. */
    private Boolean checarTotalParcelas(String parcelas){
        Integer numParcelas = stringToInteger(parcelas);
        return (numParcelas > 60) ? true : false;
    }

    private Date stringToDate(String data){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVecto;

        try{
            dataVecto = df.parse(data);
        }catch (ParseException e){
            dataVecto = new Date();
        }
        return dataVecto;
    }

    private Integer stringToInteger(String prestacao){
        return Integer.valueOf(prestacao).intValue();
    }

    private Double stringToDouble(String valor){
        return Double.valueOf(valor).doubleValue();
    }
}
