package test;

import bll.RoomBll;
import dao.RoomDao;
import model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomBllTest {

    @Mock
    RoomDao roomDao;

    RoomBll roomBll;
    @BeforeEach
    void setUp() {
        roomDao = mock(RoomDao.class);
        roomBll = new RoomBll(roomDao);
    }

    @Test
    void findByIdTest() {
        Room room = new Room();
        room.setRoomId("4trfdfgfgdt3");
        room.setPrice(1500);
        room.setFacilitati("tot ce iti poti dori");
        room.setCapacity(10);
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(room);
        when(roomDao.readAll()).thenReturn(rooms);
        assertEquals(roomBll.findById("4trfdfgfgdt3"), room);
    }
}