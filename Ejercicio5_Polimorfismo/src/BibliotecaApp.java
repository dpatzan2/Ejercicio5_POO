import java.io.*;
import java.util.*;


public class BibliotecaApp {
    private static final String NOMBRE_ARCHIVO = "catalogo.csv"; // Nombre del archivo CSV por defecto

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;



        while (!salir) {
            System.out.println("Bienvemido, Selecciona Una opción");
            System.out.println("1. Agregar Material Bibliográfico");
            System.out.println("2. Conteo de Materiales por Género");
            System.out.println("3. Conteo de Materiales por Autor");
            System.out.println("4. Conteo de Materiales por Año de Publicación");
            System.out.println("5. Mostrar Catálogo en APA");
            System.out.println("6. Guardar Catálogo en CSV");
            System.out.println("7. Cargar Catálogo desde CSV");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la línea en blanco

            switch (opcion) {
                case 1:
                    // Agregar Material Bibliográfico

                    break;
                case 2:
                    // Conteo de Materiales por Género

                    break;
                case 3:
                    // Conteo de Materiales por Autor

                    break;
                case 4:
                    // Conteo de Materiales por Año de Publicación

                    break;
                case 5:
                    // Mostrar Catálogo en APA

                    break;
                case 6:
                    // Guardar Catálogo en CSV

                    break;
                case 7:
                    // Cargar Catálogo desde CSV

                    break;
                case 8:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

}
