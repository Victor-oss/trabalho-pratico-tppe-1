package com.trabalhopratico1;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

import com.trabalhopratico1.exception.BusinessError;
import com.trabalhopratico1.exception.BusinessException;

public class Campeonato
{
    private static final int NUMERO_TIMES = 20;
    private static final int PONTOS_VITORIA = 3;
    private static final int PONTOS_EMPATE = 1;
    
    private List<Rodada> rodadas = new ArrayList<>();
    private List<Time> times = new ArrayList<>();

    public Campeonato (List<Time> times) throws BusinessException {
        if (times == null) {
            throw new BusinessException(BusinessError.NULL_LIST);
        }
        if (times.size() != NUMERO_TIMES) {
            throw new BusinessException(BusinessError.LIST_SIZE_INVALID);
        }

        Set<String> conjunto = new HashSet<>();
        for (Time time : times) {
            if (!conjunto.add(time.getNome())) {
                throw new BusinessException(BusinessError.DUPLICATE_ELEMENTS);
            }
        }

        this.times = times;
        SorteadorRodadas sorteador = new SorteadorRodadas(times);
        this.rodadas = sorteador.sortear();
    }

    public List<Time> getTimes(List<String> nomesTimes) throws BusinessException {
        if (nomesTimes == null || nomesTimes.size() != NUMERO_TIMES) {
            throw new BusinessException(BusinessError.LIST_SIZE_INVALID);
        }

        Set<String> conjunto = new HashSet<>();

        List<Time> times = new ArrayList<>();
        for (String nomeTime : nomesTimes) {
            if (nomeTime == null || nomeTime.trim().isEmpty()) {
                throw new BusinessException(BusinessError.NULL_OR_EMPTY_ELEMENT);
            }

            if (!conjunto.add(nomeTime)) {
                throw new BusinessException(BusinessError.DUPLICATE_ELEMENTS + nomeTime);
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
                    if (jogoEnvolveTime(jogo, t1, t2)) {
                        Pair<Integer, Integer> pontos = calcularPontosJogo(jogo, t1, t2);
                        pontosT1 += pontos.getLeft();
                        pontosT2 += pontos.getRight();
                    }
                }
            }
        }

        if (pontosT1 == pontosT2) return null;
        return pontosT1 > pontosT2 ? t1 : t2;
    }
    
    private boolean jogoEnvolveTime(Jogo jogo, Time t1, Time t2) {
        return (jogo.getMandante().equals(t1) && jogo.getVisitante().equals(t2)) ||
               (jogo.getMandante().equals(t2) && jogo.getVisitante().equals(t1));
    }
    
    private Pair<Integer, Integer> calcularPontosJogo(Jogo jogo, Time t1, Time t2) {
        int pontosT1 = 0;
        int pontosT2 = 0;
        
        boolean t1EhMandante = jogo.getMandante().equals(t1);
        int golsT1 = t1EhMandante ? jogo.getGolsMandante() : jogo.getGolsVisitante();
        int golsT2 = t1EhMandante ? jogo.getGolsVisitante() : jogo.getGolsMandante();
        
        if (golsT1 > golsT2) {
            pontosT1 = PONTOS_VITORIA;
        } else if (golsT1 == golsT2) {
            pontosT1 = PONTOS_EMPATE;
            pontosT2 = PONTOS_EMPATE;
        } else {
            pontosT2 = PONTOS_VITORIA;
        }
        
        return Pair.of(pontosT1, pontosT2);
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

}
