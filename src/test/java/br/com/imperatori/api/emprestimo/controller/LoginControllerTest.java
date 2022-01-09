package br.com.imperatori.api.emprestimo.controller;

import br.com.imperatori.api.emprestimo.repository.ClienteRepository;
import br.com.imperatori.api.emprestimo.service.LoginService;
import br.com.imperatori.api.emprestimo.service.TokenService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {

    @Autowired
    private LoginController loginController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private ClienteRepository clienteRepository;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(loginController)
                .build();
    }

    @Test
    void deveRetonarBadRequest_QuandoLoginNaoInformado() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/logar"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}