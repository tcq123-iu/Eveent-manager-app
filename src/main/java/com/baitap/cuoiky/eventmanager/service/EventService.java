package com.baitap.cuoiky.eventmanager.service;

import com.baitap.cuoiky.eventmanager.model.BaseResponse;
import com.baitap.cuoiky.eventmanager.model.EventRequest;
import com.baitap.cuoiky.eventmanager.model.SortEventRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class EventService {
    private static final String fileName = "./data/input_1000.txt";
    private static Year_Calendar yearCalendar = loadEventsFromFile(fileName);
    public BaseResponse sortEvent(SortEventRequest request) {
        HashMap<String, HashMap<Integer, ArrayList<Event>>> calendar;
        calendar = yearCalendar.getCalendar(request.getYear());

        Calendar.sortEvents(calendar, request.getAttribute(), request.isReverse());
        return new BaseResponse("03", "Sort success!");
    }

    public BaseResponse viewEvent() {
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth(); // Ngày
        int month = today.getMonthValue(); // Tháng
        int year = today.getYear(); // Năm
        HashMap<String, HashMap<Integer, ArrayList<Event>>> calendar = yearCalendar.getCalendar(year);
        List<Event> listEvent;
        if (calendar.containsKey(String.valueOf(month)) && calendar.get(String.valueOf(month)).containsKey(day)) {
            listEvent = new ArrayList<>(calendar.get(String.valueOf(month)).get(day));
        } else {
            return new BaseResponse("01", "Event not found!");
        }
        return new BaseResponse("00", "Success!", listEvent);
    }

    public BaseResponse createEvent(EventRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        String monthNumber = getMonthNumber(request.getMonth());
        if (monthNumber == null) {
            baseResponse.setErrorCode("01");
            baseResponse.setErrorMessage("Invalid month. Please try again.");
            return baseResponse;
        }

        // Check if the day map is properly initialized
        if (!yearCalendar.getCalendar(request.getYear()).containsKey(monthNumber)) {
            yearCalendar.getCalendar(request.getYear()).put(monthNumber, new HashMap<>());
        }
        if (!yearCalendar.getCalendar(request.getYear()).get(monthNumber).containsKey(request.getDay())) {
            yearCalendar.getCalendar(request.getYear()).get(monthNumber).put(request.getDay(), new ArrayList<>());
        }

        // Put the day, month, year in the format yyyy-MM-dd
        String dateString = request.getYear() + "-" + monthNumber + "-" + String.format("%02d", request.getDay());

        Event event = new Event(request.getTitle(), dateString, request.getTime(), request.getLocation(), request.getDescription());
        yearCalendar.getCalendar(request.getYear()).get(monthNumber).get(request.getDay()).add(event);

        return new BaseResponse("00", "Success");
    }

    public BaseResponse searchEvent(EventRequest request) {
        List<Event> listEvent = new ArrayList<>();
        Event foundEvent = null;
        switch (request.getSearchChoice()) {
            case 1:
                foundEvent = Calendar.searchEvent(yearCalendar.getCalendar(request.getYear()), request.getTitle());
                break;
            case 2:
                foundEvent = Calendar.searchEvent(yearCalendar.getCalendar(request.getYear()), LocalDate.parse(request.getDate()));
                break;
            case 3:
                foundEvent = Calendar.searchEventLocation(yearCalendar.getCalendar(request.getYear()), request.getLocation());
                break;
            default:
                return new BaseResponse("01", "Invalid choice!");
        }

        if (foundEvent != null) {
            listEvent.add(foundEvent);
            return new BaseResponse("00", "Success!", listEvent);
        } else {
            return new BaseResponse("01", "Not Found!");
        }
    }

    private static String getMonthNumber(String month) {
        switch (month) {
            case "January":
                return "01";
            case "February":
                return "02";
            case "March":
                return "03";
            case "April":
                return "04";
            case "May":
                return "05";
            case "June":
                return "06";
            case "July":
                return "07";
            case "August":
                return "08";
            case "September":
                return "09";
            case "October":
                return "10";
            case "November":
                return "11";
            case "December":
                return "12";
            case "01":
                return "01";
            case "02":
                return "02";
            case "03":
                return "03";
            case "04":
                return "04";
            case "05":
                return "05";
            case "06":
                return "06";
            case "07":
                return "07";
            case "08":
                return "08";
            case "09":
                return "09";
            case "10":
                return "10";
            case "11":
                return "11";
            case "12":
                return "12";
            case "1":
                return "01";
            case "2":
                return "02";
            case "3":
                return "03";
            case "4":
                return "04";
            case "5":
                return "05";
            case "6":
                return "06";
            case "7":
                return "07";
            case "8":
                return "08";
            case "9":
                return "09";
            default:
                return null;
        }
    }

    private static Year_Calendar loadEventsFromFile(String fileName) {
        Year_Calendar yearCalendar = new Year_Calendar();

        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(";");


                if (parts.length != 5) {
                    System.out.println("Invalid event format in file.");
                    continue;
                }

                String title = parts[0];
                String date = parts[1];
                String time = parts[2];
                String location = parts[3];
                String description = parts[4];

                System.out.println("Title: " + title);
                System.out.println("Date: " + date);

                LocalDate eventDate = LocalDate.parse(date);
                int year = eventDate.getYear();
                String month = String.format("%02d", eventDate.getMonthValue());
                int day = eventDate.getDayOfMonth();

                Event event = new Event(title, date, time, location, description);

                yearCalendar.getCalendar(year).putIfAbsent(month, new HashMap<>());
                yearCalendar.getCalendar(year).get(month).putIfAbsent(day, new ArrayList<>());
                yearCalendar.getCalendar(year).get(month).get(day).add(event);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return null;
        }

        return yearCalendar;
    }
}
