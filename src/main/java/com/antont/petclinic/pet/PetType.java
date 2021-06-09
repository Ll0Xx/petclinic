package com.antont.petclinic.pet;

import com.antont.petclinic.model.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "animal_type")
public class PetType extends NamedEntity {

//    private SessionFactory sessionFactory;



//    public static SessionFactory getCurrentSessionFromJPA() {
//        EntityManagerFactory emf =
//                Persistence.createEntityManagerFactory("jpa-tutorial");
//        EntityManager entityManager = emf.createEntityManager();
//        Session session = entityManager.unwrap(org.hibernate.Session.class);
//        SessionFactory factory = session.getSessionFactory();
//        return factory;
//    }

//    public List<PetType> getAll(){
//
//    }
}
