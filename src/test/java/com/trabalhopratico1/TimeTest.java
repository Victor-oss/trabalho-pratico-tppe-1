package com.trabalhopratico1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeTest {

    @Test
    void deveCalcularPontosComo3PorVitoriaMais1PorEmpate() {
        Time t = new Time("Time de Teste");
        t.setVitorias(2);
        t.setEmpates(1);

        assertEquals(7, t.getPontos()); 
    }
}