package com.trabalhopratico1;

import java.util.Random;

public class Time
{
    private String nome;
    private int vitorias;
    private int empates;
    private int golsMarcados;
    private int golsSofridos;
    private int cartoesAmarelos;
    private int cartoesVermelhos;

    private static final int PONTOS_POR_VITORIA = 3;
    private static final int PONTOS_POR_EMPATE = 1;

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
        return desempate(t1, t2, new Random());
    }

     public static Time confrontoDireto(Time t1, Time t2) {
        if (t1 == null || t2 == null) {
            throw new IllegalArgumentException("Times não podem ser nulos");
        }
        int cmp = Integer.compare(t1.getSaldoDeGols(), t2.getSaldoDeGols());
        if (cmp == 0) return null;
        return cmp > 0 ? t1 : t2;
    }

    public static Time desempate(Time t1, Time t2, Random rng) {
        int comparacaoVitorias = Integer.compare(t1.getVitorias(), t2.getVitorias());
        if (comparacaoVitorias != 0) {
            return comparacaoVitorias > 0 ? t1 : t2;
        }

        int comparacaoSaldo = Integer.compare(t1.getSaldoDeGols(), t2.getSaldoDeGols());
        if (comparacaoSaldo != 0) {
            return comparacaoSaldo > 0 ? t1 : t2;
        }

        int comparacaoGolsMarcados = Integer.compare(t1.getGolsMarcados(), t2.getGolsMarcados());
        if (comparacaoGolsMarcados != 0) {
            return comparacaoGolsMarcados > 0 ? t1 : t2;
        }

        Time vencedorConfronto = confrontoDireto(t1, t2);
            if (vencedorConfronto != null)
                return vencedorConfronto;

        int comparacaoVermelhos = Integer.compare(t1.getCartoesVermelhos(), t2.getCartoesVermelhos());
        if (comparacaoVermelhos != 0) {
            return comparacaoVermelhos < 0 ? t1 : t2;
        }

        int comparacaoAmarelos = Integer.compare(t1.getCartoesAmarelos(), t2.getCartoesAmarelos());
        if (comparacaoAmarelos != 0) {
            return comparacaoAmarelos < 0 ? t1 : t2;
        }

        return sorteioCBF(t1, t2, rng);
    }

    private static Time sorteioCBF(Time t1, Time t2, Random rng) {
        boolean primeiroTimeVence = rng.nextBoolean();
        return primeiroTimeVence ? t1 : t2;
    }
}
