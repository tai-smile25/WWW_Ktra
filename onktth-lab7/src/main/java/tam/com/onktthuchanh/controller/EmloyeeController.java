package tam.com.onktthuchanh.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tam.com.onktthuchanh.service.interf.EmployeeService;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class EmloyeeController {
    EmployeeService employeeService;



}
