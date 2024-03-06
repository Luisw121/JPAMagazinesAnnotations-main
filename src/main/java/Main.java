import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.TablaController;
import model.Arma;
import model.Skin;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import view.Menu;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    static SessionFactory sessionFactoryObj;

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (HibernateException he) {
            System.out.println("Session Factory creation failure");
            throw he;
        }
    }


    public static EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory emf;
        try {
            emf = Persistence.createEntityManagerFactory("JPAMagazines");
        } catch (Throwable ex) {
            System.err.println("Failed to create EntityManagerFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return emf;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TablaController tablaController = new TablaController(entityManager);

        tablaController.crearTablas();
        Menu menu = new Menu();
        int opcio;
        do {
            System.out.println("Menú:");
            System.out.println("1. Crear tablas en la base de datos");//LISTO
            System.out.println("2. Eliminar Tablas. ");//LISTO
            System.out.println("3. Poblar Masivamente las tablas");//LISTO
            System.out.println("4. Mostrar Tablas");//LISTO
            System.out.println("5. Seleccionar todos los elementos que contengan un texto en concreto");//LISTO
            System.out.println("6. Seleccionar un elemento en concreto y modificarlo");//LISTO
            System.out.println("7. Modificación de un registro por nombre");//LISTO
            System.out.println("8. Modificación de varios registros en concreto");//CASI LISTO
            System.out.println("9. Salir del programa");//LISTO
            System.out.print("Seleccione una opción: ");
            opcio = scanner.nextInt();

            switch (opcio) {
                case 1:
                    tablaController.crearTablas();
                    break;
                case 2:
                    tablaController.eliminarTablas();
                    break;
                case 3:
                    tablaController.poblarTablas();
                    break;
                case 4:
                    tablaController.monstrarTablas();
                    break;
                case 5:
                    scanner.nextLine(); // Limpiar el buffer de entrada
                    System.out.print("Introduce el texto a buscar: ");
                    String textoBusqueda = scanner.nextLine();
                    List<Arma> armasEncontradas = tablaController.seleccionarElementosPorTexto(Arma.class, textoBusqueda);
                    for (Arma arma : armasEncontradas) {
                        System.out.println(arma);
                    }
                    if (armasEncontradas.isEmpty()){
                        System.out.println("No se ha encontrado nada");
                    }
                    break;
                case 6:
                    scanner.nextLine();
                    System.out.print("Introduce el nombre del elemento a modificar: ");
                    String nombreElemento = scanner.nextLine();
                    Arma armaAModificar = tablaController.seleccionarElementoPorNombre(Arma.class, nombreElemento);
                    if (armaAModificar != null) {
                        System.out.println("Elemento seleccionado:");
                        System.out.println(armaAModificar);

                        //Modificaciónes
                        System.out.println("Introduce los nuevos valores para el elemento:");
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Nuevo daño LMB: ");
                        int nuevoDamageLMB = scanner.nextInt();
                        System.out.print("Nuevo daño RMB: ");
                        int nuevoDamageRMB = scanner.nextInt();
                        System.out.print("Nuevo premio por muerte: ");
                        scanner.nextLine();
                        String nuevoKillAward = scanner.nextLine();
                        System.out.print("Nueva velocidad de movimiento: ");
                        float nuevaRunningSpeed = scanner.nextFloat();
                        System.out.print("Nuevo lado: ");
                        String nuevoSide = scanner.next();

                        //Aplicamos las modificaciones
                        armaAModificar.setNombre(nuevoNombre);
                        armaAModificar.setDamageLMB(nuevoDamageLMB);
                        armaAModificar.setDamageRMB(nuevoDamageRMB);
                        armaAModificar.setKillAward(nuevoKillAward);
                        armaAModificar.setRunningSpeed(nuevaRunningSpeed);
                        armaAModificar.setSide(nuevoSide);

                        //Guardamos los cambios
                        entityManager.getTransaction().begin();
                        entityManager.merge(armaAModificar);
                        entityManager.getTransaction().commit();

                        System.out.println("El elemento ha sido modificado con éxito.");
                    } else {
                        System.out.println("No se ha encontrado ningún elemento con ese nombre.");
                    }
                    break;
                case 7:
                    scanner.nextLine(); // Limpiar el buffer de entrada
                    System.out.print("Introduce el nombre del elemento a eliminar: ");
                    String nombreElementoEliminar = scanner.nextLine();
                    Arma armaAEliminar = tablaController.EliminarElementoPorNombre(Arma.class, nombreElementoEliminar);
                    if (armaAEliminar != null) {
                        entityManager.getTransaction().begin();
                        entityManager.remove(armaAEliminar);
                        entityManager.getTransaction().commit();
                        System.out.println("El elemento ha sido eliminado correctamente.");
                    } else {
                        System.out.println("No se ha encontrado ningún elemento con ese nombre.");
                    }
                    break;
                case 8:
                    tablaController.modificarRegistros(Skin.class);
                    break;
                case 9:
                    System.out.println("Saliendo del programa....");
                    break;
                default:
                    System.out.println("Opción no válid. Inténtalo de nuevo.");
            }
        }while (opcio !=9);
        entityManager.close();
        entityManagerFactory.close();
    }
}