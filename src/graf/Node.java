package graf;

public class Node<K> implements Comparable<Node<K>> 
{
    protected K key;
    protected boolean mark;
    protected boolean done;
    protected double dist;
    protected Node<K> ref;

    /**
     * Constructs a new instance.
     */
    public Node(K key)
    {
        this.key = key;
    }

    public int compareTo(Node<K> node)
    {
        if (this.dist == node.dist)
            return 0;
        else if (this.dist > node.dist)
            return -1;
        else
            return 1;
    }

    /**
     * Gets the key for this instance.
     *
     * @return The key.
     */
    public K getKey()
    {
        return this.key;
    }

    /**
     * Gets the dist for this instance.
     *
     * @return The dist.
     */
    public double getDist()
    {
        return this.dist;
    }

    /**
     * Gets the ref for this instance.
     *
     * @return The ref.
     */
    public Node<K> getRef()
    {
        return this.ref;
    }
}
