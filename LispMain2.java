import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * LispMain2 lee un archivo de texto con las expresiones en Lisp y con un objeto de tipo
 * Interpreter evalua cada expresión e imprime el resultado.
 */
public class LispMain2 {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java LispCompiler <ruta_al_archivo>");
            //return;
        }

        String filePath = "datos.txt";

        Interpreter miInter = new Interpreter(); //Instancia de Interpreter

        try {
            List<String> expressions = FileHelper.readFile2(filePath);

            for(String expression : expressions){
                System.out.println(expression); //Reiteramos la expresión (pueen ser varias como se evidencia por el ciclo for)
                Integer result = miInter.Operate(expression); //Método Operate(Expression)
                System.out.println("Resultado: " + result); // Muestra el resultado de la operación

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

