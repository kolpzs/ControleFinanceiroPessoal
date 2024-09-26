package com.mensal.services;

import com.mensal.entities.CaixaEntity;
import com.mensal.repositories.CaixaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CaixaServiceTest {

    @Autowired
    private CaixaService caixaService;

    @MockBean
    private CaixaRepository caixaRepository;

    @BeforeEach
    void setup() {
        CaixaEntity caixaExistente = new CaixaEntity(1L, "Conta 1", 100f, null);

        Mockito.when(caixaRepository.findById(1L)).thenReturn(Optional.of(caixaExistente));
        Mockito.when(caixaRepository.save(Mockito.any(CaixaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    @DisplayName("Teste de salvar caixa")
    void testSaveCaixa() {
        CaixaEntity novaCaixa = new CaixaEntity(null, "Nova Conta", 50f, null);
        Mockito.when(caixaRepository.save(novaCaixa)).thenReturn(new CaixaEntity(2L, "Nova Conta", 50f, null));

        CaixaEntity savedCaixa = caixaService.save(novaCaixa);

        assertNotNull(savedCaixa.getId());
        assertEquals("Nova Conta", savedCaixa.getConta());
        assertEquals(50f, savedCaixa.getValor());
    }

    @Test
    @DisplayName("Teste de encontrar caixa por ID")
    void testFindById() {
        CaixaEntity caixaEncontrada = caixaService.findById(1L);

        assertNotNull(caixaEncontrada);
        assertEquals(1L, caixaEncontrada.getId());
        assertEquals("Conta 1", caixaEncontrada.getConta());
        assertEquals(100f, caixaEncontrada.getValor());
    }

    @Test
    @DisplayName("Teste de atualizar o valor do caixa")
    void testUpdateValor() {
        float novoValor = 200f;
        CaixaEntity caixaAtualizada = caixaService.updateValor(1L, novoValor);

        assertNotNull(caixaAtualizada);
        assertEquals(novoValor, caixaAtualizada.getValor());
        Mockito.verify(caixaRepository, Mockito.times(1)).save(caixaAtualizada);
    }
}
