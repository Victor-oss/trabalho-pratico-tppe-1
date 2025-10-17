package com.trabalhopratico1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.trabalhopratico1.exception.BusinessException;

public class Campeonato 
{
    private List<Rodada> rodadas = new ArrayList<>();

    public Campeonato () {}

    public List<Rodada> sortearRodadas(List<String> nomesTimes) throws BusinessException {
        List<Time> times = this.getTimes(nomesTimes);

        this.clearRodadas();

        Collections.shuffle(times);

        int n = 20;
        List<Time> timesRotacionaveis = new ArrayList<>(times.subList(1, n));
        int numRodadasPrimeiraMetade = 19;

        for (int r = 0; r < numRodadasPrimeiraMetade; r++) {
            Rodada rodada = new Rodada(r + 1);
            List<Time> rodadaTimes = new ArrayList<>();
            rodadaTimes.add(times.get(0));
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

    private void clearRodadas() {
        this.rodadas.clear();
    }

    private List<Rodada> getRodadas() {
        return this.rodadas;
    }
}
