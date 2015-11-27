package fplearning.jhh;

import java.util.ArrayList;
import java.util.List;

public class Generator {

    boolean debug_flag = false || true;

    String[] functor;
    int[] arity;
    String[] terminal;
    int limit_lines;
    int limit_terms;

    public Generator(String[] functor, int[] arity, String[] terminal, int num_lines, int num_terms) {
        this.functor = functor;
        this.arity = arity;
        this.terminal = terminal;
        this.limit_lines = num_lines;
        this.limit_terms = num_terms;
    }

    public Tree generateTerminal(String terminal_[]) {
        int sele = (int) (Math.random() * terminal_.length);
        Tree test_terminal = new Tree(terminal_[sele], Tree.TERMINAL);
        return test_terminal;
    }

    public Tree generateFunction(int index, int depth) {
        int sele = index;
        Tree test_function = new Tree(this.functor[sele], Tree.FUNCTION);
        for (int i = 0; i < this.arity[sele]; i++) {
            int sele_case = (int) (Math.random() * 2);
            if (sele_case == 0 || depth < 1)
                test_function.getRoot().getChildren().add(generateTerminal(terminal).getRoot());
            else
                test_function.getRoot().getChildren().add(generateFunction((int) (Math.random() * functor.length), depth - 1).getRoot());
        }
        return test_function;
    }
    
    public Tree generateFunction(List<Tree.Node> param) {
        int sele = (int) (Math.random() * functor.length);
        Tree test_function = new Tree(this.functor[sele], Tree.FUNCTION);
        for (int i = 0; i < this.arity[sele]; i++) {
            int sele_case = (int) (Math.random() * param.size());
            test_function.getRoot().getChildren().add(param.get(sele_case));
        }
        return test_function;
    }

    public Tree generateLines(int depth) {
        int sele = (int) (Math.random() * 2);

        Tree test_line = new Tree("", Tree.LINE);
        Tree funct_temp = generateFunction(0, depth - 1);
        test_line.getRoot().getChildren().add(funct_temp.getRoot()); // funct

        if (sele == 0 && depth > 0) {
            test_line.getRoot().getChildren().add(generateFunction(funct_temp.getRoot().getChildren()).getRoot()); // function
        } else {
            int sele_param = (int) (Math.random() * funct_temp.getRoot().getChildren().size());
            Tree terminal_temp = new Tree(funct_temp.getRoot().getChildren().get(sele_param));
            test_line.getRoot().getChildren().add(terminal_temp.getRoot()); // terminal
        }

        return test_line;
    }

    public ArrayList<Tree.Node> getParams(Tree function) {
        ArrayList<Tree.Node> params = new ArrayList<>();

        for (Tree.Node children : function.getRoot().getChildren().get(0).getChildren()) {
            params.add(children);
        }

        return params;
    }

    public String[] getParamsArray(Tree function) {
        int num_param = function.getRoot().getChildren().get(0).getChildren().size();
        List<Tree.Node> node_param = function.getRoot().getChildren().get(0).getChildren();

        String[] result = new String[num_param];

        for (int i = 0; i < num_param; i++) {
            result[i] = node_param.get(i).getName();
        }

        return result;
    }

    public List<Tree> generateProgramList(int depth) {
        ArrayList<Tree> test_program = new ArrayList<>();
        int temp_limit = (int) (Math.random() * limit_lines) + 1;
        for (int i = 0; i < temp_limit; i++) {
            test_program.add(generateLines(depth));
        }
        return test_program;
    }

    public String generateProgramString(int depth) {
        StringBuilder str = new StringBuilder();

        List<Tree> temp_program = generateProgramList(depth);

        for (Tree temp_program1 : temp_program) {
            str.append(temp_program1.toString());
        }

        return str.toString();
    }

    public ProgramTree generateProgram(int depth) {
        List<Tree> temp_program = generateProgramList(depth);
        return new ProgramTree(temp_program, this.functor, this.arity, this.terminal);
    }

    public ProgramTree generateValidProgram() {
        ProgramTree p;
        while(true){
            boolean break_loop = true;
            p = generateProgram(this.limit_terms);
            for(int i = 0; i < p.getProgram_list().size(); i++)
                if(p.getProgram_list().get(i).count_nodes() > this.limit_terms + 1){
                    break_loop = false;
                    break;
                }
            if(break_loop)
                break;
        }
        return p;
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

        Generator g = new Generator(functor, arityFun, terminal, 3, 5);

        ProgramTree temp = g.generateValidProgram();
        ProgramTree temp1 = g.generateValidProgram();
        ProgramTree temp2 = g.generateValidProgram();

        System.out.print("Program");
        System.out.print("\nLines: " + temp.getProgram_list().size()+"\nNodes: "+temp.getProgram_list().get(0).count_nodes()
                + "\nDepth: " +temp.getProgram_list().get(0).depth());
            for(int i=0;i<temp.getProgram_list().size();i++)
                System.out.print("\ntoString: "+temp.getProgram_list().get(i).toString() );
        System.out.print("\nLines: " + temp1.getProgram_list().size()+"\nNodes: "+temp1.getProgram_list().get(0).count_nodes()
                + "\nDepth: " +temp1.getProgram_list().get(0).depth());
            for(int i=0;i<temp1.getProgram_list().size();i++)
                System.out.print("\ntoString: "+temp1.getProgram_list().get(i).toString() );
        System.out.print("\nLines: " + temp2.getProgram_list().size()+"\nNodes: "+temp2.getProgram_list().get(0).count_nodes()
                + "\nDepth: " +temp2.getProgram_list().get(0).depth());
            for(int i=0;i<temp2.getProgram_list().size();i++)
                System.out.print("\ntoString: "+temp2.getProgram_list().get(i).toString() );
    }
}
