package my.uum;

/**
 * This class is for save, edit and retrieve the data of the school admin
 *
 * @author Hngziling
 */
public class SchoolAdmin extends STIW3054_Fivesome_bot {
    static String staff_no, name, mobile_tel_no, email, status;

    /**
     * This method is for save/edit the String value data of the staff
     *
     * @param staff_no The unique identity of the staff
     */
    public static void setStaffNo(String staff_no) {
        SchoolAdmin.staff_no = staff_no;
    }

    /**
     * This method is for retrieve the unique number of the staff
     *
     * @return staff_no
     */
    public static String getStaffNo() {
        return SchoolAdmin.staff_no;
    }

    /**
     * This method is for save/edit the String value name of the staff
     *
     * @param name The name of the staff
     */
    public static void setStaffName(String name) {
        SchoolAdmin.name = name;
    }

    /**
     * This method is for retrieve the name of the staff
     *
     * @return name
     */
    public static String getName() {
        return SchoolAdmin.name;
    }

    /**
     * This method is for save/edit the String value mobile phone number of the staff
     *
     * @param mobile_tel_no The mobile phone number of the staff
     */
    public static void setMobileTelNo(String mobile_tel_no) {
        if (mobile_tel_no.length() == 10 || mobile_tel_no.length() == 11) {
            SchoolAdmin.mobile_tel_no = mobile_tel_no;
        }
    }

    /**
     * This method is for retrieve the mobile phone number of the staff
     *
     * @return mobile_tel_no
     */
    public static String getMobileTelNo() {
        return SchoolAdmin.mobile_tel_no;
    }

    /**
     * This method is for save/edit the String value email of the staff
     *
     * @param email The email of the staff
     */
    public static void setEmail(String email) {
        SchoolAdmin.email = email;
    }

    /**
     * This method is for retrieve the email of the staff
     *
     * @return email The email of the staff
     */
    public static String getEmail() {
        return SchoolAdmin.email;
    }

    /**
     * This method is for save/edit the String value status of the school admin candidate
     *
     * @param status The application status of the school admin candidate
     */
    public static void setStatus(String status) {
        SchoolAdmin.status = status;
    }

    /**
     * This method is for retrieve the status of the school admin candicate
     *
     * @return status
     */
    public static String getStatus() {
        return SchoolAdmin.status;
    }
}
