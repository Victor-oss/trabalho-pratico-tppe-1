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
}