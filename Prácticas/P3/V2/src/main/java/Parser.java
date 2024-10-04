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
    private Token tokenActual;
    private int nivelIndentacion = 0;

    /**
     * Constructor de la clase Parser.
     * 
     * @param analizadorLexico El analizador léxico que se utilizará para obtener
     *                         los tokens.
     */
    public Parser(Lexer analizadorLexico) {
        this.analizadorLexico = analizadorLexico;
        try {
            this.tokenActual = analizadorLexico.yylex();
            this.tokenActual.verificarImprimirToken(); // Verificar e imprimir el primer token
        } catch (IOException ioe) {
            Colors.println("ERROR: No fue posible obtener el primer token de la entrada.\n", Colors.RED);
            System.exit(1);
        }
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
    public void eat(ClaseLexica claseLexica) {
        if (tokenActual.getClaseLexica() == claseLexica) {
            try {
                Token tokenPrevio = tokenActual;
                tokenActual = analizadorLexico.yylex();
                tokenActual.verificarImprimirToken(); // Verificar e imprimir el token
                if (tokenActual.getClaseLexica() != ClaseLexica.EOF) {
                    Colors.println("\nToken Previo: " + tokenPrevio.getClaseLexica().getNombre() + " (línea: "
                            + tokenPrevio.getLinea() + ") -> Token Nuevo: " + tokenActual.getClaseLexica().getNombre()
                            + " (línea: "
                            + tokenActual.getLinea() + ")", Colors.GRAY_DARK);
                    System.out.println();
                } else {
                    Colors.print("\nToken Previo: " + tokenPrevio.getClaseLexica().getNombre() + " (línea: "
                            + tokenPrevio.getLinea() + ") (línea: " + tokenActual.getLinea() + ")", Colors.BLUE_LIGHT);
                    System.out.println();
                }
            } catch (IOException ioe) {
                Colors.println("No se pudo leer el siguiente token\n", Colors.RED);
            }
        } else {
            error("Se esperaba el token de clase " + claseLexica.getNombre() + " pero se encontró "
                    + tokenActual.getClaseLexica().getNombre());
        }
    }

    /**
     * Maneja los errores de sintaxis.
     * 
     * @param mensaje El mensaje de error.
     */
    @Override
    public void error(String mensaje) {
        Colors.println("\nERROR DE SINTAXIS: " + mensaje + " en la línea " + tokenActual.getLinea(), Colors.RED);
        System.exit(1);
    }

    /**
     * Inicia el análisis sintáctico.
     */
    @Override
    public void parse() {
        Colors.println("\nInicio del análisis sintáctico", Colors.TEAL);
        programa();
        if (tokenActual.getClaseLexica() == ClaseLexica.EOF)
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
        if (tokenActual.getClaseLexica() == ClaseLexica.INT || tokenActual.getClaseLexica() == ClaseLexica.FLOAT) {
            declaracion();
            declaracionesAdicionales();
        }
        decrementarIndentacion();
    }

    /**
     * Analiza las declaraciones adicionales.
     */
    private void declaracionesAdicionales() {
        if (tokenActual.getClaseLexica() == ClaseLexica.INT || tokenActual.getClaseLexica() == ClaseLexica.FLOAT) {
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
        if (tokenActual.getClaseLexica() == ClaseLexica.INT) {
            eat(ClaseLexica.INT);
        } else if (tokenActual.getClaseLexica() == ClaseLexica.FLOAT) {
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
        if (tokenActual.getClaseLexica() == ClaseLexica.ID) {
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
        if (tokenActual.getClaseLexica() == ClaseLexica.COMA) {
            eat(ClaseLexica.COMA);
            if (tokenActual.getClaseLexica() == ClaseLexica.ID) {
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
        if (tokenActual.getClaseLexica() == ClaseLexica.LLLA) {
            eat(ClaseLexica.LLLA);
            sentenciasAdicionales();
            eat(ClaseLexica.RLLA);
        } else if (tokenActual.getClaseLexica() == ClaseLexica.ID || tokenActual.getClaseLexica() == ClaseLexica.IF
                || tokenActual.getClaseLexica() == ClaseLexica.WHILE) {
            sentencia();
            sentenciasAdicionales();
        }
        decrementarIndentacion();
    }

    /**
     * Analiza las sentencias adicionales.
     */
    private void sentenciasAdicionales() {
        if (tokenActual.getClaseLexica() == ClaseLexica.ID || tokenActual.getClaseLexica() == ClaseLexica.IF
                || tokenActual.getClaseLexica() == ClaseLexica.WHILE) {
            sentencia();
            sentenciasAdicionales();
        } else if (tokenActual.getClaseLexica() == ClaseLexica.RLLA
                || tokenActual.getClaseLexica() == ClaseLexica.EOF) {
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
        if (tokenActual.getClaseLexica() == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
            eat(ClaseLexica.ASIGNACION);
            expresion();
            eat(ClaseLexica.PYC);
        } else if (tokenActual.getClaseLexica() == ClaseLexica.IF) {
            eat(ClaseLexica.IF);
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
            sentencias();
            if (tokenActual.getClaseLexica() == ClaseLexica.ELSE) {
                eat(ClaseLexica.ELSE);
                sentencias();
            }
        } else if (tokenActual.getClaseLexica() == ClaseLexica.WHILE) {
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
        if (tokenActual.getClaseLexica() == ClaseLexica.SUMA) {
            eat(ClaseLexica.SUMA);
            termino();
            expresionAdicional();
        } else if (tokenActual.getClaseLexica() == ClaseLexica.RESTA) {
            eat(ClaseLexica.RESTA);
            termino();
            expresionAdicional();
        } else if (tokenActual.getClaseLexica() == ClaseLexica.MENORQUE) {
            eat(ClaseLexica.MENORQUE);
            termino();
            expresionAdicional();
        } else if (tokenActual.getClaseLexica() == ClaseLexica.MAYORQUE) {
            eat(ClaseLexica.MAYORQUE);
            termino();
            expresionAdicional();
        } else if (tokenActual.getClaseLexica() == ClaseLexica.MENORIGUAL) {
            eat(ClaseLexica.MENORIGUAL);
            termino();
            expresionAdicional();
        } else if (tokenActual.getClaseLexica() == ClaseLexica.MAYORIGUAL) {
            eat(ClaseLexica.MAYORIGUAL);
            termino();
            expresionAdicional();
        } else if (tokenActual.getClaseLexica() == ClaseLexica.IGUALDAD) {
            eat(ClaseLexica.IGUALDAD);
            termino();
            expresionAdicional();
        } else if (tokenActual.getClaseLexica() == ClaseLexica.DIFERENTE) {
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
        if (tokenActual.getClaseLexica() == ClaseLexica.MULTIPLICACION) {
            eat(ClaseLexica.MULTIPLICACION);
            factor();
            terminoAdicional();
        } else if (tokenActual.getClaseLexica() == ClaseLexica.DIVISION) {
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
        if (tokenActual.getClaseLexica() == ClaseLexica.LPAR) {
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
        } else if (tokenActual.getClaseLexica() == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
        } else if (tokenActual.getClaseLexica() == ClaseLexica.NUMERO_ENTERO
                || tokenActual.getClaseLexica() == ClaseLexica.NUMERO_REAL) {
            eat(tokenActual.getClaseLexica());
        } else {
            error("Se esperaba un factor válido");
        }
        decrementarIndentacion();
    }
}