package com.mensal.services;

import com.mensal.entities.CaixaEntity;
import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.ReceitaEntity;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.ReceitaRepository;
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
class ReceitaServiceTest {

    @Autowired
    private ReceitaService receitaService;

    @MockBean
    private ReceitaRepository receitaRepository;

    @MockBean
    private CaixaRepository caixaRepository;

    @MockBean
    private CarteiraRepository carteiraRepository;

    private CarteiraEntity carteira;
    private CaixaEntity caixa;
    private ReceitaEntity receita;

    @BeforeEach
    void setup() {
        caixa = new CaixaEntity(1L, "Caixa de Teste", 1000f, null);
        carteira = new CarteiraEntity(1L, "Carteira de Teste", null, caixa, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        receita = new ReceitaEntity(1L, new Date(), "Receita de Teste", 100f, carteira, null);
        
        Mockito.when(carteiraRepository.findById(carteira.getId())).thenReturn(Optional.of(carteira));
        Mockito.when(receitaRepository.save(Mockito.any(ReceitaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    @DisplayName("Teste de salvar receita")
    void testSaveReceita() {
        ReceitaEntity savedReceita = receitaService.save(receita);

        assertNotNull(savedReceita);
        assertEquals(receita.getDescricao(), savedReceita.getDescricao());
        Mockito.verify(caixaRepository).save(caixa);
    }

    @Test
    @DisplayName("Teste de encontrar todas as receitas por carteira")
    void testFindAllByCarteira() {
        List<ReceitaEntity> receitas = new ArrayList<>();
        receitas.add(receita);
        Mockito.when(receitaRepository.findAllByCarteira(carteira)).thenReturn(receitas);

        List<ReceitaEntity> foundReceitas = receitaService.findAllByCarteira(carteira.getId());

        assertNotNull(foundReceitas);
        assertEquals(1, foundReceitas.size());
        assertEquals(receita.getDescricao(), foundReceitas.get(0).getDescricao());
    }

    @Test
    @DisplayName("Teste de encontrar receita por ID")
    void testFindById() {
        Mockito.when(receitaRepository.findById(receita.getId())).thenReturn(Optional.of(receita));

        ReceitaEntity foundReceita = receitaService.findById(receita.getId());

        assertNotNull(foundReceita);
        assertEquals(receita.getDescricao(), foundReceita.getDescricao());
    }
}
