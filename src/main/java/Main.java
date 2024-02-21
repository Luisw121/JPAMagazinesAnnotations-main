import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.TablaController;
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
            System.out.println("3. Poblar Masivamente las tablas");//
            System.out.println("4. Mostrar Tablas");//
            System.out.println("5. Seleccionar todos los elementos que contengan u ntexto en concretp");//
            System.out.println("6. Seleccionar un elemento en concreto y modificarlo");//
            System.out.println("7. Modificación de diferentes registros");//
            System.out.println("8. ELimincación de un registro en concreto");//
            System.out.println("9. Eliminación de un conjunto de registros");//
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
                    //
                    break;
                case 5:
                    //
                    break;
                case 6:
                    //
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    System.out.println("Saliendo del programa..");
                    break;
                default:
                    System.out.println("Opción no válid. Inténtalo de nuevo.");
            }
        }while (opcio !=3);
        entityManager.close();
        entityManagerFactory.close();
    }
}