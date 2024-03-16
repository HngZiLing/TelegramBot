package my.uum;

/**
 * This class is for save, edit and retrieve the start and end time
 * @author ChunLoon
 */
public class Time extends STIW3054_Fivesome_bot {

    static int startTime = 0;
    static int endTime = 0;

    /**
     * This method is for retrieve the start time of the booking
     *
     * @return startTime
     */
    public static int getStartTime() {
        return startTime;
    }

    /**
     * This method is for save/edit the integer value start time of the booking
     *
     * @param startTime The start time of the booking meeting room
     */
    public static void setStartTime(int startTime) {
        Time.startTime = startTime;
    }

    /**
     * This method is for retrieve the end time of the booking
     *
     * @return endTime
     */
    public static int getEndTime() {
        return endTime;
    }

    /**
     * This method is for save/edit the integer value end time of the booking
     *
     * @param endTime The end time of the booking meeting room
     */
    public static void setEndTime(int endTime) {
        Time.endTime = endTime;
    }
}
