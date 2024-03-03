import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/** Clase principal que se encarga de la lectura del archivo y la invocación del evaluador para expresiones */
public class LispCompiler {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java LispCompiler <ruta_al_archivo>");
            return;
        }
        
        String filePath = args[0];
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String expression = br.readLine();
            double result = Evaluator.evaluatePrefixExpression(expression);
            System.out.println("Resultado: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
