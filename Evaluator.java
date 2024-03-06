import java.util.Stack;

/**
 * @author Cracks
 */
public class Evaluator {
    public static double evaluatePrefixExpression(String expression) {

        //Separar la cadena en elementos (tokens) y guardarlo en el arregl tokens.
        String[] tokens = expression.split(" ");

        Stack<Double> stack = new Stack<>(); // Creación de Pila

        //Iteración desde el token de la derecha hacia la izquierda)
        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
<<<<<<< HEAD
            System.out.println(token);
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else {
=======
            if (!isNumeric(token)) {
>>>>>>> 0bb3cda40ab60ed977bcdfcd02af0cb74ce5b6b0
                double operand1 = stack.pop();
                double operand2 = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(operand1 + operand2);
                        System.out.println("La suma es de: ");
                        break;
                    case "-":
                        stack.push(operand1 - operand2);
                        System.out.println("Resta");
                        break;
                    case "*":
                        stack.push(operand1 * operand2);
                        break;
                    case "/":
                        stack.push(operand1 / operand2);
                        break;
                }
                
            } else {
                 stack.push(Double.parseDouble(token)); //Convertir operando a tipo Double
            }
        }
        return stack.pop(); //Devolver el resultado de la expresión
    }


    //Método Auxiliar   
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
