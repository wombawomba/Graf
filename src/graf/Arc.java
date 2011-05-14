package graf;

    public class Arc 
    {
        protected Node from;
        protected Node to;
        public int weight;

        public Arc(Node from, Node to, int weight)
        {
            this.to = to;
            this.from = from;
            this.weight = weight;
        }

        public String toString() {
            return "Arc.toString(): " + from + " to " + to + ", weight: " + weight;
        }
    }
