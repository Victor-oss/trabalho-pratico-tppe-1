package com.trabalhopratico1;

import java.util.ArrayList;
import java.util.List;

public class Rodada 
{
    private List<Jogo> jogos = new ArrayList<>();
    private int numero;

    public Rodada(int numero) {
        this.setNumero(numero);
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public List<Jogo> getJogos() {
        return this.jogos;
    }

    public void addJogo(Time timeMandante, Time timeVisitante) {
        Jogo newJogo = new Jogo(timeMandante, timeVisitante);
        this.jogos.add(newJogo);
    }
}
