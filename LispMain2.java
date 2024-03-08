import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LispMain2 {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java LispCompiler <ruta_al_archivo>");
            return;
        }

        String filePath = args[0];

        Interpreter miInter = new Interpreter();

        try {
            List<String> expressions = FileHelper.readFile2(filePath);

            for(String expression : expressions){
                System.out.println(expression);
                double result = miInter.Operate(expression);
                System.out.println("Resultado: " + result);

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

