package weather_application;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TimeConverter{
    private static Map<String, String> monthMap = new HashMap<>(Map.ofEntries(
        Map.entry("01", "January"),
        Map.entry("02", "February"),
        Map.entry("03", "March"),
        Map.entry("04", "April"),
        Map.entry("05", "May"),
        Map.entry("06", "June"),
        Map.entry("07", "July"),
        Map.entry("08", "August"),
        Map.entry("09", "September"),
        Map.entry("10", "October"),
        Map.entry("11", "November"),
        Map.entry("12", "December")
    ));

    public static String standToMillitary(String stand){
        if(stand.equals("12:00 PM")){
            return "12:00";
        }else if(stand.equals("12:00 AM")){
            return "00:00";
        }
        String[] time  = stand.split("[:\s]+");
        String millitary = "";
        if(time[2].equals("PM")){
            millitary = (Integer.parseInt(time[0]) + 12) + "";
        }else{
            millitary = time[0];
        }
        millitary += ":" + time[1];
        return millitary;
    }
    
    public static String millitaryToStand(String millitary){
        if(millitary.equals("00:00") || millitary.equals("24:00")){
            return "12:00 AM";
        }else if(millitary.equals("12:00")){
            return "12:00 PM";
        }
        String[] time = millitary.split(":");
        boolean flag = false;
        String stand = "";
        if(Integer.parseInt(time[0])>12){
            stand = (Integer.parseInt(time[0]) - 12) + "";
            flag = true;
        }else if(Integer.parseInt(time[0]) == 12){
            stand = time[0];
            flag = true;
        }else{
            stand = time[0];
        }
        stand += ":" + time[1] + " ";
        if(flag){
            stand += "PM";
        }else{
            stand += "AM";
        }
        return stand;
    }

    public static String dateToWords(String date){
        String[] dateA = date.split("-");

        String words = dateA[2] + ", " + monthMap.get(dateA[1]);
        
        return words;
    }

    public static String wordsToDate(String words){
        String year = LocalDate.now().getYear()+"";

        String[] wordsA = words.split(", ");

        String date = year + "-";
        for(String k: monthMap.keySet()){
            if(monthMap.get(k).equals(wordsA[1])){
                date += k + "-";
                break;
            }
        }

        date += wordsA[0];
        return date;
    }
}