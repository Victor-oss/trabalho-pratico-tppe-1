package com.trabalhopratico1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeTest {

    @Test
    void calculaPontosCasosBasicos() {
        assertPontos(0, 0, 0);
        assertPontos(1, 0, 3);
        assertPontos(0, 1, 1);
        assertPontos(2, 1, 7);
        assertPontos(5, 4, 19);
    }

    private void assertPontos(int vitorias, int empates, int esperado) {
        Time t = new Time("Time de Teste");
        t.setVitorias(vitorias);
        t.setEmpates(empates);
        assertEquals(esperado, t.calcularPontos());
    }

    @Test
    void desempateRetornaTimeComMaisVitorias() {
        Time t1 = new Time("Time A");
        t1.setVitorias(5);

        Time t2 = new Time("Time B");
        t2.setVitorias(3);

        assertEquals(t1, Time.desempate(t1, t2));
        assertEquals(t1, Time.desempate(t2, t1)); 
    }

    @Test
    void desempateUsaSaldoDeGolsQuandoVitoriasIguais() {
        Time t1 = new Time("Time A");
        t1.setVitorias(4);
        t1.setGolsMarcados(10);
        t1.setGolsSofridos(5); 

        Time t2 = new Time("Time B");
        t2.setVitorias(4);
        t2.setGolsMarcados(9);
        t2.setGolsSofridos(7); 

        assertEquals(t1, Time.desempate(t1, t2));
        assertEquals(t1, Time.desempate(t2, t1));
    }

    @Test
    void desempateRetornaPrimeiroSeVitoriasESaldoIguais() {
        Time t1 = new Time("Time A");
        t1.setVitorias(4);
        t1.setGolsMarcados(10);
        t1.setGolsSofridos(6); 

        Time t2 = new Time("Time B");
        t2.setVitorias(4);
        t2.setGolsMarcados(8);
        t2.setGolsSofridos(4); 

        assertEquals(t1, Time.desempate(t1, t2));
    }

    @Test
    void desempateUsaGolsMarcadosComoTerceiroCriterio() {
        Time t1 = new Time("Time A");
        t1.setVitorias(4);
        t1.setGolsMarcados(10);
        t1.setGolsSofridos(6); 

        Time t2 = new Time("Time B");
        t2.setVitorias(4);
        t2.setGolsMarcados(11);
        t2.setGolsSofridos(7);

        assertEquals(t2, Time.desempate(t1, t2));
        assertEquals(t2, Time.desempate(t2, t1));
    }

    @Test
    void desempateRetornaPrimeiroSeTodosCriteriosIguais() {
        Time t1 = new Time("Time A");
        t1.setVitorias(4);
        t1.setGolsMarcados(10);
        t1.setGolsSofridos(6); 

        Time t2 = new Time("Time B");
        t2.setVitorias(4);
        t2.setGolsMarcados(10);
        t2.setGolsSofridos(6); 

        assertEquals(t1, Time.desempate(t1, t2));
    }

    @Test
    void desempateUsaMenosVermelhosComoQuartoCriterio() {
        Time t1 = new Time("Time A");
        t1.setVitorias(4);
        t1.setGolsMarcados(10);
        t1.setGolsSofridos(6); 
        t1.setCartoesVermelhos(1);

        Time t2 = new Time("Time B");
        t2.setVitorias(4);
        t2.setGolsMarcados(10);
        t2.setGolsSofridos(6); 
        t2.setCartoesVermelhos(2);

        assertEquals(t1, Time.desempate(t1, t2));
        assertEquals(t1, Time.desempate(t2, t1));
    }

    @Test
    void desempateRetornaPrimeiroSeTodosQuatroCriteriosIguais() {
        Time t1 = new Time("Time A");
        t1.setVitorias(4);
        t1.setGolsMarcados(10);
        t1.setGolsSofridos(6);
        t1.setCartoesVermelhos(2);

        Time t2 = new Time("Time B");
        t2.setVitorias(4);
        t2.setGolsMarcados(10);
        t2.setGolsSofridos(6);
        t2.setCartoesVermelhos(2);

        assertEquals(t1, Time.desempate(t1, t2));
    }

    @Test
    void desempateUsaMenosAmarelosComoQuartoCriterio() {
        Time t1 = new Time("Time A");
        t1.setVitorias(4);
        t1.setGolsMarcados(10);
        t1.setGolsSofridos(6); 
        t1.setCartoesAmarelos(1);

        Time t2 = new Time("Time B");
        t2.setVitorias(4);
        t2.setGolsMarcados(10);
        t2.setGolsSofridos(6); 
        t2.setCartoesAmarelos(2);

        assertEquals(t1, Time.desempate(t1, t2));
        assertEquals(t1, Time.desempate(t2, t1));
    }
}