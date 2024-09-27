package com.mensal.services;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.MetaEntity;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.MetaRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MetaServiceTest {

    @Autowired
    private MetaService metaService;

    @MockBean
    private MetaRepository metaRepository;

    @MockBean
    private CarteiraRepository carteiraRepository;

    private MetaEntity meta;
    private CarteiraEntity carteira;

    @BeforeEach
    void setup() {
        // Inicializa uma carteira de teste
        carteira = new CarteiraEntity(1L, "Carteira de Teste", null, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        // Inicializa uma meta de teste e seta cada valor individualmente
        meta = new MetaEntity();
        meta.setId(1L);
        meta.setNome("Meta Teste");
        meta.setDescricao("Descrição da Meta");
        meta.setValor(1000f);
        meta.setData_inicial(new Date());
        meta.setData_final(new Date());
        meta.setCompleto(0f);
        meta.setCarteira(carteira);
    }

    @Test
    @DisplayName("Teste de salvar meta")
    void testSaveMeta() {
        // Mock da carteira que será associada à meta
        Mockito.when(carteiraRepository.findById(carteira.getId())).thenReturn(Optional.of(carteira));
        Mockito.when(metaRepository.save(meta)).thenReturn(meta);

        MetaEntity savedMeta = metaService.save(meta);

        assertNotNull(savedMeta);
        assertEquals(meta.getNome(), savedMeta.getNome());
        assertEquals(meta.getDescricao(), savedMeta.getDescricao());
        assertEquals(meta.getValor(), savedMeta.getValor());
    }

    @Test
    @DisplayName("Teste de encontrar todas as metas por carteira")
    void testFindAllByCarteira() {
        List<MetaEntity> metas = new ArrayList<>();
        metas.add(meta);

        Mockito.when(metaRepository.findAllByCarteira(carteira)).thenReturn(metas);

        List<MetaEntity> foundMetas = metaService.findAllByCarteira(carteira);

        assertNotNull(foundMetas);
        assertEquals(1, foundMetas.size());
        assertEquals(meta.getNome(), foundMetas.get(0).getNome());
    }

    @Test
    @DisplayName("Teste de encontrar meta por ID")
    void testFindById() {
        Mockito.when(metaRepository.findById(meta.getId())).thenReturn(Optional.of(meta));

        MetaEntity foundMeta = metaService.findById(meta.getId());

        assertNotNull(foundMeta);
        assertEquals(meta.getNome(), foundMeta.getNome());
    }

    @Test
    @DisplayName("Teste de editar meta")
    void testEditMeta() {
        MetaEntity updatedMeta = new MetaEntity();
        updatedMeta.setId(1L);
        updatedMeta.setNome("Meta Atualizada");
        updatedMeta.setDescricao("Descrição Atualizada");
        updatedMeta.setValor(1500f);
        updatedMeta.setData_inicial(new Date());
        updatedMeta.setData_final(new Date());
        updatedMeta.setCompleto(0f);
        updatedMeta.setCarteira(carteira);

        Mockito.when(metaRepository.findById(meta.getId())).thenReturn(Optional.of(meta));
        Mockito.when(metaRepository.save(meta)).thenReturn(updatedMeta);

        MetaEntity editedMeta = metaService.edit(updatedMeta);

        assertNotNull(editedMeta);
        assertEquals(updatedMeta.getNome(), editedMeta.getNome());
        assertEquals(updatedMeta.getDescricao(), editedMeta.getDescricao());
        assertEquals(updatedMeta.getValor(), editedMeta.getValor());
    }

    @Test
    @DisplayName("Teste de deletar meta")
    void testDeleteMeta() {
        Mockito.when(metaRepository.findById(meta.getId())).thenReturn(Optional.of(meta));

        metaService.delete(meta.getId());

        Mockito.verify(metaRepository, Mockito.times(1)).delete(meta);
    }
}
