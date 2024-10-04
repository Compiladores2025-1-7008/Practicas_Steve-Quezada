package main.java;

/**
 * Clase ClaseLexica que define los diferentes tipos de tokens que el
 * analizador léxico puede reconocer en el lenguaje C_1 utilizando enteros.
 * Cada constante representa una categoría de lexemas del código fuente.
 * 
 * @author steve-quezada
 */
public class ClaseLexica {
    // Palabras clave
    public static final int INT = 1; // int
    public static final int FLOAT = 2; // float
    public static final int IF = 3; // if
    public static final int ELSE = 4; // else
    public static final int WHILE = 5; // while

    // Identificadores
    public static final int ID = 6; // Identificador

    // Números
    public static final int NUMERO_ENTERO = 7; // Números enteros
    public static final int NUMERO_REAL = 8; // Números reales

    // Símbolos y operadores
    public static final int PYC = 9; // ;
    public static final int COMA = 10; // ,
    public static final int LPAR = 11; // (
    public static final int RPAR = 12; // )
    public static final int LLLA = 13; // {
    public static final int RLLA = 14; // }
    public static final int ASIGNACION = 15; // =
    public static final int IGUALDAD = 16; // ==
    public static final int MAYORQUE = 17; // >
    public static final int MENORQUE = 18; // <
    public static final int SUMA = 19; // +
    public static final int RESTA = 20; // -
    public static final int MULTIPLICACION = 21; // *
    public static final int DIVISION = 22; // /

    // Operadores relacionales y lógicos
    public static final int MAYORIGUAL = 23; // >=
    public static final int MENORIGUAL = 24; // <=
    public static final int DIFERENTE = 25; // !=

    // Fin de archivo
    public static final int EOF = 26;

    /**
     * Método para obtener el nombre de la clase léxica.
     * 
     * @param claseLexica El código de la clase léxica.
     * @return El nombre de la clase léxica.
     */
    public static String getNombreClase(int claseLexica) {
        switch (claseLexica) {
            case INT:
                return "INT";
            case FLOAT:
                return "FLOAT";
            case IF:
                return "IF";
            case ELSE:
                return "ELSE";
            case WHILE:
                return "WHILE";
            case ID:
                return "ID";
            case NUMERO_ENTERO:
                return "NUMERO_ENTERO";
            case NUMERO_REAL:
                return "NUMERO_REAL";
            case PYC:
                return "PYC";
            case COMA:
                return "COMA";
            case LPAR:
                return "LPAR";
            case RPAR:
                return "RPAR";
            case LLLA:
                return "LLLA";
            case RLLA:
                return "RLLA";
            case ASIGNACION:
                return "ASIGNACION";
            case IGUALDAD:
                return "IGUALDAD";
            case MAYORQUE:
                return "MAYORQUE";
            case MENORQUE:
                return "MENORQUE";
            case SUMA:
                return "SUMA";
            case RESTA:
                return "RESTA";
            case MULTIPLICACION:
                return "MULTIPLICACION";
            case DIVISION:
                return "DIVISION";
            case MAYORIGUAL:
                return "MAYORIGUAL";
            case MENORIGUAL:
                return "MENORIGUAL";
            case DIFERENTE:
                return "DIFERENTE";
            case EOF:
                return "EOF";
            default:
                return "UNKNOWN";
        }
    }

    /**
     * Método para obtener el color asociado a la clase léxica.
     * 
     * @param claseLexica El código de la clase léxica.
     * @return El color asociado a la clase léxica.
     */
    public static String getColorClase(int claseLexica) {
        switch (claseLexica) {
            case INT:
                return Colors.RED;
            case FLOAT:
                return Colors.GREEN;
            case IF:
                return Colors.YELLOW;
            case ELSE:
                return Colors.BLUE;
            case WHILE:
                return Colors.MAGENTA;
            case ID:
                return Colors.CYAN;
            case NUMERO_ENTERO:
                return Colors.ORANGE;
            case NUMERO_REAL:
                return Colors.PURPLE;
            case PYC:
                return Colors.PINK;
            case COMA:
                return Colors.LIGHT_PINK;
            case LPAR:
                return Colors.BRIGHT_EMERALD;
            case RPAR:
                return Colors.BROWN;
            case LLLA:
                return Colors.LIME;
            case RLLA:
                return Colors.GOLD;
            case ASIGNACION:
                return Colors.FOREST_GREEN;
            case IGUALDAD:
                return Colors.TURQUOISE;
            case MAYORQUE:
                return Colors.CORAL;
            case MENORQUE:
                return Colors.OLIVE;
            case SUMA:
                return Colors.SAND;
            case RESTA:
                return Colors.INDIGO;
            case MULTIPLICACION:
                return Colors.SALMON;
            case DIVISION:
                return Colors.MINT;
            case MAYORIGUAL:
                return Colors.EMERALD;
            case MENORIGUAL:
                return Colors.CHOCOLATE;
            case DIFERENTE:
                return Colors.PLUM;
            case EOF:
                return Colors.AQUAMARINE;
            default:
                return Colors.RESTORE;
        }
    }
}
