package br.com.ramon.cm.modelo;

import br.com.ramon.cm.excecao.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CampoTest {

    Campo campo;

    @BeforeEach
    void iniciarCampo(){
        campo = new Campo(3, 3);
    }

    @Test
    void testeVizinhoRealComDistancia1(){
    Campo vizinho = new Campo(3,4);
    boolean resultado = campo.adicionarVizinho(vizinho);
    assertTrue(resultado);
    }
    @Test
    void testeVizinhoRealComDistancia2(){
    Campo vizinho = new Campo(4,4);
    boolean resultado = campo.adicionarVizinho(vizinho);
    assertTrue(resultado);
    }

    @Test
    void testValorPadraoAtributoMarcado(){
        assertFalse(campo.isMarcado());
    }
    @Test
    void testAlternarMarcacao(){
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }
    @Test
    void testAlternarMarcacaoDuasChamadas(){
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    void testAlternarMarcacaoAberto(){
        campo.abrir();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    void testAbrirAberto(){
        campo.abrir();
        campo.abrir();
        assertTrue(campo.isAberto());
    }

    @Test
    void testAbrirNaoMinadoNaoMarcado(){
        assertTrue(campo.abrir());
    }

    @Test
    void testeAbrirNaoMinadoMarcado(){
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirMinadoMarcado(){
        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    void testAbrirMinadoNaoMarcado(){
        campo.minar();
        assertThrows(ExplosaoException.class, () -> campo.abrir());
    }

    @Test
    void testeAbrirComVizinhos(){
        Campo campo55 = new Campo(5,5);
        Campo campo44 = new Campo(4,4);
        campo44.adicionarVizinho(campo55);
        campo.adicionarVizinho(campo44);
        campo.abrir();
        assertTrue(campo44.isAberto() && campo55.isAberto());
    }
    @Test
    void testeAbrirComVizinhoMinado(){
        Campo campo44 = new Campo(4,4);
        campo44.minar();
        campo.adicionarVizinho(campo44);
        campo.abrir();
        assertFalse(campo44.isAberto());
    }
    @Test
    void testVerificarVizinho(){
        Campo campo63 = new Campo(6,3);
        Campo campo53 = new Campo(5,3);
        campo.adicionarVizinho(campo63);
        campo.adicionarVizinho(campo53);
        assertFalse(campo53.isAberto() &&  campo63.isAberto());
    }

    @Test
    void testVerificarObjetivoAlcancado(){
        campo.abrir();
        campo.objetivoAlcancado();
        assertTrue(campo.objetivoAlcancado());
    }

    @Test
    void testVerificarObjetivoMinado(){
        campo.minar();
        campo.alternarMarcacao();
        campo.objetivoAlcancado();
        assertTrue(campo.objetivoAlcancado());
    }

    @Test
    void testVerificarObjetivoNaoAlcancado(){
        campo.objetivoAlcancado();
        assertFalse(campo.objetivoAlcancado());
    }

    @Test
    void testarMinasNaVizinhanca(){
        Campo vizinho = new Campo(3,4);
        Campo vizinho2 = new Campo(4,4);
        Campo vizinho3 = new Campo(3,2);

        campo.adicionarVizinho(vizinho);
        campo.adicionarVizinho(vizinho2);
        campo.adicionarVizinho(vizinho3);

        vizinho.minar();
        vizinho3.minar();

        campo.minasNaVizinhanca();

        assertEquals(2, campo.minasNaVizinhanca());
    }

    @Test
    void testReiniciar(){
        campo.abrir();
        campo.reiniciar();
        assertFalse(campo.isAberto());
    }

    @Test
    void testMarcacaoMarcado(){
        campo.alternarMarcacao();
        assertSame("x", campo.toString());
    }
    @Test
    void testMarcacaoMinado(){
        campo.minar();
        try {
            campo.abrir();
        }catch(ExplosaoException _){

        }
        assertEquals("*", campo.toString());
    }
    @Test
    void testarMarcacaoMinasNaVizinhanca(){
        Campo vizinho = new Campo(3,4);
        Campo vizinho2 = new Campo(4,4);
        Campo vizinho3 = new Campo(3,2);

        campo.adicionarVizinho(vizinho);
        campo.adicionarVizinho(vizinho2);
        campo.adicionarVizinho(vizinho3);

        vizinho.minar();
        vizinho3.minar();

        campo.minasNaVizinhanca();
        campo.abrir();

        assertEquals("2", campo.toString());
    }
    @Test
    void testarMarcacaoAberto(){
        campo.abrir();
        assertEquals(" ", campo.toString());
    }

    @Test
    void testarMarcacaoSemNada(){
        assertEquals("?", campo.toString());
    }

    @Test
    void testarGets(){
        int linha = campo.getLinha();
        int coluna = campo.getColuna();
        int total = linha + coluna;
        assertEquals(6, total);
    }

    @Test
    void testarSetAberto(){
        campo.setAberto(true);
        assertTrue(campo.isAberto());
    }

    @Test
    void testarIsMinado(){
        campo.minar();
        boolean a = campo.isMinado();
        assertTrue(a);
    }
}