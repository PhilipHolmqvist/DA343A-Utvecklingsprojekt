package server.controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;

public class Logger {
    private String time;

    public Logger(){

        LocalDateTime currTime = LocalDateTime.now();
        int year = currTime.getYear();
        int month = currTime.getMonthValue();
        int day = currTime.getDayOfMonth();
        int hour = currTime.getHour();
        int minute = currTime.getMinute();
        int second = currTime.getSecond();
        this.time = String.format("%d-%02d-%02d %02d:%02d", year, month, day, hour, minute, second);
    }

    public void log(String text) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(
                (new OutputStreamWriter(new FileOutputStream("logs/" + time + ".txt"), "ISO-8859-1")))) {

            bw.write(text);
            bw.newLine();
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
