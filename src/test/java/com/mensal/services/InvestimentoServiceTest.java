package com.mensal.services;

import com.mensal.entities.CaixaEntity;
import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.InvestimentoEntity;
import com.mensal.entities.MetaEntity;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.InvestimentoRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class InvestimentoServiceTest {

    @Autowired
    private InvestimentoService investimentoService;

    @MockBean
    private InvestimentoRepository investimentoRepository;

    @MockBean
    private CaixaRepository caixaRepository;

    @MockBean
    private MetaRepository metaRepository;

    @MockBean
    private CarteiraRepository carteiraRepository;

    private InvestimentoEntity investimento;
    private CarteiraEntity carteira;

    @BeforeEach
    void setup() {
        // Inicializa a carteira de teste
        carteira = new CarteiraEntity(1L, "Carteira de Teste", null, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        // Inicializa o caixa de teste
        CaixaEntity caixa = new CaixaEntity(1L, "Caixa de Teste", 1000f, null);

        // Adiciona o caixa à carteira
        carteira.setCaixa(caixa);

        // Inicializa a meta de teste
        MetaEntity meta = new MetaEntity();
        meta.setId(1L);
        meta.setNome("Meta Teste");
        meta.setDescricao("Descrição da Meta");
        meta.setValor(1000f);
        meta.setData_inicial(new Date());
        meta.setData_final(new Date());
        meta.setCompleto(0f);
        meta.setCarteira(carteira);

        // Inicializa o investimento de teste
        investimento = new InvestimentoEntity();
        investimento.setId(1L);
        investimento.setData(new Date());
        investimento.setDescricao("Investimento Teste");
        investimento.setValor(100f);
        investimento.setCarteira(carteira);
        investimento.setMeta(meta);

        Mockito.when(carteiraRepository.findById(carteira.getId())).thenReturn(Optional.of(carteira));
        Mockito.when(investimentoRepository.save(Mockito.any(InvestimentoEntity.class))).thenReturn(investimento);
        Mockito.when(metaRepository.findById(meta.getId())).thenReturn(Optional.of(meta));
        Mockito.when(caixaRepository.save(Mockito.any(CaixaEntity.class))).thenReturn(caixa);
    }

    @Test
    @DisplayName("Teste de salvar investimento")
    void testSaveInvestimento() {
        InvestimentoEntity savedInvestimento = investimentoService.save(investimento);

        assertNotNull(savedInvestimento);
        assertEquals("Investimento Teste", savedInvestimento.getDescricao());
        assertEquals(100f, savedInvestimento.getValor());
        assertEquals(carteira.getId(), savedInvestimento.getCarteira().getId());
    }

    @Test
    @DisplayName("Teste de encontrar investimento por ID")
    void testFindById() {
        Mockito.when(investimentoRepository.findById(investimento.getId())).thenReturn(Optional.of(investimento));

        InvestimentoEntity foundInvestimento = investimentoService.findById(investimento.getId());

        assertNotNull(foundInvestimento);
        assertEquals(investimento.getId(), foundInvestimento.getId());
        assertEquals("Investimento Teste", foundInvestimento.getDescricao());
    }

    @Test
    @DisplayName("Teste de editar investimento")
    void testEditInvestimento() {
        investimento.setDescricao("Investimento Atualizado");
        Mockito.when(investimentoRepository.findById(investimento.getId())).thenReturn(Optional.of(investimento));

        InvestimentoEntity editedInvestimento = investimentoService.edit(investimento);

        assertNotNull(editedInvestimento);
        assertEquals("Investimento Atualizado", editedInvestimento.getDescricao());
        Mockito.verify(investimentoRepository, Mockito.times(1)).save(investimento);
    }

    @Test
    @DisplayName("Teste de encontrar todos os investimentos por carteira")
    void testFindAllByCarteira() {
        List<InvestimentoEntity> investimentosList = new ArrayList<>();
        investimentosList.add(investimento);

        Mockito.when(investimentoRepository.findAllByCarteira(carteira)).thenReturn(investimentosList);

        List<InvestimentoEntity> foundInvestimentos = investimentoService.findAllByCarteira(carteira);

        assertNotNull(foundInvestimentos);
        assertEquals(1, foundInvestimentos.size());
        assertEquals("Investimento Teste", foundInvestimentos.get(0).getDescricao());
    }
}
