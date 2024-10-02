package main.java;

import java.io.IOException;
import main.jflex.Lexer;

public class Parser implements ParserInterface {
    private Lexer lexer;
    private int actual;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void eat(int claseLexica) {
        if (actual == claseLexica) {
            try {
                actual = lexer.yylex();
            } catch (IOException ioe) {
                System.err.println("Failed to read next token");
            }
        } else {
            error("Se esperaba el token de clase " + claseLexica + " pero se encontró " + actual);
        }
    }

    @Override
    public void error(String msg) {
        System.err.println("ERROR DE SINTAXIS: " + msg + " en la línea " + lexer.getLine());
        System.exit(1); 
    }

    @Override
    public void parse() {
        try {
            this.actual = lexer.yylex();
        } catch (IOException ioe) {
            System.err.println("Error: No fue posible obtener el primer token de la entrada.");
            System.exit(1);
        }
        programa(); 
        if (actual == ClaseLexica.EOF) 
            System.out.println("La cadena es aceptada");
        else
            error("La cadena no pertenece al lenguaje generado por la gramática");
    }

    @Override
    public void S() {
        programa();
    }

    private void programa() {
        declaraciones();
        sentencias();
    }

    private void declaraciones() {
        if (actual == ClaseLexica.INT || actual == ClaseLexica.FLOAT) {
            declaracion();
            declaracionesPrima();
        } else {
            error("Se esperaba 'int' o 'float'");
        }
    }

    private void declaracionesPrima() {
        if (actual == ClaseLexica.INT || actual == ClaseLexica.FLOAT) {
            declaracion();
            declaracionesPrima();
        }
    }

    private void declaracion() {
        tipo();
        listaVar();
        eat(ClaseLexica.PYC);
    }

    private void tipo() {
        if (actual == ClaseLexica.INT) {
            eat(ClaseLexica.INT);
        } else if (actual == ClaseLexica.FLOAT) {
            eat(ClaseLexica.FLOAT);
        } else {
            error("Se esperaba 'int' o 'float'");
        }
    }

    private void listaVar() {
        if (actual == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
            listaVarPrima();
        } else {
            error("Se esperaba un identificador");
        }
    }

    private void listaVarPrima() {
        if (actual == ClaseLexica.COMA) {
            eat(ClaseLexica.COMA);
            eat(ClaseLexica.ID);
            listaVarPrima();
        }
    }

    private void sentencias() {
        if (actual == ClaseLexica.ID || actual == ClaseLexica.IF || actual == ClaseLexica.WHILE) {
            sentencia();
            sentenciasPrima();
        } else {
            error("Se esperaba un identificador, 'if' o 'while'");
        }
    }

    private void sentenciasPrima() {
        if (actual == ClaseLexica.ID || actual == ClaseLexica.IF || actual == ClaseLexica.WHILE) {
            sentencia();
            sentenciasPrima();
        }
    }

    private void sentencia() {
        if (actual == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
            eat(ClaseLexica.ASIGNACION);
            expresion();
            eat(ClaseLexica.PYC);
        } else if (actual == ClaseLexica.IF) {
            eat(ClaseLexica.IF);
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
            sentencias();
            eat(ClaseLexica.ELSE);
            sentencias();
        } else if (actual == ClaseLexica.WHILE) {
            eat(ClaseLexica.WHILE);
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
            sentencias();
        } else {
            error("Se esperaba un identificador, 'if' o 'while'");
        }
    }

    private void expresion() {
        termino();
        expresionPrima();
    }

    private void expresionPrima() {
        if (actual == ClaseLexica.SUMA) {
            eat(ClaseLexica.SUMA);
            termino();
            expresionPrima();
        } else if (actual == ClaseLexica.RESTA) {
            eat(ClaseLexica.RESTA);
            termino();
            expresionPrima();
        }
    }

    private void termino() {
        factor();
        terminoPrima();
    }

    private void terminoPrima() {
        if (actual == ClaseLexica.MULTIPLICACION) {
            eat(ClaseLexica.MULTIPLICACION);
            factor();
            terminoPrima();
        } else if (actual == ClaseLexica.DIVISION) {
            eat(ClaseLexica.DIVISION);
            factor();
            terminoPrima();
        }
    }

    private void factor() {
        if (actual == ClaseLexica.LPAR) {
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
        } else if (actual == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
        } else if (actual == ClaseLexica.NUMERO_ENTERO || actual == ClaseLexica.NUMERO_REAL) {
            eat(actual); 
        } else {
            error("Se esperaba un número, identificador o expresión entre paréntesis");
        }
    }
}
