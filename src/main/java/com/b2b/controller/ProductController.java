package com.b2b.controller;

import com.b2b.model.Product;
import com.b2b.model.User;
import com.b2b.payload.UploadFileResponse;
import com.b2b.service.ProductService;
import com.b2b.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/addDemand",method=RequestMethod.GET)
    public ModelAndView addDemandPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        Product product = new Product();
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/addDemand");
        return modelAndView;
    }

    @RequestMapping(value="/addSupply",method=RequestMethod.GET)
    public ModelAndView addSupplyPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        Product product = new Product();
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/addSupply");
        return modelAndView;
    }



    @RequestMapping(value="/addDemand", method=RequestMethod.POST)
    public ModelAndView postAddDemandPage(MultipartHttpServletRequest request, Product product,Principal principal)
    {
        ModelAndView model = new ModelAndView();
        //User user = userService.findUserByEmail(principal.getName());
        product.setUserEmail(principal.getName());
        productService.storeProductAsDemand(request.getFile("file"),product);
        model.addObject("msg", "Product demand has been added successfully");
        model.addObject("product", new Product());
        model.setViewName("/productAdded");
        return model;
    }

    @RequestMapping(value="/addSupply", method=RequestMethod.POST)
    public ModelAndView postAddSupplyPage(MultipartHttpServletRequest request, Product product,Principal principal)
    {
        ModelAndView model = new ModelAndView();
        //User user = userService.findUserByEmail(principal.getName());
        product.setUserEmail(principal.getName());
        productService.storeProductAsSupply(request.getFile("file"),product);

        model.addObject("msg", "Product demand has been added successfully");
        model.addObject("product", new Product());
        model.setViewName("/productAdded");
        return model;
    }

    @RequestMapping(value="/demandListDisplay",method = RequestMethod.GET)
    public ModelAndView displayDemandList(Principal principal,HttpServletResponse response,HttpServletRequest request)
        throws ServletException,IOException{
        ModelAndView modelAndView = new ModelAndView();
        String userEmail = principal.getName();
        List<Product> products = productService.getDemandList(userEmail);

        //System.out.println("PRODUCT LIST**************"+products);
        //response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        modelAndView.addObject("products",products);
        modelAndView.setViewName("/demandListDisplay");
        return modelAndView;
    }

    @RequestMapping(value="/supplyListDisplay",method = RequestMethod.GET)
    public ModelAndView displaySupplyList(Principal principal,HttpServletResponse response,HttpServletRequest request)
            throws ServletException,IOException{
        ModelAndView modelAndView = new ModelAndView();
        String userEmail = principal.getName();
        List<Product> products = productService.getSupplyList(userEmail);

        //System.out.println("PRODUCT LIST**************"+products);
        //response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        modelAndView.addObject("products",products);
        modelAndView.setViewName("/supplyListDisplay");
        return modelAndView;
    }

    @RequestMapping(value="/seeMore", method=RequestMethod.GET)
    public ModelAndView seeMore() {
        ModelAndView model = new ModelAndView();
        model.setViewName("seeMore");
        return model;
    }
}



