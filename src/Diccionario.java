/**
 * Clase que implementa un diccionario genérico utilizando una tabla hash con encadenamiento.
 * Permite almacenar pares clave-valor y realizar operaciones como inserción, eliminación y búsqueda.
 * 
 * @param <K> Tipo de la clave
 * @param <V> Tipo del valor
 */
public class Diccionario<K, V>{
    private Node[] nodes;
    private int couples = 0;
    private static float LOADFACTOR = 0.75f;
    private Node<K,V> first = null;
    private Node<K,V> last = null;

    /**
     * Constructor sin parámetros. Inicializa el diccionario con una capacidad de 32 elementos.
     */
    public Diccionario() {
        this.nodes = new Node[32];
    }

    /**
     * Constructor que inicializa el diccionario con un par clave-valor.
     * 
     * @param key Clave a insertar
     * @param value Valor asociado a la clave
     */
    public Diccionario(K key, V value) {
        this.nodes = new Node[32];
        add(key, value);
    }
    
     /**
     * Constructor que inicializa el diccionario con una clave y un valor nulo.
     * 
     * @param key Clave a insertar
     */
    public Diccionario(K key) {
        this.nodes = new Node[32];
        add(key);
    }

    /**
     * Añade un nuevo par clave-valor al diccionario.
     * Si la clave ya se encuentra en el diccionario, se actualiza el valor.
     * 
     * @param key Clave a insertar
     * @param value Valor asociado a la clave
     */
    public void add(K key, V value) {

        if ((float) couples / nodes.length >= LOADFACTOR) {
            extend();
        }

        int index = calculate_index(key);

        Node<K, V> existing = nodes[index];
        while (existing != null) {
            if (java.util.Objects.equals(existing.getKey(), key)) {
                existing.setValue(value);
                return; 
            }
            existing = existing.next;
        }

        Node<K, V> node = new Node<>(key, value);
        if (first == null && last == null) {
            first = node;
            last = node;
        } else {
            last.nextDouble = node;
            node.prevDouble = last;
            last = node;
        }

        if (nodes[index] == null) {
            nodes[index] = node;
            couples++;
            return;
        }

        Node<K, V> node_index = nodes[index];
        while (node_index.next != null) {
            node_index = node_index.next;
        }
        node_index.next = node;
        couples++;
    }
    
    /**
     * Añade una clave con valor nulo al diccionario.
     * 
     * @param key Clave a insertar
     */
    public void add(K key) {
        add(key, null);
    }
    
    /**
     * Elimina una clave del diccionario y devuelve su valor asociado.
     * 
     * @param key Clave a eliminar
     * @return Valor asociado a la clave eliminada o null si no existe
     */
    public V pop(K key) {
        int index = calculate_index(key);

        if(nodes[index] == null) return null;

        Node<K, V> node = nodes[index];
        if(java.util.Objects.equals(key, node.getKey())){
            nodes[index] = node.next;
            if(node == first){
                first = node.nextDouble;
                if(first != null) {
                    first.prevDouble = null;
                }
            } else {
                node.prevDouble.nextDouble = node.nextDouble;
            }
            couples--;
            return node.getValue();
        }

        while (node.next != null) {
            if(node.next.getKey() == key) break;
            node = node.next;
        }
        if(node.next == null) return null;
        V value = node.next.getValue();
        Node<K, V> remove_node = node.next;
        remove_node.prevDouble.nextDouble = remove_node.nextDouble;
        if(remove_node == last){
            last = remove_node.prevDouble;
        } else{
            remove_node.nextDouble.prevDouble = remove_node.prevDouble;
        }
        node.next = node.next.next;
        couples--;
        return value;
    }
    
    /**
     * Elimina el último elemento insertado en el diccionario.
     * 
     * @return Valor del último elemento eliminado
     */
    public V popItem() {
        return pop(last.getKey());
    }
    
    /**
     * Duplica el tamaño del array de nodos cuando se alcanza el factor de carga.
     * Reorganiza y reubica todos los elementos en la nueva tabla hash para mantener la eficiencia.
     */
    private void extend() {
        Node<K, V> current = first;
        first = null;
        last = null;
        
        int new_length = nodes.length << 1;
        Node<K, V>[] oldNodes = nodes;
        nodes = new Node[new_length];
        couples = 0;

        while (current != null) {
            Node<K, V> next = current.nextDouble;

            current.nextDouble = null;
            current.prevDouble = null;
            add(current.getKey(), current.getValue());
            current = next;
        }
    }
    
    /**
     * Calcula la posición de una clave con su código hash.
     */
    private int calculate_index(K key){
        int hash = 31 * 37 + key.hashCode();
        return hash & (nodes.length - 1);
    }
    
    /**
     * Elimina todos los elementos del diccionario.
     */
    public void clear() {
        this.nodes = new Node[nodes.length];
        this.couples = 0;
        first = null;
        last = null;
    }
    
    /**
     * Obtiene el valor asociado a una clave dada.
     * 
     * @param key Clave a buscar
     * @return Valor asociado o null si no existe
     */
    public V getValue(K key) {
        int index = calculate_index(key);
        Node<K, V> node = nodes[index];
        while (node != null) {
            if (java.util.Objects.equals(node.getKey(), key)) {
                return node.getValue();
            }
            node = node.next;
        }
        return null;
    }
    
    /**
     * Devuelve una representación en cadena del diccionario con los pares clave-valor.
     * 
     * @return Cadena con los elementos del diccionario
     */
    public String items() {
        return toString();
    }
    
    /**
     * Actualiza el diccionario con los elementos de otro diccionario.
     * 
     * @param other Diccionario cuyos elementos se agregarán
     */
    public void update(Diccionario<K, V> other) {
        Node<K, V> currentNode = other.getFirst();
        while (currentNode != null) {
            this.add(currentNode.getKey(), currentNode.getValue());
            currentNode = currentNode.nextDouble;
        }
    }
    
    /**
     * Devuelve un array con todos los valores del diccionario.
     * 
     * @return Array de valores
     */
    public Object[] values() {
        Object[] values = new Object[couples];
        int index = 0;
        Node<K, V> currentNode = first;
        while (currentNode != null) {
            values[index++] = currentNode.getValue();
            currentNode = currentNode.nextDouble;
        }
        return values;
    }
    
    /**
     * Devuelve un array con todas las claves del diccionario.
     * 
     * @return Array de claves
     */
    public Object[] keys() {
        Object[] keys = new Object[couples];
        int index = 0;
        Node<K, V> currentNode = first;
        while (currentNode != null) {
            keys[index++] = currentNode.getKey();
            currentNode = currentNode.nextDouble;
        }
        return keys;
    }
    
    /**
     * Actualiza el valor de una clave en el diccionario si existe.
     * Si la clave no está presente, imprime un mensaje indicando que no se encuentra.
     * 
     * @param key Clave a actualizar
     * @param value Nuevo valor a asignar
     */
    public void updateValue(K key,  V value){
         Object[] keys = keys();
         
        for (Object searchNode: keys) {
            if (java.util.Objects.equals(searchNode, key)) {
                add(key,value);
            }
        }
        System.out.println("La clave " + key.toString() + " no se encuentra en el diccionario.");
    }
    
    /**
     * Obtiene el número de parejas clave-valor en el diccionario.
     * 
     * @return Número de parejas clave-valor
     */
    public int getCouples() {
        return couples;
    }
    
    /**
     * Devuelve el primer nodo de la lista enlazada dentro del diccionario.
     * 
     * @return Primer nodo del diccionario
     */
    private Node<K,V> getFirst() {
        return first;
    }
    
    /**
     * Crea y devuelve una copia del diccionario actual.
     * 
     * @return Nueva instancia del diccionario con los mismos elementos
     */
    public Diccionario<K,V> copy() {
        Diccionario<K, V> other = new Diccionario<K, V>();
        Node<K,V> current = first;
         
        while (current != null) {
            other.add(current.getKey(), current.getValue());
            current = current.nextDouble;
        }
        return other;
    }
      
    /**
     * Devuelve una representación en cadena del diccionario.
     * 
     * @return Representación en cadena del diccionario
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Diccionario: {\n");
        
        Node<K,V> currentNode = first;
        while (currentNode != null) {
            sb.append(currentNode.toString()).append("\n");
            currentNode = currentNode.nextDouble;
        }
        return sb.toString() + "}\n";
    }
    
    /**
     * Compara este diccionario con otro para determinar si son iguales.
     * 
     * @param obj Objeto a comparar
     * @return true si los diccionarios son iguales, false en caso contrario
     */
   @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; 
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
    
        Diccionario<K, V> other = (Diccionario<K, V>) obj;
    
        if (this.couples == other.getCouples() && this.couples == 0) {
            return true;
        }

        if (this.couples != other.getCouples()) {
            return false;
        }
        Object[] thisKeys = this.keys();
        for (Object key : thisKeys) {
            V valueThis = this.getValue((K) key);
            V valueOther = other.getValue((K) key);
            if (valueThis == null && valueOther == null) {
                continue;
            } else if (valueThis == null) {
                return false;
            }
            
            if (!valueThis.equals(valueOther)) {
                return false;
            }
        }
        return true;
    }
}
