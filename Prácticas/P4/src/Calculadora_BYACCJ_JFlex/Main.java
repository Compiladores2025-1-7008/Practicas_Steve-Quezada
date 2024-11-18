import java.io.*;

/**
 * Clase principal de la calculadora.
 * Implementa una calculadora de expresiones matemáticas con soporte para
 * operaciones básicas, potencias y paréntesis.
 * 
 * @author steve-quezada
 */
public class Main {
    private static boolean ejecutando = true;

    /**
     * Punto de entrada principal del programa.
     * Muestra el menú de la calculadora y procesa la entrada del usuario.
     *
     * @param args argumentos de línea de comando (no utilizados)
     */
    public static void main(String[] args) {
        mostrarMenu();
        procesarEntradas();
    }

    /**
     * Procesa las entradas del usuario de forma interactiva.
     * Lee expresiones matemáticas, las evalúa y muestra los resultados.
     * El programa termina cuando el usuario ingresa 'q'.
     */
    private static void procesarEntradas() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (ejecutando) {
            System.out.print("\nExpresión > ");
            try {
                String expresion = in.readLine();
                if (expresion == null || expresion.isEmpty()) {
                    System.out.println("Ingrese una expresión válida");
                    continue;
                }

                if (expresion.equals("q")) {
                    System.out.println("¡Hasta luego!");
                    ejecutando = false;
                    continue;
                }

                Parser parser = new Parser(new StringReader(expresion + "\n"));
                parser.setExpression(expresion);
                parser.yyparse();
            } catch (Exception e) {
                System.err.println("Error al procesar la entrada: " + e.getMessage());
            }
        }
    }

    /**
     * Muestra el menú principal con las instrucciones y operaciones disponibles.
     * Lista todas las operaciones soportadas.
     */
    private static void mostrarMenu() {
        System.out.println("===== Calculadora =====");
        System.out.println("\nOperaciones soportadas:");
        System.out.println("   +  : Suma");
        System.out.println("   -  : Resta");
        System.out.println("   *  : Multiplicación");
        System.out.println("   /  : División");
        System.out.println("   ^  : Potencia");
        System.out.println("  ( ) : Paréntesis");
        System.out.println("\nPara salir presione 'q'");
        System.out.print("=======================");
    }
}
