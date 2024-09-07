package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProductController {
    private final UploadService uploadService;
    private final ProductService productService;

    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model, @RequestParam("page") Optional<String> optionalPage) {
        int page = 1;
        try {
            if (optionalPage.isPresent()) {
                page = Integer.parseInt(optionalPage.get());
            } else {

            }
        } catch (Exception e) {

        }
        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Product> arrProducts = this.productService.getAllProduct(pageable);
        List<Product> listProduct = arrProducts.getContent();
        model.addAttribute("products", listProduct);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", arrProducts.getTotalPages());
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getProductCreatePage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String postCreateProduct(Model model,
            @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("imgFile") MultipartFile file) {

        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String imgProduct = this.uploadService.handleSaveUploadFile(file, "product");
        product.setImage(imgProduct);
        this.productService.hanldSaveProduct(product);
        return "redirect:admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getDetailProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        Product pr = this.productService.getProductById(id).get();
        model.addAttribute("productDetail", pr);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("newProduct") Product product) {
        this.productService.deleteProductById(product.getId());
        return "redirect:admin/product";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Optional<Product> pr = this.productService.getProductById(id);
        model.addAttribute("newProduct", pr.get());
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postUpdateProduct(Model model,
            @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("imgFile") MultipartFile file) {

        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        Product currentProduct = this.productService.getProductById(product.getId()).get();

        if (currentProduct != null) {
            if (!file.isEmpty()) {
                String imgProduct = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(imgProduct);
            }
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setDetailDesc(product.getDetailDesc());
            currentProduct.setShortDesc(product.getShortDesc());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setTarget(product.getTarget());
            this.productService.hanldSaveProduct(currentProduct);
        }
        return "redirect:admin/product";
    }
}
