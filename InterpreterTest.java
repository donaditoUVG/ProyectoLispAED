import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTest extends Interpreter {

    @Test // Suma básica
    public void testOp_add() throws Exception {
        Interpreter interpreter = new Interpreter();
        int result = interpreter.Op_add("(+ 5 5)");
        assertEquals(10, result);
    }

    @Test // Suma con variable
    public void testOp_add_Variable() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.Op_setq("setq x 3");
        int result = interpreter.Op_add("(+ x 5)");
        assertEquals(8, result);
    }

    @Test // Suma anidada
    public void testOp_add_NestedExp() throws Exception {
        Interpreter interpreter = new Interpreter();
        int result = interpreter.Op_add("(+ (+ 5 3) 1)");
        assertEquals(9, result);
    }

    @Test // Resta básica
    public void testOp_subs() throws Exception {
        Interpreter interpreter = new Interpreter();
        int result = interpreter.Op_subs("(- 5 2)");
        assertEquals(3, result);
    }

    @Test // Resta con variable
    public void testOp_subs_Variable() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.Op_setq("setq x 3");
        int result = interpreter.Op_subs("(- x 1)");
        assertEquals(2, result);
    }

    @Test // Resta anidada
    public void testOp_subs_NestedExp() throws Exception {
        Interpreter interpreter = new Interpreter();
        int result = interpreter.Op_subs("(- (- 7 1) 3)");
        assertEquals(3, result);
    }

    @Test // Multiplicación básica
    public void testOp_mult() throws Exception {
        Interpreter interpreter = new Interpreter();
        int result = interpreter.Op_mult("(* 5 2)");
        assertEquals(10, result);
    }

    @Test // Multiplicación con variable
    public void testOp_mult_Variable() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.Op_setq("setq x 3");
        int result = interpreter.Op_mult("(* x 0)");
        assertEquals(0, result);
    }

    @Test // Multiplicación anidada
    public void testOp_mult_NestedExp() throws Exception {
        Interpreter interpreter = new Interpreter();
        int result = interpreter.Op_mult("(* (* 7 2) 1)");
        assertEquals(14, result);
    }

    @Test // División básica
    public void testOp_div() throws Exception {
        Interpreter interpreter = new Interpreter();
        int result = interpreter.Op_div("(/ 10 2)");
        assertEquals(5, result);
    }

    @Test // División con variable
    public void testOp_div_Variable() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.Op_setq("setq x 9");
        int result = interpreter.Op_div("(/ x 3)");
        assertEquals(3, result);
    }

    @Test // División anidada
    public void testOp_div_NestedExp() throws Exception {
        Interpreter interpreter = new Interpreter();
        int result = interpreter.Op_div("(/ (/ 90 10) 3)");
        assertEquals(3, result);
    }

    @Test // Asignación simple
    public void testOp_setq() throws Exception {
        Interpreter interpreter = new Interpreter();
        int result = interpreter.Op_setq("setq x 10");
        assertEquals(10, result);
        int actualValue = interpreter.myVars.get("x");
        assertEquals(10, actualValue);
    }

}
