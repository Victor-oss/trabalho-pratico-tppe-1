package com.trabalhopratico1;

import java.util.ArrayList;
import java.util.List;

public class Rodada 
{
    private List<Jogo> jogos = new ArrayList<>();
    private int numero;

    public Rodada(int numero) {
        validarNumero(numero);
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
        validarTimes(timeMandante, timeVisitante);
        Jogo newJogo = new Jogo(timeMandante, timeVisitante);
        this.jogos.add(newJogo);
    }
    
    private void validarNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Número da rodada deve ser positivo");
        }
    }
    
    private void validarTimes(Time timeMandante, Time timeVisitante) {
        if (timeMandante == null || timeVisitante == null) {
            throw new IllegalArgumentException("Times não podem ser nulos");
        }
        if (timeMandante.equals(timeVisitante)) {
            throw new IllegalArgumentException("Um time não pode jogar contra si mesmo");
        }
    }
}
