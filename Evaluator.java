import java.util.Stack;

/**
 * Evaluator Class: Permite la evaluación de la expresión en formato prefijo.
 */

public class Evaluator {
    public static double evaluatePrefixExpression(String expression) {
        String[] tokens = expression.split(" ");
        Stack<Double> stack = new Stack<>();

        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double operand1 = stack.pop();
                double operand2 = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(operand1 + operand2);
                        break;
                    case "-":
                        stack.push(operand1 - operand2);
                        break;
                    case "*":
                        stack.push(operand1 * operand2);
                        break;
                    case "/":
                        stack.push(operand1 / operand2);
                        break;
                }
            }
        }
        return stack.pop();
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
