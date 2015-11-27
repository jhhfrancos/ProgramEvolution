package fplearning.jhh;

import fplearning.interpreter.Evaluator;
import fplearning.interpreter.GoalException;
import fplearning.interpreter.ProgramException;
import fplearning.language.LexicalException;
import fplearning.language.SyntacticalException;
import unalcol.algorithm.iterative.ForLoopCondition;
import unalcol.evolution.GrowingFunction;
import unalcol.evolution.Individual;
import unalcol.evolution.IndividualInstance;
import unalcol.evolution.haea.HAEA;
import unalcol.evolution.haea.HaeaOperators;
import unalcol.evolution.haea.SimpleHaeaOperators;
import unalcol.instance.InstanceProvider;
import unalcol.instance.InstanceService;
import unalcol.io.WriteService;
import unalcol.math.logic.Predicate;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.PopulationOptimizer;
import unalcol.optimization.iterative.IterativePopulationOptimizer;
import unalcol.optimization.operators.ArityOne;
import unalcol.optimization.operators.ArityTwo;
import unalcol.optimization.operators.Operator;
import unalcol.optimization.selection.Selection;
import unalcol.optimization.selection.Tournament;
import unalcol.optimization.solution.Solution;
import unalcol.optimization.solution.SolutionInstance;
import unalcol.optimization.transformation.Transformation;
import unalcol.reflect.service.ServiceProvider;
import unalcol.reflect.util.ReflectUtil;
import unalcol.tracer.ConsoleTracer;
import unalcol.tracer.FileTracer;
import unalcol.tracer.Tracer;
import unalcol.tracer.TracerProvider;
import unalcol.types.collection.vector.Vector;
import unalcol.types.integer.array.IntArraySimplePersistent;
import unalcol.types.real.array.DoubleArraySimpleWriteService;

public class FPLearningHAEA
{

    public static void main(String[] args)
    {
        ProgramTree best[] =
        {
            null, null
        };

        int counter = 0;
        while (true)
        {
            best[0] = HAEA(best[0]);

            counter++;

            if (evaluate_(best[0]) == 6 || counter * 50 > 5000)
                break;
        }

        System.out.println("Iterations: " + counter * 50 + "\n" + best[0].getProgram_string());
    }

    public static ProgramTree HAEA(ProgramTree best)
    {
        // Reflection
        ServiceProvider provider = ReflectUtil.getProvider("services/");
        DoubleArraySimpleWriteService key = new DoubleArraySimpleWriteService(',');
        provider.register(key);
        provider.setDefault_service(WriteService.class, double[].class, key);

        IntArraySimplePersistent iskey = new IntArraySimplePersistent(',');
        provider.register(iskey);
        provider.setDefault_service(WriteService.class, int[].class, iskey);

        // Search Space 
        InstanceService ikey = new ProgramInstance();
        provider.register(ikey);
        provider.setDefault_service(InstanceService.class, ProgramTree.class, ikey);

        // Solution Space
        ProgramTree x = new ProgramTree();
        Solution<ProgramTree> solution = new Individual<>(x, x);
        GrowingFunction<ProgramTree, ProgramTree> grow = new GrowingFunction();
        SolutionInstance skey = new IndividualInstance(grow);
        provider.register(skey);
        provider.setDefault_service(InstanceService.class, Solution.class, skey);

        // Initial population
        int POPSIZE = 72; //TODO
        Vector<Solution<ProgramTree>> pop = InstanceProvider.get(solution, POPSIZE);

        if (best != null)
            pop.set(0, new Individual<ProgramTree, ProgramTree>(best, best));

        // Function being optimized
        OptimizationFunction function = new FitnessProgramLines();

        // Evaluating the fitness of the initial population
        Solution.evaluate((Vector) pop, function);

        ArityOne mutation = new MutationTree();
        ArityTwo xover = new XOverTree();
        ArityTwo xover_lines = new XOverLines();
        ArityOne swap = new Swap();

        // Genetic operators
        Operator[] opers = new Operator[]
        {
            xover, mutation, swap, xover_lines
        };

        HaeaOperators haeaOperators = new SimpleHaeaOperators(opers);

        // Extra parent selection mechanism
        Selection selection = new Tournament(4);

        // Genetic Algorithm Transformation
        Transformation transformation = new HAEA(haeaOperators, grow, selection);

        // Evolution generations
        int MAXITER = 60; //TODO
        Predicate condition = new ForLoopCondition(MAXITER);

        // Evolutionary algorithm (is a population optimizer)
        PopulationOptimizer ea = new IterativePopulationOptimizer(condition,
                transformation, pop);

        boolean tracing = true;
        if (tracing)
        {
            // A console set tracer
            Tracer tracer = new ConsoleTracer(ea);
            // Adding the tracer collection to the given population optimizer (evolutionary algorithm)
            provider.register(tracer);
            tracer = new FileTracer(ea, "output.txt", true);
            provider.register(tracer);
        }

        // Running the population optimizer (the evolutionary algorithm)
        pop = (Vector<Solution<ProgramTree>>) ea.apply(function);
        Tracer tracer = new ConsoleTracer(pop.get(0).get());

        System.out.println("-------------------------------------\n" + pop.get(0).get().getProgram_string());
        for (int i = 0; i < pop.size(); i++)
            if (evaluate_(pop.get(i).get()) == 8)
                System.out.println("-------------------------------------\n" + pop.get(i).get().getProgram_string());
        TracerProvider.close(ea);

        return pop.get(0).get();
    }

    public static int evaluate_(ProgramTree p)
    {
        int fitness = 0;
        try
        {
            String[][] examples =
            {
                {
                    "max(0,0)", "0"             // 1
                },
                {
                    "max(1,1)", "1"             // 2
                },
                {
                    "max(0,10)", "10"           // 3
                },
                //                {
                //                    "max(4,10)", "10"           // 4
                //                },
                {
                    "max(5,10)", "10"           // 5
                },
                {
                    "max(10,0)", "10"           // 6 
                },
                //                {
                //                    "max(10,4)", "10"           // 7
                //                },
                {
                    "max(10,5)", "10"           // 8
                },
            };

            for (String[] example : examples)
                if ((Evaluator.evalue(p.getProgram_string(), example[0])).equals(example[1]))
                    fitness++;
        } catch (ProgramException | GoalException | LexicalException | SyntacticalException ex)
        {
        }

        return fitness;
    }
}