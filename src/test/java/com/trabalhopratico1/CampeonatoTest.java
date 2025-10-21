package com.trabalhopratico1;

import com.trabalhopratico1.exception.BusinessError;
import com.trabalhopratico1.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CampeonatoTest {

    @Test
	@DisplayName("Teste criar campeonato com lista nula")
    void testSortearRodadasComListaNula() {
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            new Campeonato(null);
        });
        assertEquals(BusinessError.NULL_LIST, exception.getMessage());
    }

    @Test
	@DisplayName("Teste criar campeonato com lista diferente de 20 elementos")
    void testCampeonatoSem20Times() {
        List<Time> nomesTimes = Arrays.asList(
			new Time("Time1"),
			new Time("Time2"),
			new Time("Time3")
		);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
			new Campeonato(nomesTimes);
        });
        assertEquals(BusinessError.LIST_SIZE_INVALID, exception.getMessage());

		BusinessException exception2 = assertThrows(BusinessException.class, () -> {
			List<Time> timesCom21 = gerar20Times();
			timesCom21.add(new Time("Time21"));
			new Campeonato(timesCom21);
		});
		assertEquals(BusinessError.LIST_SIZE_INVALID, exception2.getMessage());
    }

    @Test
    void testSortearRodadasComNomeDuplicado() {
        List<Time> nomesTimes = gerar20Times();
        nomesTimes.set(0, nomesTimes.get(1));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            new Campeonato(nomesTimes);
        });
        assertEquals(BusinessError.DUPLICATE_ELEMENTS, exception.getMessage());
    }

    @Test
	@DisplayName("Teste sortear rodadas corretamente")
    public void testSortearRodadas() {

		Campeonato campeonato;
        try {
			campeonato = new Campeonato(gerar20Times());
        } catch (BusinessException e) {
            assertTrue(false, "Erro no sorteio de rodadas");
            return;
        }

        List<Time> times = campeonato.getTimes();
        List<Rodada> rodadas = campeonato.getRodadas();

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

    @Test
    @DisplayName("Tabela inicial deve conter 20 times com estatísticas zeradas")
    void testTabelaClassificacaoInicialVazia() throws BusinessException {
        Campeonato campeonato = new Campeonato(gerar20Times());

        List<Time> tabelaClassificacao = campeonato.getTabelaClassificacao();

        assertEquals(20, tabelaClassificacao.size(), "A tabela de classificação deve conter 20 times.");

        for (Time time : tabelaClassificacao) {
            assertEquals(0, time.calcularPontos(), "Pontos iniciais devem ser 0.");
            assertEquals(0, time.getVitorias(), "Vitórias iniciais devem ser 0.");
            assertEquals(0, time.getEmpates(), "Empates iniciais devem ser 0.");
            assertEquals(0, time.getGolsMarcados(), "Gols marcados devem ser 0.");
            assertEquals(0, time.getGolsSofridos(), "Gols sofridos devem ser 0.");
            assertEquals(0, time.getCartoesAmarelos(), "Cartões amarelos devem ser 0.");
            assertEquals(0, time.getCartoesVermelhos(), "Cartões vermelhos devem ser 0.");
        }
    }

    public static List<Time> gerar20Times() {
        ArrayList<String> nomesTimes = new ArrayList<>();

        nomesTimes.add("Flamengo");
        nomesTimes.add("Palmeiras");
        nomesTimes.add("Santos");
        nomesTimes.add("Sao Paulo");
        nomesTimes.add("Corinthians");
        nomesTimes.add("Gremio");
        nomesTimes.add("Internacional");
        nomesTimes.add("Vasco da Gama");
        nomesTimes.add("Fluminense");
        nomesTimes.add("Botafogo");
        nomesTimes.add("Atletico Mineiro");
        nomesTimes.add("Cruzeiro");
        nomesTimes.add("Bahia");
        nomesTimes.add("Sport Recife");
        nomesTimes.add("Ceara");
        nomesTimes.add("Fortaleza");
        nomesTimes.add("Athletico Paranaense");
        nomesTimes.add("Goias");
        nomesTimes.add("Coritiba");
        nomesTimes.add("America Mineiro");

		List<Time> times = new ArrayList<>();

		for (String nome : nomesTimes) {
			times.add(new Time(nome));
		}

        return times;
    }
}
