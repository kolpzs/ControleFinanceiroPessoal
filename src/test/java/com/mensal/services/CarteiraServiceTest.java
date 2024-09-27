package com.mensal.services;

import com.mensal.entities.*;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
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

    private CarteiraEntity carteira;
    private PessoaEntity pessoa;

    @BeforeEach
    void setUp() {
        pessoa = new PessoaEntity();
        pessoa.setId(1L);
        pessoa.setNome("João");

        carteira = new CarteiraEntity();
        carteira.setId(1L);
        carteira.setNome("Carteira de João");
        carteira.setPessoa(pessoa);
        carteira.setCaixa(new CaixaEntity());
    }

    @Test
    void testSaveCarteira() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(carteiraRepository.save(Mockito.any(CarteiraEntity.class))).thenReturn(carteira);

        CarteiraEntity novaCarteira = carteiraService.save(carteira, 1L);

        assertNotNull(novaCarteira);
        assertEquals("Carteira de João", novaCarteira.getNome());
        verify(pessoaRepository, times(1)).findById(1L);
        verify(carteiraRepository, times(1)).save(Mockito.any(CarteiraEntity.class));
    }

    @Test
    void testFindById() {
        when(carteiraRepository.findById(1L)).thenReturn(Optional.of(carteira));

        CarteiraEntity encontrada = carteiraService.findById(1L);

        assertNotNull(encontrada);
        assertEquals(carteira.getNome(), encontrada.getNome());
        verify(carteiraRepository, times(1)).findById(1L);
    }

    @Test
    void testListDespesasTotal() {
        DespesaEntity despesa = new DespesaEntity();
        despesa.setValor(100);
        despesa.setData(new Date());

        when(carteiraRepository.findById(1L)).thenReturn(Optional.of(carteira));
        when(carteiraRepository.listDespesasTotal(carteira)).thenReturn(Collections.singletonList(despesa));

        List<DespesaEntity> despesas = carteiraService.listDespesasTotal(1L);

        assertNotNull(despesas);
        assertEquals(1, despesas.size());
        assertEquals(100, despesas.get(0).getValor());
        verify(carteiraRepository, times(1)).listDespesasTotal(carteira);
    }

    @Test
    void testListReceitasTotal() {
        ReceitaEntity receita = new ReceitaEntity();
        receita.setValor(200);
        receita.setData(new Date());

        when(carteiraRepository.findById(1L)).thenReturn(Optional.of(carteira));
        when(carteiraRepository.listReceitasTotal(carteira)).thenReturn(Collections.singletonList(receita));

        List<ReceitaEntity> receitas = carteiraService.listReceitasTotal(1L);

        assertNotNull(receitas);
        assertEquals(1, receitas.size());
        assertEquals(200, receitas.get(0).getValor());
        verify(carteiraRepository, times(1)).listReceitasTotal(carteira);
    }

    @Test
    void testListDespesasMensal() {
        DespesaEntity despesa = new DespesaEntity();
        despesa.setValor(150);
        despesa.setData(new Date());

        when(carteiraRepository.findById(1L)).thenReturn(Optional.of(carteira));
        when(carteiraRepository.listDespesasMensal(any(Date.class), eq(carteira))).thenReturn(Collections.singletonList(despesa));

        List<DespesaEntity> despesasMensais = carteiraService.listDespesasMensal(1L);

        assertNotNull(despesasMensais);
        assertEquals(1, despesasMensais.size());
        assertEquals(150, despesasMensais.get(0).getValor());
        verify(carteiraRepository, times(1)).listDespesasMensal(any(Date.class), eq(carteira));
    }

    @Test
    void testListReceitasMensal() {
        ReceitaEntity receita = new ReceitaEntity();
        receita.setValor(250);
        receita.setData(new Date());

        when(carteiraRepository.findById(1L)).thenReturn(Optional.of(carteira));
        when(carteiraRepository.listReceitasMensal(any(Date.class), eq(carteira))).thenReturn(Collections.singletonList(receita));

        List<ReceitaEntity> receitasMensais = carteiraService.listReceitasMensal(1L);

        assertNotNull(receitasMensais);
        assertEquals(1, receitasMensais.size());
        assertEquals(250, receitasMensais.get(0).getValor());
        verify(carteiraRepository, times(1)).listReceitasMensal(any(Date.class), eq(carteira));
    }

    @Test
    void testSomaDespesasMensal() {
        DespesaEntity despesa1 = new DespesaEntity();
        despesa1.setValor(100);
        DespesaEntity despesa2 = new DespesaEntity();
        despesa2.setValor(200);

        when(carteiraRepository.findById(1L)).thenReturn(Optional.of(carteira));
        when(carteiraRepository.listDespesasMensal(any(Date.class), eq(carteira)))
                .thenReturn(List.of(despesa1, despesa2));

        float soma = carteiraService.somaDespesasMensal(1L);

        assertEquals(300, soma);
        verify(carteiraRepository, times(1)).listDespesasMensal(any(Date.class), eq(carteira));
    }

    @Test
    void testSomaReceitasMensal() {
        ReceitaEntity receita1 = new ReceitaEntity();
        receita1.setValor(150);
        ReceitaEntity receita2 = new ReceitaEntity();
        receita2.setValor(350);

        when(carteiraRepository.findById(1L)).thenReturn(Optional.of(carteira));
        when(carteiraRepository.listReceitasMensal(any(Date.class), eq(carteira)))
                .thenReturn(List.of(receita1, receita2));

        float soma = carteiraService.somaReceitasMensal(1L);

        assertEquals(500, soma);
        verify(carteiraRepository, times(1)).listReceitasMensal(any(Date.class), eq(carteira));
    }

//    @Test
//    void testRelatorioMensal() {
//        // Inicializa o caixa
//        CaixaEntity caixa = new CaixaEntity();
//        caixa.setValor(1000);
//        carteira.setCaixa(caixa);
//
//        // Inicializa a lista de metas, mesmo que vazia
//        carteira.setMetas(Collections.emptyList());
//
//        when(carteiraRepository.findById(1L)).thenReturn(Optional.of(carteira));
//        when(carteiraRepository.listDespesasMensal(any(Date.class), eq(carteira)))
//                .thenReturn(Collections.emptyList());
//        when(carteiraRepository.listReceitasMensal(any(Date.class), eq(carteira)))
//                .thenReturn(Collections.emptyList());
//
//        String relatorio = carteiraService.relatorioMensal(1L);
//
//        // Debug: imprime o conteúdo do relatório
//        System.out.println(relatorio);
//
//        assertTrue(relatorio.contains("Carteira: Carteira de João"), "Nome da carteira não encontrado.");
//        assertTrue(relatorio.contains("Total de Receitas: R$ 0.00"), "Total de Receitas incorreto.");
//        assertTrue(relatorio.contains("Total de Despesas: R$ 0.00"), "Total de Despesas incorreto.");
//        assertTrue(relatorio.contains("Saldo do Caixa: R$ 1000.00"), "Saldo do Caixa incorreto.");
//    }
//
//
//    @Test
//    void testRelatorioMetas() {
//        MetaEntity meta = new MetaEntity();
//        meta.setNome("Meta 1");
//        meta.setValor(100);
//        meta.setCompleto(50);
//        meta.setData_final(new Date(System.currentTimeMillis() + 86400000)); // 1 dia no futuro
//
//        carteira.setMetas(Collections.singletonList(meta));
//        when(carteiraRepository.findById(1L)).thenReturn(Optional.of(carteira));
//
//        List<String> metasRelatorio = carteiraService.relatorioMetas(1L);
//
//        assertEquals(1, metasRelatorio.size());
//        assertTrue(metasRelatorio.get(0).contains("Meta 1"));
//        assertTrue(metasRelatorio.get(0).contains("Descrição: null"));
//        assertTrue(metasRelatorio.get(0).contains("Total: R$ 100.00"));
//        assertTrue(metasRelatorio.get(0).contains("Restante: R$ 50.00"));
//    }
}
