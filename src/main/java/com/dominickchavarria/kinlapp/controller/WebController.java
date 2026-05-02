package com.dominickchavarria.kinlapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("successmessage", "Conexion establecida con Exito");
        return "principal";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}

