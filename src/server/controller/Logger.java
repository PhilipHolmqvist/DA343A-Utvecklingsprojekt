package server.controller;

import java.io.*;
import java.time.LocalDateTime;

public class Logger {
    private String time;
    private String filename;
    private BufferedWriter bw;

    public Logger(){

        LocalDateTime currTime = LocalDateTime.now();
        int year = currTime.getYear();
        int month = currTime.getMonthValue();
        int day = currTime.getDayOfMonth();
        int hour = currTime.getHour();
        int minute = currTime.getMinute();
        int second = currTime.getSecond();
        this.time = String.format("%d-%02d-%02d-%02d%02d", year, month, day, hour, minute, second);

        createFile(time);

    }

    private void createFile(String filename){
        try {
            File myObj = new File("logs/" + filename + ".txt");
            this.filename = "logs/" + filename + ".txt";
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void log(String text) {
        try (BufferedWriter bw = new BufferedWriter(
                (new OutputStreamWriter(new FileOutputStream(filename), "UTF-8")))) {
            //Stängs efter varje användning.

            bw.newLine();
            bw.write(text);
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
