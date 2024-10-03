package main.java;

import java.io.IOException;
import main.jflex.Lexer;

/**
 * Clase Parser que implementa un analizador sintáctico de descenso recursivo.
 * 
 * @author steve-quezada
 */
public class Parser implements ParserInterface {
    private Lexer analizadorLexico;
    private int tokenActual;
    private int nivelIndentacion = 0;

    /**
     * Constructor de la clase Parser.
     * 
     * @param analizadorLexico El analizador léxico que se utilizará para obtener
     *                         los tokens.
     */
    public Parser(Lexer analizadorLexico) {
        this.analizadorLexico = analizadorLexico;
    }

    /**
     * Imprime un mensaje con la indentación actual y el color especificado.
     * 
     * @param mensaje El mensaje a imprimir.
     * @param color   El color del mensaje.
     */
    private void imprimirConIndentacion(String mensaje, String color) {
        for (int i = 0; i < nivelIndentacion; i++) {
            System.out.print("  ");
        }
        Colors.println(mensaje, color);
    }

    /**
     * Incrementa el nivel de indentación.
     */
    private void incrementarIndentacion() {
        nivelIndentacion++;
    }

    /**
     * Decrementa el nivel de indentación.
     */
    private void decrementarIndentacion() {
        nivelIndentacion--;
    }

    /**
     * Consume el token actual si coincide con la clase léxica esperada.
     * 
     * @param claseLexica La clase léxica esperada.
     */
    @Override
    public void eat(int claseLexica) {
        if (tokenActual == claseLexica) {
            try {
                int tokenPrevio = tokenActual;
                int lineaPrevia = analizadorLexico.getLine(); 
                tokenActual = analizadorLexico.yylex();
                if (tokenActual != ClaseLexica.EOF) {
                    Colors.println("\nToken Previo: " + ClaseLexica.getNombreClase(tokenPrevio) + " (línea: "
                            + lineaPrevia + ") -> Token Nuevo: " + ClaseLexica.getNombreClase(tokenActual) + " (línea: "
                            + analizadorLexico.getLine() + ")", Colors.GRAY_DARK);
                    System.out.println();
                } else {
                    Colors.print("\nToken Previo: " + ClaseLexica.getNombreClase(tokenPrevio) + " (línea: "
                            + lineaPrevia + ") (línea: " + analizadorLexico.getLine() + ")", Colors.BLUE_LIGHT);
                    System.out.println();
                }
            } catch (IOException ioe) {
                Colors.println("No se pudo leer el siguiente token\n", Colors.RED);
            }
        } else {
            error("Se esperaba el token de clase " + ClaseLexica.getNombreClase(claseLexica) + " pero se encontró "
                    + ClaseLexica.getNombreClase(tokenActual));
        }
    }

    /**
     * Maneja los errores de sintaxis.
     * 
     * @param mensaje El mensaje de error.
     */
    @Override
    public void error(String mensaje) {
        Colors.println("\nERROR DE SINTAXIS: " + mensaje + " en la línea " + analizadorLexico.getLine(), Colors.RED);
        System.exit(1);
    }

    /**
     * Inicia el análisis sintáctico.
     */
    @Override
    public void parse() {
        Colors.println("\nInicio del análisis sintáctico", Colors.TEAL);

        try {
            this.tokenActual = analizadorLexico.yylex();
        } catch (IOException ioe) {
            Colors.println("ERROR: No fue posible obtener el primer token de la entrada.\n", Colors.RED);
            System.exit(1);
        }
        programa();
        if (tokenActual == ClaseLexica.EOF)
            Colors.print("\nLa cadena es aceptada", Colors.GREEN);
        else
            error("La cadena no pertenece al lenguaje generado por la gramática");
    }

    /**
     * Método que inicia el análisis sintáctico.
     */
    @Override
    public void S() {
        programa();
    }

    /**
     * Analiza la estructura.
     */
    private void programa() {
        System.out.println();
        imprimirConIndentacion("\nParsing programa...", Colors.MAGENTA_LIGHT);
        incrementarIndentacion();
        declaraciones();
        sentencias();
        decrementarIndentacion();
        imprimirConIndentacion("\nFin del análisis sintáctico.", Colors.TEAL);
    }

    /**
     * Analiza las declaraciones.
     */
    private void declaraciones() {
        imprimirConIndentacion("Parsing declaraciones...", Colors.MAGENTA_LIGHT);
        incrementarIndentacion();
        if (tokenActual == ClaseLexica.INT || tokenActual == ClaseLexica.FLOAT) {
            declaracion();
            declaracionesAdicionales();
        }
        decrementarIndentacion();
    }

    /**
     * Analiza las declaraciones adicionales.
     */
    private void declaracionesAdicionales() {
        if (tokenActual == ClaseLexica.INT || tokenActual == ClaseLexica.FLOAT) {
            declaracion();
            declaracionesAdicionales();
        }
    }

    /**
     * Analiza una declaración.
     */
    private void declaracion() {
        imprimirConIndentacion("Parsing declaracion...", Colors.MAGENTA_LIGHT);
        incrementarIndentacion();
        tipo();
        listaVariables();
        eat(ClaseLexica.PYC);
        decrementarIndentacion();
    }

    /**
     * Analiza el tipo de una declaración.
     */
    private void tipo() {
        imprimirConIndentacion("Parsing tipo...", Colors.MAGENTA_LIGHT);
        incrementarIndentacion();
        if (tokenActual == ClaseLexica.INT) {
            eat(ClaseLexica.INT);
        } else if (tokenActual == ClaseLexica.FLOAT) {
            eat(ClaseLexica.FLOAT);
        } else {
            error("Se esperaba 'int' o 'float'");
        }
        decrementarIndentacion();
    }

    /**
     * Analiza la lista de variables en una declaración.
     */
    private void listaVariables() {
        imprimirConIndentacion("Parsing listaVariables...", Colors.MAGENTA_LIGHT);
        incrementarIndentacion();
        if (tokenActual == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
            listaVariablesAdicionales();
        } else {
            error("Se esperaba un identificador");
        }
        decrementarIndentacion();
    }

    /**
     * Analiza las variables adicionales en una lista de variables.
     */
    private void listaVariablesAdicionales() {
        if (tokenActual == ClaseLexica.COMA) {
            eat(ClaseLexica.COMA);
            if (tokenActual == ClaseLexica.ID) {
                eat(ClaseLexica.ID);
                listaVariablesAdicionales();
            } else {
                error("Se esperaba un identificador después de la coma");
            }
        }
    }

    /**
     * Analiza las sentencias.
     */
    private void sentencias() {
        imprimirConIndentacion("Parsing sentencias...", Colors.MAGENTA_LIGHT);
        incrementarIndentacion();
        if (tokenActual == ClaseLexica.LLLA) {
            eat(ClaseLexica.LLLA);
            sentenciasAdicionales();
            eat(ClaseLexica.RLLA);
        } else if (tokenActual == ClaseLexica.ID || tokenActual == ClaseLexica.IF || tokenActual == ClaseLexica.WHILE) {
            sentencia();
            sentenciasAdicionales();
        }
        decrementarIndentacion();
    }

    /**
     * Analiza las sentencias adicionales.
     */
    private void sentenciasAdicionales() {
        if (tokenActual == ClaseLexica.ID || tokenActual == ClaseLexica.IF || tokenActual == ClaseLexica.WHILE) {
            sentencia();
            sentenciasAdicionales();
        } else if (tokenActual == ClaseLexica.RLLA || tokenActual == ClaseLexica.EOF) {
        } else {
            error("Se esperaba una sentencia o '}'");
        }
    }

    /**
     * Analiza una sentencia.
     */
    private void sentencia() {
        imprimirConIndentacion("Parsing sentencia...", Colors.MAGENTA_LIGHT);
        incrementarIndentacion();
        if (tokenActual == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
            eat(ClaseLexica.ASIGNACION);
            expresion();
            eat(ClaseLexica.PYC);
        } else if (tokenActual == ClaseLexica.IF) {
            eat(ClaseLexica.IF);
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
            sentencias();
            if (tokenActual == ClaseLexica.ELSE) {
                eat(ClaseLexica.ELSE);
                sentencias();
            }
        } else if (tokenActual == ClaseLexica.WHILE) {
            eat(ClaseLexica.WHILE);
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
            sentencias();
        } else {
            error("Se esperaba un identificador, 'if' o 'while'");
        }
        decrementarIndentacion();
    }

    /**
     * Analiza una expresión.
     */
    private void expresion() {
        imprimirConIndentacion("Parsing expresion...", Colors.MAGENTA_LIGHT);
        incrementarIndentacion();
        termino();
        expresionAdicional();
        decrementarIndentacion();
    }

    /**
     * Analiza una expresión adicional.
     */
    private void expresionAdicional() {
        if (tokenActual == ClaseLexica.SUMA) {
            eat(ClaseLexica.SUMA);
            termino();
            expresionAdicional();
        } else if (tokenActual == ClaseLexica.RESTA) {
            eat(ClaseLexica.RESTA);
            termino();
            expresionAdicional();
        } else if (tokenActual == ClaseLexica.MENORQUE) {
            eat(ClaseLexica.MENORQUE);
            termino();
            expresionAdicional();
        } else if (tokenActual == ClaseLexica.MAYORQUE) {
            eat(ClaseLexica.MAYORQUE);
            termino();
            expresionAdicional();
        } else if (tokenActual == ClaseLexica.MENORIGUAL) {
            eat(ClaseLexica.MENORIGUAL);
            termino();
            expresionAdicional();
        } else if (tokenActual == ClaseLexica.MAYORIGUAL) {
            eat(ClaseLexica.MAYORIGUAL);
            termino();
            expresionAdicional();
        } else if (tokenActual == ClaseLexica.IGUALDAD) {
            eat(ClaseLexica.IGUALDAD);
            termino();
            expresionAdicional();
        } else if (tokenActual == ClaseLexica.DIFERENTE) {
            eat(ClaseLexica.DIFERENTE);
            termino();
            expresionAdicional();
        }
    }

    /**
     * Analiza un término en la expresión.
     */
    private void termino() {
        imprimirConIndentacion("Parsing termino...", Colors.MAGENTA_LIGHT);
        incrementarIndentacion();
        factor();
        terminoAdicional();
        decrementarIndentacion();
    }

    /**
     * Analiza un término adicional en la expresión.
     */
    private void terminoAdicional() {
        if (tokenActual == ClaseLexica.MULTIPLICACION) {
            eat(ClaseLexica.MULTIPLICACION);
            factor();
            terminoAdicional();
        } else if (tokenActual == ClaseLexica.DIVISION) {
            eat(ClaseLexica.DIVISION);
            factor();
            terminoAdicional();
        }
    }

    /**
     * Analiza un factor en la expresión.
     */
    private void factor() {
        imprimirConIndentacion("Parsing factor...", Colors.MAGENTA_LIGHT);
        incrementarIndentacion();
        if (tokenActual == ClaseLexica.LPAR) {
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
        } else if (tokenActual == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
        } else if (tokenActual == ClaseLexica.NUMERO_ENTERO || tokenActual == ClaseLexica.NUMERO_REAL) {
            eat(tokenActual);
        } else {
            error("Se esperaba un factor válido");
        }
        decrementarIndentacion();
    }
}