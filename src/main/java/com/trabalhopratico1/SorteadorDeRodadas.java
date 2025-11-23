package com.trabalhopratico1;

import java.util.*;

import com.trabalhopratico1.exception.BusinessException;

public class SorteadorDeRodadas {
    private Campeonato campeonato;
    private List<Time> times;
    private List<Rodada> rodadas;

    public SorteadorDeRodadas(Campeonato campeonato, List<Time> times) {
        this.campeonato = campeonato;
        this.times = times;
        this.rodadas = new ArrayList<>();
    }

    public List<Rodada> sortear() throws BusinessException {
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

        return rodadas;
    }
}
