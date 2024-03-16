package my.uum;

/**
 * This class is for save, edit and retrieve the data of the school
 *
 * @author Hngziling
 */
public class School extends STIW3054_Fivesome_bot {
    static String school_id, school_name, building_location, office_no;

    /**
     * This method is for save/edit the String value id of the school
     * @param school_id The unique identity of the school
     */
    public static void setSchoolId(String school_id) {
        switch (school_id) {
            case "1":
                school_id = "SOC";
                break;
            case "2":
                school_id = "SMMTC";
                break;
            case "3":
                school_id = "SAPSP";
                break;
            case "4":
                school_id = "SLCP";
                break;
            case "5":
                school_id = "SCIMPA";
                break;
            case "6":
                school_id = "SQS";
                break;
        }
        School.school_id = school_id;
    }

    /**
     * This method is for retrieve the id of the meeting room
     *
     * @return school_id
     */
    public static String getSchoolId() {
        return School.school_id;
    }

    /**
     * This method is for save/edit the String value name of school
     *
     * @param school_name The name of the school
     */
    public static void setSchoolName(String school_name) {
        switch (school_name) {
            case "1":
                school_name = "School of Computing";
                break;
            case "2":
                school_name = "School of Multimedia Technology and Communication";
                break;
            case "3":
                school_name = "School of Applied Psychology, Social Work and Policy";
                break;
            case "4":
                school_name = "School of Languages, Civilisation and Philosophy";
                break;
            case "5":
                school_name = "School of Creative Industry Management and Performing Arts";
                break;
            case "6":
                school_name = "School of Quantitative Sciences";
                break;
        }
        School.school_name = school_name;
    }

    /**
     * This method is for save/edit the String value name of the school
     *
     * @return school_name
     */
    public static String getSchoolName() {
        return School.school_name;
    }

    /**
     * This method is for save/edit the String value building location of the school
     *
     * @param building_location The location of the school
     */
    public static void setBuildingLocation(String building_location) {
        School.building_location = building_location;
    }

    /**
     * This method is for retrieve the building location of the school
     *
     * @return building_location
     */
    public static String getBuildingLocation() {
        return School.building_location;
    }

    /**
     * This method is for save/edit the String value office phone number of the school
     *
     * @param office_no The phone number of the school office
     */
    public static void setOfficeNo(String office_no) {
        School.office_no = office_no;
    }

    /**
     * This method is for retrieve the phone number of the school office
     *
     * @return office_no
     */
    public static String getOfficeNo() {
        return School.office_no;
    }
}
