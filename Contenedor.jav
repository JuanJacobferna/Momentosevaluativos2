public class Contenedor {
    String id;
    double peso;
    int prioridad;

    // Constructor para inicializar los datos [cite: 38-42]
    public Contenedor(String id, double peso, int prioridad) {
        this.id = id;
        this.peso = peso;
        this.prioridad = prioridad;
    }

    // Para que al imprimir el contenedor se vea el ID, el Peso y la Prioridad
    @Override
    public String toString() {
        return "[" + id + " | " + peso + "t | " + prioridad + "]";
    }
}
