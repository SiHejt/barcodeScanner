package kirin.barcodescanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectFromIndex() {
        return "redirect:/home/viewAll";
    }

    @GetMapping("/home/viewAll")
    public String viewAllPage() {
        return "/home/viewAll";
    }


    @GetMapping("/home/sevenEleven")
    public String sevenElevenPage() {

        return "/home/sevenEleven";
    }

    @GetMapping("/home/gs25")
    public String gs25Page() {

        return "/home/gs25";
    }

    @GetMapping("/home/event")
    public String eventPage() {

        return "/home/event";
    }

    @GetMapping("/home/myPage")
    public String myPage() {

        return "/home/myPage";
    }

}
