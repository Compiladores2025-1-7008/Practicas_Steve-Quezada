package main.java;

/**
 * Clase enum ClaseLexica que define los diferentes tipos de tokens que el
 * analizador léxico puede reconocer en el lenguaje C_1. 
 * Cada tipo de token representa una categoría de lexemas del código fuente.
 */
public enum ClaseLexica {
    // Palabras clave
    INT,  
    FLOAT,
    IF,   
    ELSE,  
    WHILE,

    // Identificadores
    ID,

    // Números
    NUMERO,

    // Símbolos
    PYC,        
    COMA,       
    LPAR,       
    RPAR,       
    LLLA,   
    RLLA,   
    ASIGNACION, 
    IGUALDAD,   
    MAYORQUE,   
    MENORQUE,   
    SUMA,       
    RESTA       
}
