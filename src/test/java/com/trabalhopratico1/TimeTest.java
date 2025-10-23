package com.trabalhopratico1;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("funcional")
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
    void desempateUsaMenosAmarelosComoQuintoCriterio() {
        Time t1 = new Time("Time A");
        t1.setVitorias(4);
        t1.setGolsMarcados(10);
        t1.setGolsSofridos(6); 
        t1.setCartoesVermelhos(2);
        t1.setCartoesAmarelos(1);

        Time t2 = new Time("Time B");
        t2.setVitorias(4);
        t2.setGolsMarcados(10);
        t2.setGolsSofridos(6); 
        t2.setCartoesVermelhos(2);
        t2.setCartoesAmarelos(2);

        assertEquals(t1, Time.desempate(t1, t2));
        assertEquals(t1, Time.desempate(t2, t1));
    }

    @Test
    void desempateFazSorteioQuandoTodosOsCriteriosIguais() {
        Time t1 = criarTimeComTodasEstatisticas("Time A", 4, 10, 6, 2, 3);
        Time t2 = criarTimeComTodasEstatisticas("Time B", 4, 10, 6, 2, 3);

        Time vencedorQuandoPrimeiroSorteado = Time.desempate(t1, t2, new RandomStub(true));
        Time vencedorQuandoSegundoSorteado = Time.desempate(t1, t2, new RandomStub(false));

        assertEquals(t1, vencedorQuandoPrimeiroSorteado, "Quando sorteio retorna true, deve retornar primeiro time");
        assertEquals(t2, vencedorQuandoSegundoSorteado, "Quando sorteio retorna false, deve retornar segundo time");
    }

    private Time criarTimeComTodasEstatisticas(String nome, int vitorias, int golsMarcados, 
                                                int golsSofridos, int vermelho, int amarelo) {
        Time time = new Time(nome);
        time.setVitorias(vitorias);
        time.setGolsMarcados(golsMarcados);
        time.setGolsSofridos(golsSofridos);
        time.setCartoesVermelhos(vermelho);
        time.setCartoesAmarelos(amarelo);
        return time;
    }

    static class RandomStub extends Random {
        private final boolean valorRetornado;
        
        RandomStub(boolean valorRetornado) { 
            this.valorRetornado = valorRetornado; 
        }
        
        @Override 
        public boolean nextBoolean() { 
            return valorRetornado; 
        }
    }

    @Test
    void confrontoDiretoRetornaTimeComMelhorSaldoNoConfronto() {
        Time t1 = criarTimeComTodasEstatisticas("Time A", 0, 5, 2, 0, 0);
        Time t2 = criarTimeComTodasEstatisticas("Time B", 0, 2, 5, 0, 0);

        assertConfrontoDireto(t1, t2);
    }

    private void assertConfrontoDireto(Time esperado, Time outro) {
        assertEquals(esperado, Time.confrontoDireto(esperado, outro));
        assertEquals(esperado, Time.confrontoDireto(outro, esperado));
    }
}