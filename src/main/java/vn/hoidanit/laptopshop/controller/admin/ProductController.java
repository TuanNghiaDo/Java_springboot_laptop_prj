package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;

import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "/admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProductPage(Model model, @ModelAttribute("newProduct") @Valid Product nghiaDo,
            BindingResult newProductBindingResult, @RequestParam("hoidanitFile") MultipartFile file) {
        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        if (newProductBindingResult.hasErrors()) {
            System.out.println(newProductBindingResult.hasErrors());
            return "admin/product/create";
        }
        String imgProduct = this.uploadService.handleSaveUploadFile(file, "product");
        nghiaDo.setImage(imgProduct);
        this.productService.handleSaveNewProduct(nghiaDo);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductConfirmPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("id", id);
        Product product = new Product();
        product.setId(id);
        model.addAttribute("newProduct", product);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(Model model, @ModelAttribute("newProduct") Product nghia) {
        this.productService.deleteProductById(nghia.getId());
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getIdOfProduct(Model model, @PathVariable("id") Long id) {
        Product currentProduct = this.productService.getProductById(id);
        model.addAttribute("newProduct", currentProduct);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(@ModelAttribute("newProduct") @Valid Product nghia,
            BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        System.out.println(">>>>>>>>>ID: " + nghia.getId());
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        Product productCurrent = this.productService.getProductById(nghia.getId());
        if (productCurrent != null) {
            if (!file.isEmpty()) {
                String imgProduct = this.uploadService.handleSaveUploadFile(file, "product");
                productCurrent.setImage(imgProduct);
            }

            productCurrent.setName(nghia.getName());
            productCurrent.setPrice(nghia.getPrice());
            productCurrent.setQuantity(nghia.getQuantity());
            productCurrent.setDetailDesc(nghia.getDetailDesc());
            productCurrent.setShortDesc(nghia.getShortDesc());
            productCurrent.setFactory(nghia.getFactory());
            productCurrent.setTarget(nghia.getTarget());
            this.productService.handleSaveNewProduct(productCurrent);

        }
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/detail/{id}")
    public String getUserDetailPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("id", id);
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

}
