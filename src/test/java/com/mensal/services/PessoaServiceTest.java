package com.mensal.services;

import com.mensal.entities.CaixaEntity;
import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.PessoaEntity;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.PessoaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    PessoaRepository pessoaRepository;

    @Mock
    CaixaRepository caixaRepository;

    private PessoaEntity pessoa1;

    @BeforeEach
    void setup() {
        pessoa1 = new PessoaEntity(1L, "João", "99999-9999", "joao@mail.com", null);
        PessoaEntity pessoa2 = new PessoaEntity(2L, "Maria", "88888-8888", "maria@mail.com", null);

        CarteiraEntity carteira = new CarteiraEntity();
        CaixaEntity caixa = new CaixaEntity();
        caixa.setConta("Caixa João");
        carteira.setCaixa(caixa);
        pessoa1.setCarteira(carteira);

        Mockito.when(pessoaRepository.findAll()).thenReturn(Arrays.asList(pessoa1, pessoa2));
        Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa1));
        Mockito.when(pessoaRepository.findById(2L)).thenReturn(Optional.of(pessoa2));
        Mockito.when(pessoaRepository.save(Mockito.any(PessoaEntity.class))).thenReturn(pessoa1);

        Mockito.when(caixaRepository.save(Mockito.any(CaixaEntity.class))).thenReturn(caixa);
    }

    @Test
    @DisplayName("Teste para salvar pessoa")
    void testSavePessoa() {
        PessoaEntity pessoa = new PessoaEntity(null, "Carlos", "77777-7777", "carlos@mail.com", null);
        Mockito.when(pessoaRepository.save(pessoa)).thenReturn(pessoa1);

        PessoaEntity savedPessoa = pessoaService.save(pessoa);
        Assertions.assertNotNull(savedPessoa);
        Assertions.assertEquals("João", savedPessoa.getNome());
    }

    @Test
    @DisplayName("Teste para buscar todas as pessoas")
    void testFindAllPessoas() {
        List<PessoaEntity> pessoas = pessoaService.findAll();
        Assertions.assertEquals(2, pessoas.size());
        Assertions.assertEquals("João", pessoas.get(0).getNome());
        Assertions.assertEquals("Maria", pessoas.get(1).getNome());
    }

    @Test
    @DisplayName("Teste para buscar pessoa por ID")
    void testFindById() {
        PessoaEntity pessoa = pessoaService.findById(1L);
        Assertions.assertNotNull(pessoa);
        Assertions.assertEquals("João", pessoa.getNome());
    }
}
