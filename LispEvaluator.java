import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//Clase Principal
public class LispEvaluator {
    public static void main(String[] args) {

        //Objeto Bufferr  
       try (BufferedReader br = new BufferedReader(new FileReader("operacion.lisp"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            //construir cadena con la expresión de lisp.
            String lispExpression = sb.toString();
            System.out.println("Expresión Lisp: " + lispExpression);

            //convertir la cadena y efectuar la operación.
            List<Character> tokens = convertToListOfCharacters(lispExpression);
            double result = evaluateExpression(tokens);
            System.out.println("Resultado: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Método para convertir cadenas en tokens
    private static List<Character> convertToListOfCharacters(String expression) {
        List<Character> tokens = new ArrayList<>();
        for (char c : expression.toCharArray()) {
            if (c != ' ') {
                tokens.add(c);
            }
        }
        return tokens;
    }

    private static double evaluateExpression(List<Character> tokens) {
        Stack<Double> stack = new Stack<>(); //pila 

        //ciclo For
        for (char token : tokens) {
            if (token == '(') {
                // Omitir cuando encontramos un paréntesis abierto
            } else if (token == ')') {
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                char operator = (char) stack.pop().intValue();
                double result = applyOperator(operator, operand1, operand2);
                stack.push(result);
            } else if (isNumeric(token)) {
                stack.push((double) (token - '0')); // Convertir el char en un número
            } else if (isOperator(token)) {
                stack.push((double) token); // Operadores se guardan como números
            }
        }

        return stack.pop();
    }

    private static double applyOperator(char operator, double operand1, double operand2) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Operador desconocido: " + operator);
        }
    }

    private static boolean isNumeric(char c) {
        return Character.isDigit(c);
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
