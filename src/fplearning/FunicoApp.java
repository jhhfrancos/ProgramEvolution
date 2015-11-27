package fplearning;

/*
 * FunicoApp.java
 * -Xms512m -Xmx1024m
 * -Xms32m -Xmx32m
 */
import fplearning.interpreter.Evaluator;
import fplearning.interpreter.GoalException;
import fplearning.interpreter.ProgramException;
import fplearning.language.LexicalException;
import fplearning.language.SyntacticalException;

/**
 * The main class of the application.
 */
public class FunicoApp {

    public FunicoApp() {
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        try {
            System.out.println(Evaluator.evalue(
                    "mod3(0) = 0; mod3(1) = 1; mod3(2) = 2; mod3(s(s(s(X)))) = mod3(X)",
                    "mod3(5)"));
            System.out.println(Evaluator.evalue(
                    "even(0) = true; even(1) = false; even(s(s(X))) = even(X)",
                    "even(5)"));
            System.out.println(Evaluator.evalue(
                    "sum(0,X) = X; sum(s(X),Y) = s(sum(X,Y))",
                    "sum(5,3)"));
            
            String[] functor = {"geq", "s"};
            int[] arityFun = {2, 1};
            String[] terminal = {"0", "X", "Y"};
        } catch (ProgramException | GoalException | LexicalException | SyntacticalException ex) {
            System.out.println(ex);
        }
    }
}