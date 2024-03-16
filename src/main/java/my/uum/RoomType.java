package my.uum;

/**
 * This class is for save, edit and retrieve the data of meeting room type
 * @author Hngziling
 */
public class RoomType extends STIW3054_Fivesome_bot {
    static String room_type_id, room_type;

    /**
     * This method is for save/edit the type id of the meeting room
     * @param room_type_id The unique identity of the room type
     */
    public static void setRoomTypeId(String room_type_id) {
        switch (room_type_id) {
            case "1":
                room_type_id = "BR";
                break;
            case "2":
                room_type_id = "MR";
                break;
            case "3":
                room_type_id = "SR";
                break;
            case "4":
                room_type_id = "CR";
                break;
            case "5":
                room_type_id = "TR";
                break;
            case "6":
                room_type_id = "LR";
                break;
        }
        RoomType.room_type_id = room_type_id;
    }

    /**
     * This method is for retrieve the type id of the meeting room
     *
     * @return room_type_id
     */
    public static String getRoom_type_id() {
        return RoomType.room_type_id;
    }

    /**
     * This method is for save/edit the type of the meeting room
     *
     * @param room_type The type of the meeting room
     */
    public static void setRoomType(String room_type) {
        switch (Integer.valueOf(room_type)) {
            case 1:
                room_type = "Breakout room";
                break;
            case 2:
                room_type = "Meeting room";
                break;
            case 3:
                room_type = "Session room";
                break;
            case 4:
                room_type = "Conference room";
                break;
            case 5:
                room_type = "Training room";
                break;
            case 6:
                room_type = "Laboratory";
                break;
        }
        RoomType.room_type = room_type;
    }

    /**
     * This method is for retrieve the type of the meeting room
     *
     * @return room_type
     */
    public static String getRoomType() {
        return RoomType.room_type;
    }
}
