package com.trabalhopratico1;

import java.util.*;

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

        List<Time> timesEmbaralhados = new ArrayList<>(times);
        Collections.shuffle(timesEmbaralhados);

        int n = 20;
        List<Time> timesRotacionaveis = new ArrayList<>(timesEmbaralhados.subList(1, n));
        int numRodadasPrimeiraMetade = 19;

        for (int r = 0; r < numRodadasPrimeiraMetade; r++) {
            Rodada rodada = new Rodada(r + 1);
            List<Time> rodadaTimes = new ArrayList<>();
            rodadaTimes.add(timesEmbaralhados.get(0));
            rodadaTimes.addAll(timesRotacionaveis);

            for (int i = 0; i < n / 2; i++) {
                Time mandante = rodadaTimes.get(i);
                Time visitante = rodadaTimes.get(n - 1 - i);
                rodada.addJogo(mandante, visitante);
            }

            rodadas.add(rodada);

            Time last = timesRotacionaveis.remove(timesRotacionaveis.size() - 1);
            timesRotacionaveis.add(0, last);
        }

        int rodadaNum = numRodadasPrimeiraMetade + 1;
        for (int r = 0; r < numRodadasPrimeiraMetade; r++, rodadaNum++) {
            Rodada rodada = new Rodada(rodadaNum + 1);
            Rodada primeiraMetadeRodada = rodadas.get(r);

            for (Jogo jogo : primeiraMetadeRodada.getJogos()) {
                rodada.addJogo(jogo.getVisitante(), jogo.getMandante());
            }

            rodadas.add(rodada);
        }

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

    public List<Time> getTabelaClassificacao() {
        TabelaClassificacao tabelaClassificacao = new TabelaClassificacao(times, rodadas);
        return tabelaClassificacao.gerar();
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
