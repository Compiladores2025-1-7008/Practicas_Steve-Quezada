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
    public static final int INT = 1;             // int
    public static final int FLOAT = 2;           // float
    public static final int IF = 3;              // if
    public static final int ELSE = 4;            // else
    public static final int WHILE = 5;           // while

    // Identificadores
    public static final int ID = 6;              // Identificador

    // Números
    public static final int NUMERO_ENTERO = 7;   // Números enteros
    public static final int NUMERO_REAL = 8;     // Números reales

    // Símbolos y operadores
    public static final int PYC = 9;             // ;
    public static final int COMA = 10;           // ,
    public static final int LPAR = 11;           // (
    public static final int RPAR = 12;           // )
    public static final int LLLA = 13;           // {
    public static final int RLLA = 14;           // }
    public static final int ASIGNACION = 15;     // =
    public static final int IGUALDAD = 16;       // ==
    public static final int MAYORQUE = 17;       // >
    public static final int MENORQUE = 18;       // <
    public static final int SUMA = 19;           // +
    public static final int RESTA = 20;          // -
    public static final int MULTIPLICACION = 21; // *
    public static final int DIVISION = 22;       // /

    // Operadores relacionales y lógicos
    public static final int MAYORIGUAL = 23;     // >=
    public static final int MENORIGUAL = 24;     // <=
    public static final int DIFERENTE = 25;      // !=

}
