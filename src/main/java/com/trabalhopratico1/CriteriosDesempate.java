package com.trabalhopratico1;

import java.util.Random;

public class CriteriosDesempate {
    
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
        if (vencedorConfronto != null) {
            return vencedorConfronto;
        }

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

    private static Time sorteioCBF(Time t1, Time t2, Random rng) {
        boolean primeiroTimeVence = rng.nextBoolean();
        return primeiroTimeVence ? t1 : t2;
    }
}