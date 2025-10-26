package com.trabalhopratico1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.trabalhopratico1.exception.BusinessException;

public class SorteadorRodadas {
    private static final int NUMERO_TIMES = 20;
    private static final int NUMERO_RODADAS_POR_TURNO = 19;
    
    private List<Time> times;
    
    public SorteadorRodadas(List<Time> times) {
        this.times = times;
    }
    
    public List<Rodada> sortear() throws BusinessException {
        List<Rodada> rodadas = new ArrayList<>();
        
        List<Time> timesEmbaralhados = embaralharTimes();
        List<Rodada> primeiroTurno = sortearPrimeiroTurno(timesEmbaralhados);
        rodadas.addAll(primeiroTurno);
        
        List<Rodada> segundoTurno = sortearSegundoTurno(primeiroTurno);
        rodadas.addAll(segundoTurno);
        
        return rodadas;
    }
    
    private List<Time> embaralharTimes() {
        List<Time> timesEmbaralhados = new ArrayList<>(times);
        Collections.shuffle(timesEmbaralhados);
        return timesEmbaralhados;
    }
    
    private List<Rodada> sortearPrimeiroTurno(List<Time> timesEmbaralhados) {
        List<Rodada> primeiroTurno = new ArrayList<>();
        List<Time> timesRotacionaveis = new ArrayList<>(timesEmbaralhados.subList(1, NUMERO_TIMES));
        Time timeFixo = timesEmbaralhados.get(0);
        
        for (int r = 0; r < NUMERO_RODADAS_POR_TURNO; r++) {
            Rodada rodada = criarRodada(r + 1, timeFixo, timesRotacionaveis);
            primeiroTurno.add(rodada);
            rotacionarTimes(timesRotacionaveis);
        }
        
        return primeiroTurno;
    }
    
    private Rodada criarRodada(int numeroRodada, Time timeFixo, List<Time> timesRotacionaveis) {
        Rodada rodada = new Rodada(numeroRodada);
        List<Time> rodadaTimes = montarListaTimesRodada(timeFixo, timesRotacionaveis);
        
        for (int i = 0; i < NUMERO_TIMES / 2; i++) {
            Time mandante = rodadaTimes.get(i);
            Time visitante = rodadaTimes.get(NUMERO_TIMES - 1 - i);
            rodada.addJogo(mandante, visitante);
        }
        
        return rodada;
    }
    
    private List<Time> montarListaTimesRodada(Time timeFixo, List<Time> timesRotacionaveis) {
        List<Time> rodadaTimes = new ArrayList<>();
        rodadaTimes.add(timeFixo);
        rodadaTimes.addAll(timesRotacionaveis);
        return rodadaTimes;
    }
    
    private void rotacionarTimes(List<Time> timesRotacionaveis) {
        Time ultimoTime = timesRotacionaveis.remove(timesRotacionaveis.size() - 1);
        timesRotacionaveis.add(0, ultimoTime);
    }
    
    private List<Rodada> sortearSegundoTurno(List<Rodada> primeiroTurno) {
        List<Rodada> segundoTurno = new ArrayList<>();
        int rodadaNumInicial = NUMERO_RODADAS_POR_TURNO + 1;
        
        for (int r = 0; r < NUMERO_RODADAS_POR_TURNO; r++) {
            Rodada rodada = new Rodada(rodadaNumInicial + r);
            Rodada rodadaPrimeiroTurno = primeiroTurno.get(r);
            
            inverterJogosRodada(rodadaPrimeiroTurno, rodada);
            segundoTurno.add(rodada);
        }
        
        return segundoTurno;
    }
    
    private void inverterJogosRodada(Rodada rodadaOrigem, Rodada rodadaDestino) {
        for (Jogo jogo : rodadaOrigem.getJogos()) {
            rodadaDestino.addJogo(jogo.getVisitante(), jogo.getMandante());
        }
    }
}
