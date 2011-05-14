package graph;

public abstract class WeightedDirectedGraph {
    public abstract void insertNode(Node node, String key);
    public abstract Node findNode(String key);
    public abstract void deleteNode(Node node);
    public abstract void insertArc(Node from, Node to, int weight);
    public abstract void getNeighbours(Node node);
    public abstract int getWeight(Node from, Node to);
    public abstract WeightedDirectedGraph shortestPath(Node from, Node to);
}
