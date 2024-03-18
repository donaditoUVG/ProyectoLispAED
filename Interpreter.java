import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {

    protected HashMap<String, Integer> myVars;

    public Interpreter() {
        myVars = new HashMap<>();
    }

    public Integer Operate(String expression) throws Exception {
        int state = SyntaxScanner.getState(expression);

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
            case 6:
                return Op_atom(expression);
            case 7:
                return Op_list(expression);
            case 8:
                return Op_equal(expression);
            case 9:
                return Op_less(expression);
            case 10:
                return Op_greater(expression);
        }
        System.out.println("Error de reconocimiento");
        return null;
    }

    private Integer Op_setq(String expression) throws Exception {
        Pattern pattern = Pattern.compile("[a-z]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expression);
        String varName = null;
        Integer varValue = null;
        if (matcher.find()) {
            varName = matcher.group().trim();
        }

        pattern = Pattern.compile("[-]?[0-9]+", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(expression);
        if (matcher.find()) {
            varValue = Integer.parseInt(matcher.group().trim());
        }

        if (varName != null && varValue != null) {
            myVars.put(varName, varValue);
            return varValue;
        } else {
            throw new Exception("Error de sintaxis en setq");
        }
    }

    private Integer Op_add(String expression) throws Exception {
        expression = expression.substring(1, expression.length() - 1);
        Pattern pattern = Pattern.compile("([a-z]+|[-]?[0-9]+|\\((?:(?!\\)).)*\\))", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expression);
        int total = 0;

        while (matcher.find()) {
            String parameter = matcher.group().trim();

            if (parameter.matches("[-]?[0-9]+")) {
                int num = Integer.parseInt(matcher.group().trim());
                total += num;
            } else if (parameter.matches("[a-z]+")) {
                if (myVars.get(parameter) != null) {
                    int num = myVars.get(parameter);
                    total += num;
                } else {
                    throw new Exception("Variable \"" + parameter + "\" no encontrada");
                }
            } else {
                int num = Operate(parameter);
                total += num;
            }
        }

        return total;
    }

    private Integer Op_subs(String expression) throws Exception {
        expression = expression.substring(1, expression.length() - 1);
        Pattern pattern = Pattern.compile("([a-z]+|[-]?[0-9]+|\\((?:(?!\\)).)*\\))", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expression);
        int total = 0;
        boolean loop = false;

        while (matcher.find()) {
            String parameter = matcher.group().trim();
            if (!loop) {
                if (parameter.matches("[-]?[0-9]+")) {
                    int num = Integer.parseInt(matcher.group().trim());
                    total = num;
                } else if (parameter.matches("[a-z]+")) {
                    if (myVars.get(parameter) != null) {
                        int num = myVars.get(parameter);
                        total = num;
                    } else {
                        throw new Exception("Variable \"" + parameter + "\" no encontrada");
                    }
                } else {
                    int num = Operate(parameter);
                    total = num;
                }
                loop = true;
            } else {
                if (parameter.matches("[-]?[0-9]+")) {
                    int num = Integer.parseInt(matcher.group().trim());
                    total -= num;
                } else if (parameter.matches("[a-z]+")) {
                    if (myVars.get(parameter) != null) {
                        int num = myVars.get(parameter);
                        total -= num;
                    } else {
                        throw new Exception("Variable \"" + parameter + "\" no encontrada");
                    }
                } else {
                    int num = Operate(parameter);
                    total -= num;
                }
            }
        }

        return total;
    }

    private Integer Op_mult(String expression) throws Exception {
        expression = expression.substring(1, expression.length() - 1);
        Pattern pattern = Pattern.compile("([a-z]+|[-]?[0-9]+|\\((?:(?!\\)).)*\\))", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expression);
        int total = 1;

        while (matcher.find()) {
            String parameter = matcher.group().trim();

            if (parameter.matches("[-]?[0-9]+")) {
                int num = Integer.parseInt(matcher.group().trim());
                total *= num;
            } else if (parameter.matches("[a-z]+")) {
                if (myVars.get(parameter) != null) {
                    int num = myVars.get(parameter);
                    total *= num;
                } else {
                    throw new Exception("Variable \"" + parameter + "\" no encontrada");
                }
            } else {
                int num = Operate(parameter);
                total *= num;
            }
        }

        return total;
    }

    private Integer Op_div(String expression) throws Exception {
        expression = expression.substring(1, expression.length() - 1);
        Pattern pattern = Pattern.compile("([a-z]+|[-]?[0-9]+|\\((?:(?!\\)).)*\\))", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expression);
        double total = 0;
        boolean loop = false;

        while (matcher.find()) {
            String parameter = matcher.group().trim();
            if (!loop) {
                if (parameter.matches("[-]?[0-9]+")) {
                    total = Double.parseDouble(matcher.group().trim());
                } else if (parameter.matches("[a-z]+")) {
                    if (myVars.get(parameter) != null) {
                        total = myVars.get(parameter);
                    } else {
                        throw new Exception("Variable \"" + parameter + "\" no encontrada");
                    }
                } else {
                    total = Operate(parameter);
                }
                loop = true;
            } else {
                if (parameter.matches("[-]?[0-9]+")) {
                    double num = Double.parseDouble(matcher.group().trim());
                    if (num == 0) {
                        throw new Exception("División por cero");
                    }
                    total /= num;
                } else if (parameter.matches("[a-z]+")) {
                    if (myVars.get(parameter) != null) {
                        double num = myVars.get(parameter);
                        if (num == 0) {
                            throw new Exception("División por cero");
                        }
                        total /= num;
                    } else {
                        throw new Exception("Variable \"" + parameter + "\" no encontrada");
                    }
                } else {
                    double num = Operate(parameter);
                    if (num == 0) {
                        throw new Exception("División por cero");
                    }
                    total /= num;
                }
            }
        }

        return (int) total;
    }

    private Integer Op_atom(String expression) {
        expression = expression.substring(5, expression.length() - 1).trim();
        if (expression.contains("list")) {
            return 0;
        } else {
            return 1;
        }
    }

    private Integer Op_list(String expression) {
        expression = expression.substring(6, expression.length() - 1).trim();
        if (expression.startsWith("(") && expression.endsWith(")")) {
            return 1;
        } else {
            return 0;
        }
    }

    private Integer Op_equal(String expression) throws Exception {
        expression = expression.substring(7, expression.length() - 1).trim();
        String[] parameters = expression.split("\\s+");
        if (parameters.length != 2) {
            throw new Exception("El predicado EQUAL necesita dos argumentos.");
        }

        if (parameters[0].matches("[a-z]+") && parameters[1].matches("[a-z]+")) {
            int value1 = myVars.getOrDefault(parameters[0], Integer.MIN_VALUE);
            int value2 = myVars.getOrDefault(parameters[1], Integer.MIN_VALUE);
            return value1 == value2 ? 1 : 0;
        } else if (parameters[0].matches("[a-z]+")) {
            int value = myVars.getOrDefault(parameters[0], Integer.MIN_VALUE);
            int num = Integer.parseInt(parameters[1]);
            return value == num ? 1 : 0;
        } else if (parameters[1].matches("[a-z]+")) {
            int value = myVars.getOrDefault(parameters[1], Integer.MIN_VALUE);
            int num = Integer.parseInt(parameters[0]);
            return value == num ? 1 : 0;
        } else {
            int num1 = Integer.parseInt(parameters[0]);
            int num2 = Integer.parseInt(parameters[1]);
            return num1 == num2 ? 1 : 0;
        }
    }

    private Integer Op_less(String expression) throws Exception {
        expression = expression.substring(6, expression.length() - 1).trim();
        String[] parameters = expression.split("\\s+");
        if (parameters.length != 2) {
            throw new Exception("El predicado LESS necesita dos argumentos.");
        }

        int num1 = Integer.parseInt(parameters[0]);
        int num2 = Integer.parseInt(parameters[1]);
        return num1 < num2 ? 1 : 0;
    }

    private Integer Op_greater(String expression) throws Exception {
        expression = expression.substring(9, expression.length() - 1).trim();
        String[] parameters = expression.split("\\s+");
        if (parameters.length != 2) {
            throw new Exception("El predicado GREATER necesita dos argumentos.");
        }

        int num1 = Integer.parseInt(parameters[0]);
        int num2 = Integer.parseInt(parameters[1]);
        return num1 > num2 ? 1 : 0;
    }
}
