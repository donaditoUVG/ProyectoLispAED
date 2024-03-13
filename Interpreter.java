import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {

    protected HashMap<String, Integer> myVars;

    public Interpreter() {
        myVars = new HashMap<String, Integer>();
    }

    public Integer Operate(String expression) throws Exception {
        int state = SyntaxScanner.getState(expression);

        // System.out.println(state);

        switch (state) {
            case 1:
                return Op_setq(expression);
            case 2:
                return Op_add(expression);
            case 3:
                return Op_subs(expression);
            case 4:
                return Op_mult(expression);
            case 5:
                return Op_div(expression);
        }
        System.out.println("Error de reconocimiento");
        return null;
    }

    protected Integer Op_add(String expression) throws Exception {
        expression = expression.substring(1, expression.length() - 1);
        Pattern pattern = Pattern.compile("([a-z]+|[-]?[0-9]+|\\((?:(?!\\)).)*\\))", Pattern.CASE_INSENSITIVE); //
        Matcher matcher = pattern.matcher(expression);
        int total = 0;

        while (matcher.find()) {
            String parameter = matcher.group().trim();
            // System.out.println("par: "+parameter);

            if (parameter.matches("[-]?[0-9]+")) {
                // Es un número
                int num = Integer.parseInt(matcher.group().trim());
                total += num;
            } else if (parameter.matches("[a-z]+")) {
                if (myVars.get(parameter) != null) {
                    int num = myVars.get(parameter);
                    total += num;
                } else {
                    throw new Exception("Variable \"" + parameter + "\" no encontrada");
                }
                // Es variable
            } else {
                // Si no es ninguno entonces es una expresión
                int num = Operate(parameter);
                total += num;
            }
        }

        return total;
    }

    protected Integer Op_subs(String expression) throws Exception {
        expression = expression.substring(1, expression.length() - 1);
        Pattern pattern = Pattern.compile("([a-z]+|[-]?[0-9]+|\\((?:(?!\\)).)*\\))", Pattern.CASE_INSENSITIVE); //
        Matcher matcher = pattern.matcher(expression);
        int total = 0;
        boolean loop = false;

        while (matcher.find()) {
            String parameter = matcher.group().trim();
            // System.out.println("par: "+parameter);
            if (!loop) {
                if (parameter.matches("[-]?[0-9]+")) {
                    // Es un número
                    int num = Integer.parseInt(matcher.group().trim());
                    total = num;
                } else if (parameter.matches("[a-z]+")) {
                    if (myVars.get(parameter) != null) {
                        int num = myVars.get(parameter);
                        total = num;
                    } else {
                        throw new Exception("Variable \"" + parameter + "\" no encontrada");
                    }
                    // Es variable
                } else {
                    // Si no es ninguno entonces es una expresión
                    int num = Operate(parameter);
                    total = num;
                }
                loop = true;
            } else {
                if (parameter.matches("[-]?[0-9]+")) {
                    // Es un número
                    int num = Integer.parseInt(matcher.group().trim());
                    total -= num;
                } else if (parameter.matches("[a-z]+")) {
                    if (myVars.get(parameter) != null) {
                        int num = myVars.get(parameter);
                        total -= num;
                    } else {
                        throw new Exception("Variable \"" + parameter + "\" no encontrada");
                    }
                    // Es variable
                } else {
                    // Si no es ninguno entonces es una expresión
                    int num = Operate(parameter);
                    total -= num;
                }
            }
        }

        return total;
    }

    protected Integer Op_mult(String expression) throws Exception {
        expression = expression.substring(1, expression.length() - 1);
        Pattern pattern = Pattern.compile("([a-z]+|[-]?[0-9]+|\\((?:(?!\\)).)*\\))", Pattern.CASE_INSENSITIVE); //
        Matcher matcher = pattern.matcher(expression);
        int total = 1;

        while (matcher.find()) {
            String parameter = matcher.group().trim();
            // System.out.println("par: "+parameter);

            if (parameter.matches("[-]?[0-9]+")) {
                // Es un número
                int num = Integer.parseInt(matcher.group().trim());
                total *= num;
            } else if (parameter.matches("[a-z]+")) {
                if (myVars.get(parameter) != null) {
                    int num = myVars.get(parameter);
                    total *= num;
                } else {
                    throw new Exception("Variable \"" + parameter + "\" no encontrada");
                }
                // Es variable
            } else {
                // Si no es ninguno entonces es una expresión
                int num = Operate(parameter);
                total *= num;
            }
        }

        return total;
    }

    protected Integer Op_div(String expression) throws Exception {
        expression = expression.substring(1, expression.length() - 1);
        Pattern pattern = Pattern.compile("([a-z]+|[-]?[0-9]+|\\((?:(?!\\)).)*\\))", Pattern.CASE_INSENSITIVE); //
        Matcher matcher = pattern.matcher(expression);
        int total = 1;
        boolean loop = false;

        while (matcher.find()) {
            String parameter = matcher.group().trim();
            // System.out.println("par: "+parameter);
            if (!loop) {
                if (parameter.matches("[-]?[0-9]+")) {
                    // Es un número
                    int num = Integer.parseInt(matcher.group().trim());
                    total = num;
                } else if (parameter.matches("[a-z]+")) {
                    if (myVars.get(parameter) != null) {
                        int num = myVars.get(parameter);
                        total = num;
                    } else {
                        throw new Exception("Variable \"" + parameter + "\" no encontrada");
                    }
                    // Es variable
                } else {
                    // Si no es ninguno entonces es una expresión
                    int num = Operate(parameter);
                    total = num;
                }
                loop = true;
            } else {
                if (parameter.matches("[-]?[0-9]+")) {
                    // Es un número
                    int num = Integer.parseInt(matcher.group().trim());
                    total /= num;
                } else if (parameter.matches("[a-z]+")) {
                    if (myVars.get(parameter) != null) {
                        int num = myVars.get(parameter);
                        total /= num;
                    } else {
                        throw new Exception("Variable \"" + parameter + "\" no encontrada");
                    }
                    // Es variable
                } else {
                    // Si no es ninguno entonces es una expresión
                    int num = Operate(parameter);
                    total /= num;
                }
            }
        }

        return total;
    }

    protected Integer Op_setq(String expression) throws Exception {
        Pattern pattern = Pattern.compile("[ ]+[a-z]+[ ]+", Pattern.CASE_INSENSITIVE); //
        Matcher matcher = pattern.matcher(expression);
        String varName = null;
        Integer varValue = null;
        if (matcher.find()) {
            varName = matcher.group().trim();
        }

        pattern = Pattern.compile("[ ]+[-]?[0-9]+[ ]*", Pattern.CASE_INSENSITIVE); //
        matcher = pattern.matcher(expression);
        if (matcher.find()) {
            varValue = Integer.parseInt(matcher.group().trim());
        }

        pattern = Pattern.compile("[ ]+\\((?:[^)]+|\\([^)]+\\))*\\)", Pattern.CASE_INSENSITIVE); //
        matcher = pattern.matcher(expression);
        if (matcher.find()) {
            varValue = Operate(matcher.group().trim());
        }

        if (varName != null && varValue != null) {
            myVars.put(varName, varValue);
            return varValue;
        } else {
            throw new Exception("Error de sintaxis en setq");
        }
    }
}
