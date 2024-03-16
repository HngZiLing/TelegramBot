package my.uum;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * This class is for telegram bot function
 *
 * @author ChunLoon, Hngziling
 */
public class STIW3054_Fivesome_bot extends TelegramLongPollingBot {

    /**
     * This method is for return bot username
     * @return bot username
     */
    @Override
    public String getBotUsername() {
        // TODO
        return "STIW3054_Fivesome_bot";
    }

    /**
     * This method is for return bot token
     * @return bot token
     */
    @Override
    public String getBotToken() {
        // TODO
        return "5824748087:AAEpGfeA_JQJS52IgVjkQciWZFBRF30XpSI";
    }

    String role = null, approvedAdmin = "false";
    int stepBook = 0, stepCancel = 0, stepEdit = 0, stepApply = 0, stepCheck = 0, stepVerify = 0, stepRegister = 0, stepUpdate = 0, stepConfirm = 0;

    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        String reply = String.valueOf(message.getText());
        String text = message.getText();
        String chatId = message.getChatId().toString();
        SendMessage sendMessage = new SendMessage();

        if (text.equals("/start") || text.equals("0") || text.equals("/systemadmin") || text.equals("/schooladmin") || text.equals("/user")) {
            switch (text) {
                case "0":
                case "/start": {
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Hello, Welcome to STIW3054_Fivesome_bot\n" +
                            "Please choose your role\n\n" +
                            "Click /systemadmin if you want to manage application\n" +
                            "Click /schooladmin if you are an applied school admin\n" +
                            "Click /user if you want to manage booking or apply to be an admin");
                    break;
                }
                case "/systemadmin": {
                    role = "systemadmin";
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Hello system admin,\n\n" +
                            "First please provide your username for identity verification");
                    stepConfirm = 1;
                    break;
                }
                case "/schooladmin": {
                    role = "schooladmin";
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Hello school admin,\n\n" +
                            "First please provide your staff no for identity verification");
                    stepVerify = 1;
                    break;
                }
                case "/user": {
                    role = "user";
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Hello user,\n\n" +
                            "Click /booking if you want book a meeting room\n" +
                            "Click /cancel if you want to cancel a meeting room\n" +
                            "Click /edit if you want to edit your profile info and booking info\n" +
                            "Click /list if you want to display the list of users\n" +
                            "Click /apply if you are interested in becoming a school admin");
                    break;
                }
            }
        } else if (text.equals("/booking") || text.equals("/cancel") || text.equals("/list") || text.equals("/edit") || text.equals("/apply")) { //command
            if (role == "user") {
                switch (text) {
                    case "/cancel": {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("May I know your email?\n\nReply 0: Back to Main Menu");
                        stepCancel = 1;
                        break;
                    }
                    case "/list": {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText(SQLite.list());
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Hello user,\n\n" +
                                "Click /booking if you want book a meeting room\n" +
                                "Click /cancel if you want to cancel a meeting room\n" +
                                "Click /edit if you want to edit your profile info and booking info\n" +
                                "Click /list if you want to display the list of users\n" +
                                "Click /apply if you are interested in becoming a school admin");
                        break;
                    }
                    case "/booking": {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Please provide a preferred booking date for meeting room \n(eg: 25.12.2022)(DD-MM-YYYY)\n\n" +
                                "Reply 0: Back to Main Menu");
                        stepBook = 1;
                        break;
                    }
                    case "/edit": {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("May I know your email?\n\nReply 0: Back to Main Menu");
                        stepEdit = 1;
                        break;
                    }
                    case "/apply": {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("I request some information from you to apply for school admin");
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("First at all, may I know which of the following is your school? Choose one from 1 to 6 below\n\n" +
                                "1: SOC\n" +
                                "2: SMMTC\n" +
                                "3: SAPSP\n" +
                                "4: SLCP\n" +
                                "5: SCIMPA\n" +
                                "6: SQS");
                        stepApply = 1;
                        break;
                    }
                }
            } else {
                sendMessage.setChatId(chatId);
                sendMessage.setText("Sorry, you are not eligible to perform.\n" +
                        "Please select your correct role before operation\n\n" +
                        "Click /systemadmin if you want to manage application\n" +
                        "Click /schooladmin if you are an applied school admin\n" +
                        "Click /user if you want to manage booking or apply to be an admin");
            }
        } else if (text.equals("/registerroom") || text.equals("/updateroom") || text.equals("/checkstatus")) { //command
            if (approvedAdmin == "true") {
                switch (text) {
                    case "/registerroom": {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("May I know the room id you want to register?\n\nReply 0: Back to Main Menu");
                        stepRegister = 1;
                        break;
                    }
                    case "/updateroom": {
                        sendMessage.setChatId(chatId);
                        School.setSchoolId(SQLite.searchSchoolId(SchoolAdmin.getStaffNo()));
                        sendMessage.setText("May you provide your school ID?");
                        stepUpdate = 1;
                        break;
                    }
                    case "/checkstatus": {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Please provide your staff no for me to check");
                        stepCheck = 1;
                        break;
                    }
                }
            } else {
                sendMessage.setChatId(chatId);
                sendMessage.setText("Sorry, you are not eligible to perform.\n" +
                        "Please select your correct role before operation\n\n" +
                        "Click /systemadmin if you want to manage application\n" +
                        "Click /schooladmin if you are an applied school admin\n" +
                        "Click /user if you want to manage booking or apply to be an admin");
            }
        } //end choose command

        else if (message.hasText()) {
            switch (stepBook) {
                case 1: {
                    sendMessage.setChatId(chatId);
                    if (reply.length() == 10 & reply.matches(".*[.]+.*")) {
                        Booking_info.setBooking_Date(reply);
                        stepBook = 2;
                        sendMessage.setText("Please enter start  time of booking meeting room.\n(In 24hour format eg:11:20, 09:20, 14:00)\n\nReply 0: Back to Main Menu");
                    } else {
                        sendMessage.setText("Invalid date format. Please insert again your Booking Date.\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 2: {
                    if (reply.length() == 5 & reply.matches(".*[:]+.*")) {
                        Booking_info.setBooking_StartTime(reply);
                        covertStartTime(reply);
                        stepBook = 3;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Please enter end  time  of booking meeting room.\n(In 24hour format eg:11:20, 09:20, 14:00)\n\nReply 0: Back to Main Menu");
                    } else {
                        stepBook = 2;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Invalid time format. Please enter start  time  of booking meeting room again.\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 3: {
                    if (reply.length() == 5 & reply.matches(".*[:]+.*")) {
                        covertEndTime(reply);
                        if (Time.getStartTime() > Time.getEndTime()) {
                            sendMessage.setChatId(chatId);
                            sendMessage.setText("End time must be greater than end time.Please enter end time  of booking meeting room again.\n\nReply 0: Back to Main Menu");
                        } else {
                            Booking_info.setBooking_EndTime(reply);
                            stepBook = 4;
                            sendMessage.setChatId(chatId);
                            sendMessage.setText("May I know the purpose of you booking?\n\n" +
                                    "Reply 1: Group Discussion\n" +
                                    "Reply 2: Hold Meeting\n" +
                                    "Reply 3: Conduct lecture\n" +
                                    "Reply 4: Take a quiz\n" +
                                    "Reply 5: Use equipment\n\n" +
                                    "Reply 0: Back to Main Menu");
                        }
                    } else {
                        stepBook = 3;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Invalid time format. Please enter end time  of booking meeting room again.\n");
                    }
                    break;
                }
                case 4: {
                    sendMessage.setChatId(chatId);
                    if (reply.equals("1") || reply.equals("2") || reply.equals("3") || reply.equals("4") || reply.equals("5")) {
                        switch (reply) {
                            case "1":
                                //   purpose = "Group Discussion";
                                Booking_info.setPurpose("Group Discussion");
                                break;
                            case "2":
                                // purpose = "Hold Meeting";
                                Booking_info.setPurpose("Hold Meeting");
                                break;
                            case "3":
                                // purpose = "Conduct lecture";
                                Booking_info.setPurpose("Conduct lecture");
                                break;
                            case "4":
                                //  purpose = "Take a quiz";
                                Booking_info.setPurpose("Take a quiz");
                                break;
                            case "5":
                                // purpose = "Use equipment";
                                Booking_info.setPurpose("Use equipment");
                                break;
                        }
                        stepBook = 24;
                        String rr = SQLite.displaySchool();
                        sendMessage.setText("Before booking a room, you need choose the building location.\nThis is a list for school:\n" + rr + "\nPlease Insert School Id you want (eg:S11)\n\n\nReply 0: Back to Main Menu");
                    } else {
                        stepBook = 4;
                        sendMessage.setText("Please enter 1-5 only\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 5: {
                    Booking_info.setRoomId(reply);
                    String rr = SQLite.selectRoom(reply);
                    if (rr.equals("Invalid room ID")) {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Invalid room ID, please enter again. \n\nReply 0: Back to Main Menu");
                    } else {
                        stepBook = 6;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Kindly provide your email address?\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 6: {
                    sendMessage.setChatId(chatId);
                    if (reply.matches(".*[@]+.*")) {
                        User_list.setEmail(reply);
                        stepBook = 7;
                        sendMessage.setText("May I have your full name please?\n\nReply 0: Back to Main Menu");
                        sendMessage.setChatId(chatId);
                    } else {
                        stepBook = 6;
                        sendMessage.setText("Email need to contains @. Please enter again your email.\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 7: {
                    sendMessage.setChatId(chatId);
                    User_list.setName(reply);
                    stepBook = 8;
                    sendMessage.setText("First at all, may I know your ic number?\n(eg:111111-08-1234)\n\nReply 0: Back to Main Menu");
                    break;
                }
                case 8: {
                    sendMessage.setChatId(chatId);
                    if (reply.matches(".*[a-zA-Z]+.*")) { //if contain a-z *
                        sendMessage.setText("Ic number should not contain alphabet, please enter again\n\nReply 0: Back to Main Menu");
                    } else {
                        if (reply.length() == 14) {
                            // ic = reply;
                            User_list.setICNO(reply);
                            stepBook = 9;
                            sendMessage.setText("How about your telephone number?\n\nReply 0: Back to Main Menu");
                        } else {
                            sendMessage.setText("Failed, please enter 14 digit number including '-'\n\nReply 0: Back to Main Menu");
                        }
                    }
                    break;
                }
                case 9: {
                    sendMessage.setChatId(chatId);
                    if (reply.length() < 10 || reply.length() > 11) {
                        sendMessage.setText("Please enter 10 or 11 digit phone number\n\nReply 0: Back to Main Menu");
                    } else {
                        User_list.setMobile_TelNo(reply);
                        stepBook = 10;
                        sendMessage.setText("Kindly provide your password, this will be needed when cancel or edit booking. (Need to more than 4 value and contain alphabet and number)\n(eg:1111A)\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 10: {
                    sendMessage.setChatId(chatId);
                    if (reply.length() > 4 && reply.matches(".*[a-zA-Z]+.*") && reply.matches(".*[1-9]+.*")) {
                        User_list.setPassword(reply);
                        stepBook = 14;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Pls check your info \n\nReply 1: Yes \n\nReply 0: Back to Main Menu");
                    } else {
                        stepBook = 10;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Pls enter again your password (Need to more than 4 value and contain alphabet and number)\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 11: {
                    sendMessage.setChatId(chatId);
                    if (reply.equals("1")) {
                        SQLite.insertBooking(Booking_info.getPurpose(), Booking_info.getBooking_Date(), Booking_info.getBooking_StartTime(), Booking_info.getBooking_EndTime(), Booking_info.getRoomId());
                        SQLite.insert_User_Info(User_list.getEmail(), User_list.getICNO(), User_list.getName(), User_list.getMobile_TelNo(), User_list.getPassword());
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Booking successfully");
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        stepBook = 0;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Click /booking if you want book a meeting room\n" +
                                "Click /cancel if you want to cancel a meeting room\n" +
                                "Click /list if you want to display the list of users");

                    } else if (reply.equals("2")) {
                        stepBook = 12;
                        String roomInfo = SQLite.roomInfo(Booking_info.getRoomId());
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("What number you want edit? Please enter the number \n" +
                                "\nReply 1: Booking Info&Room Info " + "\nRoom Id: " + Booking_info.getRoomId() + roomInfo + "\nPurpose: " + Booking_info.getPurpose() + "\nDate: " + Booking_info.getBooking_Date()
                                + "\nStart Time: " + Booking_info.getBooking_StartTime() + "\nEnd Time: " + Booking_info.getBooking_EndTime() +
                                "\n\nUser Info: " + "\nReply 2: IC no: " + User_list.getICNO() + "\nReply 3: Name: " + User_list.getName() +
                                "\nReply 4: Tel no: " + User_list.getMobile_TelNo() + "\nReply 5: Email: " + User_list.getEmail() + "\nReply 6: Password: " + User_list.getPassword() +
                                "\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 12: {
                    sendMessage.setChatId(chatId);
                    if (reply.equals("1")) {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Please provide a preferred booking date for meeting room? \n(eg: 25.12.2022)\n\n" +
                                "Reply 0: Back to Main Menu");
                        stepBook = 19;
                        break;
                    } else if (reply.equals("2") || reply.equals("3") || reply.equals("4") || reply.equals("5") || reply.equals("6")) {
                        switch (reply) {
                            case "2":
                                stepBook = 13;
                                sendMessage.setText("Kindly provide your ICNo you want update.\n\nReply 0: Back to Main Menu");
                                break;
                            case "3":
                                stepBook = 15;
                                sendMessage.setText("Kindly provide your name you want update.\n\nReply 0: Back to Main Menu");
                                break;
                            case "4":
                                stepBook = 16;
                                sendMessage.setText("Kindly provide your TelNo you want update.\n\nReply 0: Back to Main Menu");
                                break;
                            case "5":
                                stepBook = 17;
                                sendMessage.setText("Kindly provide your Email you want update.\n\nReply 0: Back to Main Menu");
                                break;
                            case "6":
                                stepBook = 18;
                                sendMessage.setText("Kindly provide your Password you want update.\n\nReply 0: Back to Main Menu");
                                break;
                        }
                    }
                    break;
                }
                case 13: {
                    sendMessage.setChatId(chatId);
                    if (reply.matches(".*[a-zA-Z]+.*")) { //if contain a-z *
                        sendMessage.setText("Ic number should not contain alphabet, please enter again\n\nReply 0: Back to Main Menu");
                    } else {
                        if (reply.length() == 14) {
                            User_list.setICNO(reply);
                            sendMessage.setText("Update Successfully" + "\nReply 0: Back to Main Menu");
                            stepBook = 14;
                            sendMessage.setText("Pls check your info \n\nReply 1: Yes \n\nReply 0: Back to Main Menu");
                        } else {
                            sendMessage.setText("Failed, please enter 14 digit number including '-'\n\nReply 0: Back to Main Menu");
                        }
                    }
                    break;
                }
                case 14: {
                    sendMessage.setChatId(chatId);
                    if (reply.equals("1")) {
                        String roomInfo = SQLite.roomInfo(Booking_info.getRoomId());
                        sendMessage.setText("This is your booking detail\n" +
                                "\nRoom Info: " + "\nRoom Id: " + Booking_info.getRoomId() + roomInfo + "\n\nBooking Info: " + "\nPurpose: " + Booking_info.getPurpose() + "\nDate: " + Booking_info.getBooking_Date()
                                + "\nStart Time: " + Booking_info.getBooking_StartTime() + "\nEnd Time: " + Booking_info.getBooking_EndTime() +
                                "\n\nUser Info: " + "\nIC no: " + User_list.getICNO() + "\nName: " + User_list.getName() +
                                "\nTel no: " + User_list.getMobile_TelNo() + "\nEmail: " + User_list.getEmail() + "\nPassword: " + User_list.getPassword() +
                                "\n\nAre these correct?\n" +
                                "Reply 1: Yes\nReply 2: No, I would like to make a correction \n\nReply 0: Back to Main Menu");
                        stepBook = 11;
                        break;
                    }
                }
                case 15: {
                    sendMessage.setChatId(chatId);
                    User_list.setName(reply);
                    sendMessage.setText("Update Successfully" + "\nReply 0: Back to Main Menu");
                    stepBook = 14;
                    sendMessage.setText("Pls check your info \n\nReply 1: Yes \n\nReply 0: Back to Main Menu");
                    break;
                }
                case 16: {
                    sendMessage.setChatId(chatId);
                    if (reply.length() < 10 || reply.length() > 11) {
                        // stepBook = 16;
                        sendMessage.setText("Please enter 10 or 11 digit phone number\n\nReply 0: Back to Main Menu");
                    } else {
                        User_list.setMobile_TelNo(reply);
                        stepBook = 14;
                        sendMessage.setText("Pls check your info \n\nReply 1: Yes \n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 17: {
                    sendMessage.setChatId(chatId);
                    if (reply.matches(".*[@]+.*")) {
                        User_list.setEmail(reply);
                        stepBook = 14;
                        sendMessage.setText("Update Successfully" + "\nReply 0: Back to Main Menu");
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Pls check your info \n\nReply 1: Yes \n\nReply 0: Back to Main Menu");
                    } else {
                        //  stepBook = 17;
                        sendMessage.setText("Email need to contains @. Please enter again your email.\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 18: {
                    sendMessage.setChatId(chatId);
                    if (reply.length() > 4 && reply.matches(".*[a-zA-Z]+.*") && reply.matches(".*[1-9]+.*")) {
                        User_list.setPassword(reply);
                        stepBook = 14;
                        sendMessage.setText("Update Successfully" + "\nReply 0: Back to Main Menu");
                        sendMessage.setText("Pls check your info \n\nReply 1: Yes \n\nReply 0: Back to Main Menu");
                    } else {
                        //  stepBook=18;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Pls enter again your password (Need to more than 4 value and contain alphabet and number)\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 19: {
                    if (reply.length() == 10 & reply.matches(".*[.]+.*")) {
                        Booking_info.setBooking_Date(reply);
                        stepBook = 20;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Please enter start  time  of booking meeting room.\n(In 24hour format eg:11:20, 09:20, 14:00)\n\nReply 0: Back to Main Menu");
                    } else {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Invalid date format. Please insert again your Booking Date.\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 20: {
                    if (reply.length() == 5 & reply.matches(".*[:]+.*")) {
                        Booking_info.setBooking_StartTime(reply);
                        covertStartTime(reply);
                        stepBook = 21;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Please enter end  time  of booking meeting room.(eg: 11:20)\n");
                    } else {
                        stepBook = 20;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Invalid time format. Please enter start  time  of booking meeting room again.\n");
                    }
                    break;
                }
                case 21: {
                    if (reply.length() == 5 & reply.matches(".*[:]+.*")) {
                        covertEndTime(reply);
                        if (Time.getStartTime() > Time.getEndTime()) {
                            sendMessage.setChatId(chatId);
                            sendMessage.setText("End time must be greater than end time.Please enter end time  of booking meeting room again.\n\nReply 0: Back to Main Menu");
                        } else {
                            Booking_info.setBooking_EndTime(reply);
                            stepBook = 22;
                            sendMessage.setChatId(chatId);
                            sendMessage.setText("May I know the purpose of you booking?\n\n" +
                                    "Reply 1: Group Discussion\n" +
                                    "Reply 2: Hold Meeting\n" +
                                    "Reply 3: Conduct lecture\n" +
                                    "Reply 4: Take a quiz\n" +
                                    "Reply 5: Use equipment\n\n" +
                                    "Reply 0: Back to Main Menu");
                        }
                    } else {
                        stepBook = 21;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Invalid time format. Please enter end time  of booking meeting room again.\n");
                    }
                    break;
                }
                case 22: {
                    sendMessage.setChatId(chatId);
                    if (reply.equals("1") || reply.equals("2") || reply.equals("3") || reply.equals("4") || reply.equals("5")) {
                        switch (reply) {
                            case "1":
                                //   purpose = "Group Discussion";
                                Booking_info.setPurpose("Group Discussion");
                                break;
                            case "2":
                                // purpose = "Hold Meeting";
                                Booking_info.setPurpose("Hold Meeting");
                                break;
                            case "3":
                                // purpose = "Conduct lecture";
                                Booking_info.setPurpose("Conduct lecture");
                                break;
                            case "4":
                                //  purpose = "Take a quiz";
                                Booking_info.setPurpose("Take a quiz");
                                break;
                            case "5":
                                // purpose = "Use equipment";
                                Booking_info.setPurpose("Use equipment");
                                break;
                        }
                        stepBook = 23;
                        String rr = SQLite.displaySchool();
                        sendMessage.setText("Before booking a room, you need choose the building location.\nThis is a list for school:\n" + rr + "\nPlease Insert School Id you want (eg:S11)\n\n\nReply 0: Back to Main Menu");
                    } else {
                        sendMessage.setText("Please enter 1-5 only\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 23: {
                    sendMessage.setChatId(chatId);
                    String rr = SQLite.displayAvailableRoom(reply);
                    if (rr.equals("Invalid school ID")) {
                        sendMessage.setText(rr + ". Please enter again\n\nReply 0: Back to Main Menu");
                    } else {
                        stepBook = 25;
                        sendMessage.setText("This is a  list for available room:\n" + rr + "\nPlease Insert Room Id you want \n\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 24: {
                    sendMessage.setChatId(chatId);
                    String rr = SQLite.displayAvailableRoom(reply);
                    if (rr.equals("Invalid school ID")) {
                        sendMessage.setText(rr + "Please enter school ID again\n\nReply 0: Back to Main Menu");
                    } else {
                        stepBook = 5;
                        sendMessage.setText("This is a  list for available room:\n" + rr + "\n\nPlease Insert Room Id you want \n\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 25: {
                    Booking_info.setRoomId(reply);
                    String rr = SQLite.selectRoom(reply);
                    if (rr.equals("Invalid room ID")) {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Invalid room ID, please enter again. \n\nReply 0: Back to Main Menu");
                    } else {
                        sendMessage.setChatId(chatId);
                        stepBook = 14;
                        sendMessage.setText("Update Successfully.\nPls check your info \n\nReply 1: Yes \n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
            }

            switch (stepCancel) {
                case 1: {
                    User_list.setEmail(reply);
                    sendMessage.setChatId(chatId);
                    String rr = SQLite.select1(User_list.getEmail());
                    if (rr.equals("No")) {
                        sendMessage.setText("Sorry, I can't find the booked meeting room with this email, please enter again\n\nReply 0: Back to Main Menu");
                    } else {
                        stepCancel = 2;
                        sendMessage.setText("Please provide your password\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 2: {
                    User_list.setPassword(reply);
                    sendMessage.setChatId(chatId);
                    String rr = SQLite.selectPass(User_list.getPassword());
                    if (rr.equals("No")) {
                        sendMessage.setText("Sorry, your password is wrong, please enter again\n\nReply 0: Back to Main Menu");
                    } else {
                        stepCancel = 3;
                        sendMessage.setText("I found your booking detail\n" + SQLite.selectPass(reply) +
                                "\nDo you want to cancel your booking?\nReply 1 : Yes \nReply 2: No \n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 3: {
                    sendMessage.setChatId(chatId);
                    if (reply.equals("1")) {
                        SQLite.delete();
                        sendMessage.setText("Booking record was deleted successfully!");
                    } else if (reply.equals("2")) {
                        stepCancel = 0;
                        sendMessage.setText("Stop canceling meeting room booking records");
                    }
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Click /booking if you want book a meeting room\n" +
                            "Click /cancel if you want to cancel a meeting room\n" +
                            "Click /list if you want to display the list of users");
                    break;
                }
            }

            switch (stepEdit) {
                case 1: {
                    sendMessage.setChatId(chatId);
                    User_list.setEmail(reply);
                    String rr = SQLite.select1(User_list.getEmail());
                    if (rr.equals("No")) {
                        sendMessage.setText("Sorry, I can't find the booked meeting room with this email,please enter again\n\nReply 0: Back to Main Menu");
                    } else {
                        stepEdit = 2;
                        sendMessage.setText("Please provide your password\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 2: {
                    User_list.setPassword(reply);
                    sendMessage.setChatId(chatId);
                    String rr = SQLite.editDisplay(User_list.getPassword());
                    if (rr.equals("No")) {
                        sendMessage.setText("Sorry, your password is wrong, please enter again\n\nReply 0: Back to Main Menu");
                    } else {
                        stepEdit = 3;
                        sendMessage.setText("I found your booking detail\n" + rr +
                                "\n\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 3: {
                    sendMessage.setChatId(chatId);
                    if (reply.equals("1")) {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Please provide a preferred booking date for meeting room? \n(eg: 25.12.2022)\n\n" +
                                "Reply 0: Back to Main Menu");
                        stepEdit = 10;
                        break;
                    } else if (reply.equals("2") || reply.equals("3") || reply.equals("4") || reply.equals("5") || reply.equals("6")) {
                        switch (reply) {
                            case "2":
                                stepEdit = 4;
                                sendMessage.setText("Kindly provide your ICNo you want update.\n\nReply 0: Back to Main Menu");
                                break;
                            case "3":
                                stepEdit = 5;
                                sendMessage.setText("Kindly provide your name you want update.\n\nReply 0: Back to Main Menu");
                                break;
                            case "4":
                                stepEdit = 6;
                                sendMessage.setText("Kindly provide your TelNo you want update.\n\nReply 0: Back to Main Menu");
                                break;
                            case "5":
                                stepEdit = 7;
                                sendMessage.setText("Kindly provide your Password you want update.\n\nReply 0: Back to Main Menu");
                                break;
                        }
                    }
                    break;
                }
                case 4: {
                    sendMessage.setChatId(chatId);
                    if (reply.matches(".*[a-zA-Z]+.*")) { //if contain a-z *
                        sendMessage.setText("Ic number should not contain alphabet, please enter again\n\nReply 0: Back to Main Menu");
                    } else {
                        if (reply.length() == 14) {
                            User_list.setICNO(reply);
                            SQLite.editIC(reply);
                            stepEdit = 9;
                            sendMessage.setText("Update Successfully \n\nPls check your info \n\nReply 1: Yes \n\nReply 0: Back to Main Menu");
                        } else {
                            sendMessage.setText("Failed, please enter 14 digit number including '-'\n\nReply 0: Back to Main Menu");
                        }
                    }
                    break;
                }
                case 5: {
                    sendMessage.setChatId(chatId);
                    User_list.setName(reply);
                    SQLite.editName(reply);
                    stepEdit = 9;
                    sendMessage.setText("Update Successfully \n\nPls check your info \n\nReply 1: Yes \n\nReply 0: Back to Main Menu");
                    break;
                }
                case 6: {
                    sendMessage.setChatId(chatId);
                    if (reply.length() < 10 || reply.length() > 11) {
                        sendMessage.setText("Please enter 10 or 11 digit phone number\n\nReply 0: Back to Main Menu");
                    } else {
                        User_list.setMobile_TelNo(reply);
                        SQLite.editTelNo(reply);
                        stepEdit = 9;
                        sendMessage.setText("Update Successfully \n\nPls check your info \n\nReply 1: Yes \n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 7: {
                    sendMessage.setChatId(chatId);
                    if (reply.length() > 4 && reply.matches(".*[a-zA-Z]+.*") && reply.matches(".*[1-9]+.*")) {
                        User_list.setPassword(reply);
                        SQLite.editPass(reply);
                        stepEdit = 9;
                        sendMessage.setText("Update Successfully \n\nPls check your info \n\nReply 1: Yes \n\nReply 0: Back to Main Menu");
                    } else {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Pls enter again your password (Need to more than 4 value and contain alphabet and number)\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 9: {
                    sendMessage.setChatId(chatId);
                    if (reply.equals("1")) {
                        String rr = SQLite.editDisplay(User_list.getPassword());
                        stepEdit = 3;
                        sendMessage.setText("Booking detail\n" + rr +
                                "\nPlease enter the number, if you want edit again \n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 10: {
                    if (reply.length() == 10 & reply.matches(".*[.]+.*")) {
                        Booking_info.setBooking_Date(reply);
                        stepEdit = 11;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Please enter start  time  of booking meeting room.\n(In 24hour format eg:11:20, 09:20, 14:00)\n\nReply 0: Back to Main Menu");
                    } else {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Invalid date format. Please insert again your Booking Date.\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 11: {
                    if (reply.length() == 5 & reply.matches(".*[:]+.*")) {
                        Booking_info.setBooking_StartTime(reply);
                        covertStartTime(reply);
                        stepEdit = 13;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Please enter end  time  of booking meeting room.(eg: 11:20)\n");
                    } else {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Invalid time format. Please enter start  time  of booking meeting room again.\n");
                    }
                    break;
                }
                case 13: {
                    if (reply.length() == 5 & reply.matches(".*[:]+.*")) {
                        covertEndTime(reply);
                        if (Time.getStartTime() > Time.getEndTime()) {
                            sendMessage.setChatId(chatId);
                            sendMessage.setText("End time must be greater than end time.Please enter end time  of booking meeting room again.\n\nReply 0: Back to Main Menu");
                        } else {
                            Booking_info.setBooking_EndTime(reply);
                            stepEdit = 14;
                            sendMessage.setChatId(chatId);
                            sendMessage.setText("May I know the purpose of you booking?\n\n" +
                                    "Reply 1: Group Discussion\n" +
                                    "Reply 2: Hold Meeting\n" +
                                    "Reply 3: Conduct lecture\n" +
                                    "Reply 4: Take a quiz\n" +
                                    "Reply 5: Use equipment\n\n" +
                                    "Reply 0: Back to Main Menu");
                        }
                    } else {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Invalid time format. Please enter end time  of booking meeting room again.\n");
                    }
                    break;
                }
                case 14: {
                    sendMessage.setChatId(chatId);
                    if (reply.equals("1") || reply.equals("2") || reply.equals("3") || reply.equals("4") || reply.equals("5")) {
                        switch (reply) {
                            case "1":
                                //   purpose = "Group Discussion";
                                Booking_info.setPurpose("Group Discussion");
                                break;
                            case "2":
                                // purpose = "Hold Meeting";
                                Booking_info.setPurpose("Hold Meeting");
                                break;
                            case "3":
                                // purpose = "Conduct lecture";
                                Booking_info.setPurpose("Conduct lecture");
                                break;
                            case "4":
                                //  purpose = "Take a quiz";
                                Booking_info.setPurpose("Take a quiz");
                                break;
                            case "5":
                                // purpose = "Use equipment";
                                Booking_info.setPurpose("Use equipment");
                                break;
                        }
                        stepEdit = 15;
                        String rr = SQLite.displaySchool();
                        sendMessage.setText("Before booking a room, you need choose the building location.\nThis is a list for school:\n" + rr + "\nPlease Insert School Id you want (eg:S11)\n\n\nReply 0: Back to Main Menu");
                    } else {
                        sendMessage.setText("Please enter 1-5 only\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 15: {
                    sendMessage.setChatId(chatId);
                    String rr = SQLite.displayAvailableRoom(reply);
                    if (rr.equals("Invalid school ID")) {
                        sendMessage.setText(rr + "Please enter again\n\nReply 0: Back to Main Menu");
                    } else {
                        stepEdit = 16;
                        sendMessage.setText("This is a  list for available room.\n" + rr + "\n\nPlease Insert Room Id you want \n\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 16: {
                    Booking_info.setRoomId(reply);
                    String rr = SQLite.selectRoom(reply);
                    if (rr.equals("Invalid room ID")) {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Invalid room ID, please enter again. \n\nReply 0: Back to Main Menu");
                    } else {
                        sendMessage.setChatId(chatId);
                        stepEdit = 9;
                        SQLite.editBooking(Booking_info.getBooking_Date(), Booking_info.getBooking_StartTime(), Booking_info.getBooking_EndTime(), Booking_info.getPurpose(), Booking_info.getRoomId());
                        sendMessage.setText("Update Successfully.\nPls check your info \n\nReply 1: Yes \n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
            }

            switch (stepApply) {
                case 1: {
                    if (SQLite.checkSchoolAdminAvailable(reply) != "existed") {
                        School.setSchoolId(reply);
                        School.setSchoolName(reply);
                        stepApply = 2;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("May I know your staff no?\n\nReply 0: Back to Main Menu");
                    } else {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Sorry you can't apply, your school already has a school admin");
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        stepApply = 0;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Click /systemadmin if you want to manage application\n" +
                                "Click /schooladmin if you are an applied school admin\n" +
                                "Click /user if you want to manage booking or apply to be an admin");
                    }
                    break;
                }
                case 2: {
                    SchoolAdmin.setStaffNo(reply);
                    stepApply = 3;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("May I know your name?\n\nReply 0: Back to Main Menu");
                    break;
                }
                case 3: {
                    SchoolAdmin.setStaffName(reply);
                    stepApply = 4;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("How about your mobile telephone no?\n\nReply 0: Back to Main Menu");
                    break;
                }
                case 4: {
                    SchoolAdmin.setMobileTelNo(reply);
                    stepApply = 5;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Kindly provide your email address?\n\nReply 0: Back to Main Menu");
                    break;
                }
                case 5: {
                    SchoolAdmin.setEmail(reply);
                    stepApply = 6;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("May I have your office telephone no please?\n\nReply 0: Back to Main Menu");
                    break;
                }
                case 6: {
                    School.setOfficeNo(reply);
                    stepApply = 7;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Please provide your school building location?\n\nReply 0: Back to Main Menu");
                    break;
                }
                case 7: {
                    School.setBuildingLocation(reply);
                    stepApply = 8;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("May I have your meeting room id?\n\nReply 0: Back to Main Menu");
                    break;
                }
                case 8: {
                    Room.setRoomId(reply);
                    stepApply = 9;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("How about your meeting room description?\n\nReply 0: Back to Main Menu");
                    break;
                }
                case 9: {
                    Room.setDescription(reply);
                    stepApply = 10;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Kindly provide your meeting room maximum capacity?\n\nReply 0: Back to Main Menu");
                    break;
                }
                case 10: {
                    Room.setCapacity(Integer.valueOf(reply));
                    stepApply = 11;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("May I know your meeting room type?\n\n" +
                            "1: Breakout room\n" +
                            "2: Meeting room\n" +
                            "3: Session room\n" +
                            "4: Conference room\n" +
                            "5: Training room\n" +
                            "6: Laboratory\n\n" +
                            "Reply 0: Back to Main Menu");
                    break;
                }
                case 11: {
                    RoomType.setRoomType(reply);
                    RoomType.setRoomTypeId(reply);
                    stepApply = 12;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("This is your application detail\n" +
                            "\nYOUR INFO" +
                            "\nStaff No: " + SchoolAdmin.getStaffNo() +
                            "\nName: " + SchoolAdmin.getName() +
                            "\nMobile no: " + SchoolAdmin.getMobileTelNo() +
                            "\nEmail: " + SchoolAdmin.getEmail() +
                            "\n\nYOUR SCHOOL" +
                            "\nSchool ID: " + School.getSchoolId() +
                            "\nSchool Name: " + School.getSchoolName() +
                            "\nOffice no: " + School.getOfficeNo() +
                            "\nBuilding Location: " + School.getBuildingLocation() +
                            "\n\nYOUR MEETING ROOM" +
                            "\nRoom ID: " + Room.getRoomId() +
                            "\nDescription: " + Room.getDescription() +
                            "\nMax. Capacity: " + Room.getCapacity() +
                            "\nRoom Type ID: " + RoomType.getRoom_type_id() +
                            "\nRoom Type: " + RoomType.getRoomType() + "\n\n" +
                            "Are these correct?\n" +
                            "Reply 1: Yes\n" +
                            "Reply 2: No, I would like to make a correction (Remind: You need to enter again from the beginning\n\n" +
                            "Reply 0: Back to Main Menu");
                    break;
                }
                case 12: {
                    if (reply.equals("1")) {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Apply successfully, please wait for approval");
                        SchoolAdmin.setStatus("Applied");
                        SQLite.insertSchoolAdmin();
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        stepApply = 0;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Click /systemadmin if you want to manage application\n" +
                                "Click /schooladmin if you are an applied school admin\n" +
                                "Click /user if you want to manage booking or apply to be an admin");
                    } else if (reply.equals("2")) {
                        stepApply = 1;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("First at all, may I know which of the following is your school? Choose one from 1 to 6 below\\n\\n\" +" +
                                "1: SOC\n" +
                                "2: SMMTC\n" +
                                "3: SAPSP\n" +
                                "4: SLCP\n" +
                                "5: SCIMPA\n" +
                                "6: SQS");
                    }
                    break;
                }
            }

            switch (stepCheck) {
                case 1: {
                    SchoolAdmin.setStaffNo(reply);
                    SchoolAdmin.setStatus(SQLite.checkApplication(reply));
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Staff no: " + SchoolAdmin.getStaffNo() +
                            "\nApplication status: " + SchoolAdmin.getStatus());
                    stepCheck = 0;
                    break;
                }
            }

            switch (stepVerify) {
                case 1: {
                    SchoolAdmin.setStaffNo(reply);
                    SchoolAdmin.setStatus(SQLite.checkApplication(reply));
                    sendMessage.setChatId(chatId);
                    if (SchoolAdmin.getStatus().equals("Approved")) {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText(
                                "Hello, you are an approved school admin and you are allow to perform the following task.\n\n" +
                                        "Click /registerroom if you want to register a new meeting room\n" +
                                        "Click /updateroom if you want to edit your current meeting room\n" +
                                        "Click /checkstatus if you want to check your application status");
                        approvedAdmin = "true";
                    } else {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Sorry, you are not an approved school admin.\n" +
                                "Please select your correct role before operation\n\n" +
                                "Click /systemadmin if you want to manage application\n" +
                                "Click /schooladmin if you are an applied school admin\n" +
                                "Click /user if you want to manage booking or apply to be an admin");
                    }
                    stepVerify = 0;
                }
            }

            switch (stepRegister) {
                case 1: {
                    Room.setRoomId(reply);
                    School.setSchoolId(SQLite.searchSchoolId(Room.getRoomId()));
                    stepRegister = 2;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Kindly provide your room description.\n\nReply 0: Back to Main Menu");
                    break;
                }
                case 2: {
                    Room.setDescription(reply);
                    stepRegister = 3;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("How about your room maximum capacity.\n\nReply 0: Back to Main Menu");
                    break;
                }
                case 3: {
                    Room.setCapacity(Integer.valueOf(reply));
                    stepRegister = 4;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("May I know your meeting room type?\n\n" +
                            "1: Breakout room\n" +
                            "2: Meeting room\n" +
                            "3: Session room\n" +
                            "4: Conference room\n" +
                            "5: Training room\n" +
                            "6: Laboratory\n\n" +
                            "Reply 0: Back to Main Menu");
                    break;
                }
                case 4: {
                    RoomType.setRoomTypeId(reply);
                    stepRegister = 5;
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("This is the room detail you want to register.\n" +
                            "\nRoom ID: " + Room.getRoomId() +
                            "\nRoom Description: " + Room.getDescription() +
                            "\nRoom Capacity: " + Room.getCapacity() +
                            "\nRoom Type ID: " + RoomType.getRoom_type_id() +
                            "\n\nAre these correct?\n" +
                            "Reply 1: Yes\n" +
                            "Reply 2: No, I would like to make a correction (Remind: You need to enter again from the beginning\n\n" +
                            "Reply 0: Back to Main Menu");
                    break;
                }
                case 5: {
                    sendMessage.setChatId(chatId);
                    if (reply.equals("1")) {
                        SQLite.insertRoom();
                        sendMessage.setText("Registered successfully");
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        stepRegister = 0;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Click /systemadmin if you want to manage application\n" +
                                "Click /schooladmin if you are an applied school admin\n" +
                                "Click /user if you want to manage booking or apply to be an admin");
                    } else if (reply.equals("2")) {
                        stepRegister = 1;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("May I know the room id you want to register?\\n\\nReply 0: Back to Main Menu");
                    }
                    break;
                }
            }

            switch (stepUpdate) {
                case 1: {
                    School.setSchoolId(reply);
                    sendMessage.setChatId(chatId);
                    sendMessage.setText(SQLite.listMeetingRoom() + "\n\nMay I know which room do you want to update? Write its room id");
                    stepUpdate = 2;
                    break;
                }
                case 2: {
                    Room.setRoomId(reply);
                    sendMessage.setChatId(chatId);
                    sendMessage.setText(SQLite.selectMeetingRoom(Room.getRoomId()) + "\n\nWhich one data you want to update? Enter 1-4");
                    stepUpdate = 3;
                    break;
                }
                case 3: {
                    sendMessage.setChatId(chatId);
                    switch (reply) {
                        case "1": {
                            sendMessage.setText("Kindly provide your new room id");
                            stepUpdate = 4;
                            break;
                        }
                        case "2": {
                            sendMessage.setText("Kindly provide your new room description");
                            stepUpdate = 5;
                            break;
                        }
                        case "3": {
                            sendMessage.setText("Kindly provide your new room maximum capacity");
                            stepUpdate = 6;
                            break;
                        }
                        case "4": {
                            sendMessage.setText("Kindly provide your new room type id");
                            stepUpdate = 7;
                            break;
                        }
                    }
                    break;
                }
                case 4: {
                    Room.setRoomId(reply);
                    SQLite.updateRoom(1, reply);
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("This is the room detail you want to update.\n" +
                            "\nRoom ID: " + Room.getRoomId() +
                            "\nRoom Description: " + Room.getDescription() +
                            "\nRoom Capacity: " + Room.getCapacity() +
                            "\nRoom Type ID: " + RoomType.getRoom_type_id() +
                            "\n\nAre these correct?\n" +
                            "Reply 1: Yes\n" +
                            "Reply 2: No, I would like to make a correction (Remind: You need to enter again from the beginning\n\n" +
                            "Reply 0: Back to Main Menu");
                    stepUpdate = 8;
                    break;
                }
                case 5: {
                    Room.setDescription(reply);
                    SQLite.updateRoom(2, reply);
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("This is the room detail you want to update.\n" +
                            "\nRoom ID: " + Room.getRoomId() +
                            "\nRoom Description: " + Room.getDescription() +
                            "\nRoom Capacity: " + Room.getCapacity() +
                            "\nRoom Type ID: " + RoomType.getRoom_type_id() +
                            "\n\nAre these correct?\n" +
                            "Reply 1: Yes\n" +
                            "Reply 2: No, I would like to make a correction (Remind: You need to enter again from the beginning\n\n" +
                            "Reply 0: Back to Main Menu");
                    stepUpdate = 8;
                    break;
                }
                case 6: {
                    Room.setCapacity(Integer.valueOf(reply));
                    SQLite.updateRoom(3, reply);
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("This is the room detail you want to update.\n" +
                            "\nRoom ID: " + Room.getRoomId() +
                            "\nRoom Description: " + Room.getDescription() +
                            "\nRoom Capacity: " + Room.getCapacity() +
                            "\nRoom Type ID: " + RoomType.getRoom_type_id() +
                            "\n\nAre these correct?\n" +
                            "Reply 1: Yes\n" +
                            "Reply 2: No, I would like to make a correction (Remind: You need to enter again from the beginning\n\n" +
                            "Reply 0: Back to Main Menu");
                    stepUpdate = 8;
                    break;
                }
                case 7: {
                    RoomType.setRoomTypeId(reply);
                    SQLite.updateRoom(4, reply);
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("This is the room detail you want to update.\n" +
                            "\nRoom ID: " + Room.getRoomId() +
                            "\nRoom Description: " + Room.getDescription() +
                            "\nRoom Capacity: " + Room.getCapacity() +
                            "\nRoom Type ID: " + RoomType.getRoom_type_id() +
                            "\n\nAre these correct?\n" +
                            "Reply 1: Yes\n" +
                            "Reply 2: No, I would like to make a correction (Remind: You need to enter again from the beginning\n\n" +
                            "Reply 0: Back to Main Menu");
                    stepUpdate = 8;
                    break;
                }
                case 8: {
                    sendMessage.setChatId(chatId);
                    if (reply.equals("1")) {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Updated successfully!");

                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        stepUpdate = 0;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Click /systemadmin if you want to manage application\n" +
                                "Click /schooladmin if you are an applied school admin\n" +
                                "Click /user if you want to manage booking or apply to be an admin");
                    } else if (reply.equals("2")) {
                        stepUpdate = 3;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText(SQLite.selectMeetingRoom(Room.getRoomId()) + "\n\nWhich one data you want to update? Enter 1-4");
                    }
                    break;
                }
            }

            switch (stepConfirm) {
                case 1: {
                    SystemAdmin.setUsername(text);
                    sendMessage.setChatId(chatId);
                    String result = SQLite.checkUsername(SystemAdmin.getUsername());
                    if (result.equals("No")) {
                        sendMessage.setText("Sorry, your username is invalid, please enter again\n\nReply 0: Back to Main Menu");
                    } else {
                        stepConfirm = 2;
                        sendMessage.setText("Please provide your password\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 2: {
                    SystemAdmin.setPassword(text);
                    sendMessage.setChatId(chatId);
                    String result = SQLite.checkPassword(SystemAdmin.getPassword());
                    if (result.equals("No")) {
                        sendMessage.setText("Sorry, your password is wrong, please enter again\n\nReply 0: Back to Main Menu");
                    } else {
                        stepConfirm = 3;
                        sendMessage.setText("Identity verification success!\n" +
                                "\nHere is the list of waiting approval for School Admin " +
                                SQLite.waitingApproval() +
                                "\n\nDo you wish to proceed approve School Admin? Please provide the staff no you wish to approve: " +
                                "\n\nReply 0: Back to Main Menu");
                    }
                    break;
                }
                case 3: {
                    SchoolAdmin.setStaffNo(text);
                    sendMessage.setChatId(chatId);
                    sendMessage.setText(SQLite.selectSchoolAdmin(SchoolAdmin.getStaffNo()) +
                            "\n\nAre you sure want to approve this application?\n" +
                            "Reply 1: Yes\n" +
                            "Reply 2: No\n\n" +
                            "Reply 0: Back to Main Menu");
                    stepConfirm = 4;
                    break;
                }
                case 4: {
                    sendMessage.setChatId(chatId);
                    if (reply.equals("1")) {
                        sendMessage.setChatId(chatId);
                        SQLite.updateApproval(SchoolAdmin.getStaffNo());
                        sendMessage.setText("Updated successfully!");
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        stepConfirm = 0;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Click /systemadmin if you want to manage application\n" +
                                "Click /schooladmin if you are an applied school admin\n" +
                                "Click /user if you want to manage booking or apply to be an admin");
                    } else if (reply.equals("2")) {
                        stepConfirm = 1;
                        sendMessage.setChatId(chatId);
                        sendMessage.setText(SQLite.selectMeetingRoom("Hello school admin,\n\n" +
                                "First please provide your staff no for identity verification"));
                    }
                    break;
                }
            }
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is for convert the end time of the booking meeting room
     *
     * @param reply The end time of the booking meeting room
     */
    private void covertEndTime(String reply) {
        String[] arr = reply.split(":");
        // Converting hours into integer
        int hh = Integer.parseInt(arr[0]);
        String hour = String.format("%02d", hh);
        String minute = arr[1];
        Time.setEndTime(Integer.parseInt(hour));
    }

    /**
     * This method is for convert the start time of the booking meeting room
     *
     * @param time The start time of the booking meeting room
     */
    private void covertStartTime(String time) {
        String[] arr = time.split(":");
        // Converting hours into integer
        int hh = Integer.parseInt(arr[0]);
        String hour = String.format("%02d", hh);
        String minute = arr[1];
        Time.setStartTime(Integer.parseInt(hour));
    }
}