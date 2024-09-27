package com.mensal.services;

import com.mensal.entities.DespesaEntity;
import com.mensal.entities.ReceitaEntity;
import com.mensal.entities.TipoEntity;
import com.mensal.repositories.DespesaRepository;
import com.mensal.repositories.ReceitaRepository;
import com.mensal.repositories.TipoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TipoServiceTest {

    @Autowired
    private TipoService tipoService;

    @MockBean
    private TipoRepository tipoRepository;

    @MockBean
    private DespesaRepository despesaRepository;

    @MockBean
    private ReceitaRepository receitaRepository;

    private TipoEntity tipo;

    @BeforeEach
    void setup() {
        tipo = new TipoEntity(1L, "Alimentação", null, null);
    }

    @Test
    void testSave() {
        when(tipoRepository.save(any(TipoEntity.class))).thenReturn(tipo);

        TipoEntity savedTipo = tipoService.save(tipo);

        assertNotNull(savedTipo);
        assertEquals("Alimentação", savedTipo.getTipo());
        verify(tipoRepository, times(1)).save(tipo);
    }

    @Test
    void testFindAllTipos() {
        when(tipoRepository.findAllTipos()).thenReturn(Collections.singletonList(tipo));

        List<TipoEntity> tipos = tipoService.findAllTipos();

        assertEquals(1, tipos.size());
        assertEquals("Alimentação", tipos.get(0).getTipo());
        verify(tipoRepository, times(1)).findAllTipos();  // Verifique se o método correto foi chamado
    }




    @Test
    void testFindById() {
        when(tipoRepository.findById(anyLong())).thenReturn(Optional.of(tipo));

        TipoEntity foundTipo = tipoService.findById(1L);

        assertNotNull(foundTipo);
        assertEquals("Alimentação", foundTipo.getTipo());
        verify(tipoRepository, times(1)).findById(1L);
    }

    @Test
    void testTotalDespesaTipo() {
        // Configurando o mock para retornar o tipo correto quando findById for chamado
        when(tipoRepository.findById(anyLong())).thenReturn(Optional.of(tipo));

        // Criando despesa mockada
        DespesaEntity despesa1 = new DespesaEntity();
        despesa1.setValor(100);
        despesa1.setTipos(Collections.singletonList(tipo));

        DespesaEntity despesa2 = new DespesaEntity();
        despesa2.setValor(200);
        despesa2.setTipos(Collections.singletonList(tipo));

        when(despesaRepository.findAll()).thenReturn(Arrays.asList(despesa1, despesa2));

        float total = tipoService.totalDespesaTipo(1L);

        assertEquals(300, total);
        verify(despesaRepository, times(1)).findAll();
    }

    @Test
    void testTotalReceitaTipo() {
        // Configurando o mock para retornar o tipo correto quando findById for chamado
        when(tipoRepository.findById(anyLong())).thenReturn(Optional.of(tipo));

        // Criando receita mockada
        ReceitaEntity receita1 = new ReceitaEntity();
        receita1.setValor(150);
        receita1.setTipos(Collections.singletonList(tipo));

        ReceitaEntity receita2 = new ReceitaEntity();
        receita2.setValor(350);
        receita2.setTipos(Collections.singletonList(tipo));

        when(receitaRepository.findAll()).thenReturn(Arrays.asList(receita1, receita2));

        float total = tipoService.totalReceitaTipo(1L);

        assertEquals(500, total);
        verify(receitaRepository, times(1)).findAll();
    }

//    @Test
//    void testTotalTipo() {
//        // Mockando o retorno do findById para o ID que será utilizado no teste
//        when(tipoRepository.findById(1L)).thenReturn(Optional.of(tipo));
//
//        // Criando o TipoEntity
//        TipoEntity tipo = new TipoEntity();
//        tipo.setId(1L);
//        tipo.setTipo("Alimentação");
//
//        // Criando a despesa com o Tipo
//        DespesaEntity despesa = new DespesaEntity();
//        despesa.setId(1L);
//        despesa.setValor(200);
//        despesa.setTipos(List.of(tipo));
//
//        // Criando a receita com o Tipo
//        ReceitaEntity receita = new ReceitaEntity();
//        receita.setId(1L);
//        receita.setValor(300);
//        receita.setTipos(List.of(tipo));
//
//        // Mockando o retorno das despesas e receitas
//        when(despesaRepository.findAll()).thenReturn(List.of(despesa));
//        when(receitaRepository.findAll()).thenReturn(List.of(receita));
//
//        // Executando o método a ser testado
//        String total = tipoService.totalTipo(1L);
//
//        // Debugging: Veja o que está retornando
//        System.out.println(total); // Adicione esta linha para depuração
//
//        // Verificando os totais
//        String expectedDespesa = "Total despesa: R$ 200.0";
//        String expectedReceita = "Total receita: R$ 300.0";
//
//        assertTrue(total.contains(expectedDespesa));
//        assertTrue(total.contains(expectedReceita));
//    }

    @Test
    void testFindByNome() {
        when(tipoRepository.findByNome("Alimentação")).thenReturn(Collections.singletonList(tipo));

        List<TipoEntity> tipos = tipoService.findByNome("Alimentação");

        assertEquals(1, tipos.size());
        assertEquals("Alimentação", tipos.get(0).getTipo());
        verify(tipoRepository, times(1)).findByNome("Alimentação");
    }
}
