package RiskyHash;

public class Entry<K,V> {
    public K key;
    public V value;
    
    public Entry( K key, V value ) {
        this.key = key;
        this.value = value;
    }
    
    // j�mf�r tv� nycklar, returnerar true om lika
    public boolean equals( Object obj ) {
        Entry keyValue = ( Entry )obj;
        return key.equals( keyValue.key );
    }
}
