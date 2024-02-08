import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import controller.ArticleController;
import controller.AuthorController;
import controller.MagazineController;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import view.Menu;

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
        ArrayList<Magazine> revistes = new ArrayList();

        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();

        AuthorController authorController = new AuthorController(entityManagerFactory);
        ArticleController articleController = new ArticleController(entityManagerFactory);
        MagazineController magazineController = new MagazineController(entityManagerFactory);

        Menu menu = new Menu();
        int opcio;
        opcio = menu.mainMenu();

        switch (opcio) {

            case 1:

                System.out.println("1!!");
                try {

                    List<Author> authors = authorController.readAuthorsFile("src/main/resources/autors.txt");
                    List<Magazine> magazines = articleController.readArticlesFile("src/main/resources/articles.txt", "src/main/resources/revistes.txt", "src/main/resources/autors.txt");
                    List<Article> articles = articleController.readArticlesFile("src/main/resources/articles.txt", "src/main/resources/autors.txt");

                    System.out.println("Revistes llegides des del fitxer");
                    for (int i = 0; i < magazines.size(); i++) {
                        System.out.println(magazines.get(i).toString() + "\n");
                        for (int j = 0; j < magazines.get(i).getArticles().size(); j++) {
                            Author author = magazines.get(i).getArticles().get(j).getAuthor();
                            authorController.addAuthor(author);

                            System.out.println("EL AUTOR:");
                            System.out.println(author);

                            Article article = magazines.get(i).getArticles().get(j);
                            article.setAuthor(author);

                            System.out.println("EL ARTICLE:");
                            System.out.println(article);

                            articleController.addArticle(article);
                        }

                        magazineController.addMagazine(magazines.get(i));
                    }

                } catch (NumberFormatException | IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                System.out.println("Adeu!!");
                System.exit(1);
                break;

        }
    }
}