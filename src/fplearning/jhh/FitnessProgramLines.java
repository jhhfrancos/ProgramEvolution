package fplearning.jhh;

import fplearning.interpreter.Evaluator;
import fplearning.interpreter.GoalException;
import fplearning.interpreter.ProgramException;
import fplearning.language.LexicalException;
import fplearning.language.SyntacticalException;
import unalcol.optimization.OptimizationFunction;

public class FitnessProgramLines extends OptimizationFunction<ProgramTree> {

    @Override
    public Double apply(ProgramTree p) {
        
        double fitness = 0;
        
        for (int i = 0; i < p.getProgram_list().size(); i++){
                int count = p.getProgram_list().get(i).depth();
                if (count > 5)
                    return -100.0;
        }
        
        if(p.getProgram_list().size() > 3) fitness -= 1;
        
        try {
            String[][] examples
                    = {
                        {"max(0,0)", "0"},    //1
                        {"max(1,1)", "1"},    //
                        {"max(0,8)", "8"},    //2
                        {"max(8,0)", "8"},    //3
                        {"max(5,8)", "8"},    //4
                        {"max(8,5)", "8"},    //5
                        {"max(5,10)", "10"},  //5
                        {"max(10,5)", "10"},  //6
                    };

            for (String[] example : examples) {
                if ((Evaluator.evalue(p.getProgram_string(), example[0])).equals(example[1]))
                    fitness += 1;
            }

        } catch (ProgramException | GoalException | LexicalException | StackOverflowError | SyntacticalException ex) {
            fitness = -3;
        }

        return fitness;
    }

    public static void main(String[] args) {
        String[] functor
                = {
                    "max", "s"
                };
        int[] arityFun
                = {
                    2, 1
                };
        String[] terminal
                = {
                    "0", "X", "Y"
                };

        FitnessProgramLines fp = new FitnessProgramLines();

        Generator g = new Generator(functor, arityFun, terminal, 3, 10);

        ProgramTree temp_program = g.generateValidProgram();

        System.out.println("Fitness: " + fp.apply(temp_program));
        System.out.println(temp_program.getProgram_string());
    }
}
