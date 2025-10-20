package com.trabalhopratico1;

public class Jogo 
{
    private Time mandante;
    private Time visitante;
    private boolean realizado;
    private int golsMandante;
    private int golsVisitante;

    public Jogo(Time mandante, Time visitante) {
        this.setMandante(mandante);
        this.setVisitante(visitante);
        this.setRealizado(false);
        this.setGolsMandante(0);
        this.setGolsVisitante(0);
    }

    public Time getMandante() {
        return mandante;
    }

    public void setMandante(Time mandante) {
        this.mandante = mandante;
    }

    public Time getVisitante() {
        return visitante;
    }

    public void setVisitante(Time visitante) {
        this.visitante = visitante;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public int getGolsMandante() {
        return golsMandante;
    }

    public void setGolsMandante(int golsMandante) {
        this.golsMandante = golsMandante;
    }

    public int getGolsVisitante() {
        return golsVisitante;
    }

    public void setGolsVisitante(int golsVisitante) {
        this.golsVisitante = golsVisitante;
    }

    public void finalizarJogo() {
        if (realizado) return;
        realizado = true;

        if (golsMandante > golsVisitante) {
            mandante.registrarVitoria(golsMandante, golsVisitante);
            visitante.registrarDerrota(golsVisitante, golsMandante);
        } else if (golsMandante < golsVisitante) {
            visitante.registrarVitoria(golsVisitante, golsMandante);
            mandante.registrarDerrota(golsMandante, golsVisitante);
        } else {
            mandante.registrarEmpate(golsMandante, golsVisitante);
            visitante.registrarEmpate(golsVisitante, golsMandante);
        }
    }

}
