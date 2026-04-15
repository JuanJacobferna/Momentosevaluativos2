import java.util.*;

public class GestionPortuaria {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);

        // --- MÓDULO 1: Registro de Manifiesto (Arreglos) ---
        System.out.println(">>> MÓDULO 1: ENTRADA DE CAMIÓN <<<");
        System.out.print("¿Cuántos contenedores trae el camión?: ");
        int n = lector.nextInt();

        Contenedor[] manifiesto = new Contenedor[n]; // Arreglo de objetos [cite: 13]
        double pesoTotal = 0;

        for (int i = 0; i < n; i++) {
            System.out.println("\nDatos del contenedor #" + (i + 1));
            System.out.print("ID: ");
            String id = lector.next();
            System.out.print("Peso (double): ");
            double peso = lector.nextDouble();
            System.out.print("Prioridad (1: Normal, 3: Alta): ");
            int prio = lector.nextInt();

            manifiesto[i] = new Contenedor(id, peso, prio);
            pesoTotal += manifiesto[i].peso; // Resumen del peso total
        }
        System.out.println("\nREGISTRO COMPLETO. Peso total entrante: " + pesoTotal + "t");

        // --- MÓDULO 2: Patio de Almacenamiento (Matrices) ---
        // Matriz R x K sugerida por el Director [cite: 18-19, 44]
        Contenedor[][] patio = new Contenedor[5][5];
        System.out.println("\n>>> MÓDULO 2: UBICACIÓN EN PATIO <<<");
        // Ubicamos el primer contenedor del camión como prueba
        ubicarEnPatio(patio, manifiesto[0]);

        // --- MÓDULO 3: Inspección (Colas - FIFO) ---
        // Primero en llegar, primero en ser revisado [cite: 24-25]
        Queue<Contenedor> inspeccion = new LinkedList<>();
        System.out.println("\n>>> MÓDULO 3: BAHÍA DE INSPECCIÓN <<<");
        for (Contenedor c : manifiesto) {
            if (c.prioridad >= 3) {
                inspeccion.add(c); // Encolar peligrosos
                System.out.println("Contenedor " + c.id + " enviado a Rayos X.");
            }
        }
        if (!inspeccion.isEmpty()) {
            System.out.println("Procesando en Rayos X: " + inspeccion.poll()); // Dequeue
        }

        // --- MÓDULO 4: Estiba en el Buque (Pilas - LIFO) ---
        // Último en entrar, único accesible en el tope [cite: 27-28]
        Stack<Contenedor> buque = new Stack<>();
        System.out.println("\n>>> MÓDULO 4: CARGA DEL BUQUE <<<");

        for (Contenedor c : manifiesto) {
            // Aplicación del BONO de estabilidad [cite: 52]
            if (buque.isEmpty() || c.peso <= buque.peek().peso) {
                buque.push(c);
                System.out.println("Cargado al buque: " + c.id);
            } else {
                System.out.println("RECHAZADO: " + c.id + " violaría el centro de gravedad.");
            }
        }

        // Operación Crítica: Retirar el del fondo con Pila Auxiliar
        if (!buque.isEmpty()) {
            corregirFalla(buque);
        }

        lector.close();
    }

    // Función para Módulo 2 [cite: 20-21]
    public static void ubicarEnPatio(Contenedor[][] m, Contenedor c) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] == null) {
                    m[i][j] = c;
                    System.out.println("Contenedor " + c.id + " ubicado en Patio celda [" + i + "," + j + "]");
                    return;
                }
            }
        }
        System.out.println("ALERTA: Puerto Saturado");
    }

    // Función para Módulo 4: Operación Crítica
    public static void corregirFalla(Stack<Contenedor> s) {
        Stack<Contenedor> auxiliar = new Stack<>();
        while (s.size() > 1) {
            auxiliar.push(s.pop()); // Desapilar al auxiliar
        }
        System.out.println("\nRETIRANDO CONTENEDOR DAÑADO DEL FONDO: " + s.pop());

        while (!auxiliar.isEmpty()) {
            s.push(auxiliar.pop()); // Re-apilar en orden original
        }
        System.out.println("Resto de la carga re-apilada con éxito.");
    }
}