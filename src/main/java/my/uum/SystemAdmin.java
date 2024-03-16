package my.uum;

/**
 * This method is for save/edit the data of the system admin
 */
public class SystemAdmin extends STIW3054_Fivesome_bot {
    static String username, password;

    /**
     * This method is for save the String value username of the system admin
     *
     * @param username The username of the system admin for verify identity
     */
    public static void setUsername(String username) {
        SystemAdmin.username = username;
    }

    /**
     * This method is for retrieve the username of the system admin
     *
     * @return username
     */
    public static String getUsername() {
        return SystemAdmin.username;
    }

    /**
     * This method is for save the String value password of the system admin
     *
     * @param password The password of the system admin for verify identity
     */
    public static void setPassword(String password) {
        SystemAdmin.password = password;
    }

    /**
     * This method is for retrieve the password of the system admin
     *
     * @return password
     */
    public static String getPassword() {
        return SystemAdmin.password;
    }
}