package com.trabalhopratico1;

import java.util.*;

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

    public List<Time> getTabelaClassificacao() {
        List<Time> listaTimes = new ArrayList<>(times);
        listaTimes.sort((t1, t2) -> {
            int cmpPontos = Integer.compare(t2.calcularPontos(), t1.calcularPontos());
            if (cmpPontos != 0) return cmpPontos;
            Time vencedor = desempate(t1, t2);
            if (vencedor == null) return 0;
            return vencedor == t1 ? -1 : 1;
        });
        return listaTimes;
    }

    public Time desempate(Time t1, Time t2) {
        int cmpVitorias = Integer.compare(t1.getVitorias(), t2.getVitorias());
        if (cmpVitorias != 0) return cmpVitorias > 0 ? t1 : t2;

        int cmpSaldo = Integer.compare(t1.getSaldoDeGols(), t2.getSaldoDeGols());
        if (cmpSaldo != 0) return cmpSaldo > 0 ? t1 : t2;

        int cmpGols = Integer.compare(t1.getGolsMarcados(), t2.getGolsMarcados());
        if (cmpGols != 0) return cmpGols > 0 ? t1 : t2;

        Time vencedorConfronto = confrontoDireto(t1, t2);
        if (vencedorConfronto != null) return vencedorConfronto;

        int cmpVermelhos = Integer.compare(t1.getCartoesVermelhos(), t2.getCartoesVermelhos());
        if (cmpVermelhos != 0) return cmpVermelhos < 0 ? t1 : t2;

        int cmpAmarelos = Integer.compare(t1.getCartoesAmarelos(), t2.getCartoesAmarelos());
        if (cmpAmarelos != 0) return cmpAmarelos < 0 ? t1 : t2;

        return new Random().nextBoolean() ? t1 : t2;
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

    private void clearRodadas() {
        this.rodadas.clear();
    }

}
