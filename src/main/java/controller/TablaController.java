package controller;

import model.Arma;
import model.Caja;
import model.Llave;
import model.Skin;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
}
/*
private void poblarDatosLlaves() {
        String csvFile = "ruta/al/archivo/Llaves.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Leemos el archivo línea por línea
            while ((line = br.readLine()) != null) {
                // Dividimos la línea por comas para obtener los valores individuales
                String[] data = line.split(cvsSplitBy);

                // Creamos una nueva instancia de Llave y la poblamos con los datos del archivo CSV
                Llave llave = new Llave();
                llave.setNombre(data[0]);
                llave.setPrecio(new BigDecimal(data[1]));

                // Convertimos las cajas que abre a una lista y la asociamos a la llave
                List<String> cajasQueAbre = new ArrayList<>();
                for (int i = 2; i < data.length; i++) {
                    cajasQueAbre.add(data[i]);
                }
                llave.setCajasQueAbre(cajasQueAbre.toArray(new String[0]));

                // Persistimos la instancia en la base de datos
                entityManager.persist(llave);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void poblarDatosArmas() {
        String csvFile = "ruta/al/archivo/Armas.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Leemos el archivo línea por línea
            while ((line = br.readLine()) != null) {
                // Dividimos la línea por comas para obtener los valores individuales
                String[] data = line.split(cvsSplitBy);

                // Creamos una nueva instancia de Arma y la poblamos con los datos del archivo CSV
                Arma arma = new Arma();
                arma.setNombre(data[0]);
                arma.setDamageLMB(data[1]);
                arma.setDamageRMB(data[2]);
                arma.setKillAward(data[3]);
                arma.setRunningSpeed(data[4]);
                arma.setSide(data[5]);

                // Persistimos la instancia en la base de datos
                entityManager.persist(arma);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 */