package fplearning.jhh;

import java.util.ArrayList;
import java.util.List;

public class Tree
{

    public static int FUNCTION = 0, LINE = 1, TERMINAL = 2;
    private Node root;

    public Tree(String rootData, int type)
    {
        root = new Node();
        root.name = rootData;
        root.type = type;
        root.children = new ArrayList<>();
    }
    public Tree(Node n){
        root = n;
    }
    public Tree(Tree t){
        this.root = new Node(t.getRoot());
    }
    public int depth(){
        return this.root.depth();
    }
    
    public int count_nodes(){
        return this.root.count_nodes() - 1;
    }

    public Node getRandomNode(){
        return getRandomNode(this.root, 100/depth());
    }

    private Node getRandomNode(Node node, int probability){
        if (node.getChildren().isEmpty()){
            return node;
        } else if ((Math.random() * 100) < probability){
            return node;
        }
        int sele = (int) (Math.random() * node.children.size());
        return getRandomNode(node.children.get(sele), probability + (100 / depth()));
    }

    public static class Node
    {
        private String name;
        private Node parent;
        int type;
        private List<Node> children;

        public Node(Node n)
        {
            this.children = new ArrayList<>();
            
            for(Node child : n.children)
                children.add(new Node(child));
            
            this.name = n.name;
            this.parent = n.parent;
            this.type = n.type;
        }

        public Node(String name, Node parent, int type, List<Node> children)
        {
            this.children = children;
            this.name = name;
            this.parent = parent;
            this.type = type;
        }

        public Node()
        {
        }

        public void setChildren(List<Node> children)
        {
            this.children = children;
        }

        public int depth()
        {
            int counter[] = new int[this.children.size()];
            int mayor = 0;

            if (children == null)
                return 0;

            for (int i = 0; i < counter.length; i++)
            {
                counter[i] = this.children.get(i).depth();
            }

            for (int i = 0; i < counter.length; i++)
            {
                if (counter[i] > mayor)
                {
                    mayor = counter[i];
                }
            }

            return 1 + mayor;
        }
        
        public int count_nodes()
        {
            int counter[] = new int[this.children.size()];
            
            int mayor = 0;

            if (children == null)
                return 0;

            for (int i = 0; i < counter.length; i++)
                counter[i] = this.children.get(i).count_nodes();

            for (int i = 0; i < counter.length; i++)
                mayor += counter[i];

            return 1 + mayor;
        }

        @Override
        public String toString()
        {
            if (type == FUNCTION)
            {
                StringBuilder str = new StringBuilder();
                str.append(name);

                str.append("(");

                for (Node children1 : children)
                {
                    str.append(children1.toString()).append(",");
                }

                str.deleteCharAt(str.lastIndexOf(","));

                str.append(")");

                return str.toString();
            }

            if (type == LINE)
            {
                StringBuilder str = new StringBuilder("");
                str.append(children.get(0).toString());
                str.append("=");
                str.append(children.get(1).toString());
                str.append(";");
                return str.toString();
            }
            if (type == TERMINAL)
            {
                StringBuilder str = new StringBuilder();
                str.append(name);
                return str.toString();
            }
            return "";
        }

        public String getName()
        {
            return name;
        }

        public int getType()
        {
            return type;
        }

        public Node getParent()
        {
            return parent;
        }

        public List<Node> getChildren()
        {
            return children;
        }

        public void replace(Node n)
        {
            this.parent = n.parent;
            this.name = n.name;
            this.children = n.children;
            this.type = n.type;
        }
    }

    public Node getRoot()
    {
        return root;
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();

        str.append(this.root.toString());

        return str.toString();
    }
}
