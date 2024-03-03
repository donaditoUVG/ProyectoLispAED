import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Idea base de acuerdo con la sugerencia de Moisés: Procesar y Evaluar expresiones a través de la separación de Tokens y formación de la lista.
 */

public class LispInterpreter {
    
    public List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        
        // Definir la expresión regular para identificar los tokens
        String regex = "\\(|\\)|[^\\s\\(\\)]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);
        
        // Encontrar tokens y agregarlos a la lista
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        
        return tokens;
    }
    
    public List<Object> createListStructure(List<String> tokens) {
        List<Object> listStructure = new ArrayList<>();
        
        // Inicializamos un índice para recorrer los tokens
        int index = 0;
        
        // Llamamos al método auxiliar para construir la estructura de lista
        buildListStructure(tokens, index, listStructure);
        
        return listStructure;
    }
    
    private void buildListStructure(List<String> tokens, int index, List<Object> listStructure) {
        while (index < tokens.size()) {
            String token = tokens.get(index);
            
            if ("(".equals(token)) {
                // Comenzamos una nueva lista interna
                List<Object> sublist = new ArrayList<>();
                listStructure.add(sublist);
                
                // Avanzamos al siguiente token y llamamos recursivamente para construir la sublista
                buildListStructure(tokens, index + 1, sublist);
            } else if (")".equals(token)) {
                // Fin de la lista actual
                return;
            } else {
                // Agregamos el token a la lista actual
                listStructure.add(token);
            }
            
            index++; // Avanzamos al siguiente token
        }
    }
    
    public int evaluateExpression(List<Object> expression) {
        // Verificamos si la expresión es una lista (y por lo tanto una llamada a una función)
        if (expression.get(0) instanceof List) {
            // Si es una lista, evaluamos la función
            return evaluateFunctionCall((List<Object>) expression.get(0), expression.subList(1, expression.size()));
        } else {
            // Si es un valor, lo convertimos a entero y lo devolvemos
            return Integer.parseInt((String) expression.get(0));
        }
    }
    
    private int evaluateFunctionCall(List<Object> function, List<Object> arguments) {
        // Obtenemos el nombre de la función (primer elemento de la lista)
        String functionName = (String) function.get(0);
        
        // Evaluamos la función según su nombre
        switch (functionName) {
            case "+":
                return evaluateAddition(arguments);
            case "-":
                return evaluateSubtraction(arguments);
            case "*":
                return evaluateMultiplication(arguments);
            // Añade más casos para otras funciones Lisp
            default:
                throw new IllegalArgumentException("Función no reconocida: " + functionName);
        }
    }
    
    private int evaluateAddition(List<Object> arguments) {
        int result = 0;
        for (Object argument : arguments) {
            result += evaluateExpression((List<Object>) argument);
        }
        return result;
    }
    
    private int evaluateSubtraction(List<Object> arguments) {
        int result = evaluateExpression((List<Object>) arguments.get(0));
        for (int i = 1; i < arguments.size(); i++) {
            result -= evaluateExpression((List<Object>) arguments.get(i));
        }
        return result;
    }
    
    private int evaluateMultiplication(List<Object> arguments) {
        int result = 1;
        for (Object argument : arguments) {
            result *= evaluateExpression((List<Object>) argument);
        }
        return result;
    }

    public static void main(String[] args) {
        LispInterpreter interpreter = new LispInterpreter();
        String inputExpression = "(+ 2 (* 3 4))";

        List<String> tokens = interpreter.tokenize(inputExpression);
        List<Object> listStructure = interpreter.createListStructure(tokens);
        
        int result = interpreter.evaluateExpression(listStructure);
        System.out.println("Resultado de la expresión: " + result);
    }
}
