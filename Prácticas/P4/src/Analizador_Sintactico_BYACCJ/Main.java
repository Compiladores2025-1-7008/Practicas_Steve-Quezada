package src.Analizador_Sintactico_BYACCJ;

import java.io.FileReader;

/**
 * Clase principal que inicia el análisis sintáctico.
 * 
 * Esta clase contiene el método principal que se encarga de iniciar el proceso
 * de análisis sintáctico utilizando un analizador léxico y un analizador sintáctico.
 * 
 * @author steve-quezada
 */
public class Main {
    /**
     * Método principal que inicia el análisis sintáctico.
     * 
     * @param args Argumentos de la línea de comandos. Se espera un único argumento
     *             que es el nombre del archivo de entrada.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: java Main <archivo_entrada>");
            System.exit(1);
        }

        Colors.println("Analizador Sintáctico BYACCJ + JFlex", Colors.HIGH_INTENSITY);
        Colors.println("Archivo de Entrada: " + args[0], Colors.HIGH_INTENSITY);

        try {
            Parser parser = new Parser(new FileReader(args[0]));
            parser.yyparse();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}