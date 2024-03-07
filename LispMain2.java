import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LispMain2 {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java LispCompiler <ruta_al_archivo>");
            return;
        }

        String filePath = args[0];

        try {
            String expression = FileHelper.readFile2(filePath);
            double result = Evaluator.evaluatePrefixExpression(expression);
            System.out.println("Resultado: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

