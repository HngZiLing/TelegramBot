package my.uum;

/**
 * This class is for save, edit and retrieve the room data
 *
 * @author Hngziling
 */
public class Room extends STIW3054_Fivesome_bot {
    static String room_id, description;
    static int capacity;

    /**
     * This method is for edit/save String value id of the meeting room
     *
     * @param room_id The unique identity of the meeting room
     */
    public static void setRoomId(String room_id) {
        Room.room_id = room_id;
    }

    /**
     * This method is for retrieve the id of the meeting room
     *
     * @return room_id
     */
    public static String getRoomId() {
        return Room.room_id;
    }

    /**
     * This method is for edit/save String value description of the meeting room
     *
     * @param description The description of the meeting room
     */
    public static void setDescription(String description) {
        Room.description = description;
    }

    /**
     * This method is for retrieve the description of the meeting room
     *
     * @return description
     */
    public static String getDescription() {
        return Room.description;
    }

    /**
     * This method is for edit/save integer value maximum capacity of the meeting room
     *
     * @param capacity The maximum capacity of the meeting room
     */
    public static void setCapacity(int capacity) {
        Room.capacity = capacity;
    }

    /**
     * This method is for retrieve the maximum capacity of the meeting room
     *
     * @return capacity
     */
    public static int getCapacity() {
        return Room.capacity;
    }
}