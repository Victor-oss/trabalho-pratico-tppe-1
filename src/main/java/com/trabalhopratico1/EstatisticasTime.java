package com.trabalhopratico1;

public class EstatisticasTime {
    private int vitorias;
    private int empates;
    private int golsMarcados;
    private int golsSofridos;
    private int cartoesAmarelos;
    private int cartoesVermelhos;

    private static final int PONTOS_POR_VITORIA = 3;
    private static final int PONTOS_POR_EMPATE = 1;

    public EstatisticasTime() {
        this.vitorias = 0;
        this.empates = 0;
        this.golsMarcados = 0;
        this.golsSofridos = 0;
        this.cartoesAmarelos = 0;
        this.cartoesVermelhos = 0;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }

    public int getGolsMarcados() {
        return golsMarcados;
    }

    public void setGolsMarcados(int golsMarcados) {
        this.golsMarcados = golsMarcados;
    }

    public int getGolsSofridos() {
        return golsSofridos;
    }

    public void setGolsSofridos(int golsSofridos) {
        this.golsSofridos = golsSofridos;
    }

    public int getCartoesAmarelos() {
        return cartoesAmarelos;
    }

    public void setCartoesAmarelos(int cartoesAmarelos) {
        this.cartoesAmarelos = cartoesAmarelos;
    }

    public int getCartoesVermelhos() {
        return cartoesVermelhos;
    }

    public void setCartoesVermelhos(int cartoesVermelhos) {
        this.cartoesVermelhos = cartoesVermelhos;
    }

    public int calcularPontos() {
        return (this.vitorias * PONTOS_POR_VITORIA) + (this.empates * PONTOS_POR_EMPATE);
    }

    public int getSaldoDeGols() {
        return this.golsMarcados - this.golsSofridos;
    }

    public void registrarVitoria(int golsFeitos, int golsSofridos) {
        this.vitorias++;
        this.golsMarcados += golsFeitos;
        this.golsSofridos += golsSofridos;
    }

    public void registrarEmpate(int golsFeitos, int golsSofridos) {
        this.empates++;
        this.golsMarcados += golsFeitos;
        this.golsSofridos += golsSofridos;
    }

    public void registrarDerrota(int golsFeitos, int golsSofridos) {
        this.golsMarcados += golsFeitos;
        this.golsSofridos += golsSofridos;
    }
}