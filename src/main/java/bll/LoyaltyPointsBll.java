package bll;

import dao.LoyaltyPointsDao;
import model.LoyaltyPoints;

import java.util.ArrayList;

public class LoyaltyPointsBll {
    public LoyaltyPointsDao loyaltyPointsDao;

    public LoyaltyPointsBll(LoyaltyPointsDao loyaltyPointsDao) {
        this.loyaltyPointsDao = loyaltyPointsDao;
    }

    public LoyaltyPointsBll() {
        loyaltyPointsDao = new LoyaltyPointsDao();
    }

    public LoyaltyPoints findById(String id) {
        ArrayList<LoyaltyPoints> loyaltyPoints = (ArrayList<LoyaltyPoints>) loyaltyPointsDao.readAll();
        for(LoyaltyPoints loyaltyPoints1 : loyaltyPoints) {
            if(loyaltyPoints1.getLoyaltyPointsId().equals(id)) {
                return loyaltyPoints1;
            }
        }
        return null;
    }
    public void edit(LoyaltyPoints loyaltyPoints) {
        loyaltyPointsDao.update(loyaltyPoints);
    }
}
