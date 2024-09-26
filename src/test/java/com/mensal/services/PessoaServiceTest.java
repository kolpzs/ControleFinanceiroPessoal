package com.mensal.services;

import com.mensal.entities.PessoaEntity;
import com.mensal.repositories.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    @MockBean
    private PessoaRepository pessoaRepository;

    @BeforeEach
    void setUp() {
        Mockito.reset(pessoaRepository);
    }

    @Test
    @DisplayName("teste save")
    public void testSave() {
        PessoaEntity pessoaValida = new PessoaEntity();
        pessoaValida.setNome("Nome Válido");

        PessoaEntity pessoaInvalida = new PessoaEntity();
        pessoaInvalida.setNome(null);

        Mockito.when(pessoaRepository.save(pessoaValida)).thenReturn(pessoaValida);

        PessoaEntity resultValido = pessoaService.save(pessoaValida);
        assertNotNull(resultValido);
        assertEquals("Nome Válido", resultValido.getNome());

        Mockito.verify(pessoaRepository, Mockito.times(1)).save(pessoaValida);
        Mockito.verify(pessoaRepository, Mockito.times(0)).save(pessoaInvalida);
    }

    @Test
    @DisplayName("teste findAll")
    public void testFindAll() {
        List<PessoaEntity> pessoas = Arrays.asList(new PessoaEntity(), new PessoaEntity());
        Mockito.when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<PessoaEntity> result = pessoaService.findAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());

        Mockito.when(pessoaRepository.findAll()).thenReturn(Arrays.asList());
        result = pessoaService.findAll();
        assertTrue(result.isEmpty());

        Mockito.verify(pessoaRepository, Mockito.times(2)).findAll();
    }

    @Test
    @DisplayName("teste findById")
    public void testFindById() {
        PessoaEntity pessoa = new PessoaEntity();
        pessoa.setId(1L);
        Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        PessoaEntity result = pessoaService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());

        Mockito.when(pessoaRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> pessoaService.findById(2L));

        Mockito.verify(pessoaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(pessoaRepository, Mockito.times(1)).findById(2L);
    }

    @Test
    @DisplayName("teste edit")
    public void testEdit() {
        PessoaEntity pessoa = new PessoaEntity();
        pessoa.setId(1L);
        pessoa.setNome("Nome Original");

        PessoaEntity pessoaEditada = new PessoaEntity();
        pessoaEditada.setId(1L);
        pessoaEditada.setNome("Nome Editado");

        Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        Mockito.when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        PessoaEntity result = pessoaService.edit(pessoaEditada);
        assertNotNull(result);
        assertEquals("Nome Editado", result.getNome());

        PessoaEntity pessoaInvalida = new PessoaEntity();
        pessoaInvalida.setId(2L);
        Mockito.when(pessoaRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> pessoaService.edit(pessoaInvalida));

        Mockito.verify(pessoaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(pessoaRepository, Mockito.times(1)).findById(2L);
        Mockito.verify(pessoaRepository, Mockito.times(1)).save(pessoa);
    }
}
