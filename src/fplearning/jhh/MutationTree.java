package fplearning.jhh;

import unalcol.optimization.operators.ArityOne;
import unalcol.types.collection.vector.Vector;

public class MutationTree extends ArityOne<ProgramTree>
{
    @Override
    public Vector<ProgramTree> generates(ProgramTree p)
    {
        Vector<ProgramTree> result = new Vector<>();

        Generator g = new Generator(p.functor, p.arityFun, p.terminal, 2, 4);
        
        ProgramTree child_1 = new ProgramTree(p);
        ProgramTree child_2 = g.generateValidProgram();

        int index1 = (int) (Math.random() * child_1.getProgram_list().size());
        Tree.Node point1 = child_1.getProgram_list().get(index1).getRandomNode();
        
        int index2 = (int) (Math.random() * child_2.getProgram_list().size());
        point1.replace(child_2.getProgram_list().get(index2).getRandomNode());

        result.add(child_1);

        return result;
    }

    @Override
    public Object owner()
    {
        return ProgramTree.class;
    }
    
    public static void main(String[] args)
    {
        String[] functor =
        {
            "max", "s"
        };
        int[] arityFun =
        {
            2, 1
        };
        String[] terminal =
        {
            "0", "X", "Y"
        };

        Generator g = new Generator(functor, arityFun, terminal, 5, 10);

        ProgramTree test1 = g.generateValidProgram();
       
        MutationTree mutation = new MutationTree();
        
        Vector<ProgramTree> result = mutation.generates(test1);

        System.out.println("Padre 1:\n" + test1.getProgram_string());
        System.out.println("Hijo  1:\n" + result.get(0).getProgram_string());
    }
}
