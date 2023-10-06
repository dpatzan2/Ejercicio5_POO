import java.io.*;
import java.util.*;

enum TipoMaterial {
    LIBRO, DVD, ARTICULO_CIENTIFICO, PERIODICO, TESIS
}

abstract class MaterialBibliografico {
    private String nombre;
    private String editorial;
    private int añoPublicacion;
    private String género;
    private String autor;
    private TipoMaterial tipo;

    public MaterialBibliografico(String nombre, String editorial, int añoPublicacion, String género, String autor, TipoMaterial tipo) {
        this.nombre = nombre;
        this.editorial = editorial;
        this.añoPublicacion = añoPublicacion;
        this.género = género;
        this.autor = autor;
        this.tipo = tipo;
    }

    public abstract String generarReferenciaAPA();

    public String getNombre() {
        return nombre;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getAñoPublicacion() {
        return añoPublicacion;
    }

    public String getGénero() {
        return género;
    }

    public String getAutor() {
        return autor;
    }

    public TipoMaterial getTipo() {
        return tipo;
    }
}

class Libro extends MaterialBibliografico {
    public Libro(String nombre, String editorial, int añoPublicacion, String género, String autor) {
        super(nombre, editorial, añoPublicacion, género, autor, TipoMaterial.LIBRO);
    }

    @Override
    public String generarReferenciaAPA() {
        return String.format("%s. (%d). %s. %s: %s.", getAutor(), getAñoPublicacion(), getNombre(), getEditorial(), getGénero());
    }
}

class DVD extends MaterialBibliografico {
    public DVD(String nombre, String editorial, int añoPublicacion, String género, String autor) {
        super(nombre, editorial, añoPublicacion, género, autor, TipoMaterial.DVD);
    }

    @Override
    public String generarReferenciaAPA() {
        return String.format("%s. (%d). %s. %s.", getAutor(), getAñoPublicacion(), getNombre(), getGénero());
    }
}

class Catalogo {
    private static List<MaterialBibliografico> listaMateriales;

    public Catalogo() {
        listaMateriales = new ArrayList<>();
    }

    public void agregarMaterial(MaterialBibliografico material) {
        listaMateriales.add(material);
    }

    public Map<String, Integer> contarMaterialesPorGenero() {
        Map<String, Integer> conteoPorGenero = new HashMap<>();
        for (MaterialBibliografico material : listaMateriales) {
            String género = material.getGénero();
            conteoPorGenero.put(género, conteoPorGenero.getOrDefault(género, 0) + 1);
        }
        return conteoPorGenero;
    }

    public Map<String, Integer> contarMaterialesPorAutor() {
        Map<String, Integer> conteoPorAutor = new HashMap<>();
        for (MaterialBibliografico material : listaMateriales) {
            String autor = material.getAutor();
            conteoPorAutor.put(autor, conteoPorAutor.getOrDefault(autor, 0) + 1);
        }
        return conteoPorAutor;
    }

    public Map<Integer, Integer> contarMaterialesPorAñoPublicacion() {
        Map<Integer, Integer> conteoPorAño = new HashMap<>();
        for (MaterialBibliografico material : listaMateriales) {
            int año = material.getAñoPublicacion();
            conteoPorAño.put(año, conteoPorAño.getOrDefault(año, 0) + 1);
        }
        return conteoPorAño;
    }

    public String mostrarCatalogoEnAPA() {
        StringBuilder catalogoAPA = new StringBuilder();
        for (MaterialBibliografico material : listaMateriales) {
            catalogoAPA.append(material.generarReferenciaAPA()).append("\n");
        }
        return catalogoAPA.toString();
    }

    public static void guardarCatalogoEnCSV(String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new File(nombreArchivo))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Nombre,Editorial,AñoPublicacion,Género,Autor,Tipo\n");

            for (MaterialBibliografico material : listaMateriales) {
                sb.append(material.getNombre()).append(",");
                sb.append(material.getEditorial()).append(",");
                sb.append(material.getAñoPublicacion()).append(",");
                sb.append(material.getGénero()).append(",");
                sb.append(material.getAutor()).append(",");
                sb.append(material.getTipo()).append("\n");
            }

            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cargarCatalogoDesdeCSV(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String line;
            boolean firstLine = true; // Para ignorar la primera línea (encabezado)
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                String nombre = parts[0];
                String editorial = parts[1];
                int añoPublicacion = Integer.parseInt(parts[2]);
                String género = parts[3];
                String autor = parts[4];
                TipoMaterial tipo = TipoMaterial.valueOf(parts[5]);

                // Crear el material y agregarlo al catálogo
                switch (tipo) {
                    case LIBRO:
                        listaMateriales.add(new Libro(nombre, editorial, añoPublicacion, género, autor));
                        break;
                    case DVD:
                        listaMateriales.add(new DVD(nombre, editorial, añoPublicacion, género, autor));
                        break;
                    // Agregar casos para otros tipos de materiales si es necesario
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class BibliotecaApp {
    private static final String NOMBRE_ARCHIVO = "catalogo.csv"; // Nombre del archivo CSV por defecto

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Catalogo catalogo = new Catalogo();
        boolean salir = false;

        cargarCatalogoDesdeArchivoSiExiste(NOMBRE_ARCHIVO, catalogo);

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
                    agregarMaterialBibliografico(scanner, catalogo);
                    break;
                case 2:
                    // Conteo de Materiales por Género
                    mostrarConteoPorGenero(catalogo);
                    break;
                case 3:
                    // Conteo de Materiales por Autor
                    mostrarConteoPorAutor(catalogo);
                    break;
                case 4:
                    // Conteo de Materiales por Año de Publicación
                    mostrarConteoPorAñoPublicacion(catalogo);
                    break;
                case 5:
                    // Mostrar Catálogo en APA
                    mostrarCatalogoEnAPA(catalogo);
                    break;
                case 6:
                    // Guardar Catálogo en CSV
                    Catalogo.guardarCatalogoEnCSV(NOMBRE_ARCHIVO);
                    System.out.println("Catálogo guardado en " + NOMBRE_ARCHIVO);
                    break;
                case 7:
                    // Cargar Catálogo desde CSV
                    cargarCatalogoDesdeArchivoSiExiste(NOMBRE_ARCHIVO, catalogo);
                    System.out.println("Catálogo cargado desde " + NOMBRE_ARCHIVO);
                    break;
                case 8:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void agregarMaterialBibliografico(Scanner scanner, Catalogo catalogo) {
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la editorial: ");
        String editorial = scanner.nextLine();
        System.out.print("Ingrese el año de publicación: ");
        int añoPublicacion = scanner.nextInt();
        scanner.nextLine(); // Consumir la línea en blanco
        System.out.print("Ingrese el género: ");
        String género = scanner.nextLine();
        System.out.print("Ingrese el autor: ");
        String autor = scanner.nextLine();

        // Selección del tipo de material
        System.out.println("Seleccione el tipo de material:");
        int contador = 1;
        for (TipoMaterial tipo : TipoMaterial.values()) {
            System.out.println(contador + ". " + tipo.name());
            contador++;
        }
        System.out.print("Seleccione una opción: ");
        int opcionTipo = scanner.nextInt();
        TipoMaterial tipoMaterial = TipoMaterial.values()[opcionTipo - 1];

        // Crear el material y agregarlo al catálogo
        MaterialBibliografico material = null;
        switch (tipoMaterial) {
            case LIBRO:
                material = new Libro(nombre, editorial, añoPublicacion, género, autor);
                break;
            case DVD:
                material = new DVD(nombre, editorial, añoPublicacion, género, autor);
                break;
            // Agregar casos para otros tipos de materiales si es necesario
        }

        if (material != null) {
            catalogo.agregarMaterial(material);
            System.out.println("Material agregado correctamente.");
        } else {
            System.out.println("Tipo de material no válido.");
        }
    }


    private static void mostrarConteoPorGenero(Catalogo catalogo) {
        Map<String, Integer> conteoPorGenero = catalogo.contarMaterialesPorGenero();
        System.out.println("Conteo de Materiales por Género:");
        for (Map.Entry<String, Integer> entry : conteoPorGenero.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void mostrarConteoPorAutor(Catalogo catalogo) {
        Map<String, Integer> conteoPorAutor = catalogo.contarMaterialesPorAutor();
        System.out.println("Conteo de Materiales por Autor:");
        for (Map.Entry<String, Integer> entry : conteoPorAutor.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void mostrarConteoPorAñoPublicacion(Catalogo catalogo) {
        Map<Integer, Integer> conteoPorAño = catalogo.contarMaterialesPorAñoPublicacion();
        System.out.println("Conteo de Materiales por Año de Publicación:");
        for (Map.Entry<Integer, Integer> entry : conteoPorAño.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void mostrarCatalogoEnAPA(Catalogo catalogo) {
        String catalogoAPA = catalogo.mostrarCatalogoEnAPA();
        System.out.println("Catálogo en formato APA:");
        System.out.println(catalogoAPA);
    }

    private static void cargarCatalogoDesdeArchivoSiExiste(String nombreArchivo, Catalogo catalogo) {
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            catalogo.cargarCatalogoDesdeCSV(nombreArchivo);
        }
    }
}
