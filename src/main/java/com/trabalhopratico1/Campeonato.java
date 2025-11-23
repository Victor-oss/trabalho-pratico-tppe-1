package com.trabalhopratico1;

import java.util.*;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;

import com.trabalhopratico1.exception.BusinessError;
import com.trabalhopratico1.exception.BusinessException;

public class Campeonato
{
    private List<Rodada> rodadas = new ArrayList<>();
    private List<Time> times = new ArrayList<>();

    public Campeonato (List<Time> times) throws BusinessException {
        if (times == null) {
            throw new BusinessException(BusinessError.NULL_LIST);
        }
        if (times.size() != 20) {
            throw new BusinessException(BusinessError.LIST_SIZE_INVALID);
        }

        Set<String> conjunto = new HashSet<>();
        for (Time time : times) {
            if (!conjunto.add(time.getNome())) {
                throw new BusinessException(BusinessError.DUPLICATE_ELEMENTS);
            }
        }

        this.times = times;
        List<Rodada> rodadasSorteadas = sortearRodadas(times);
        this.rodadas = rodadasSorteadas;
    }

    public List<Rodada> sortearRodadas(List<Time> times) throws BusinessException {
        this.clearRodadas();
        SorteadorDeRodadas sorteador = new SorteadorDeRodadas(this, times);
        List<Rodada> rodadasSorteadas = sorteador.sortear();
        this.rodadas.addAll(rodadasSorteadas);
        return this.getRodadas();
    }

    public List<Time> getTimes(List<String> nomesTimes) throws BusinessException {
        if (nomesTimes == null || nomesTimes.size() != 20) {
            throw new BusinessException("A lista deve conter exatamente 20 elementos.");
        }

        Set<String> conjunto = new HashSet<>();

        List<Time> times = new ArrayList<>();
        for (String nomeTime : nomesTimes) {
            if (nomeTime == null || nomeTime.trim().isEmpty()) {
                throw new BusinessException("A lista contem elementos nulos ou vazios.");
            }

            if (!conjunto.add(nomeTime)) {
                throw new BusinessException("A lista contém elementos duplicados: " + nomeTime);
            }
            Time time = new Time(nomeTime);
            times.add(time);
        }

        return times;
    }

    private String gerarChaveCriteriosPrimarios(Time time) {
        return time.calcularPontos() + "|" +
            time.getVitorias() + "|" +
            time.getSaldoDeGols() + "|" +
            time.getGolsMarcados();
    }

    private Integer compararPorCriteriosPrimarios(Time t1, Time t2) {
        int cmpPontos = Integer.compare(t2.calcularPontos(), t1.calcularPontos());
        if (cmpPontos != 0) return cmpPontos;
        int cmpVitorias = Integer.compare(t2.getVitorias(), t1.getVitorias());
        if (cmpVitorias != 0) return cmpVitorias;
        int cmpSaldo = Integer.compare(t2.getSaldoDeGols(), t1.getSaldoDeGols());
        if (cmpSaldo != 0) return cmpSaldo;
        return Integer.compare(t2.getGolsMarcados(), t1.getGolsMarcados());
    }

    private String gerarChaveCriteriosSecundarios(Time time) {
        return time.getCartoesVermelhos() + "|" + time.getCartoesAmarelos();
    }

    private Integer compararPorCriteriosSecundarios(Time t1, Time t2, Time vencedorConfronto) {
        if (vencedorConfronto != null) {
            return t1.equals(vencedorConfronto) ? -1 : 1;
        }
        int cmpCartoesVermelhos = Integer.compare(t1.getCartoesVermelhos(), t2.getCartoesVermelhos());
        if (cmpCartoesVermelhos != 0) return cmpCartoesVermelhos;

        return Integer.compare(t1.getCartoesAmarelos(), t2.getCartoesAmarelos());
    }

    private void mapearTimesEmpatados(Map<String, Pair<Integer, Integer>> mapaEmpates, int indiceInicio, int indiceFim, List<Time> listaTimes, Function<Time, String> geradorChave) {
        for(int i = indiceInicio; i < indiceFim; i++) {
            Time time = listaTimes.get(i);
            String chave = geradorChave.apply(time);
            if (!mapaEmpates.containsKey(chave)) {
                mapaEmpates.put(chave, Pair.of(i, i));
            } else {
                Pair<Integer, Integer> intervaloAtual = mapaEmpates.get(chave);
                mapaEmpates.put(chave, Pair.of(intervaloAtual.getKey(), i));
            }
        }
    }

    private void aplicarDesempatePorCriteriosSecundarios(List<Time> times, Map<String, Pair<Integer, Integer>> timesEmpatadosPorCriteriosPrimarios) {
        Map<String, Pair<Integer, Integer>> timesEmpatadosPorCriteriosSecundarios = new HashMap<>();

        for (Map.Entry<String, Pair<Integer, Integer>> entrada : timesEmpatadosPorCriteriosPrimarios.entrySet()) {
            Pair<Integer, Integer> intervalo = entrada.getValue();
            int indiceInicio = intervalo.getLeft();
            int indiceFim = intervalo.getRight();

            if (indiceInicio != indiceFim) {
                List<Time> timesEmpatados = times.subList(indiceInicio, indiceFim + 1);
                final Time vencedorConfronto = timesEmpatados.size() == 2 ?
                    confrontoDireto(timesEmpatados.get(0), timesEmpatados.get(1)) : null;

                timesEmpatados.sort((t1, t2) -> compararPorCriteriosSecundarios(t1, t2, vencedorConfronto));

                if(vencedorConfronto == null) {
                    mapearTimesEmpatados(timesEmpatadosPorCriteriosSecundarios, indiceInicio, indiceFim + 1, times, this::gerarChaveCriteriosSecundarios);
                }
            }
        }

        aplicarSorteioParaTimesEmpatados(times, timesEmpatadosPorCriteriosSecundarios);
    }

    private void aplicarSorteioParaTimesEmpatados(List<Time> times, Map<String, Pair<Integer, Integer>> timesEmpatados) {
        for (Map.Entry<String, Pair<Integer, Integer>> entrada : timesEmpatados.entrySet()) {
            Pair<Integer, Integer> intervalo = entrada.getValue();
            int indiceInicio = intervalo.getLeft();
            int indiceFim = intervalo.getRight();

            if (indiceInicio != indiceFim) {
                List<Time> timesParaSortear = times.subList(indiceInicio, indiceFim + 1);

                Collections.shuffle(timesParaSortear);
            }
        }
    }

    public List<Time> getTabelaClassificacao() {
        List<Time> timesOrdenados = new ArrayList<>(times);

        // Primeira ordenação: critérios primários (pontos, vitórias, saldo, gols)
        timesOrdenados.sort(this::compararPorCriteriosPrimarios);

        // Mapear times com mesmos critérios primários
        Map<String, Pair<Integer, Integer>> timesEmpatadosPorCriteriosPrimarios = new HashMap<>();
        mapearTimesEmpatados(timesEmpatadosPorCriteriosPrimarios, 0, timesOrdenados.size(), timesOrdenados, this::gerarChaveCriteriosPrimarios);

        // Aplicar critérios secundários (confronto direto, cartões) e sorteio
        aplicarDesempatePorCriteriosSecundarios(timesOrdenados, timesEmpatadosPorCriteriosPrimarios);

        return timesOrdenados;
    }

    public Time confrontoDireto(Time t1, Time t2) {
        int pontosT1 = 0;
        int pontosT2 = 0;

        for (Rodada rodada : rodadas) {
            for (Jogo jogo : rodada.getJogos()) {
                if (jogo.isRealizado()) {
                    if (jogo.getMandante().equals(t1) && jogo.getVisitante().equals(t2)) {
                        if (jogo.getGolsMandante() > jogo.getGolsVisitante()) pontosT1 += 3;
                        else if (jogo.getGolsMandante() == jogo.getGolsVisitante()) {
                            pontosT1++; pontosT2++;
                        } else pontosT2 += 3;
                    }
                    else if (jogo.getMandante().equals(t2) && jogo.getVisitante().equals(t1)) {
                        if (jogo.getGolsMandante() > jogo.getGolsVisitante()) pontosT2 += 3;
                        else if (jogo.getGolsMandante() == jogo.getGolsVisitante()) {
                            pontosT1++; pontosT2++;
                        } else pontosT1 += 3;
                    }
                }
            }
        }

        if (pontosT1 == pontosT2) return null;
        return pontosT1 > pontosT2 ? t1 : t2;
    }


    public List<Time> getTimes() {
        return this.times;
    }

    public List<Rodada> getRodadas() {
        return this.rodadas;
    }

    public void mockResultadoConfrontoDireto(Time t1, Time t2) {
        for (Rodada rodada : rodadas) {
            for (Jogo jogo : rodada.getJogos()) {
                if (jogo.getMandante().equals(t1) && jogo.getVisitante().equals(t2)) {
                    jogo.setGolsMandante(0);
                    jogo.setGolsVisitante(1);
                    jogo.setRealizado(true);
                }
                else if (jogo.getMandante().equals(t2) && jogo.getVisitante().equals(t1)) {
                    jogo.setGolsMandante(1);
                    jogo.setGolsVisitante(0);
                    jogo.setRealizado(true);
                }
            }
        }
    }

    private void clearRodadas() {
        this.rodadas.clear();
    }

}
