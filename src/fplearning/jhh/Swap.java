package fplearning.jhh;

import unalcol.optimization.operators.ArityOne;
import unalcol.types.collection.vector.Vector;

public class Swap extends ArityOne<ProgramTree>
{

    @Override
    public Vector<ProgramTree> generates(ProgramTree p)
    {
        Vector<ProgramTree> result = new Vector<>();

        Generator g = new Generator(p.functor, p.arityFun, p.terminal, 3, 10);

        ProgramTree child_1 = new ProgramTree(p);

        int index1 = (int) (Math.random() * child_1.getProgram_list().size());
        Tree.Node point1 = child_1.getProgram_list().get(index1).getRandomNode();

        int index2 = (int) (Math.random() * child_1.getProgram_list().size());
        Tree.Node point2 = child_1.getProgram_list().get(index2).getRandomNode();
        
        Tree.Node temp1 = new Tree.Node(point1);
        Tree.Node temp2 = new Tree.Node(point2);
        
        point1.replace(temp2);
        point2.replace(temp1);

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

        Generator g = new Generator(functor, arityFun, terminal, 3, 10);

        ProgramTree test1 = g.generateValidProgram();

        //System.out.println(test1.functor[0]);
        Swap mutation = new Swap();

        Vector<ProgramTree> result = mutation.generates(test1);

        System.out.println("Padre 1:\n" + test1.getProgram_string());
        System.out.println("Hijo  1:\n" + result.get(0).getProgram_string());
    }
}
