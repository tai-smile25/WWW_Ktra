package tam.com.onktthuchanh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tam.com.onktthuchanh.entity.Department;
import tam.com.onktthuchanh.entity.Tower;
import tam.com.onktthuchanh.service.interf.DepartmentService;
import tam.com.onktthuchanh.service.interf.TowerService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/department")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class DepartmentController {
    DepartmentService departmentService;
    TowerService towerService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String getAllDepartments(Model model) {
        List<Department> departmentList = departmentService.getAllDepartments();
        List<Tower> towers = towerService.getAllTowers();
//            departmentList.forEach(dept-> System.out.println(dept.toString()));
        model.addAttribute("departments", departmentList);
        model.addAttribute("towers", towers);
        return "list-departments";
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String search(Model model, @RequestParam("keyword") String keyword) {
        List<Department> departmentList;
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Filter departments by keyword (search in name)
            departmentList = departmentService.getAllDepartments().stream()
                    .filter(dept -> dept.getName().toLowerCase().contains(keyword.toLowerCase().trim()))
                    .collect(Collectors.toList());
        } else {
            departmentList = departmentService.getAllDepartments();
        }
        List<Tower> towers = towerService.getAllTowers();
        model.addAttribute("departments", departmentList);
        model.addAttribute("towers", towers);
        model.addAttribute("keyword", keyword); // Add keyword back to the form
        return "list-departments";
    }

//    @GetMapping("/detail/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
//    public String getDepartmentById(@PathVariable("id") Integer id, Model model) throws JsonProcessingException {
//        Department department = departmentService.getDepartmentById(id);
//        List<Tower> towers = towerService.getAllTowers();
//
//        model.addAttribute("department", department);
//        model.addAttribute("towers", towers);
//        model.addAttribute("departmentTowers", new ObjectMapper().writeValueAsString(
//                department.getTowers().stream().map(t -> new HashMap<String, Object>() {{
//                    put("id", t.getId());
//                    put("name", t.getName());
//                }}).collect(Collectors.toList())
//        ));
//
//        return "department-detail";
//    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String createDepartment(@ModelAttribute Department department,
                                   @RequestParam(value = "towers", required = false) Integer[] towerIds,
                                   RedirectAttributes redirectAttributes) {
        try {
            if (towerIds != null && towerIds.length > 0) {
                Set<Tower> towers = new HashSet<>();
                for (Integer towerId : towerIds) {
                    Tower tower = towerService.getTowerById(towerId);
                    if (tower != null) {
                        towers.add(tower);
                    }
                }
                department.setTowers(towers);
            }
            departmentService.createDepartment(department);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm phòng ban thành công!");
            return "redirect:/department/all";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
            return "redirect:/department/all";
        }
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute Department department,
                         @RequestParam(value = "towers", required = false) Integer[] towerIds,
                         RedirectAttributes redirectAttributes) {
        try {
            if (towerIds != null && towerIds.length > 0) {
                Set<Tower> towers = new HashSet<>();
                for (Integer towerId : towerIds) {
                    Tower tower = towerService.getTowerById(towerId);
                    if (tower != null) {
                        towers.add(tower);
                    }
                }
                department.setTowers(towers);
            } else {
                department.setTowers(new HashSet<>());
            }
            departmentService.updateDepartment(id, department);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật phòng ban thành công!");
            return "redirect:/department/all";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
            return "redirect:/department/all";
        }
    }

        @PostMapping("/delete/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
        public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
            try {
                departmentService.deleteDepartment(id);
                redirectAttributes.addFlashAttribute("successMessage", "Xóa phòng ban thành công!");
                return "redirect:/department/all";
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
                return "redirect:/department/all";
            }
        }
    }
