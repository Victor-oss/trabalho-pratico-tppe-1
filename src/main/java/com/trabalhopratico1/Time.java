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
        int v1 = t1.getVitorias(), v2 = t2.getVitorias();
        if (v1 != v2) return (v1 > v2) ? t1 : t2;

        int s1 = t1.getSaldoDeGols(), s2 = t2.getSaldoDeGols();
        if (s1 != s2) return (s1 > s2) ? t1 : t2;

        int gm1 = t1.getGolsMarcados(), gm2 = t2.getGolsMarcados();
        if (gm1 != gm2) return (gm1 > gm2) ? t1 : t2;

        int cv1 = t1.getCartoesVermelhos(), cv2 = t2.getCartoesVermelhos();
        if (cv1 != cv2) return (cv1 < cv2) ? t1 : t2;

        int ca1 = t1.getCartoesAmarelos(), ca2 = t2.getCartoesAmarelos();
        if (ca1 != ca2) return (ca1 < ca2) ? t1 : t2;
        
        return t1;
    }
}
