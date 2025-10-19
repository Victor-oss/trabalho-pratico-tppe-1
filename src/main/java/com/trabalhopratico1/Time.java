package com.trabalhopratico1;

public class Time
{
    private String nome;
    private int vitorias;
    private int empates;
    private int golsMarcados;
    private int golsSofridos;
    private int cartoesAmarelos;
    private int cartoesVermelhos;

    public Time(String nome) {
        this.setNome(nome);
        this.setVitorias(0);
        this.setEmpates(0);
        this.setGolsMarcados(0);
        this.setGolsSofridos(0);
        this.setCartoesAmarelos(0);
        this.setCartoesVermelhos(0);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public int getPontos() {
        return (this.vitorias * 3) + this.empates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return getNome() != null ? getNome().equals(time.getNome()) : time.getNome() == null;
    }

    @Override
    public int hashCode() {
        return getNome() != null ? getNome().hashCode() : 0;
    }
}
