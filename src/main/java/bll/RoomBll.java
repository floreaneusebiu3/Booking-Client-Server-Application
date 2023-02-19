package bll;



import dao.RoomDao;

import model.RentUnit;
import model.Room;
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

public class RoomBll {
    private RoomDao roomDao;

    public RoomBll() {
        roomDao = new RoomDao();
    }

    public RoomBll(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public void insertRoom(Room room) {
        roomDao.insert(room);
    }
    public void updateRoom(Room room) {

        roomDao.update(room);
    }
    public void deleteRoom(Room room) {

        roomDao.delete(room);
    }

    public ArrayList<Room> readAllRooms() {
        return (ArrayList<Room>) roomDao.readAll();
    }
    public Room findById(String id) {
       ArrayList<Room> rooms= (ArrayList<Room>) roomDao.readAll();
       for(Room room: rooms){
           if(room.getRoomId().equals(id))
               return room;
       }
       return null;
    }

    public Set<Room> getRoomsForThisRentUnit(RentUnit rentUnit) {
        ArrayList<Room> rooms = (ArrayList<Room>) roomDao.readAll();
        Set<Room> searchedRooms = new HashSet<>();
        for(Room room : rooms) {
            if(room.getRentUnit().getUnitId().equals(rentUnit.getUnitId())) {
                searchedRooms.add(room);
            }
        }
        return searchedRooms;
    }
    public Set<Room> getRoomsForThisRentUnitbyId(String id) {
        ArrayList<Room> rooms = (ArrayList<Room>) roomDao.readAll();
        Set<Room> searchedRooms = new HashSet<>();
        for(Room room : rooms) {
            if(room.getRentUnit().getUnitId().equals(id)) {
                searchedRooms.add(room);
            }
        }
        return searchedRooms;
    }

}
