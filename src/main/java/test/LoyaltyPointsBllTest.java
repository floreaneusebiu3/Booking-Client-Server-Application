package test;

import bll.LoyaltyPointsBll;
import dao.LoyaltyPointsDao;
import model.LoyaltyPoints;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoyaltyPointsBllTest {

    @Mock
    LoyaltyPointsDao loyaltyPointsDao;

    LoyaltyPointsBll loyaltyPointsBll;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loyaltyPointsBll = new LoyaltyPointsBll(loyaltyPointsDao);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findById() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints();
        loyaltyPoints.setLoyaltyPointsId("dbshjfhjdgjgsf3443");
        loyaltyPoints.setValue(15);
        ArrayList<LoyaltyPoints> loyaltyPointsList = new ArrayList<>();
        loyaltyPointsList.add(loyaltyPoints);
        when(loyaltyPointsDao.readAll()).thenReturn(loyaltyPointsList);
        assertEquals(loyaltyPointsBll.findById("dbshjfhjdgjgsf3443"), loyaltyPoints);
    }
}