package com.mensal.services;

import com.mensal.entities.CaixaEntity;
import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.DespesaEntity;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.DespesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DespesaServiceTest {

    @Autowired
    private DespesaService despesaService;

    @MockBean
    private DespesaRepository despesaRepository;

    @MockBean
    private CaixaRepository caixaRepository;

    @MockBean
    private CarteiraRepository carteiraRepository;

    private CarteiraEntity carteira;
    private CaixaEntity caixa;
    private DespesaEntity despesa;

    @BeforeEach
    void setup() {
        caixa = new CaixaEntity(1L, "Caixa de Teste", 1000f, null);
        carteira = new CarteiraEntity(1L, "Carteira de Teste", null, caixa, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        despesa = new DespesaEntity(1L, new Date(), "Despesa de Teste", 100f, carteira, null);

        Mockito.when(carteiraRepository.findById(carteira.getId())).thenReturn(Optional.of(carteira));
        Mockito.when(despesaRepository.save(Mockito.any(DespesaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    @DisplayName("Teste de salvar despesa")
    void testSaveDespesa() {
        DespesaEntity savedDespesa = despesaService.save(despesa);

        assertNotNull(savedDespesa);
        assertEquals(despesa.getDescricao(), savedDespesa.getDescricao());
        Mockito.verify(caixaRepository).save(caixa);
    }

    @Test
    @DisplayName("Teste de encontrar todas as despesas por carteira")
    void testFindAllByCarteira() {
        List<DespesaEntity> despesas = new ArrayList<>();
        despesas.add(despesa);
        Mockito.when(despesaRepository.findAllByCarteira(carteira)).thenReturn(despesas);

        List<DespesaEntity> foundDespesas = despesaService.findAllByCarteira(carteira.getId());

        assertNotNull(foundDespesas);
        assertEquals(1, foundDespesas.size());
        assertEquals(despesa.getDescricao(), foundDespesas.get(0).getDescricao());
    }

    @Test
    @DisplayName("Teste de encontrar despesa por ID")
    void testFindById() {
        Mockito.when(despesaRepository.findById(despesa.getId())).thenReturn(Optional.of(despesa));

        DespesaEntity foundDespesa = despesaService.findById(despesa.getId());

        assertNotNull(foundDespesa);
        assertEquals(despesa.getDescricao(), foundDespesa.getDescricao());
    }
}
