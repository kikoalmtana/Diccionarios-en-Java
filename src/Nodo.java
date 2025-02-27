public class Nodo<K, V> {
    private K key;
    private V value;
    private Nodo<K, V> next;
    private Nodo<K, V> prevDouble;
    private Nodo<K, V> nextDouble;

    public Nodo(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
        this.prevDouble = null;
        this.nextDouble = null;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }
    public Nodo<K, V> getNext() { return next; }
    public Nodo<K, V> getPrevDouble() { return prevDouble; }
    public Nodo<K, V> getNextDouble() { return nextDouble; }

    @Override
    public String toString() {
        String nextKey;
        if (this.next != null && this.next.key != null) {
            nextKey = this.next.key.toString();
        } else {
            nextKey = "null";
        }
        return "Key: " + key.toString() + ", Value: " + value.toString() + ", Next: " + nextKey;
    }
}

