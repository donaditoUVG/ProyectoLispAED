import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxScanner {
    public static int getState(String expresion){
        //System.out.println(expresion);
        if      (evaluate("^[(][+][ ]?(?:\\([^)]+\\)[ ]*|[^\\)]+[ ]*)*[)]$",expresion)) //This is a simple add operation of 2 operands
            return 2;
        else if (evaluate("^[(][-][ ]?(?:\\([^)]+\\)[ ]*|[^\\)]+[ ]*)*[)]$",expresion)) //This is a simple add operation of 2 operands
            return 3;
        else if (evaluate("^[(][*][ ]?(?:\\([^)]+\\)[ ]*|[^\\)]+[ ]*)*[)]$",expresion)) //This is a simple add operation of 2 operands
            return 4;
        else if (evaluate("^[(][/][ ]?(?:\\([^)]+\\)[ ]*|[^\\)]+[ ]*)*[)]$",expresion)) //This is a simple add operation of 2 operands
            return 5;
        else if (evaluate("^[(]setq[ ]+[a-z]+[ ]+[0-9]+|(?:\\([^)]+\\))[)]$",expresion)) //This is a simple assignment using setq
            return 1;
        else if (evaluate("^[(]atom[ ]+[a-z]+|[0-9]+|(?:\\([^)]+\\))[)]$",expresion)) //This is a simple assignment using setq
            return 6;
        else
            return -1; //if no match found then the expression is incorrect
    }

    private static boolean evaluate(String regex, String expresion) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expresion);
        return matcher.find();
    }

}
