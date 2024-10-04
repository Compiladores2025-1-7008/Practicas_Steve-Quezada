package main.java;

/**
 * Clase Token que representa un token en el análisis léxico.
 * 
 * @author steve-quezada
 */
public class Token {
    private int clase;
    private String lexema;

    /**
     * Constructor de la clase Token.
     * 
     * @param clase La clase léxica del token.
     * @param lexema El lexema del token.
     */
    public Token(int clase, String lexema) {
        this.clase = clase;
        this.lexema = lexema;
    }

    /**
     * Obtiene la clase léxica del token.
     * 
     * @return La clase léxica del token.
     */
    public int getClaseLexica() {
        return clase;
    }

    /**
     * Devuelve una representación en cadena del token.
     * 
     * @return Una cadena que representa el token en el formato "<clase, lexema>".
     */
    @Override
    public String toString() {
        return "<" + this.clase + "," + this.lexema + ">";
    }
}