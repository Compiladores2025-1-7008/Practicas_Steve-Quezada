package main.java;

/**
 * Enumeración ClaseLexica que define los diferentes tipos de tokens que el
 * analizador léxico puede reconocer en el lenguaje C_1.
 * Cada constante representa una categoría de lexemas del código fuente.
 * 
 * @author steve-quezada
 */
public enum ClaseLexica {
    // Palabras clave
    INT("INT", Colors.RED),
    FLOAT("FLOAT", Colors.GREEN),
    IF("IF", Colors.YELLOW),
    ELSE("ELSE", Colors.BLUE),
    WHILE("WHILE", Colors.MAGENTA),

    // Identificadores
    ID("ID", Colors.CYAN),

    // Números
    NUMERO_ENTERO("NUMERO_ENTERO", Colors.ORANGE),
    NUMERO_REAL("NUMERO_REAL", Colors.PURPLE),

    // Símbolos y operadores
    PYC("PYC", Colors.PINK),
    COMA("COMA", Colors.LIGHT_PINK),
    LPAR("LPAR", Colors.BRIGHT_EMERALD),
    RPAR("RPAR", Colors.BROWN),
    LLLA("LLLA", Colors.LIME),
    RLLA("RLLA", Colors.GOLD),
    ASIGNACION("ASIGNACION", Colors.FOREST_GREEN),
    IGUALDAD("IGUALDAD", Colors.TURQUOISE),
    MAYORQUE("MAYORQUE", Colors.CORAL),
    MENORQUE("MENORQUE", Colors.OLIVE),
    SUMA("SUMA", Colors.SAND),
    RESTA("RESTA", Colors.INDIGO),
    MULTIPLICACION("MULTIPLICACION", Colors.SALMON),
    DIVISION("DIVISION", Colors.MINT),

    // Operadores relacionales y lógicos
    MAYORIGUAL("MAYORIGUAL", Colors.EMERALD),
    MENORIGUAL("MENORIGUAL", Colors.CHOCOLATE),
    DIFERENTE("DIFERENTE", Colors.PLUM),

    // Fin de archivo
    EOF("EOF", Colors.AQUAMARINE),

    // Desconocido
    UNKNOWN("UNKNOWN", Colors.RESTORE);

    private final String nombre;
    private final String color;

    /**
     * Constructor de la enumeración ClaseLexica.
     * 
     * @param nombre El nombre de la clase léxica.
     * @param color  El color asociado a la clase léxica.
     */
    ClaseLexica(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
    }

    /**
     * Obtiene el nombre de la clase léxica.
     * 
     * @return El nombre de la clase léxica.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el color asociado a la clase léxica.
     * 
     * @return El color asociado a la clase léxica.
     */
    public String getColor() {
        return color;
    }

    /**
     * Método para obtener el nombre de la clase léxica a partir de su código.
     * 
     * @param claseLexica El código de la clase léxica.
     * @return El nombre de la clase léxica.
     */
    public static String getNombreClase(ClaseLexica claseLexica) {
        for (ClaseLexica clase : values()) {
            if (clase == claseLexica) {
                return clase.getNombre();
            }
        }
        return UNKNOWN.getNombre();
    }

    /**
     * Método para obtener el color asociado a la clase léxica a partir de su
     * código.
     * 
     * @param claseLexica El código de la clase léxica.
     * @return El color asociado a la clase léxica.
     */
    public static String getColorClase(ClaseLexica claseLexica) {
        for (ClaseLexica clase : values()) {
            if (clase == claseLexica) {
                return clase.getColor();
            }
        }
        return UNKNOWN.getColor();
    }
}