package bll;

import dao.RentUnitDao;
import dao.RoomDao;
import model.Account;
import model.RentUnit;
import model.Room;

import java.util.ArrayList;

public class RentUnitBll {
    private RentUnitDao rentUnitDao;

    public RentUnitBll() {

        rentUnitDao = new RentUnitDao();
    }
    public void insertRentUnit(RentUnit rentUnit) {

        rentUnitDao.insert(rentUnit);
    }
    public void updateRentUnit(RentUnit rentUnit) {

        rentUnitDao.update(rentUnit);
    }

    public ArrayList<RentUnit> readAllUnits() {
        return (ArrayList<RentUnit>) rentUnitDao.readAll();
    }

    public RentUnit findByOwner(String username){
        ArrayList<RentUnit> rentUnits= (ArrayList<RentUnit>) rentUnitDao.readAll();
       for(RentUnit unit: rentUnits)
           if (unit.getAccount().getUsername().equals(username))
               return unit;
        return null;
    }

    public RentUnit findById(String id){
        ArrayList<RentUnit> rentUnits= (ArrayList<RentUnit>) rentUnitDao.readAll();

        for(RentUnit unit: rentUnits)
            if (unit.getUnitId().equals(id))
                return unit;
        return null;
    }

    public void deleteRentUnit(RentUnit rentUnit) {
        rentUnitDao.delete(rentUnit);
    }

}
