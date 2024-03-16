package my.uum;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is for running the telegram bot
 *
 * @author ChunLoon, Hngziling, Sookqichow
 */
public class App extends SQLite {
    /**
     * This is Calendar constructor
     */
    Calendar calendar = Calendar.getInstance();

    /**
     * This method is for running the telegram bot
     * @param args This is main method
     */
    public static void main(String[] args) {

        SQLite.connect();
        SQLite.deleteDataAfterAWeek();
        App app = new App();
        app.setCalendar();

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new STIW3054_Fivesome_bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * This class is for list the data
     *
     * @author Sookqichow
     */
    static class listData extends TimerTask {
        @Override
        public void run() {
            long millis = System.currentTimeMillis();
            java.util.Date date = new java.util.Date(millis);
            System.out.println(SQLite.list());
            System.out.println("Booking list on " + date + " are updated.");
        }
    }

    /**
     * This class is for setting the calendar to monitor the data at 12.01am everyday
     *
     * @author Sookqichow
     */
    public void setCalendar() {
        Calendar today = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 35);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();

        Timer timer = new Timer(true);
        if (time.before(new Date())) {
            today.add(Calendar.DATE, 1);
            time = today.getTime();
        }
        timer.schedule(new listData(), time);
    }
}
