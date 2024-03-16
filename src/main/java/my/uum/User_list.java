package my.uum;

/**
 * This class is for save  and edit the user data
 *
 * @author ChunLoon
 */
public class User_list extends STIW3054_Fivesome_bot {
    static String name="";
    static String Mobile_TelNo= "";
    static String email= "";
    static String ICNO= "";
    static String Password= "";

    /**
     * This method is for return String value ICNO
     * @return ICNO
     */
    public static String getICNO() {
        return ICNO;
    }

    /**
     * This method is for displaying String value ICNO
     * @param ICNO The ic number of the user
     */
    public static void setICNO(String ICNO) {
        User_list.ICNO = ICNO;
    }

    /**
     * This method is for return value name of the user
     * @return name
     */
    public static String getName() {
        return name;
    }

    /**
     * This method is for edit/save String value name
     * @param name The name of the user
     */
    public static void setName(String name) {
        User_list.name = name;
    }

    /**
     * This method is for return value hand phone number of the user
     * @return Mobile_TelNo
     */
    public static String getMobile_TelNo() {
        return Mobile_TelNo;
    }

    /**
     * This method is for edit/save String value hand phone number of the user
     * @param mobile_TelNo The phone number of the user
     */
    public static void setMobile_TelNo(String mobile_TelNo) {
        Mobile_TelNo = mobile_TelNo;
    }

    /**
     * This method is for return value email of the user
     * @return email
     */
    public static String getEmail() {
        return email;
    }

    /**
     * This method is for edit/save String value email of the user
     * @param email The email of the user
     */
    public static void setEmail(String email) {
        User_list.email = email;
    }

    /**
     * The method is for return value password of the user
     * @return password
     */
    public static String getPassword() {
        return Password;
    }

    /**
     * This method is for save/edit the password of the user
     * @param password The password of the user
     */
    public static void setPassword(String password) {
        Password = password;
    }
}