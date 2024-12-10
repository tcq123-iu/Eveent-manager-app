package com.baitap.cuoiky.eventmanager.controller;


import com.baitap.cuoiky.eventmanager.model.BaseResponse;
import com.baitap.cuoiky.eventmanager.model.EventRequest;
import com.baitap.cuoiky.eventmanager.model.SortEventRequest;
import com.baitap.cuoiky.eventmanager.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {
    private EventService eventService = new EventService();

    @GetMapping("")
    public String showHomePage(Model model) {
        BaseResponse baseResponse = eventService.viewEvent();
        model.addAttribute("baseResponse", baseResponse);
        return "index";
    }

    @GetMapping("/add-event")
    public String viewAddEvent(Model model) {
        return "add_event";
    }

    @GetMapping("/sort-event")
    public String viewSortEvent(Model model) {
        return "sort_event";
    }

    @PostMapping("/sort-event")
    public String postSortEvent(
            @RequestParam("year") int year,
            @RequestParam("sortParam") String sortParam,
            @RequestParam("reverse") boolean reverse,
            Model model
    ) {
        SortEventRequest sortEventRequest = new SortEventRequest();
        sortEventRequest.setYear(year);
        sortEventRequest.setAttribute(sortParam);
        sortEventRequest.setReverse(reverse);
        BaseResponse responseCreate = eventService.sortEvent(sortEventRequest);
        if(responseCreate.getErrorCode().equals("03")) {
            BaseResponse baseResponse = eventService.viewEvent();
            model.addAttribute("baseResponse", baseResponse);
            return "index";
        }else {
            model.addAttribute("baseResponse", new BaseResponse("05","Sort Error!"));
            return "index";
        }
    }

    @PostMapping("/add-event")
    public String postAddEvent(
            @RequestParam("year") int year,
            @RequestParam("month") String month,
            @RequestParam("day") int day,
            @RequestParam("title") String title,
            @RequestParam("time") String time,
            @RequestParam("location") String location,
            @RequestParam("description") String description,
            Model model
    ) {
        EventRequest eventRequest = new EventRequest();
        eventRequest.setYear(year);
        eventRequest.setMonth(month);
        eventRequest.setDay(day);
        eventRequest.setTitle(title);
        eventRequest.setTime(time);
        eventRequest.setLocation(location);
        eventRequest.setDescription(description);
        BaseResponse responseCreate = eventService.createEvent(eventRequest);
        if(responseCreate.getErrorCode().equals("00")) {
            BaseResponse baseResponse = eventService.viewEvent();
            model.addAttribute("baseResponse", baseResponse);
            return "index";
        }else {
            model.addAttribute("baseResponse", responseCreate);
            return "index";
        }
    }

    @PostMapping("/search")
    public String handleSearch(
            @RequestParam("year") int year,
            @RequestParam("query") String query,
            @RequestParam("filter") int filter,
            Model model) {
        EventRequest eventRequest = new EventRequest();
        eventRequest.setYear(year);
        if (filter == 1) {
            eventRequest.setTitle(query);
        } else if (filter == 2) {
            eventRequest.setDate(query);
        } else if (filter == 3) {
            eventRequest.setLocation(query);
        }
        eventRequest.setSearchChoice(filter);
        BaseResponse baseResponse = eventService.searchEvent(eventRequest);
        model.addAttribute("baseResponse", baseResponse);
        return "index";
    }
}
