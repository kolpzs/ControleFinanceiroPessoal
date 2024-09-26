package com.mensal.services;

import com.mensal.entities.*;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CarteiraServiceTest {

    @Autowired
    private CarteiraService carteiraService;

    @MockBean
    private CarteiraRepository carteiraRepository;

    @MockBean
    private CaixaRepository caixaRepository;

    @MockBean
    private PessoaRepository pessoaRepository;

    private PessoaEntity pessoa;
    private CarteiraEntity carteira;

    @BeforeEach
    void setup() {
        pessoa = new PessoaEntity();
        pessoa.setId(1L);
        pessoa.setNome("João");

        carteira = new CarteiraEntity();
        carteira.setId(1L);
        carteira.setNome("Carteira João");
        carteira.setPessoa(pessoa);

        CaixaEntity caixa = new CaixaEntity();
        caixa.setId(1L);
        caixa.setConta("Caixa de João");
        caixa.setValor(0);
        caixa.setCarteira(carteira);

        carteira.setCaixa(caixa);

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(carteiraRepository.save(any(CarteiraEntity.class))).thenAnswer(invocation -> {
            CarteiraEntity c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });
        when(caixaRepository.save(any(CaixaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    @DisplayName("Teste de salvar uma nova carteira")
    void testSaveCarteira() {
        CarteiraEntity novaCarteira = new CarteiraEntity();
        novaCarteira.setNome("Carteira Nova");

        CarteiraEntity carteiraSalva = carteiraService.save(novaCarteira, 1L);

        assertNotNull(carteiraSalva);
        assertEquals("Carteira Nova", carteiraSalva.getNome());
        assertEquals(pessoa, carteiraSalva.getPessoa());
        assertNotNull(carteiraSalva.getCaixa());
        assertEquals("Caixa de João", carteiraSalva.getCaixa().getConta());
        assertEquals(0, carteiraSalva.getCaixa().getValor());

        verify(carteiraRepository, times(1)).save(any(CarteiraEntity.class));
        verify(caixaRepository, times(1)).save(any(CaixaEntity.class));
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    @DisplayName("Teste de encontrar carteira por ID")
    void testFindById() {
        when(carteiraRepository.findById(1L)).thenReturn(Optional.of(carteira));

        CarteiraEntity encontrada = carteiraService.findById(1L);

        assertNotNull(encontrada);
        assertEquals("Carteira João", encontrada.getNome());
        assertEquals("Caixa de João", encontrada.getCaixa().getConta());

        verify(carteiraRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Teste de obter saldo do caixa")
    void testGetCaixaSaldo() {
        when(carteiraRepository.findById(1L)).thenReturn(Optional.of(carteira));

        float saldo = carteiraService.getCaixaSaldo(1L);

        assertEquals(0, saldo);

        verify(carteiraRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Teste de gerar relatório mensal")
    void testRelatorioMensal() {
        DespesaEntity despesa1 = new DespesaEntity();
        despesa1.setValor(100L);

        ReceitaEntity receita1 = new ReceitaEntity();
        receita1.setValor(200L);

        MetaEntity meta1 = new MetaEntity();
        meta1.setNome("Meta 1");
        meta1.setDescricao("Descrição Meta 1");
        meta1.setValor(500L);
        meta1.setCompleto(100L);
        meta1.setData_final(java.sql.Date.valueOf(LocalDate.now().plusDays(10)));

        carteira.setDespesas(List.of(despesa1));
        carteira.setReceitas(List.of(receita1));
        carteira.setMetas(List.of(meta1));

        when(carteiraRepository.listDespesasMensal(new Date(), new CarteiraEntity())).thenReturn(carteira.getDespesas());
        when(carteiraRepository.listReceitasMensal(new Date(), new CarteiraEntity())).thenReturn(carteira.getReceitas());

        String relatorio = carteiraService.relatorioMensal(1L);

        assertNotNull(relatorio);
        assertTrue(relatorio.contains("Carteira: Carteira João"));
        assertTrue(relatorio.contains("Total de Receitas: R$ 200.00"));
        assertTrue(relatorio.contains("Total de Despesas: R$ 100.00"));
        assertTrue(relatorio.contains("Saldo do Caixa: R$ 0.00"));
        assertTrue(relatorio.contains("Meta 1"));

        verify(carteiraRepository, times(1)).findById(1L);
        verify(carteiraRepository, times(1)).listDespesasMensal(new Date(), new CarteiraEntity());
        verify(carteiraRepository, times(1)).listReceitasMensal(new Date(), new CarteiraEntity());
    }
}
