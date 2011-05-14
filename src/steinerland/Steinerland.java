package steinerland;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.TreeSet;

import graf.Node;
import graf.WDGimpl;
import graf.WeightedDirectedGraph;

public class Steinerland {

    protected WeightedDirectedGraph<String, Node<String>> graph;
    //protected LinkedList<SteinerlandNode> locations;
    protected TreeSet<String> locations;
    protected Gui gui;

    public Steinerland() {
        //graph = new WDGDijkstra();
        //graph = new WDGimpl<String>();
        //graph = new WDGimpl<SteinerlandNode>();
        // TODO : ? Why can we not use generics here
        graph = new WDGimpl<String>();
        locations = new TreeSet<String>();
        //locations = new LinkedList<SteinerlandNode>();
        gui = new Gui(this);

        loadFile("data/timetable-fixed.tbl");
        for(String s : locations) {
            System.out.println("location = " + s);
            Node<String> n = graph.findNode(s);
            for(Node node : graph.getNeighbours(n)) {
                System.out.println("    neighbour = " + node);
            }
        }
    }

    public String search(String from, short hours, short minutes, String to) {
        System.out.println("to = " + to);
        System.out.println("search():");
        boolean wasNodeNull = false;
        // create from node and arcs from it (if it does not exist)
        SteinerlandNode<String> fromTimeNode = (SteinerlandNode<String>) graph.findNode(generateKey(from, hours, minutes));
        if (fromTimeNode == null) {
            wasNodeNull = true;
            System.out.println("search(): node was null...");
            fromTimeNode = new SteinerlandNode<String>(from, generateKey(from, hours, minutes), getMinutesFromMidnight(hours, minutes));
            graph.insertNode(generateKey(from, hours, minutes), fromTimeNode);
            //  all from.to
            for (Node<String> node : graph.getNeighbours((SteinerlandNode<String>) graph.findNode(from))) {
                System.out.println("search(): adding arc...");
                SteinerlandNode<String> steinerlandNode = (SteinerlandNode<String>) node;
                graph.insertArc(fromTimeNode, steinerlandNode, getTimeDifference(getMinutesFromMidnight(hours, minutes), steinerlandNode.getMinutesFromMidnight()));
            }
        }
        System.out.println("search(): " + fromTimeNode.toString());
        // get to node
        SteinerlandNode<String> toNode = (SteinerlandNode<String>) graph.findNode(to);
        // Seach graph for shortest path
        WeightedDirectedGraph<String, Node<String>> shortestPath = graph.shortestPath(fromTimeNode, toNode);
        // set returnValue to the WDG in form of a nice time table
        // TODO : ? remove first node from shortestPath graph if wasNodeNull == true
        String returnValue = graphToString(fromTimeNode, shortestPath);
        // remove the node we created (and its arcs) (if added)
        if (wasNodeNull) {
            // TODO : implement...
        }

        return returnValue;
    }

    protected String graphToString(SteinerlandNode<String> from, WeightedDirectedGraph<String, Node<String>> graph) {
        String returnValue = "Your recomended path is:\n";

        if (graph == null) {
            return "THERE IS NO SPOON!";
        }

        SteinerlandNode<String> node = from;
        while (node != null) {
            // set next node
            Node<String>[] neighbours = graph.getNeighbours(node);
            if (neighbours.length > 0) {
                SteinerlandNode<String> toNode = (SteinerlandNode<String>) neighbours[0];
                returnValue += "From '" + node.getCity() + "' at '" + toMilitaryTime(node.getMinutesFromMidnight()) + "' to '" + toNode.getCity();
                node = toNode;
            } else {
                // You've reached your target location...
                returnValue += "\nCongratulations, we can serve you in your travelz! Total time is '" + toMilitaryTime((short) node.getDist());
                node = null;
            }
        }

        return returnValue;
    }

    public String toMilitaryTime(short minutes) {
        // TODO : impl..
        return "14:88";
    }

    public void addLink(String from, short hoursDeparture, short minutesDeparture, String to, short hoursArrival, short minutesArrival, String train) {
        // TODO : ? Do "city nodes" really need to be in the graph
        // Add city nodes to locations list
        System.out.println("addLink() start");
        locations.add(from);
        locations.add(to);
        // Add nodes (unless they exist).
        SteinerlandNode<String> fromNode = new SteinerlandNode<String>(from, from);
        SteinerlandNode<String> toNode = new SteinerlandNode<String>(to, to);
        SteinerlandNode<String> fromTimeNode = new SteinerlandNode<String>(from, generateKey(from, hoursDeparture,
                minutesDeparture), getMinutesFromMidnight(hoursDeparture,
                minutesDeparture));
        SteinerlandNode<String> toTimeNode = new SteinerlandNode<String>(to,
                generateKey(to, hoursArrival, minutesArrival),
                getMinutesFromMidnight(hoursArrival, minutesArrival));
        // - from
        if (graph.findNode(from) == null) {
            graph.insertNode(from, fromNode);
        }
        // - fromTime
        if (graph.findNode(generateKey(from, hoursDeparture, minutesDeparture)) == null) {
            graph.insertNode(generateKey(from, hoursDeparture, minutesDeparture), fromTimeNode);
        }
        // - to
        if (graph.findNode(to) == null) {
            graph.insertNode(to, toNode);
        }
        // - toTime
        if (graph.findNode(generateKey(to, hoursArrival, minutesArrival)) == null) {
            graph.insertNode(generateKey(to, hoursArrival, minutesArrival), toTimeNode);
        }

        // Add arc.
        // - fromTime
        //  toTime
        graph.insertArc(fromTimeNode, toTimeNode, getTimeDifference(fromTimeNode.getMinutesFromMidnight(), toTimeNode.getMinutesFromMidnight()));
        //  all from.to
        for (Node<String> nodd : graph.getNeighbours(fromNode)) {
            SteinerlandNode<String> node = (SteinerlandNode<String>) nodd;
            System.out.println("addLink() fromNode, node: " + node);
            graph.insertArc(fromTimeNode, node, getTimeDifference(fromTimeNode.getMinutesFromMidnight(), node.getMinutesFromMidnight()));
        }
        // - from
        //  fromTime
        graph.insertArc(fromNode, fromTimeNode, 0);
        // - toTime
        //  all to.to
        for (Node<String> nodd : graph.getNeighbours(toNode)) {
            SteinerlandNode<String> node = (SteinerlandNode<String>) nodd;
            System.out.println("addLink() toNode, node: " + node);
            graph.insertArc(toTimeNode, node, getTimeDifference(toTimeNode.getMinutesFromMidnight(), node.getMinutesFromMidnight()));
        }
        // - to
        //  toTime
        graph.insertArc(toNode, toTimeNode, 0);

        System.out.println("addLink() added: " + from + ", " + hoursDeparture + ", " + minutesDeparture + ", " + to + ", " + hoursArrival + ", " + minutesArrival + ", " + train);
    }

    protected String generateKey(String city, short hours, short minute) {
        return city + hours + minute;
    }

    protected short getMinutesFromMidnight(short hours, short minutes) {
        return (short) (hours * 60 + minutes);
    }

    protected short getTimeDifference(short totalMinutesDeparture, short totalMinutesArrival) {
        short time = (short) (totalMinutesArrival - totalMinutesDeparture);
        if (time > 0) {
            return time;
        } else {
            return (short) (1440 + time);
        }
    }

    protected short getTimeDifference(short hoursDeparture, short minutesDeparture, short hoursArrival, short minutesArrival) {
        return getTimeDifference(((short) (hoursArrival * 60 + minutesArrival)), (short) ((hoursDeparture * 60 + minutesDeparture)));
    }

    public void loadFile(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // the formatting in the time table file is shady, it's not uniformed..
            // assuming format:
            // <train>
            // <departureCity>:<departureTime>-<arrivalCity>:<arrivalTime>
            // REGEXP to match a line: [^:]*: *[^ ]* *- *[^:]*: *[^$]*
            // <part0>: <part1>:<part2>- <part3>: <part4>:<part5>
            //
            // example:
            // high-speed commuter train 0501
            // Steinerstadt: 05.40 - El Seco: 06.03
            // El Seco: 06.05 - Neubergstadt: 06.39
            // Neubergstadt: 06.41 - Steinerstadt: 06:58
            String line = bufferedReader.readLine();
            String train = "Unknown train";
            while (line != null) {
                System.out.println("line=" + line);
                // Link (an arc)
                if (line.matches("[^:]*: *[^ ]* *- *[^:]*: *[^$]*")) {
                    String[] parts = line.split("[:.-]");
                    //System.out.println("From" + parts[0].trim() + ",h" + parts[1].trim() + ",min" + parts[2].trim() + ",To" + parts[3].trim() + ",h" + parts[4].trim() + ",min" + parts[5].trim() + ",train" + train);
                    addLink(parts[0].trim(), Short.parseShort(parts[1].trim()), Short.parseShort(parts[2].trim()), parts[3].trim(), Short.parseShort(parts[4].trim()), Short.parseShort(parts[5].trim()), train);
                } else // Train name (or blank line, but who-the-heck cares)
                {
                    train = line.trim();
                }

                line = bufferedReader.readLine();
            }

            bufferedReader.close();

            gui.updateLocations();
        } catch (IOException e) {
            System.out.println("[ERROR] Exception: " + e);
        }
    }

    public static void main(String[] args) {
        new Steinerland();
    }

    /**
     * Gets the locations for this instance.
     *
     * @return The locations.
     */
    public TreeSet<String> getLocations() {
        return this.locations;
    }
}
