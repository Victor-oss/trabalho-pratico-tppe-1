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

        mandante.setGolsMarcados(mandante.getGolsMarcados() + golsMandante);
        mandante.setGolsSofridos(mandante.getGolsSofridos() + golsVisitante);

        visitante.setGolsMarcados(visitante.getGolsMarcados() + golsVisitante);
        visitante.setGolsSofridos(visitante.getGolsSofridos() + golsMandante);

        if (golsMandante > golsVisitante) {
            mandante.setVitorias(mandante.getVitorias() + 1);
        } else if (golsMandante < golsVisitante) {
            visitante.setVitorias(visitante.getVitorias() + 1);
        } else {
            mandante.setEmpates(mandante.getEmpates() + 1);
            visitante.setEmpates(visitante.getEmpates() + 1);
        }
    }

}
