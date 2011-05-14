package steinerland;

import java.util.Comparator;

public class SteinerlandNode<K> extends graf.Node<K> implements Comparator<SteinerlandNode<K>>
{
    protected String city;
    protected short minutesFromMidnight;

    public SteinerlandNode(String city, K key)
    {
        // TODO : ? Can we have -1 here, in order to represent an invalid/no
        // time? In other words a "city node".
        this(city, key, (short)-1);
    }

    public SteinerlandNode(String city, K key, short minutesFromMidnight)
    {
        super(key);
        this.city = city;
        this.minutesFromMidnight = minutesFromMidnight;
    }

    /**
     * {@inheritDoc}
     * @see Comparator#compare(SteinerlandNode,SteinerlandNode)
     */
    public int compare(SteinerlandNode o1, SteinerlandNode o2)
    {
        return 0;
    }

    /**
     * {@inheritDoc}
     * @see Comparator#equals(Object)
     */
    public boolean equals(Object obj)
    {
        return (city.equals(((SteinerlandNode)obj).getCity()));
    }

    /**
     * Gets the city for this instance.
     *
     * @return The city.
     */
    public String getCity()
    {
        return this.city;
    }

    /**
     * Gets the minutesFromMidnight for this instance.
     *
     * @return The minutesFromMidnight.
     */
    public short getMinutesFromMidnight()
    {
        return this.minutesFromMidnight;
    }

    public String toString(){
        if (minutesFromMidnight == -1){
            return this.city;
        }else
        return this.city + " " + this.minutesFromMidnight;
    }
}
