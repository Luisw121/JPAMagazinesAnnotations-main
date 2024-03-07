package controller;

import model.Arma;
import model.Caja;
import model.Llave;
import model.Skin;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TablaController {
    private EntityManager entityManager;
    public TablaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void crearTablas() {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            //Lectura
            String sqlScript;
            try(BufferedReader br = new BufferedReader(new FileReader("/home/dam2a/Baixades/Acess a dades/JPAMagazinesAnnotations-main/src/main/resources/schema.sql"))){
                sqlScript = br.lines().collect(Collectors.joining(" \n"));
            }catch (IOException e) {
                throw new RuntimeException("Error al leer el archivo schema.sql", e);
            }

            entityManager.createNativeQuery(sqlScript).executeUpdate();

            transaction.commit();
            System.out.println("Se han creado las tablas ");
        }catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public void eliminarTablas() {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            //sentencias sql para eliminar las tablas de la bsd
            entityManager.createNativeQuery("DROP TABLE IF EXISTS Datos_Armas").executeUpdate();
            entityManager.createNativeQuery("DROP TABLE IF EXISTS Datos_Llaves").executeUpdate();
            entityManager.createNativeQuery("DROP TABLE IF EXISTS Datos_Skins").executeUpdate();
            entityManager.createNativeQuery("DROP TABLE IF EXISTS Nombre_Cajas").executeUpdate();

            transaction.commit();
            System.out.println("Se han eliminado las tablas de la base de datos.");

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public void poblarTablas() {
        poblarDatosArmas();
        poblarDatosLlaves();
        poblarDatosSkins();
        poblarNombreCajas();
    }
    public void poblarDatosArmas() {
        String csvFile = "/home/dam2a/Baixades/Acess a dades/JPAMagazinesAnnotations-main/src/main/resources/CSV/datos_armas.csv";

        EntityTransaction transaction = entityManager.getTransaction();

        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            transaction.begin();
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\"", "");
                String[] data = line.split(",");

                String nombre = data[0];
                String damageLMB = data[1];
                String damageRMB = data[2];
                String killAward = data[3];
                String runningSpeed = data[4];
                String side = data[5];

                Arma arma = new Arma();
                arma.setNombre(nombre);
                arma.setDamageLMB(parseDamage(damageLMB));
                arma.setDamageRMB(parseDamage(damageRMB));
                arma.setKillAward(killAward);
                arma.setRunningSpeed(Float.parseFloat(runningSpeed));
                arma.setSide(side);

                entityManager.persist(arma);
            }
            transaction.commit();
            System.out.println("Se han poblado los datos de armas");

        }catch (IOException e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Revertir la transacción en caso de error
            }
            e.printStackTrace();
        } catch (NumberFormatException e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Revertir la transacción en caso de error
            }
            System.out.println("No se pudo convertir un valor numérico");
            e.printStackTrace();
        }
    }

    private Integer parseDamage(String damage) {
        String cleanedDamage = damage.replaceAll("[^0-9/]", "");

        String[] parts = damage.split("/");
        int numerator = Integer.parseInt(parts[0]);
        int denominator = Integer.parseInt(parts[1]);

        return numerator + denominator;
    }

    private void poblarDatosLlaves() {
        String csvFile = "/home/dam2a/Baixades/Acess a dades/JPAMagazinesAnnotations-main/src/main/resources/CSV/datos_llaves.csv";

        EntityTransaction transaction = entityManager.getTransaction();

        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            transaction.begin();
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\"", "");
                String[] data = line.split(",");

                String nombre = data[0];
                Float precio = Float.parseFloat(data[1]);
                String cajaQueAbre = data[2];

                Llave llave = new Llave(nombre, precio, cajaQueAbre);

                entityManager.persist(llave);
            }
            transaction.commit();
            System.out.println("Se ha poblado los datos de llaves");
        } catch (IOException e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Revertir la transacción en caso de error
            }
            e.printStackTrace();
        } catch (NumberFormatException e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Revertir la transacción en caso de error
            }
            System.out.println("No se pudo convertir un valor numérico");
            e.printStackTrace();
        }
    }

    private void poblarNombreCajas() {
        String csvFile = "/home/dam2a/Baixades/Acess a dades/JPAMagazinesAnnotations-main/src/main/resources/CSV/nombre_cajas.csv";
        EntityTransaction transaction = entityManager.getTransaction();

        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            transaction.begin();
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {

                line = line.replaceAll("\"", "");
                String nombreCaja = line.trim();

                Caja caja = new Caja(nombreCaja);

                entityManager.persist(caja);
            }
            transaction.commit();
            System.out.println("Se ha poblado los datos de cajas");
        }catch (IOException e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Revertir la transacción en caso de error
            }
            e.printStackTrace();
        }
    }

    private void poblarDatosSkins() {
        String csvFile = "/home/dam2a/Baixades/Acess a dades/JPAMagazinesAnnotations-main/src/main/resources/CSV/datos_skins.csv";

        EntityTransaction transaction = entityManager.getTransaction();

        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            transaction.begin();
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {

                line = line.replaceAll("\"", "");
                String[] data = line.split(",");

                String nombreCaja = data[0];
                String nombreSkin = data[1];

                Skin skin = new Skin(nombreCaja, nombreSkin);

                entityManager.persist(skin);
            }
            transaction.commit();
            System.out.println("Se han poblado los datos de skins");
        }catch (IOException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public void monstrarTablas() {
        List<Arma> armas = entityManager.createQuery("SELECT a FROM Arma a", Arma.class).getResultList();
        List<Llave> llaves = entityManager.createQuery("SELECT l FROM Llave l", Llave.class).getResultList();
        List<Skin> skins = entityManager.createQuery("SELECT s FROM Skin s", Skin.class).getResultList();
        List<Caja> cajas = entityManager.createQuery("SELECT c FROM Caja c", Caja.class).getResultList();
        System.out.println("__Armas__");
        for (Arma arma : armas) {
            System.out.println(arma);
        }
        System.out.println("__Llaves__");
        for (Llave llave : llaves) {
            System.out.println(llave);
        }
        System.out.println("__Skins__");
        for (Skin skin : skins) {
            System.out.println(skin);
        }
        System.out.println("__Caja__");
        for (Caja caja : cajas) {
            System.out.println(caja);
        }
    }
    public <T> List<T> seleccionarElementosPorTexto(Class<T> clase, String texto) {
        String nombreTabla = clase.getSimpleName();
        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<T> entityType = metamodel.entity(clase);

        String queryJPQL = "SELECT e FROM " + nombreTabla + " e WHERE ";

        for (javax.persistence.metamodel.Attribute<? super T, ?> attribute : entityType.getDeclaredAttributes()) {
            if (attribute.getJavaType() == String.class) {
                queryJPQL += "e." + attribute.getName() + " LIKE '%" + texto + "%' OR ";
            }
        }
        queryJPQL = queryJPQL.substring(0, queryJPQL.lastIndexOf(" OR "));

        Query query = entityManager.createQuery(queryJPQL, clase);
        return query.getResultList();
    }
    public <T> T seleccionarElementoPorAtributo(Class<T> clase,String nombreAtributo, Object valorAtributo) {
        String queryJPQL = "SELECT e FROM " + clase.getSimpleName() + " e WHERE e." + nombreAtributo + " = :valor";
        TypedQuery<T> query = entityManager.createQuery(queryJPQL, clase);
        query.setParameter("valor", valorAtributo);
        List<T> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    public <T> T EliminarElementoPorNombre(Class<T> clase, String nombre) {
        String queryJPQL = "SELECT e FROM " + clase.getSimpleName() + " e WHERE e.nombre = :nombre";
        TypedQuery<T> query = entityManager.createQuery(queryJPQL, clase);
        query.setParameter("nombre", nombre);
        List<T> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
    public <T> int eliminarElementosPorNombre(Class<T> clase, String nombre) {
        String nombreTabla = clase.getSimpleName();
        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<T> entityType = metamodel.entity(clase);

        // Obtener el nombre del atributo que contiene el nombre
        String nombreAtributo = null;
        for (javax.persistence.metamodel.Attribute<? super T, ?> attribute : entityType.getDeclaredAttributes()) {
            if (attribute.getJavaType() == String.class) {
                nombreAtributo = attribute.getName();
                break;
            }
        }

        if (nombreAtributo == null) {
            throw new IllegalArgumentException("La clase " + clase.getSimpleName() + " no contiene atributos de tipo String.");
        }

        String queryJPQL = "DELETE FROM " + nombreTabla + " e WHERE e." + nombreAtributo + " = :nombre";

        entityManager.getTransaction().begin(); // Iniciar la transacción
        Query query = entityManager.createQuery(queryJPQL);
        query.setParameter("nombre", nombre);
        int cantidadEliminada = query.executeUpdate();
        entityManager.getTransaction().commit(); // Confirmar la transacción
        return cantidadEliminada;
    }
}
