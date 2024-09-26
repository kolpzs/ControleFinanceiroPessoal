package com.mensal.services;

import com.mensal.entities.CaixaEntity;
import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.DespesaEntity;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.DespesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DespesaServiceTest {

    @Autowired
    private DespesaService despesaService;

    @Mock
    private DespesaRepository despesaRepository;

    @Mock
    private CaixaRepository caixaRepository;

    @Mock
    private CarteiraRepository carteiraRepository;

    private CarteiraEntity carteira;
    private CaixaEntity caixa;
    private DespesaEntity despesa;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        caixa = new CaixaEntity(1L, "Caixa de Teste", 1000f, null);
        carteira = new CarteiraEntity(1L, "Carteira de Teste", null, caixa, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        despesa = new DespesaEntity(1L, new Date(), "Despesa de Teste", 100f, carteira, null);
    }

    @Test
    public void testSaveDespesa() {
        when(carteiraRepository.findById(carteira.getId())).thenReturn(Optional.of(carteira));
        when(despesaRepository.save(despesa)).thenReturn(despesa);

        DespesaEntity savedDespesa = despesaService.save(despesa);

        assertNotNull(savedDespesa);
        assertEquals(despesa.getDescricao(), savedDespesa.getDescricao());
        verify(caixaRepository).save(caixa);
    }

    @Test
    public void testFindAllByCarteira() {
        List<DespesaEntity> despesas = new ArrayList<>();
        despesas.add(despesa);
        when(carteiraRepository.findById(carteira.getId())).thenReturn(Optional.of(carteira));
        when(despesaRepository.findAllByCarteira(carteira)).thenReturn(despesas);

        List<DespesaEntity> foundDespesas = despesaService.findAllByCarteira(carteira.getId());

        assertNotNull(foundDespesas);
        assertEquals(1, foundDespesas.size());
        assertEquals(despesa.getDescricao(), foundDespesas.get(0).getDescricao());
    }

    @Test
    public void testFindById() {
        when(despesaRepository.findById(despesa.getId())).thenReturn(Optional.of(despesa));

        DespesaEntity foundDespesa = despesaService.findById(despesa.getId());

        assertNotNull(foundDespesa);
        assertEquals(despesa.getDescricao(), foundDespesa.getDescricao());
    }
}
