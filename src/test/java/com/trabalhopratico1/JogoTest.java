package com.trabalhopratico1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("funcional")
class JogoTest {

    private Time mandante;
    private Time visitante;
    private Jogo jogo;

    @BeforeEach
    void setUp() {
        mandante = new Time("Time Mandante");
        visitante = new Time("Time Visitante");
        jogo = new Jogo(mandante, visitante);
    }

    @Test
    @DisplayName("Teste deve atualizar estatísticas corretamente quando o mandante vence")
    void testDeveAtualizarQuandoMandanteVence() {
        jogo.setGolsMandante(3);
        jogo.setGolsVisitante(1);
        jogo.finalizarJogo();

        assertTrue(jogo.isRealizado());

        assertEquals(3, mandante.getGolsMarcados());
        assertEquals(1, visitante.getGolsMarcados());
        assertEquals(1, mandante.getGolsSofridos());
        assertEquals(3, visitante.getGolsSofridos());

        assertEquals(1, mandante.getVitorias());
        assertEquals(0, visitante.getVitorias());
        assertEquals(0, mandante.getEmpates());
        assertEquals(0, visitante.getEmpates());
    }

    @Test
    @DisplayName("Teste deve atualizar estatísticas corretamente quando o visitante vence")
    void testDeveAtualizarQuandoVisitanteVence() {
        jogo.setGolsMandante(0);
        jogo.setGolsVisitante(2);
        jogo.finalizarJogo();

        assertTrue(jogo.isRealizado());

        assertEquals(0, mandante.getGolsMarcados());
        assertEquals(2, visitante.getGolsMarcados());
        assertEquals(2, mandante.getGolsSofridos());
        assertEquals(0, visitante.getGolsSofridos());

        assertEquals(0, mandante.getVitorias());
        assertEquals(1, visitante.getVitorias());
        assertEquals(0, mandante.getEmpates());
        assertEquals(0, visitante.getEmpates());
    }

    @Test
    @DisplayName("Teste deve registrar empate corretamente")
    void testDeveRegistrarEmpateCorretamente() {
        jogo.setGolsMandante(2);
        jogo.setGolsVisitante(2);
        jogo.finalizarJogo();

        assertTrue(jogo.isRealizado());

        assertEquals(2, mandante.getGolsMarcados());
        assertEquals(2, visitante.getGolsMarcados());
        assertEquals(2, mandante.getGolsSofridos());
        assertEquals(2, visitante.getGolsSofridos());

        assertEquals(1, mandante.getEmpates());
        assertEquals(1, visitante.getEmpates());
        assertEquals(0, mandante.getVitorias());
        assertEquals(0, visitante.getVitorias());
    }

    @Test
    @DisplayName("Teste não deve duplicar atualização se finalizarJogo for chamado mais de uma vez")
    void testNaoDeveDuplicarAtualizacaoSeFinalizadoNovamente() {
        jogo.setGolsMandante(1);
        jogo.setGolsVisitante(0);

        jogo.finalizarJogo();
        jogo.finalizarJogo();

        assertEquals(1, mandante.getVitorias());
        assertEquals(1, mandante.getGolsMarcados());
        assertEquals(0, visitante.getEmpates());
    }

}
