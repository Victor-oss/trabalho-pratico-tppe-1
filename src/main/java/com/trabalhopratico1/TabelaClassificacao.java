package com.trabalhopratico1;

import java.util.*;
import org.apache.commons.lang3.tuple.Pair;

public class TabelaClassificacao {
    private List<Time> times;
    private List<Rodada> rodadas;

    public TabelaClassificacao(List<Time> times, List<Rodada> rodadas) {
        this.times = times;
        this.rodadas = rodadas;
    }

    public List<Time> gerar() {
        List<Time> listaTimes = new ArrayList<>(times);
        Map<String, Pair<Integer, Integer>> pontosTimeIndiceInicialIndiceFinal = new HashMap<>();

        listaTimes.sort((t1, t2) -> {
            int cmpPontos = Integer.compare(t2.calcularPontos(), t1.calcularPontos());
            if (cmpPontos != 0) return cmpPontos;
            int cmpVitorias = Integer.compare(t2.getVitorias(), t1.getVitorias());
            if (cmpVitorias != 0) return cmpVitorias;
            int cmpSaldo = Integer.compare(t2.getSaldoDeGols(), t1.getSaldoDeGols());
            if (cmpSaldo != 0) return cmpSaldo;
            return Integer.compare(t2.getGolsMarcados(), t1.getGolsMarcados());
        });

        for (int i = 0; i < listaTimes.size(); i++) {
            Time time = listaTimes.get(i);

            String key = time.calcularPontos() + "|" +
                        time.getVitorias() + "|" +
                        time.getSaldoDeGols() + "|" +
                        time.getGolsMarcados();

            if (!pontosTimeIndiceInicialIndiceFinal.containsKey(key)) {
                pontosTimeIndiceInicialIndiceFinal.put(key, Pair.of(i, i));
            } else {
                Pair<Integer, Integer> atual = pontosTimeIndiceInicialIndiceFinal.get(key);
                pontosTimeIndiceInicialIndiceFinal.put(key, Pair.of(atual.getKey(), i));
            }
        }

        Map<String, Pair<Integer, Integer>> cartoesTimeIndiceInicialIndiceFinal = new HashMap<>();

        for (Map.Entry<String, Pair<Integer, Integer>> entrada : pontosTimeIndiceInicialIndiceFinal.entrySet()) {
            Pair<Integer, Integer> intervalo = entrada.getValue();
            int indiceInicio = intervalo.getLeft();
            int indiceFim = intervalo.getRight();

            if (indiceInicio != indiceFim) {
                List<Time> subLista = listaTimes.subList(indiceInicio, indiceFim + 1);
                final Time vencedorConfronto = subLista.size() == 2 ? confrontoDireto(subLista.get(0), subLista.get(1)) : null;
                subLista.sort((t1, t2) -> {
                    if (vencedorConfronto != null) {
                        if (t1.equals(vencedorConfronto)) return -1;
                        else return 1;
                    }
                    int cmpCartoesVermelhos = Integer.compare(t1.getCartoesVermelhos(), t2.getCartoesVermelhos());
                    if (cmpCartoesVermelhos != 0) return cmpCartoesVermelhos;

                    return Integer.compare(t1.getCartoesAmarelos(), t2.getCartoesAmarelos());
                });

                if(vencedorConfronto == null) {
                    for(int i = indiceInicio; i <= indiceFim; i++) {
                        Time time = listaTimes.get(i);
                        String key = time.getCartoesVermelhos() + "|" + time.getCartoesAmarelos();
                        if (!cartoesTimeIndiceInicialIndiceFinal.containsKey(key)) {
                            cartoesTimeIndiceInicialIndiceFinal.put(key, Pair.of(i, i));
                        } else {
                            Pair<Integer, Integer> atual = cartoesTimeIndiceInicialIndiceFinal.get(key);
                            cartoesTimeIndiceInicialIndiceFinal.put(key, Pair.of(atual.getKey(), i));
                        }
                    }
                }
            }
        }

        for (Map.Entry<String, Pair<Integer, Integer>> entrada : cartoesTimeIndiceInicialIndiceFinal.entrySet()) {
            Pair<Integer, Integer> intervalo = entrada.getValue();
            int indiceInicio = intervalo.getLeft();
            int indiceFim = intervalo.getRight();

            if (indiceInicio != indiceFim) {
                List<Time> subLista = listaTimes.subList(indiceInicio, indiceFim + 1);

                Collections.shuffle(subLista);
            }
        }
        return listaTimes;
    }

    private Time confrontoDireto(Time t1, Time t2) {
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
}
