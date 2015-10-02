/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.local;

import java.util.List;
import javax.ejb.Local;
import org.whitley.object.entities.Room;

/**
 * <h1>RoomControllerLocal</h1>
 * Local Bean of {@link org.whitley.object.controller.session.RoomController}
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.controller.session.RoomController
 * @since 10/1/2015
 */
@Local
public interface RoomControllerLocal {
    
    public boolean addRoom(Room room);
    public void removeRoom(int roomid);
    public void editRoomDetail(Room room, String roomName, int roomIn, int roomMax);
    public List<Room> findAllRoom();
    public Room findRoom(int roomid);
    
}
