import java.io.FileNotFoundException;
import java.io.FileReader;
import main.byacc.Parser;

public class Main {
    public static void main(String args[])
    {
	try {
	    Parser parser = new Parser(new FileReader(args[0]));
	    System.out.println("Iniciando analisis sintactico.");
	    parser.parse();
	} catch (FileNotFoundException fnfe) {
	    System.err.println("Error: No fue posible leer del archivo de entrada: "+args[0]);
	    System.exit(1);
	}
    }
}
