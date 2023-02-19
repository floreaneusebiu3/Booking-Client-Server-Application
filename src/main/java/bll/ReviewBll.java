package bll;

import dao.ReviewDao;
import model.RentUnit;
import model.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ReviewBll {

    ReviewDao reviewDao = new ReviewDao();

    public Set<Review> getReviewForThisRentUnit(RentUnit rentUnit) {
        ArrayList<Review> reviews = (ArrayList<Review>) reviewDao.readAll();
        Set<Review> searchedReviews = new HashSet<>();
        for(Review review : reviews) {
            if(review.getRentUnit().getUnitId().equals(rentUnit.getUnitId())) {
                searchedReviews.add(review);
            }
        }
        return searchedReviews;
    }
    public Set<Review> getReviewForThisRentUnitbyId(String id) {
/*        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        RentUnit rentUnit1 = session.get(RentUnit.class,id);
        Set<Review> reviews = rentUnit1.getReviews();
        return reviews;*/
        ArrayList<Review> reviews = (ArrayList<Review>) reviewDao.readAll();
        Set<Review> searchedReviews = new HashSet<>();
        for(Review review : reviews) {
            if(review.getRentUnit().getUnitId().equals(id)) {
                searchedReviews.add(review);
            }
        }
        return searchedReviews;
    }

    public void insertReview(Review review) {
        reviewDao.insert(review);
    }

}
