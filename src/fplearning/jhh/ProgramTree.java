package fplearning.jhh;

import java.util.ArrayList;
import java.util.List;

public class ProgramTree
{

    private List<Tree> program_list;

    public String[] functor;
    public int[] arityFun;
    public String[] terminal;

    public ProgramTree()
    {
    }

    public ProgramTree(List<Tree> program_list, String[] functor, int[] arityFun, String[] terminal)
    {
        this.program_list = program_list;
        this.arityFun = arityFun;
        this.functor = functor;
        this.terminal = terminal;
    }

    public ProgramTree(ProgramTree p)
    {
        this.program_list = new ArrayList<>();

        this.arityFun = p.arityFun;
        this.functor = p.functor;
        this.terminal = p.terminal;
        
        for (Tree t : p.program_list)
        {
            program_list.add(new Tree(t));
        }
    }

    public List<Tree> getProgram_list()
    {
        return program_list;
    }

    public String getProgram_string()
    {
        StringBuilder str = new StringBuilder();

        for (Tree t : this.program_list)
        {
            str.append(t.toString());
        }
        
        return str.toString();
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

        ProgramTree p = g.generateValidProgram();
        ProgramTree p2 = new ProgramTree(p);

        System.out.println("P : " + p.getProgram_string());
        System.out.println("P2: " + p2.getProgram_string());

        
    }
}
