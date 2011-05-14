package graf;

import RiskyHash.RiskyHash;
import RiskyHash.Entry;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class WDGimpl<K> implements WeightedDirectedGraph<K,Node<K>>
{
    static int MAX_SIZE = 1501;

    //RiskyHash<String, Node> nodes = new RiskyHash<String, Node>(MAX_SIZE);
    RiskyHash<K, Node<K>> nodes = new RiskyHash<K, Node<K>>(MAX_SIZE);
    RiskyHash<Node<K>, LinkedList<Arc>> arcs = new RiskyHash<Node<K>, LinkedList<Arc>>(MAX_SIZE);

    @Override
    public void insertNode(K key, Node<K> node)
    {
        nodes.put(key, node);
    }

    public Node<K> findNode(K key) {
        return nodes.get(key);
    }

    public void deleteNode(Node<K> p_node) {
        for (int i = 0; i < nodes.size(); i++) {
            Iterator<Entry<K,Node<K>>> it = nodes.table[i].iterator();
            while (it.hasNext()) {
                Entry<K,Node<K>> node = it.next();
                LinkedList<Arc> arcs = this.arcs.get(node);
                //LinkedList<Arc> tempList = it.next().value.arcs;
                Iterator<Arc> itArc = arcs.iterator();
                while (itArc.hasNext()) {
                    if (itArc.next().to == p_node) {
                        itArc.remove();
                    }
                }
            }
        }
        //nodes.remove(p_node.key, p_node);
        nodes.remove(p_node.key);
    }

    public void insertArc(Node<K> from, Node<K> to, int weight) {
        System.out.println("insert arc: " + from + ", " + to + ", " + weight);
        LinkedList<Arc> arcs = this.arcs.get(from);
        System.out.println("insertArc(): arcs = " + arcs);
        if(arcs != null){
            System.out.println("ej unik avgångstid");
            arcs.add(new Arc(from, to, weight));
        }
        else
        {
            System.out.println("unik avgångstid");
            arcs = new LinkedList<Arc>();
            arcs.add(new Arc(from, to, weight));
            this.arcs.put(from, arcs);
        }
    }

    @SuppressWarnings("unchecked")
    public Node<K>[] getNeighbours(Node<K> node)
    {
        LinkedList<Arc> arcs = this.arcs.get(node);
        Node<K>[] returnValue;

        if (arcs == null) {
            System.out.println("getNeighbours: no neighbours :(" );
            returnValue = new Node[0];
        }
        else
        {
            Iterator<Arc> it = arcs.iterator();
            returnValue = new Node[arcs.size()];
            int i = 0;
            while (it.hasNext()) 
            {
                Arc arrrc = it.next();
                System.out.println(arrrc);
                returnValue[i] = arrrc.to;
                i++;
            }
        }

        return returnValue;
    }

    public int getWeight(Node<K> from, Node<K> to) {
        LinkedList<Arc> arcs = this.arcs.get(from);
        Iterator<Arc> it = arcs.iterator();
        while (it.hasNext()){
            Arc temp = it.next();
            if (temp.to == to){
                return temp.weight;
            }
        }
            return Integer.MAX_VALUE;
    }

    public WeightedDirectedGraph<K,Node<K>> shortestPath(Node<K> from, Node<K> to) {
        // TODO : ? Use heap
        PriorityQueue<Node<K>> Q = new PriorityQueue<Node<K>>();
        Node<K> u;
        from.dist = 0;
        from.mark = true;
        Q.add(from);

        while (!Q.isEmpty()) {
            u = Q.poll();
            System.out.println("u: " + u);
            if (u.equals(to)) {
                break;
            }
            Node<K>[] neighbours = getNeighbours(u);

            for (Node<K> v : getNeighbours(u)) {
                System.out.println("   v: " + v);
                if (!v.mark){
                    v.dist = Integer.MAX_VALUE;
                    Q.add(v);
                    v.mark = true;
                    System.out.println("    adding v for vandetta");
                }
                if (!v.done) {
                    System.out.println("      v not done");
                    if (u.dist + getWeight(u, v) < v.dist) {
                        v.dist = u.dist + getWeight(u,v);
                        v.ref = u;
                        // TODO ? update rather than remove + add
                        Q.remove(v);
                        Q.add(v);
                    }
                    v.done = true;
                }
                u.done = true;
            }
        }

        WeightedDirectedGraph<K,Node<K>> returnGraph = new WDGimpl<K>();
        returnGraph.insertNode(to.getKey(), to);
        Node<K> current = to;
        while (current.ref != null)
        {
            returnGraph.insertNode(current.getRef().getKey(), current.getRef());
            returnGraph.insertArc(current.getRef(), current, getWeight(current.getRef(), current)); 
            current = current.getRef();
        }

        return returnGraph;
    }
}

