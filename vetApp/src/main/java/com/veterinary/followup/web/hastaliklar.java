package com.veterinary.followup.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller

public class hastaliklar {
	@RequestMapping("/kedi")
    public String kedi() {
        return "kedi/hastaliklar";
    }
    @GetMapping("/kedi/{name}")
    public String getHTMLKedi(@PathVariable("name") String name) {
        String htmlDosyasi = "kedi/" + name;
        return htmlDosyasi;
    }
    @GetMapping("/köpek")
    public String köpek() {
        return "köpek/hastaliklar";
    }
    @GetMapping("/köpek/{name}")
    public String getHTMLKöpek(@PathVariable("name") String name) {
        String htmlDosyasi = "köpek/" + name;
        return htmlDosyasi;
    }
}