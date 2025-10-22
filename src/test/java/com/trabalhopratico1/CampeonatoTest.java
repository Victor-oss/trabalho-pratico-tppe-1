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

    @Test
    @DisplayName("Deve ordenar a tabela por pontuação decrescente")
    void testTimesOrdenadosPorPontuacao() throws BusinessException {
        Campeonato campeonato = new Campeonato(gerar20Times());
        List<Time> times = campeonato.getTimes();

        simularJogo(times.get(0), times.get(1), 0, 1);
        simularJogo(times.get(3), times.get(2), 2, 0);

        List<Time> classificacao = campeonato.getTabelaClassificacao();

        assertTrue(classificacao.get(0).calcularPontos() >= classificacao.get(1).calcularPontos());
        assertTrue(classificacao.get(1).calcularPontos() >= classificacao.get(2).calcularPontos());
    }

    @Test
    @DisplayName("Deve desempatar por número de vitórias quando pontos forem iguais")
    void testDesempatePorVitorias() throws BusinessException {
        Campeonato campeonato = new Campeonato(gerar20Times());
        List<Time> times = campeonato.getTimes();

        Time timeMaisVitorias = times.get(1);
        Time timeMenosVitorias = times.get(0);

        timeMaisVitorias.setVitorias(1);
        timeMenosVitorias.setEmpates(3);

        List<Time> classificacao = campeonato.getTabelaClassificacao();

        assertEquals(timeMaisVitorias, classificacao.get(0), "O time com mais vitórias deve vir antes quando os pontos forem iguais");
    }


    @Test
    @DisplayName("Deve desempatar por saldo de gols quando pontos e vitórias forem iguais")
    void testDesempatePorSaldoDeGols() throws BusinessException {
        Campeonato campeonato = new Campeonato(gerar20Times());
        List<Time> times = campeonato.getTimes();

        Time timeMaiorSaldo = times.get(1);
        Time timeMenorSaldo = times.get(0);

        timeMaiorSaldo.setVitorias(2);
        timeMenorSaldo.setVitorias(2);

        timeMaiorSaldo.setGolsMarcados(10);
        timeMaiorSaldo.setGolsSofridos(5);

        timeMenorSaldo.setGolsMarcados(7);
        timeMenorSaldo.setGolsSofridos(6);

        List<Time> classificacao = campeonato.getTabelaClassificacao();

        assertEquals(timeMaiorSaldo, classificacao.get(0), "O time com saldo de gols maior deve vir antes quando pontos e vitórias forem iguais");
    }

    @Test
    @DisplayName("Deve desempatar por gols marcados quando pontos, vitórias e saldo forem iguais")
    void testDesempatePorGolsMarcados() throws BusinessException {
        Campeonato campeonato = new Campeonato(gerar20Times());
        List<Time> times = campeonato.getTimes();

        Time timeMaisGols = times.get(1);
        Time timeMenosGols = times.get(0);

        timeMaisGols.setVitorias(2);
        timeMenosGols.setVitorias(2);
        assertEquals(timeMaisGols.calcularPontos(), timeMenosGols.calcularPontos());

        timeMaisGols.setGolsMarcados(10);
        timeMaisGols.setGolsSofridos(5);
        timeMenosGols.setGolsMarcados(8);
        timeMenosGols.setGolsSofridos(3);

        List<Time> classificacao = campeonato.getTabelaClassificacao();

        assertEquals(timeMaisGols, classificacao.get(0));
    }

    @Test
    @DisplayName("Deve desempatar por número de cartões vermelhos quando pontos, vitórias, saldo e confronto direto forem iguais")
    void testDesempatePorCartoesVermelhos() throws BusinessException {
        Campeonato campeonato = new Campeonato(gerar20Times());
        List<Time> times = campeonato.getTimes();

        Time timeMaisVermelhos  = times.get(0);
        Time timeMenosVermelhos = times.get(1);

        timeMaisVermelhos.setVitorias(5);
        timeMaisVermelhos.setGolsMarcados(10);
        timeMaisVermelhos.setGolsSofridos(5);
        timeMaisVermelhos.setCartoesVermelhos(3);

        timeMenosVermelhos.setVitorias(5);
        timeMenosVermelhos.setGolsMarcados(10);
        timeMenosVermelhos.setGolsSofridos(5);
        timeMenosVermelhos.setCartoesVermelhos(1);

        List<Time> classificacao = campeonato.getTabelaClassificacao();

        assertEquals(timeMenosVermelhos, classificacao.get(0),
                "O time com menos cartões vermelhos deve aparecer primeiro na classificação");
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

    private void simularJogo(Time mandante, Time visitante, int golsMandante, int golsVisitante) {
        Jogo jogo = new Jogo(mandante, visitante);
        jogo.setGolsMandante(golsMandante);
        jogo.setGolsVisitante(golsVisitante);
        jogo.finalizarJogo();
    }
}
