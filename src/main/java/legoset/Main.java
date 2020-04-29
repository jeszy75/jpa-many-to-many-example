package legoset;

import java.time.Year;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import legoset.model.LegoSet;

import legoset.model.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");

    private static void createLegoSets() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new LegoSet("60073", "Service Truck", Year.of(2015), 233)
                    .addTag(new Tag("Truck"))
                    .addTag(new Tag("Trailer")));
            em.persist(new LegoSet("75211", "Imperial TIE Fighter", Year.of(2018), 519)
                    .addTag(new Tag("Solo"))
                    .addTag(new Tag("Starfighter")));
            em.persist(new LegoSet("21034", "London", Year.of(2017), 468)
                    .addTag(new Tag("United Kingdom")));
            em.persist(new LegoSet("21044", "Paris", Year.of(2017), 649)
                    .addTag(new Tag("France"))
                    .addTag(new Tag("Skyscraper")));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private static void updateLegoSets() {
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                Tag tag = new Tag("Microscale");
                em.createQuery("SELECT l FROM LegoSet l WHERE l.name IN ('London', 'Paris')", LegoSet.class)
                        .getResultStream()
                        .forEach(legoSet -> legoSet.addTag(tag));
                em.getTransaction().commit();
            } finally {
                em.close();
            }
    }

    private static void listLegoSets() {
        EntityManager em = emf.createEntityManager();
        try {
            em.createQuery("SELECT t FROM Tag t ORDER BY t.text", Tag.class)
                    .getResultStream()
                    .forEach(tag -> {
                        log.info("Lego sets tagged with {}:", tag.getText());
                        tag.getLegoSets().forEach(log::info);
                    });
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        createLegoSets();
        updateLegoSets();
        listLegoSets();
        emf.close();
    }

}
