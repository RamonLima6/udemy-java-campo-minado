package br.com.ramon.cm.modelo;

import br.com.ramon.cm.excecao.ExplosaoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TabuleiroTest {

    Tabuleiro tabuleiro = new Tabuleiro(2,1,1);

    @Test
    void testarAbrir(){

        int coluna = 0;
        int linha;
        if(tabuleiro.getCampos().stream().filter(c -> c.getLinha() == 1 && c.getColuna() == 0).findFirst().map(Campo::isMinado).orElseThrow()){
            linha = 0;
        } else{
            linha = 1;
        }

        tabuleiro.abrir(linha,coluna);
        boolean campoAberto = tabuleiro.getCampos()
                .stream()
                .filter(c->c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .map(Campo::isAberto)
                .orElse(false);

        assertTrue(campoAberto);
    }

    @Test
    void testarObjetivoAlcancado(){
        if(tabuleiro.getCampos().stream().filter(c -> c.getLinha() == 1 && c.getColuna() == 0).findFirst().map(Campo::isMinado).orElseThrow()){
        tabuleiro.abrir(0,0);
        tabuleiro.alternarMarcacao(1,0);
        } else {
            tabuleiro.abrir(1, 0);
            tabuleiro.alternarMarcacao(0, 0);
        }
        assertTrue(tabuleiro.objetivoAlcancado());
    }

    @Test
    void testarExplosao(){
        if(tabuleiro.getCampos().stream().filter(c -> c.getLinha() == 1 && c.getColuna() == 0).findFirst().map(Campo::isMinado).orElseThrow()) {
            assertThrows(ExplosaoException.class, () -> tabuleiro.abrir(1, 0));
        } else{
            assertThrows(ExplosaoException.class, () -> tabuleiro.abrir(0, 0));
        }
    }

    @Test
    void testarReiniciar(){
        tabuleiro.alternarMarcacao(0, 0);
        tabuleiro.reiniciar();
        assertEquals("?", tabuleiro.getCampos()
                .stream()
                .filter(c -> c.getLinha() == 0 && c.getColuna() == 0)
                .findFirst()
                .map(Campo::toString)
                .orElseThrow()
        );
    }

    @Test
    void testarToString(){
        System.out.println(tabuleiro.toString());
    }
}