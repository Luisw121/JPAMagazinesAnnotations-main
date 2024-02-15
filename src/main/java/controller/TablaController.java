package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
            //Lectura
            String sqlScript;
            try (BufferedReader br = new BufferedReader(new FileReader("/home/dam2a/Baixades/Acess a dades/JPAMagazinesAnnotations-main/src/main/resources/schema.sql"))){
                sqlScript = br.lines().collect(Collectors.joining(" \n"));
            }catch (IOException e) {
                throw new RuntimeException("Error al leer el archivo " + e);
            }
            sqlScript = sqlScript.replaceAll("DROP TABLE", "DROP TABLE IF EXISTS");

            entityManager.createNativeQuery(sqlScript).executeUpdate();

            transaction.commit();
            System.out.println("Se han eliminado las tablas");

        }catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

}
