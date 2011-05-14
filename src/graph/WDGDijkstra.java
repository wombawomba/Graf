package graph;

public class WDGDijkstra extends WeightedDirectedGraph {
    public WDGDijkstra() {
    }

    /**
     * {@inheritDoc}
     * @see WeightedDirectedGraph#insertNode(Node,String)
     */
    public void insertNode(Node node, String key)
    {
    }

    /**
     * {@inheritDoc}
     * @see WeightedDirectedGraph#findNode(String)
     */
    public Node findNode(String key)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     * @see WeightedDirectedGraph#deleteNode(Node)
     */
    public void deleteNode(Node node)
    {
    }

    /**
     * {@inheritDoc}
     * @see WeightedDirectedGraph#insertArc(Node,Node,int)
     */
    public void insertArc(Node from, Node to, int weight)
    {
    }

    /**
     * {@inheritDoc}
     * @see WeightedDirectedGraph#getNeighbours(Node)
     */
    public void getNeighbours(Node node)
    {
    }

    /**
     * {@inheritDoc}
     * @see WeightedDirectedGraph#getWeight(Node,Node)
     */
    public int getWeight(Node from, Node to)
    {
        return -1;
    }

    /**
     * {@inheritDoc}
     * @see WeightedDirectedGraph#shortestPath(Node,Node)
     */
    public WeightedDirectedGraph shortestPath(Node from, Node to)
    {
        return null;
    }
}
