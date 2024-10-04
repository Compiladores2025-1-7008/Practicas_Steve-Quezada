package main.java;

import java.io.FileNotFoundException;
import java.io.FileReader;

import main.jflex.Lexer;
import main.java.Parser;
import main.java.Colors;

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

        Colors.println("Analizador Sintáctico de descenso recursivo.", Colors.HIGH_INTENSITY);
        Colors.println("Archivo de Entrada: " + args[0], Colors.HIGH_INTENSITY);

        try {
            Lexer lexer = new Lexer(new FileReader(args[0]));
            Parser parser = new Parser(lexer);
            parser.parse();
        } catch (FileNotFoundException fnfe) {
            System.err.println("Error: No fue posible leer del archivo de entrada: " + args[0]);
            System.exit(1);
        }
    }
}