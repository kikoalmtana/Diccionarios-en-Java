/**
 * Clase que implementa un nodo con clave y valor.
 * Posee un enlace simple para nodos que se encuentren en el mismo indice del diccionario.
 * Posee un doble enlace para todos los nodos del diccionario, tiene como objetivo poder mantener el orden de inserción.
 * 
 * @param <K> Tipo de la clave
 * @param <V> Tipo del valor
 */
public class Node<K, V> {
    private K key;
    private V value;
    public Node<K, V> next;
    public Node<K, V> prevDouble;
    public Node<K, V> nextDouble;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
        this.prevDouble = null;
        this.nextDouble = null;
    }
    
    /**
     * Método que devuelve la clave del nodo
     * 
     * @return key, clave del nodo
     */
     public K getKey() {
        return key;
    }
    
    /**
     * Método que cambia el valor de la clave
     * 
     * @param K newKey, valor nuevo de la clave
     */
    public void setKey(K newKey) {
        key = newKey;
    }
    
    /**
     * Método que devuelve el valor del nodo
     * 
     * @return value, clave del nodo
     */
     
    public V getValue() {
        return value;
    }
    
    /**
     * Método que cambia el valor del nodo
     * 
     * @param K newValue, valor nuevo
     */
     
    public void setValue(V newValue) {
        value = newValue;
    }
    
    /**
     * Método que devuelve la referencia al siguiente nodo del mismo índice
     * 
     * @return next, referencia al siguiente nodo por índice
     */

    public Node<K, V> getNext() {
        return next;
    }
    
     /**
     * Método que devuelve la referencia al nodo anterior que se haya insertado en el diccionario.
     * 
     * @return prevDouble, referencia al nodo previo por orden de inserción
     */

    public Node<K, V> getPrevDouble() {
        return prevDouble;
    }
    
     /**
     * Método que devuelve la referencia al siguiente nodo que se haya insertado en el diccionario.
     * 
     * @return nextDouble, referencia al siguiente nodo por orden de inserción
     */

    public Node<K, V> getNextDouble() {
        return nextDouble;
    }
    
    /**
     * Método que devuelve una String con la información del nodo
     * 
     * @return El valor de clave con su valor asociado
     */

    @Override
    public String toString() {
        if (this.value == null) return "Key: " + key.toString() + ", Value: null";
        return "Key: " + key.toString() + ", Value: " + value.toString();
    }
}
