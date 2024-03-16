package my.uum;

import java.io.File;
import java.sql.*;
import java.util.Date;

/**
 * This class is for SQLite database to select, insert, and delete the data
 *
 * @author ChunLoon, Hngziling, Sookqichow
 */
public class SQLite extends STIW3054_Fivesome_bot {

    /**
     * This method is to connect the telegram bot with SQLite database
     * @return connection The connection with the database
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            File file = new File("database.db");
            if (!file.exists()) {
                file.createNewFile();
            }
            String url = "jdbc:sqlite:" + file.getPath();
            conn = DriverManager.getConnection(url);
            return conn;

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * This method is for insert booking info to the tbl_booking
     * @param purpose The purpose of the booking
     * @param booking_date The date of the booking
     * @param booking_startTime The start time of the booking
     * @param booking_endTime The end time of the booking
     * @param roomId The room id of the booking
     */
    public static void insertBooking(String purpose, String booking_date, String booking_startTime, String booking_endTime, String roomId) {
        Date date = new Date();
        try {
            Connection conn = SQLite.connect();
            String sql = "INSERT INTO tbl_booking(booking_id, purpose, booking_date, booking_start_time,booking_end_time,currentTime,email,room_id) VALUES (?,?,?,?,?,?,?,?)";
            if (conn != null) {
                PreparedStatement pstmt;
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,null );
                pstmt.setString(2, purpose);
                pstmt.setString(3, (booking_date));
                pstmt.setString(4, (booking_startTime));
                pstmt.setString(5, (booking_endTime));
                pstmt.setString(6, date.toString());
                pstmt.setString(7,User_list.getEmail());
                pstmt.setString(8, Booking_info.getRoomId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is for insert user info to the tbl_user
     * @param email The email of the user
     * @param icno The ic number of the user
     * @param name The name of the user
     * @param mobile_telNo The mobile phone number of the user
     * @param password The password of the user
     */
    public static void insert_User_Info(String email, String icno, String name, String mobile_telNo, String password) {
        String mobile = mobile_telNo;
        try {
            Connection conn = SQLite.connect();
            String sql = "INSERT INTO tbl_user(email, ic_no, name, mobile_telNo, password) VALUES (?,?,?,?,?)";
            if (conn != null) {
                PreparedStatement pstmt;
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, (email));
                pstmt.setString(2, (icno));
                pstmt.setString(3, name);
                pstmt.setString(4, (mobile_telNo));
                pstmt.setString(5, (password));

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is for check email exist in database or not while log in
     * @param email1 The email entered by the user for verification
     * @return response Notify whether the email exists or not
     */
    public static String select1(String email1){
        String response = "";
        String sql = "SELECT *FROM tbl_user WHERE email = '" + email1 + "'";
        try {
            Connection conn = SQLite.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.getString("email") != null) {
                response = "Available";
            } else {
                response = "No";
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    /**
     * This method is for check password correct or not while log in
     * @param password The email entered by the user for verification
     * @return response Notify whether the email exists or not
     */
    public static String selectPass(String password) {
        String data = "";
        String sql = "SELECT * FROM tbl_user WHERE password ='" + password + "'";
        String sql1 = "SELECT * FROM tbl_booking WHERE email='" + User_list.getEmail() + "'";
        Connection conn = SQLite.connect();
        try {
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(sql1);

                if (rs.getString("password")!=null) {
                    while (rs1.next()) {
                        String roomId = rs1.getString("room_id");
                        String sql3 = "SELECT *FROM tbl_room WHERE room_id='" + roomId + "'";
                        stmt = conn.createStatement();
                        ResultSet rs3 = stmt.executeQuery(sql3);

                        int bookingId = rs1.getInt("booking_id");
                        String purpose = rs1.getString("purpose");
                        String booking_date = rs1.getString("booking_date");
                        String booking_stime = rs1.getString("booking_start_time");
                        String booking_etime = rs1.getString("booking_end_time");
                        String room_id = rs1.getString("room_id");
                        String description = rs3.getString("description");
                        String capacity = rs3.getString("capacity");

                        data = data + "\nBooking ID: " + bookingId +
                                "\nPurpose: " + purpose +
                                "\nDate: " + booking_date +
                                "\nStart_Time: " + booking_stime + "\nEnd_Time: " + booking_etime +
                                "\nRoom ID: " + room_id +
                                "\nCapacity: " + capacity +
                                "\nDescription: " + description +
                                "\n";
                    }
                } else {
                    data = "No";
                }
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    /**
     * This method for cancel booking and delete the data in the database
     */
    public static void delete() {
        try {
            String sql = "DELETE FROM tbl_user WHERE email='"+User_list.getEmail()+"'";
            Connection conn = SQLite.connect();

            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                String sql1 = "DELETE FROM tbl_booking WHERE email='" + User_list.getEmail() + "'";
                stmt.executeUpdate(sql1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is to display all the user info and the meeting room info
     * @return All the info from tbl_user and tbl_booking and tbl_room in database
     */
    public static String list() {
        Connection conn = SQLite.connect();
        String list = null;
        try {
            if (conn != null) {
                String sql1 = "SELECT *FROM tbl_user";
                Statement stmt;
                stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(sql1);

                String sql2 = "SELECT *FROM tbl_booking";
                stmt = conn.createStatement();
                ResultSet rs2 = stmt.executeQuery(sql2);

                list = "";
                while (rs1.next() && rs2.next()) {
                    String bookingId = rs2.getString("booking_id");
                   // String icNo = rs1.getString("ic_no");
                    String name = rs1.getString("name");
                    String telNo = rs1.getString("mobile_telNo");
                   // String email = rs1.getString("email");
                    String purpose = rs2.getString("purpose");

                    String roomId = rs2.getString("room_id");

                    String sql3= "SELECT *FROM tbl_room WHERE room_id='"+roomId+"'";
                    stmt = conn.createStatement();
                    ResultSet rs3 = stmt.executeQuery(sql3);

                    String school_id= rs3.getString("school_id");

                    String sql4 = "SELECT *FROM tbl_school  WHERE school_id='"+school_id+"'";
                    stmt = conn.createStatement();
                    ResultSet rs4 = stmt.executeQuery(sql4);


                    String description = rs3.getString("description");
                    String capacity = rs3.getString("capacity");

                    String date = rs2.getString("booking_date");
                    String stime = rs2.getString("booking_start_time");
                    String etime = rs2.getString("booking_end_time");


                    list = list + "\nBooking ID: " + bookingId +
                            "\nName: " + name +
                            "\nTel No: " + telNo +
                            "\nRoom ID: " + roomId +
                            "\nRoom description: " + description +
                            "\nMaximum capacity: " + capacity +
                            "\nSchool Name: " + rs4.getString("school_name") +
                            "\nBuilding Location: " + rs4.getString("building_location") +
                            "\nOffice Number: " + rs4.getString("office_no") +
                            "\nPurpose: " + purpose + "\nRoom Id: " + roomId +
                            "\nBooking date: " + date +
                            "\nBooking time: " + stime + "\nBooking time: " + etime +

                            "\n";
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    /**
     * This method is to return info while edit
     * @param password The password of the user to verify identity
     * @return data All the info for the user to edit
     */
    public static String editDisplay(String password) {
        String data = "";
        String sql = "SELECT * FROM tbl_user WHERE password ='"+password +"'";
        String sql1 = "SELECT * FROM tbl_booking WHERE email='"+User_list.getEmail()+"'";
        Connection conn = SQLite.connect();
        try {
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(sql1);

                if (rs.getString("password")!=null){

                    String roomId = rs1.getString("room_id");

                    String sql3= "SELECT *FROM tbl_room WHERE room_id='"+roomId+"'";
                    stmt = conn.createStatement();
                    ResultSet rs3 = stmt.executeQuery(sql3);

                    String school_id= rs3.getString("school_id");

                    String sql2 = "SELECT *FROM tbl_school  WHERE school_id='"+school_id+"'";
                    stmt = conn.createStatement();
                    ResultSet rs2 = stmt.executeQuery(sql2);


                    while (rs1.next()&& rs.next()) {
                        int bookingId = rs1.getInt("booking_id");
                        String purpose = rs1.getString("purpose");
                        String booking_date = rs1.getString("booking_date");
                        String booking_stime = rs1.getString("booking_start_time");
                        String booking_etime = rs1.getString("booking_end_time");
                        String room_id = rs1.getString("room_id");
                        String description = rs3.getString("description");
                        String capacity = rs3.getString("capacity");

                        String icNo = rs.getString("ic_no");
                        String name = rs.getString("name");
                        String telNo = rs.getString("mobile_telNo");
                        String email = rs.getString("email");
                        String pass = rs.getString("password");

                        data= data +
                                "\nReply 1 : Booking Info: " +
                                "\n\nBooking ID: " + bookingId +
                                "\nRoom Id: " + room_id +
                                "\nRoom description: " + description +
                                "\nMaximum capacity: " + capacity +
                                "\nSchool Name: " + rs2.getString("school_name") +
                                "\nBuilding Location: " + rs2.getString("building_location") +
                                "\nOffice Number: " + rs2.getString("office_no") +
                                "\nBooking date: " + booking_date +
                                "\nBooking start time: " + booking_stime +
                                "\nBooking end time: " + booking_etime +
                                "\nPurpose: " + purpose + "" +
                                "\n\nUser Info: " +
                                "\nReply 2 :Ic number: " + icNo +
                                "\nReply 3 :Name: " + name +
                                "\nReply 4 :Tel No: " + telNo +
                                "\nReply 5:Password: " + pass +
                                "\n";
                    }
                } else {
                    data = "No";
                }
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    /**
     * This method is for update ic of the user
     * @param icNo The ic number of the user
     */
    public static void editIC(String icNo) {
        String sql="UPDATE tbl_user SET ic_no='"+icNo+"'"  + " WHERE email='"+User_list.getEmail()+"'";
        Connection conn = SQLite.connect();
        try{
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is for update name of the user
     * @param name The name of the user
     */
    public static void editName(String name) {
        String sql="UPDATE tbl_user SET name='"+name+"'"  + " WHERE email='"+User_list.getEmail()+"'";
        Connection conn = SQLite.connect();
        try{
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is for update mobile telephone number of the user
     * @param telNo The mobile telephone number of the user
     */
    public static void editTelNo(String telNo) {
        String sql="UPDATE tbl_user SET mobile_telNo='"+telNo+"'"  + " WHERE email='"+User_list.getEmail()+"'";
        Connection conn = SQLite.connect();
        try{
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is for update password of the user
     * @param pass The password of the user
     */
    public static void editPass(String pass) {
        String sql="UPDATE tbl_user SET password='"+pass+"'"  + " WHERE email='"+User_list.getEmail()+"'";
        Connection conn = SQLite.connect();
        try{
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is for return available room in school
     * @param schoolID The unique id of the school
     * @return The list of the available room in the school
     */
    public static String displayAvailableRoom(String schoolID) {

        /*
        if(  (rs.getString("school_id").equals(schoolID)&& date.equals(Booking_info.getBooking_Date())&&endTime>Time.getStartTime()&&startTime<=Time.getEndTime()){
        System.out.println("Overlapp");
        }*/
        String sql = "SELECT *FROM tbl_room  WHERE school_id ='"+schoolID+"'";
        Statement stmt;
        StringBuilder stringBuilder=new StringBuilder();
        String responseFinal=null;
        String response = null;
        try {
            Connection conn = SQLite.connect();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.getString("school_id") != null) {
                String sql5 = "SELECT * FROM tbl_room WHERE school_id='" + schoolID + "'AND room_id NOT IN (Select room_id FROM tbl_booking Where booking_date='" + Booking_info.getBooking_Date() + "'AND booking_end_time>='" + Booking_info.getBooking_StartTime() + "'AND booking_start_time<='" + Booking_info.getBooking_EndTime() + "')";
                //  String sql5="SELECT * from tbl_room WHERE school_id='S12' AND room_id NOT IN (Select room_id FROM tbl_booking Where booking_date='11.11.1111' AND booking_end_time>='09:30' AND booking_start_time<='13:50' )";
                stmt = conn.createStatement();
                ResultSet rs5 = stmt.executeQuery(sql5);

                while (rs5.next()) {
                    String type_id = rs.getString("room_type_id");
                    String sql1 = "SELECT *FROM tbl_roomType  WHERE room_type_id ='" + type_id + "'";
                    stmt = conn.createStatement();
                    ResultSet rs1 = stmt.executeQuery(sql1);

                    response = ("\n\n" +
                            "RoomID:  " + rs5.getString("room_id") + "\n" +
                            "Room_Description:  " + rs5.getString("description") + "\n" +
                            "Maximum_capacity:  " + rs5.getInt("capacity") + "\n" +
                            "Room Type:  " + rs1.getString("room_type") + "\n");

                    if (response == null)
                        response = "\nRoom Not Available";
                    stringBuilder.append(response);
                }
                responseFinal = stringBuilder.toString();
            } else {
                responseFinal = "Invalid school ID";
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return responseFinal;
    }

    /**
     * This method is return all info of the school
     * @return school info
     */
    public static String displaySchool() {
        StringBuilder stringBuilder=new StringBuilder();
        String responseFinal=null;
        String response = null;
        String sql = "SELECT * FROM tbl_school ";

        try {
            Connection conn = SQLite.connect();
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    response = ("\n" +
                            "School ID:  " + rs.getString("school_id") + "\n" +
                            "School Name:  " + rs.getString("school_name") + "\n" +
                            "Bulding Location:  " + rs.getString("building_location") + "\n" +
                            "\n");
                    if (response == null)
                        response = "\n School Not Available";
                    stringBuilder.append(response);
                }
                responseFinal=stringBuilder.toString();
            }
            conn.close();;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  responseFinal;
    }

    /**
     * This method is to check id of the room selected is available or not
     * @param roomID The id of the room
     * @return response Notify whether the room available or not
     */
    public static String selectRoom(String roomID) {

        String sql = "SELECT *FROM tbl_room  WHERE room_id ='"+roomID+"'";
        String response = null;

        try {
            Connection conn = SQLite.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.getString("room_id") != null) {
                if (response == null)
                    response = "\nRoom Not Available";
            } else {
                response = "Invalid room ID";
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    /**
     * This method is for update Booking info
     * @param booking_date The date of the booking
     * @param booking_startTime The start time of the booking
     * @param booking_endTime The end time of the booking
     * @param purpose The purpose of the booking
     * @param roomId The id of the room for booking
     */
    public static void editBooking(String booking_date, String booking_startTime, String booking_endTime, String purpose, String roomId) {
        String sql = "UPDATE tbl_booking SET booking_date='" + booking_date + "',booking_start_time='" + booking_startTime + "',booking_end_time='" + booking_endTime + "',purpose='" + purpose + "',room_id='" + roomId + "'" + " WHERE email='" + User_list.getEmail() + "'";

        Connection conn = SQLite.connect();

        try{
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is return to room Info
     * @param roomId The id of the room
     * @return response All the room info with the room id
     */
    public static String roomInfo(String roomId) {

        String sql = "SELECT *FROM tbl_room  WHERE room_id ='"+roomId+"'";
        String response = null;

        try {
            Connection conn = SQLite.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.getString("room_id")!=null) {

                String type_id= rs.getString("room_type_id");

                String sql1 = "SELECT *FROM tbl_roomType  WHERE room_type_id='"+type_id+"'";
                stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(sql1);

                String school_id= rs.getString("school_id");

                String sql2 = "SELECT *FROM tbl_school  WHERE school_id='"+school_id+"'";
                stmt = conn.createStatement();
                ResultSet rs2 = stmt.executeQuery(sql2);

                response = "\nRoom description: " + rs.getString("description") +
                        "\nCapacity: " + rs.getString("capacity") +
                        "\nRoom Type: " + rs1.getString("room_type") +
                        "\nSchool Name: " + rs2.getString("school_name") +
                        "\nBuilding Location: " + rs2.getString("building_location") +
                        "\nOffice Number: " + rs2.getString("office_no");
                if (response == null)
                    response = "\nRoom Not Available";
            } else {
                response = "Invalid room ID";
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    /**
     * This method is for insert the data of school admin to the database
     */
    public static void insertSchoolAdmin() {
        try {
            Connection conn = SQLite.connect();
            PreparedStatement pstmt;
            String sql;
            if (conn != null) {
                sql = "INSERT INTO tbl_school_admin(staff_no, name, mobile_tel_no, email, school_id, status) VALUES (?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, SchoolAdmin.getStaffNo());
                pstmt.setString(2, SchoolAdmin.getName());
                pstmt.setString(3, SchoolAdmin.getMobileTelNo());
                pstmt.setString(4, SchoolAdmin.getEmail());
                pstmt.setString(5, School.getSchoolId());
                pstmt.setString(6, SchoolAdmin.getStatus());
                pstmt.executeUpdate();

                sql = "INSERT INTO tbl_school(school_id, school_name, building_location, office_no) VALUES (?,?,?,?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, School.getSchoolId());
                pstmt.setString(2, School.getSchoolName());
                pstmt.setString(3, School.getBuildingLocation());
                pstmt.setString(4, School.getOfficeNo());
                pstmt.executeUpdate();

                sql = "INSERT INTO tbl_room(room_id, description, capacity, room_type_id, school_id) VALUES (?,?,?,?,?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, Room.getRoomId());
                pstmt.setString(2, Room.getDescription());
                pstmt.setInt(3, Room.getCapacity());
                pstmt.setString(4, RoomType.getRoom_type_id());
                pstmt.setString(5, School.getSchoolId());
                pstmt.executeUpdate();

                sql = "INSERT INTO tbl_roomType(room_type_id, room_type) VALUES (?,?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, RoomType.getRoom_type_id());
                pstmt.setString(2, RoomType.getRoomType());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is for check if the school admin exist for the school
     *
     * @param school_id The unique id of the school
     * @return status Notify whether the school admin exists or not
     */
    public static String checkSchoolAdminAvailable(String school_id) {
        String status = "available";
        String sql = "SELECT 1 FROM tbl_school_admin WHERE school_id ='" + School.getSchoolId() + "'";
        Connection conn = SQLite.connect();
        try {
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs == null) {
                    status = "existed";
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    /**
     * This method is for check the application status of the school admin candidate
     *
     * @param staffNo The staff no of the school admin candidate
     * @return status Notify whether the school admin exists or not
     */
    public static String checkApplication(String staffNo) {
        String status = null;
        String sql = "SELECT status FROM tbl_school_admin WHERE staff_no = '" + staffNo + "'";
        Connection conn = SQLite.connect();
        try {
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                status = rs.getString("status");
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    /**
     * This method is for search the school id that the school admin belongs to
     *
     * @param staff_no The staff no of the school admin
     * @return school_id The unique id of the school
     */
    public static String searchSchoolId(String staff_no) {
        String school_id = null;
        String sql = "SELECT school_id FROM tbl_school_admin WHERE staff_no ='" + SchoolAdmin.getStaffNo() + "'";
        try {
            Connection conn = SQLite.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                school_id = rs.getString("school_id");
            }
            conn.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return school_id;
    }

    /**
     * This method is for insert the new room data to the database
     */
    public static void insertRoom() {
        try {
            Connection conn = SQLite.connect();
            PreparedStatement pstmt;
            String sql = "INSERT INTO tbl_room(room_id, description, capacity, school_id, room_type_id) VALUES (?,?,?,?,?)";

            if (conn != null) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, Room.getRoomId());
                pstmt.setString(2, Room.getDescription());
                pstmt.setInt(3, Room.getCapacity());
                pstmt.setString(4, School.getSchoolId());
                pstmt.setString(5, RoomType.getRoom_type_id());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is for list the meeting room that the school has
     *
     * @return data The list of the meeting room
     */
    public static String listMeetingRoom() {
        String data = "";
        Connection conn = SQLite.connect();
        String sql = "SELECT * FROM tbl_room WHERE school_id = '" + School.getSchoolId() + "'";
        try {
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String room_id = rs.getString("room_id");
                    String description = rs.getString("description");
                    int capacity = rs.getInt("capacity");
                    String school_id = rs.getString("school_id");
                    String room_type_id = rs.getString("room_type_id");

                    data = data + "\n\nRoom ID: " + room_id +
                            "\nDescription: " + description +
                            "\nMax. Capacity: " + capacity +
                            "\nSchool ID: " + school_id +
                            "\nRoom Type ID: " + room_type_id;
                }
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    /**
     * This method is for select a meeting room for the update
     *
     * @param roomId The id of the room selected by the school admin for update
     * @return data All the room info with the room id
     */
    public static String selectMeetingRoom(String roomId) {
        String data = "";
        String sql = "SELECT * FROM tbl_room WHERE room_id = '" + roomId + "'";
        Connection conn = SQLite.connect();
        try {
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Room.setRoomId(rs.getString("room_id"));
                    Room.setDescription(rs.getString("description"));
                    Room.setCapacity(rs.getInt("capacity"));
                    RoomType.setRoomTypeId(rs.getString("room_type_id"));

                    data = "Reply the number for the data you want to update\n" +
                            "\n1. Room ID: " + Room.getRoomId() +
                            "\n2. Description: " + Room.getDescription() +
                            "\n3. Max. Capacity: " + Room.getCapacity() +
                            "\n4. Room Type ID: " + RoomType.getRoom_type_id();
                }
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    /**
     * This method is for update the info of the room in the database
     *
     * @param select  The info selected by the school admin for update
     * @param updated The updated info to replace the old info in the database
     */
    public static void updateRoom(int select, String updated) {
        String sql = null;
        switch (select) {
            case 1:
                sql = "UPDATE tbl_room SET room_id='" + updated + "'" + " WHERE room_id='" + Room.getRoomId() + "'";
                break;
            case 2:
                sql = "UPDATE tbl_room SET description='" + updated + "'" + " WHERE room_id='" + Room.getRoomId() + "'";
                break;
            case 3:
                sql = "UPDATE tbl_room SET capacity='" + updated + "'" + " WHERE room_id='" + Room.getRoomId() + "'";
                break;
            case 4:
                sql = "UPDATE tbl_room SET room_type_id='" + updated + "'" + " WHERE room_id='" + Room.getRoomId() + "'";
                break;
        }
        Connection conn = SQLite.connect();
        try {
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is for check username exist in database or not for system admin
     * @param username1 The username of the system admin
     * @return response Notify whether the username exists or not
     */
    public static String checkUsername(String username1) {
        String response = "";
        String sql = "SELECT * FROM tbl_system_admin WHERE username = '" + username1 + "'";
        try {
            Connection conn = SQLite.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.getString("username") != null) {
                response = "Available";
            } else {
                response = "No";
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    /**
     * This method is for check password correct or not for system admin
     * @param password1 The password of the system admin
     * @return response Notify whether the password correct or not
     */
    public static String checkPassword(String password1) {
        String response = "";
        String sql = "SELECT * FROM tbl_system_admin WHERE password = '" + password1 + "'";
        try {
            Connection conn = SQLite.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.getString("password") != null) {
                response = "Available";
            } else {
                response = "No";
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    /**
     * This method is for display the list of school admin candidate who have applied but not yet approved
     *
     * @return waitingList The list of the candidates who have applied
     */
    public static String waitingApproval() {
        String waitingList = "";
        String sql = "SELECT * FROM tbl_school_admin WHERE status == 'Applied' ";
        Connection conn = SQLite.connect();
        try {
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String waitingList2 = "\n\nStaff No: " + rs.getString("staff_no") +
                            "\nName: " + rs.getString("name") +
                            "\nPhone NO: " + rs.getString("mobile_tel_no") +
                            "\nEmail: " + rs.getString("email") +
                            "\nSchool ID: " + rs.getString("school_id") +
                            "\nStatus: " + rs.getString("status");

                    StringBuffer buffer = new StringBuffer(waitingList2);
                    buffer.append(waitingList2);
                    waitingList = waitingList + waitingList2;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return waitingList;
    }

    /**
     * This method is for select a school admin candidate for approve
     *
     * @param staff_no The staff no of the candidate
     * @return data All the info about the candidate
     */
    public static String selectSchoolAdmin(String staff_no) {
        String data = "";
        String sql = "SELECT * FROM tbl_school_admin WHERE staff_no = '" + staff_no + "'";
        Connection conn = SQLite.connect();
        try {
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    SchoolAdmin.setStaffNo(rs.getString("staff_no"));
                    SchoolAdmin.setStaffName(rs.getString("name"));
                    SchoolAdmin.setMobileTelNo(rs.getString("mobile_tel_no"));
                    SchoolAdmin.setEmail(rs.getString("email"));
                    School.setSchoolId(rs.getString("school_id"));
                    SchoolAdmin.setStatus(rs.getString("status"));

                    data = "This is the candidate you want to approve to be an school admin\n" +
                            "\nStaff No: " + SchoolAdmin.getStaffNo() +
                            "\nName: " + SchoolAdmin.getName() +
                            "\nPhone No: " + SchoolAdmin.getMobileTelNo() +
                            "\nEmail: " + SchoolAdmin.getEmail() +
                            "\nSchool ID: " + School.getSchoolId() +
                            "\nStatus: " + SchoolAdmin.getStatus();
                }
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    /**
     * This method is for update the status of the candidate from applied to approved
     *
     * @param staff_no The staff no of the candidate
     */
    public static void updateApproval(String staff_no) {
        String sql = sql = "UPDATE tbl_school_admin SET status = 'Approved' WHERE staff_no='" + SchoolAdmin.getStaffNo() + "'";
        Connection conn = SQLite.connect();
        try {
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is for delete the data after 1 week
     *
     * @author Sookqichow
     */
    public static void deleteDataAfterAWeek() {
        try {
            String sql = "DELETE FROM tbl_booking WHERE booking_date <= date('now','-7 day') ";
            Connection conn = SQLite.connect();
            if (conn != null) {
                Statement stmt;
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                System.out.println("All booking in pass 7 days was deleted successfully.");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}