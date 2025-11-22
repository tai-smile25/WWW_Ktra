package tam.com.onktthuchanh.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tam.com.onktthuchanh.entity.Department;
import tam.com.onktthuchanh.entity.Employee;
import tam.com.onktthuchanh.entity.Role;
import tam.com.onktthuchanh.entity.Tower;
import tam.com.onktthuchanh.service.interf.DepartmentService;
import tam.com.onktthuchanh.service.interf.EmployeeService;
import tam.com.onktthuchanh.service.interf.TowerService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class LoginController {
    
    EmployeeService employeeService;
    DepartmentService departmentService;
    TowerService towerService;
    PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "Đăng xuất thành công!");
        }
        return "login";
    }


    @GetMapping("/register")
    public String registerForm(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "success", required = false) String success,
                               Model model) {
        List<Department> departments = departmentService.getAllDepartments();
        List<Tower> towers = towerService.getAllTowers();
        model.addAttribute("departments", departments);
        model.addAttribute("towers", towers);
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        if (success != null) {
            model.addAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("name") String name,
                           @RequestParam("confirmPassword") String confirmPassword,
                           @RequestParam(value = "departmentId", required = false) Integer departmentIds,
                           @RequestParam(value = "role", defaultValue = "USER") String role) {
        try {
            // Validate
            if (username == null || username.trim().isEmpty()) {
                return "redirect:/register?error=Ten dang nhap trong!";
            }

            if (password == null || password.length() < 6) {
                return "redirect:/register?error=Mat khau phai co 6 ki tu!";
            }

            if (!password.equals(confirmPassword)) {
                return "redirect:/register?error=Mat khau khong khop!";
            }

//            if (towerIds == null || towerIds.isEmpty()) {
//                return "redirect:/register?error=Vui lòng chọn ít nhất một tòa nhà!";
//            }

            if (employeeService.existsByUsername(username)) {
                return "redirect:/register?error=Ten dang nhap da ton tai";
            }

            Employee employee = Employee.builder()
                    .name(name)
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .role(Role.valueOf(role))
                    .department(departmentService.getDepartmentById(departmentIds))
                    .build();
            // Đăng ký user mới (mặc định role là USER)
            employeeService.registerUser(employee);
            return "redirect:/register?success=true";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/register?error=" + e.getMessage();
        }
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping
    public String home() {
        return "home";
    }
}
