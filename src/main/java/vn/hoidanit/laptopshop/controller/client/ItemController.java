package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.servlet.ModelAndView;

@Controller
public class ItemController {
    @GetMapping("/product/{id}")
    public String getProductPage(ModelAndView model, @PathVariable("id") long id) {
        return "client/product/detail";
    }

}
