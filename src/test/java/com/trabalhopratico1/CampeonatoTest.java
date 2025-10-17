package com.trabalhopratico1;

import com.trabalhopratico1.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CampeonatoTest {

    private Campeonato campeonato;

    @BeforeEach
    void setUp() {
        campeonato = new Campeonato();
    }

    @Test
    void testSortearRodadasComListaNula() {
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            campeonato.sortearRodadas(null);
        });
        assertEquals("A lista deve conter exatamente 20 elementos.", exception.getMessage());
    }

    @Test
    void testSortearRodadasComListaIncompleta() {
        List<String> nomesTimes = Arrays.asList("Time1", "Time2", "Time3");
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            campeonato.sortearRodadas(nomesTimes);
        });
        assertEquals("A lista deve conter exatamente 20 elementos.", exception.getMessage());
    }

    @Test
    void testSortearRodadasComNomeDuplicado() {
        List<String> nomesTimes = gerar20Times();
        nomesTimes.set(0, nomesTimes.get(1));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            campeonato.sortearRodadas(nomesTimes);
        });
        assertEquals("A lista contém elementos duplicados: " + nomesTimes.get(1), exception.getMessage());
    }

    @Test
    void testSortearRodadasComNomeVazio() {
        List<String> nomesTimes = gerar20Times();
        nomesTimes.set(5, "  ");

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            campeonato.sortearRodadas(nomesTimes);
        });
        assertEquals("A lista contem elementos nulos ou vazios.", exception.getMessage());
    }

    @Test
    public void testSortearRodadas() {
        List<String> nomesTimes = gerar20Times();
        List<Time> times;
        List<Rodada> rodadas;
        try {
            rodadas = campeonato.sortearRodadas(nomesTimes);
            times = campeonato.getTimes(nomesTimes);
        } catch (BusinessException e) {
            assertTrue(false, "Erro no sorteio de rodadas");
            return;
        }

        assertEquals(38, rodadas.size(), "Deve haver 38 rodadas");

        Map<Time, Integer> mandanteCount = new HashMap<>();
        Map<Time, Integer> visitanteCount = new HashMap<>();
        times.forEach(time -> {
            mandanteCount.put(time, 0);
            visitanteCount.put(time, 0);
        });

        for (Rodada rodada : rodadas) {
            List<Jogo> jogos = rodada.getJogos();

            assertEquals(10, jogos.size(), "Cada rodada deve ter 10 jogos");

            for (Jogo jogo : jogos) {
                mandanteCount.put(jogo.getMandante(), mandanteCount.get(jogo.getMandante()) + 1);
                visitanteCount.put(jogo.getVisitante(), visitanteCount.get(jogo.getVisitante()) + 1);
            }
        }

        for (Time time : times) {
            assertEquals(19, mandanteCount.get(time), time + " deve ter jogado 19 vezes como mandante");
            assertEquals(19, visitanteCount.get(time), time + " deve ter jogado 19 vezes como visitante");
        }
    }

    public static List<String> gerar20Times() {
        ArrayList<String> times = new ArrayList<>();

        times.add("Flamengo");
        times.add("Palmeiras");
        times.add("Santos");
        times.add("Sao Paulo");
        times.add("Corinthians");
        times.add("Gremio");
        times.add("Internacional");
        times.add("Vasco da Gama");
        times.add("Fluminense");
        times.add("Botafogo");
        times.add("Atletico Mineiro");
        times.add("Cruzeiro");
        times.add("Bahia");
        times.add("Sport Recife");
        times.add("Ceara");
        times.add("Fortaleza");
        times.add("Athletico Paranaense");
        times.add("Goias");
        times.add("Coritiba");
        times.add("America Mineiro");

        return times;
    }
}
