import java.util.LinkedList;
public class Fila {
    private LinkedList<Ponto> elementos = new LinkedList<>();
    public void enqueue(Ponto p) {
        elementos.addLast(p);
    }
    public Ponto dequeue() {
        return elementos.removeFirst();
    }
    public boolean isEmpty() {
        return elementos.isEmpty();
    }
}
