package RiskyHash;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Hashtabellen använder öppen hashing
 * @author Risky Axelsson
 */
public class RiskyHash<K,V> {
    public LinkedList<Entry<K,V>>[] table;
    private int size;
    
    /** Creates a new instance of HashtabellOH */
    public RiskyHash( int size ) {
        table = new LinkedList[ size ];
        for( int i = 0; i < size; i++ ) {
            table[ i ] = new LinkedList<Entry<K,V>>();
        }
    }
    
    private int hashIndex( Object key ) {
        int hashCode = key.hashCode();
        hashCode = hashCode % table.length;
        return ( hashCode < 0 ) ? -hashCode : hashCode;
    }
    
    public void put( Object key, Object value ) {
        int hashIndex = hashIndex( key );
        Entry entry = new Entry( key, value );
        int index = table[ hashIndex ].indexOf( entry );
        if( index == -1 ) {
            table[ hashIndex ].addFirst( entry );// effektivt även vid enkellänkad lista
            size++;
        }
//        else
//            table[ hashIndex ].set( index, entry );
    }

    /*
     * Rensar hela Hastabellen
     */
    public void clear(){
        for (int i = 0; i< table.length; i++){
            table[i].clear();
        }
        size = 0;
    }
    /*
     * Returnerar true om det finns ett object med en viss nyckel i Hashtabellen
     */
    public boolean containsKey(Object key){
        return table[hashIndex(key)].contains(new Entry(key, null));
    }
    /*
     * Returnerar value på objectet med en viss key.
     * Finns inte nyckeln returneras null
     */
    public V get(Object key){
        if (containsKey(key)){
            int tableindex = hashIndex(key);
            for (int i=0; i<table[tableindex].size(); i++){
                if (table[tableindex].get(i).equals(new Entry(key, null))){
                    return table[tableindex].get(i).value;
                }
            }
        }
            return null;        
    }

    /*
     * Tar bort ett object ur Hashtabellen och minskar sizen.     *
     */
    public void remove(Object key){
        int index = hashIndex(key);
        if (index != -1){
        boolean togsBort = table[hashIndex(key)].remove(new Entry(key,null));
        if (togsBort)
        size--;
        }
    }
    public void remove(K key, V value){
        int index = hashIndex(key);
        if (index != -1){
            LinkedList<Entry<K,V>> tuttar = table[hashIndex(key)];
            Iterator<Entry<K,V>> it = tuttar.iterator();
            int i=-1;
            while (it.hasNext()){
                Entry pattar = it.next();
                i++;
                if (pattar.key.equals(key) && pattar.value.equals(value)){
                    tuttar.remove(i);
                }
                
            }
        }        
    }
    /*
     * Returnerar antalet object som är lagrade i Hashmapen
     */
    public int size(){
        return size;
    }
    
    public void list() {
        Entry entry;
        for(int i=0; i<table.length; i++) {
            System.out.print( i + ":");
            for( int j = 0; j < table[ i ].size(); j++ ) {
                entry = table[ i ].get( j );
                System.out.print(" <" + entry.key +"," + entry.value + ">" );
            }
            System.out.println();
        }
    }



}
    
   
