package com.example.lab4.backend;

import com.example.lab4.models.ClassTeacher;
import com.example.lab4.models.Rate;
import com.example.lab4.models.Teacher;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private DbManager(){};

    private static DbManager Instance;

    static {Instance = new DbManager();}

    public static DbManager getInstance() {
        return Instance;
    }

    public void saveTeacher(Teacher teacher) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(teacher);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteTeacher(Teacher teacher) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(teacher);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateTeacher(Teacher teacher) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(teacher);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void saveClassTeacher(ClassTeacher classTeacher) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(classTeacher);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteClass(ClassTeacher classTeacher) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.remove(classTeacher);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<ClassTeacher> getAllClassTeachers() {
        List<ClassTeacher> list = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ClassTeacher> query = session.createQuery("FROM ClassTeacher", ClassTeacher.class);
            list = query.list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ObservableList<Teacher> getAllTeachers(){
        ObservableList<Teacher> resultList = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Teacher> list = session.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
            resultList.addAll(list);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

//    public List<Teacher> getTeacherByGroupName(String groupName) {
//        List<Teacher> list = new ArrayList<>();
//
//        Transaction transaction = null;
//
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            Query<Teacher> query = session.createQuery("FROM Teacher t WHERE groupName = :groupName", Teacher.class).setParameter("groupName", groupName);
//            list.addAll(query.list());
//
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//
//        return list;
//    }

    //Użyty obiekt Criteria
    public List<Teacher> getTeacherByGroupName(String groupName) {
        List<Teacher> list = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Teacher> criteriaQuery = builder.createQuery(Teacher.class);
            Root<Teacher> root = criteriaQuery.from(Teacher.class);

            Predicate condition = builder.equal(root.get("groupName"), groupName);
            criteriaQuery.where(condition);

            list = session.createQuery(criteriaQuery).getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return list;
    }

    public void rateGroup(Rate rate) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(rate);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Rate> getRateByGroupID(int grupa_id) {
        List<Rate> list = new ArrayList<>();

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<Rate> query = session.createQuery("FROM Rate r WHERE r.grupa.id = :grupa_id", Rate.class).setParameter("grupa_id", grupa_id);
            list.addAll(query.list());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return list;
    }

    public List<Rate> getAllRates() {
        ObservableList<Rate> resultList = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Rate> list = session.createQuery("SELECT t FROM Rate t", Rate.class).getResultList();
            resultList.addAll(list);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

}
