import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase Lexer que realiza el análisis léxico de un archivo de entrada.
 * 
 * @author steve-quezada
 */
public class Lexer {
    private PushbackReader entrada;
    private String textoActual;
    private char ultimoCaracter;
    private int[][] tabla = new int[14][256];
    private boolean[] estadosAceptacion = new boolean[14];
    private List<Token> listaTokens = new ArrayList<>();
    private boolean ultimoSaltoLinea = false;

    /**
     * Constructor de la clase Lexer.
     * Inicializa la tabla de transiciones.
     */
    public Lexer() {
        inicializarTabla();
    }

    /**
     * Abre el archivo especificado para su análisis.
     * 
     * @param rutaArchivo Ruta del archivo a abrir.
     * @throws IOException Si ocurre un error al abrir el archivo.
     */
    public void abrirArchivo(String rutaArchivo) throws IOException {
        entrada = new PushbackReader(new BufferedReader(new FileReader(new File(rutaArchivo))));
        textoActual = "";
    }

    /**
     * Cierra el archivo abierto.
     */
    public void cerrarArchivo() {
        if (entrada != null) {
            try {
                entrada.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el archivo: " + e.getMessage());
            }
        }
    }

    /**
     * Obtiene el siguiente carácter del archivo.
     * 
     * @return El siguiente carácter del archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public char obtenerCaracter() throws IOException {
        int c = entrada.read();
        ultimoCaracter = (char) c;
        return (c == -1) ? (char) -1 : ultimoCaracter;
    }

    /**
     * Devuelve el último carácter leído al flujo de entrada.
     */
    public void devolverCaracter() {
        try {
            if (ultimoCaracter != (char) -1) {
                entrada.unread(ultimoCaracter);
            }
        } catch (IOException e) {
            System.err.println("Error devolviendo carácter: " + e.getMessage());
        }
    }

    /**
     * Analiza el siguiente lexema del archivo.
     * 
     * @return El token correspondiente al lexema analizado.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public Token analizarLexema() throws IOException {
        int estado = 0;
        textoActual = "";
        char c = obtenerCaracter();

        while (true) {
            if (c == (char) -1) {
                if (!textoActual.isEmpty() && estadosAceptacion[estado]) {
                    imprimirLexemaFinal();
                    return obtenerToken(estado);
                }
                return null;
            }

            if (esCaracterEspecial(c)) {
                if (!textoActual.isEmpty()) {
                    devolverCaracter();
                    imprimirLexemaFinal();
                    return obtenerToken(estado);
                }
                textoActual = Character.toString(c);
                imprimirLexemaFinal();
                return procesarCaracterEspecial(c);
            }

            if (c == '\n' || c == '\r') {
                if (!textoActual.isEmpty()) {
                    imprimirLexemaFinal();
                    return obtenerToken(estado);
                }
                textoActual = "";
                if (!ultimoSaltoLinea) {
                    System.out.println("\t\t\t\t     Lexema: '↵'\n");
                    ultimoSaltoLinea = true;
                }
                return new Token(ClaseLexica.SALTOLINEA, "↵");
            }

            if (c == ' ' || c == '\t') {
                if (!textoActual.isEmpty()) {
                    imprimirLexemaFinal();
                    return obtenerToken(estado);
                }
                textoActual = "";
                c = obtenerCaracter();
                continue;
            }

            System.out.println("Estado actual: " + estado + ", Carácter: '" + c + "', Lexema: '" + textoActual + "'");

            if (c >= 0 && c < 256 && tabla[estado][c] != -1) {
                estado = tabla[estado][c];
                textoActual += c;
                c = obtenerCaracter();
            } else {
                if (estadosAceptacion[estado]) {
                    devolverCaracter();
                    imprimirLexemaFinal();
                    Token token = obtenerToken(estado);
                    textoActual = ""; 
                    System.out.println();
                    return token;
                } else {
                    error(estado);
                    return null;
                }
            }
        }
    }

    /**
     * Verifica si un carácter es especial.
     * 
     * @param c Carácter a verificar.
     * @return true si el carácter es especial, false en caso contrario.
     */
    private boolean esCaracterEspecial(char c) {
        return c == '=' || c == '+' || c == '-' || c == ';' || c == '{' || c == '}' || c == '(' || c == ')';
    }

    /**
     * Procesa un carácter especial y devuelve el token correspondiente.
     * 
     * @param c Carácter especial a procesar.
     * @return El token correspondiente al carácter especial.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private Token procesarCaracterEspecial(char c) throws IOException {
        Token token;
        switch (c) {
            case '=':
                c = obtenerCaracter();
                if (c == '=') {
                    token = new Token(ClaseLexica.IGUALDAD, "==");
                } else {
                    devolverCaracter();
                    token = new Token(ClaseLexica.ASIGNACION, "=");
                }
                break;
            case '+':
                token = new Token(ClaseLexica.SUMA, "+");
                break;
            case '-':
                token = new Token(ClaseLexica.RESTA, "-");
                break;
            case ';':
                token = new Token(ClaseLexica.PYC, ";");
                break;
            case '{':
                token = new Token(ClaseLexica.LLLA, "{");
                break;
            case '}':
                token = new Token(ClaseLexica.RLLA, "}");
                break;
            case '(':
                token = new Token(ClaseLexica.LPAR, "(");
                break;
            case ')':
                token = new Token(ClaseLexica.RPAR, ")");
                break;
            default:
                error(0);
                return null;
        }
        listaTokens.add(token);
        return token;
    }

    /**
     * Imprime el lexema final.
     */
    private void imprimirLexemaFinal() {
        System.out.println("\t\t\t\t     Lexema: '" + textoActual.trim() + "'\n");
        ultimoSaltoLinea = false;
    }

    /**
     * Obtiene el token correspondiente al estado actual.
     * 
     * @param estado Estado actual.
     * @return El token correspondiente al estado.
     */
    private Token obtenerToken(int estado) {
        ClaseLexica tipo;
        String lexema = textoActual.trim();

        switch (estado) {
            case 1: 
                tipo = revisarPalabraClave(lexema);
                break;
            case 2:
                tipo = ClaseLexica.NUMERO;
                break;
            case 4:
                tipo = ClaseLexica.FLOAT;
                break;
            case 5:
                tipo = ClaseLexica.PYC;
                break;
            case 6:
                tipo = ClaseLexica.ASIGNACION;
                break;
            case 7:
                tipo = ClaseLexica.SUMA;
                break;
            case 8:
                tipo = ClaseLexica.RESTA;
                break;
            case 9:
                tipo = ClaseLexica.LLLA;
                break;
            case 10:
                tipo = ClaseLexica.RLLA;
                break;
            case 11:
                tipo = ClaseLexica.IGUALDAD;
                break;
            case 12:
                tipo = ClaseLexica.LPAR;
                break;
            case 13:
                tipo = ClaseLexica.RPAR;
                break;
            default:
                error(estado);
                return null;
        }

        Token token = new Token(tipo, lexema);
        listaTokens.add(token);
        return token;
    }

    /**
     * Revisa si un lexema es una palabra clave.
     * 
     * @param lexema Lexema a revisar.
     * @return La clase léxica correspondiente a la palabra clave o identificador.
     */
    private ClaseLexica revisarPalabraClave(String lexema) {
        switch (lexema) {
            case "if":
                return ClaseLexica.IF;
            case "else":
                return ClaseLexica.ELSE;
            case "float":
                return ClaseLexica.FLOAT;
            case "int":
                return ClaseLexica.INT;
            default:
                return ClaseLexica.ID;
        }
    }

    /**
     * Muestra un mensaje de error para un estado no aceptado.
     * 
     * @param estado Estado no aceptado.
     */
    private void error(int estado) {
        System.err.println("Error en el estado: " + estado + " con lexema: " + textoActual);
    }

    /**
     * Inicializa la tabla de transiciones de estados.
     */
    private void inicializarTabla() {
        for (char c = 'a'; c <= 'z'; c++) {
            tabla[0][c] = tabla[1][c] = 1; // Identificadores
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            tabla[0][c] = tabla[1][c] = 1;
        }
        for (char c = '0'; c <= '9'; c++) {
            tabla[0][c] = tabla[2][c] = 2; // Números
            tabla[1][c] = 1; 
            tabla[4][c] = 4; 
        }
        tabla[2]['.'] = 3; // Punto para floats
        for (char c = '0'; c <= '9'; c++) {
            tabla[3][c] = 4; 
        }
        tabla[4]['e'] = 5; // e/E para notación científica
        tabla[4]['E'] = 5;
        for (char c = '0'; c <= '9'; c++) {
            tabla[5][c] = 4;
        }

        // Operadores y caracteres especiales
        tabla[0]['='] = 6;
        tabla[6]['='] = 11;
        tabla[0]['+'] = 7;
        tabla[0]['-'] = 8;
        tabla[0][';'] = 5;
        tabla[0]['{'] = 9;
        tabla[0]['}'] = 10;
        tabla[0]['('] = 12;
        tabla[0][')'] = 13;

        // Estados de aceptación
        estadosAceptacion[1] = true; // Identificadores
        estadosAceptacion[2] = true; // Enteros
        estadosAceptacion[4] = true; // Decimales
        estadosAceptacion[5] = true; // ;
        estadosAceptacion[6] = true; // =
        estadosAceptacion[7] = true; // +
        estadosAceptacion[8] = true; // -
        estadosAceptacion[9] = true; // {
        estadosAceptacion[10] = true; // }
        estadosAceptacion[11] = true; // ==
        estadosAceptacion[12] = true; // (
        estadosAceptacion[13] = true; // )
    }

    /**
     * Método principal para ejecutar el análisis léxico.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        Lexer analizador = new Lexer();
        analizador.inicializarTabla();

        try {
            analizador.abrirArchivo("input.txt");
            System.out.println("--- Análisis Léxico Iniciado ---\n");

            while (analizador.analizarLexema() != null) {
            }

            analizador.cerrarArchivo();
            System.out.println("--- Análisis Léxico Finalizado ---\n");

        } catch (IOException e) {
            System.err.println("Error al abrir o cerrar el archivo: " + e.getMessage());
        }

        System.out.println("\n--- Análisis de Tokens Iniciado ---\n");

        for (Token token : analizador.listaTokens) {
            System.out
                    .println("<" + token.getTipo() + (token.getLexema() != null ? ", " + token.getLexema() : "") + ">");
        }

        System.out.println("\n--- Análisis de Tokens Finalizado ---");
    }
}