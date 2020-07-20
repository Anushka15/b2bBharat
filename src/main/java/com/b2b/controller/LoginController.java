package com.b2b.controller;

import javax.validation.Valid;

import com.b2b.model.Product;
import com.b2b.service.ProductService;
import com.b2b.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.b2b.model.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = {"/","/index"}, method=RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        List<Product> productList = productService.getAllProducts();
        List<Product> products = new ArrayList<>();
        int max = 7;
        int min = 0;
        int range = max-min +1;
        if(productList.size()>8)
        {
            for(int i=0;i<8;i++)
            {
                products.add(productList.get((int)(Math.random()*range)));
            }
        }
        else
        {
            for(int i=0;i<productList.size();i++)
            {
                products.add(productList.get(i));
            }
        }
        model.addObject("products",products);
        return model;
    }

    @RequestMapping(value = {"/login","/index/login"}, method=RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = {"/registration","/index/registration"}, method=RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }


    @RequestMapping(value= {"/registration","/index/registration"}, method=RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());

        if(userExists != null) {
            bindingResult.rejectValue("email", "error.user", "This email already exists! Go to the login page");
        }
        if(bindingResult.hasErrors()) {
            model.setViewName("registration");
        } else {
            userService.saveUser(user);
            model.addObject("msg", "User has been registered successfully!");
            model.addObject("user", new User());
            model.setViewName("/loginAgain");
        }

        return model;
    }

    @GetMapping(value="/home")
    public ModelAndView loginDone(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("name", "Welcome " + user.getName());
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        List<Product> productList = productService.getAllProducts();
        List<Product> products = new ArrayList<>();
        int max = 7;
        int min = 0;
        int range = max-min +1;
        if(productList.size()>8)
        {
            for(int i=0;i<8;i++)
            {
                products.add(productList.get((int)(Math.random()*range)));
            }
        }
        else
        {
            for(int i=0;i<productList.size();i++)
            {
                products.add(productList.get(i));
            }
        }
        modelAndView.addObject("products",products);
        modelAndView.setViewName("/home");
        return modelAndView;
    }



}