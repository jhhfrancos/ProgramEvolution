package fplearning.jhh;

import unalcol.optimization.operators.ArityTwo;
import unalcol.types.collection.vector.Vector;

public class XOverTree extends ArityTwo<ProgramTree>
{

    @Override
    public Vector<ProgramTree> generates(ProgramTree p, ProgramTree p1)
    {
        ProgramTree child_1 = new ProgramTree(p);
        ProgramTree child_2 = new ProgramTree(p1);

        int index1 = (int) (Math.random() * child_1.getProgram_list().size());
        Tree.Node point1 = child_1.getProgram_list().get(index1).getRandomNode();

        int index2 = (int) (Math.random() * child_2.getProgram_list().size());
        Tree.Node point2 = child_2.getProgram_list().get(index2).getRandomNode();

        Tree.Node temp1 = new Tree.Node(point1);
        Tree.Node temp2 = new Tree.Node(point2);
        
        point1.replace(temp2);
        point2.replace(temp1);

        Vector<ProgramTree> result = new Vector<>();

        result.add(child_1);
        result.add(child_2);

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
        ProgramTree test2 = g.generateValidProgram();

        XOverTree xover = new XOverTree();
        
        Vector<ProgramTree> result = xover.generates(test1, test2);

        System.out.println("Padre 1:\n" + test1.getProgram_string());
        System.out.println("Hijo  1:\n" + result.get(0).getProgram_string());
        System.out.println("Padre 2:\n" + test2.getProgram_string());
        System.out.println("Hijo  2:\n" + result.get(1).getProgram_string());
    }
}
