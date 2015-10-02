/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.session;

import java.util.List;
import org.whitley.object.controller.local.RoomControllerLocal;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.whitley.object.entities.Room;

/**
 * <h1>RoomController</h1>
 * Controller of POJO of {@code org.whitley.object.entities.Room}.
 * {@link org.whitley.object.handler.RoomController} 
 * and {@link org.whitley.object.handler.TutorialHandler} then
 * access database to handle request.
 * 
 * Provides interface for CRUD operation.
 * 
 * Extends {@link org.whitley.object.controller.BasicController}
 * 
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.handler.RoomController
 * @see org.whitley.object.handler.TutorialHandler
 * @see org.whitley.object.controller.BasicController
 * @since 10/1/2015
 */
@Stateless
public class RoomController extends BasicController implements RoomControllerLocal {

    /**
     * addRoom method.
     * Add new room record to table/
     * @param room: New room to add.
     * @return True if successful, else false.
     */
    @Override
    public boolean addRoom(Room room){
        Query q = em.createNamedQuery("findByRoomName");
        q.setParameter("roomName", room.getRoomName());
        try{
            q.getSingleResult();
            return false;
        }catch (Exception e){
            em.persist(room);
            return true;
        }
        
    }

    /**
     * findRoom method.
     * Find a room from table specified by room id.
     * @param roomid: ID to search by.
     * @return Room POJO.
     */
    @Override
    public Room findRoom(int roomid){
        Query q = em.createNamedQuery("findRoomById");
        q.setParameter("id", roomid);
        return (Room)q.getSingleResult();
    }
    
    /**
     * editRoomDetail method.
     * Edit a room detail on table.
     * @param room: Room object being editted
     * @param roomName: new Name
     * @param roomMin: new Min
     * @param roomMax: new Max
     */
    @Override
    public void editRoomDetail(Room room, String roomName, int roomMin, int roomMax){
        room.setRoomName(roomName);
        room.setMinCapacity(roomMin);
        room.setMaxCapacity(roomMax);
        em.merge(room);
        em.flush();   
    }
    
    /**
     * findAllRoom method.
     * Retrieve all room records from the table.
     * @return List of Room POJOs.
     */
    @Override
    public List<Room> findAllRoom() {
        Query q = em.createNamedQuery("findAllRoom");
        return (List<Room>)q.getResultList();
    }
         
    /**
     * removeRoom method.
     * Remove a specific room from table.
     * @param roomid: ID of the room being deleted.
     */
    @Override
    public void removeRoom(int roomid){
        Room room = findRoom(roomid);
        em.remove(room);
    }
    
}
