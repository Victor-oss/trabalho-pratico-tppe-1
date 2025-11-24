package com.trabalhopratico1;

import java.util.Random;

public class Time
{
    private String nome;
    private EstatisticasTime estatisticas;

    public Time(String nome) {
        this.setNome(nome);
        this.estatisticas = new EstatisticasTime();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVitorias() {
        return estatisticas.getVitorias();
    }

    public void setVitorias(int vitorias) {
        this.estatisticas.setVitorias(vitorias);
    }

    public int getEmpates() {
        return estatisticas.getEmpates();
    }

    public void setEmpates(int empates) {
        this.estatisticas.setEmpates(empates);
    }

    public int getGolsMarcados() {
        return estatisticas.getGolsMarcados();
    }

    public void setGolsMarcados(int golsMarcados) {
        this.estatisticas.setGolsMarcados(golsMarcados);
    }

    public int getGolsSofridos() {
        return estatisticas.getGolsSofridos();
    }

    public void setGolsSofridos(int golsSofridos) {
        this.estatisticas.setGolsSofridos(golsSofridos);
    }

    public int getCartoesAmarelos() {
        return estatisticas.getCartoesAmarelos();
    }

    public void setCartoesAmarelos(int cartoesAmarelos) {
        this.estatisticas.setCartoesAmarelos(cartoesAmarelos);
    }

    public int getCartoesVermelhos() {
        return estatisticas.getCartoesVermelhos();
    }

    public void setCartoesVermelhos(int cartoesVermelhos) {
        this.estatisticas.setCartoesVermelhos(cartoesVermelhos);
    }

    public int calcularPontos() {
        return estatisticas.calcularPontos();
    }

    public int getSaldoDeGols() {
        return estatisticas.getSaldoDeGols();
    }

    public void registrarVitoria(int golsFeitos, int golsSofridos) {
        this.estatisticas.registrarVitoria(golsFeitos, golsSofridos);
    }

    public void registrarEmpate(int golsFeitos, int golsSofridos) {
        this.estatisticas.registrarEmpate(golsFeitos, golsSofridos);
    }

    public void registrarDerrota(int golsFeitos, int golsSofridos) {
        this.estatisticas.registrarDerrota(golsFeitos, golsSofridos);
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

    public static Time desempate(Time t1, Time t2) {
        return CriteriosDesempate.desempate(t1, t2);
    }

    public static Time confrontoDireto(Time t1, Time t2) {
        return CriteriosDesempate.confrontoDireto(t1, t2);
    }

    public static Time desempate(Time t1, Time t2, Random rng) {
        return CriteriosDesempate.desempate(t1, t2, rng);
    }
}