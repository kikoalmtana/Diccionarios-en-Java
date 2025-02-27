package project.ejecutable.desarrollo;

public class Diccionario {

    private Nodo[] nodos;
    private int ocupados;
    private static float TOLERANCIA = 0.8f;

    public Diccionario () {
        this.nodos = new Nodo[32];
        this.ocupados = 0;
    }
}
