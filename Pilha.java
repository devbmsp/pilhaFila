import java.util.LinkedList;
public class Pilha {
    private LinkedList<Ponto> elementos = new LinkedList<>();
    public void push(Ponto p) {
        elementos.addFirst(p);
    }
    public Ponto pop() {
        return elementos.removeFirst();
    }
    public boolean isEmpty() {
        return elementos.isEmpty();
    }
}
