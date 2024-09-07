package vn.hoidanit.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class DashBoardController {
    private final UserService userService;

    public DashBoardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getDashboard(Model model) {
        model.addAttribute("countUser", this.userService.CountUser());
        model.addAttribute("countProduct", this.userService.CountProduct());
        model.addAttribute("countOrder", this.userService.CountOrder());
        return "admin/dashboard/show";
    }
}
