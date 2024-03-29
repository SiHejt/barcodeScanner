package kirin.barcodescanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectFromIndex() {
        return "redirect:/mainDir/home";
    }

    @GetMapping("/mainDir/home")
    public String home() {
        return "/mainDir/home";
    }

    @GetMapping("/mainDir/CU")
    public String cuPage() {

        return "/mainDir/CU";
    }

    @GetMapping("/mainDir/SevenEleven")
    public String SevenElevenPage() {

        return "/mainDir/SevenEleven";
    }

    @GetMapping("/mainDir/GS25")
    public String GS25Page() {

        return "/mainDir/GS25";
    }

    @GetMapping("/mainDir/event")
    public String eventPage() {

        return "/mainDir/event";
    }

    @GetMapping("/myPageDir/myPage")
    public String myPage() {

        return "/myPageDir/myPage";
    }


}
