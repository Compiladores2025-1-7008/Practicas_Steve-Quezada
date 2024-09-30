/**
 * Clase Token que representa una unidad léxica que será reconocida por el
 * analizador léxico. Un token contiene un tipo y el lexema asociado.
 * 
 * @author steve-quezada
 */
public class Token {
    private ClaseLexica tipo;

    private String lexema;

    /**
     * Constructor de la clase Token.
     * 
     * @param tipo   Tipo del token, que pertenece a una clase léxica.
     * @param lexema Lexema correspondiente al token.
     */
    public Token(ClaseLexica tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
    }

    /**
     * Método para obtener el tipo del token.
     * 
     * @return El tipo del token como un objeto de la clase ClaseLexica.
     */
    public ClaseLexica getTipo() {
        return tipo;
    }

    /**
     * Método para obtener el lexema del token.
     * 
     * @return El lexema como una cadena de caracteres.
     */
    public String getLexema() {
        return lexema;
    }

    @Override
    public String toString() {
        return "Token: <" + tipo + "> Lexema: \"" + lexema + "\"";
    }
}
