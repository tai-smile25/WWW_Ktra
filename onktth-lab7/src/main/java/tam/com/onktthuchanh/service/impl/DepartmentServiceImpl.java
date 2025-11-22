package tam.com.onktthuchanh.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import tam.com.onktthuchanh.dao.DepartmentRepository;
import tam.com.onktthuchanh.entity.Department;
import tam.com.onktthuchanh.service.interf.DepartmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;
    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Department> searchDepartment(String keyword) {
        return departmentRepository.findAllByNameContainingIgnoreCase(keyword);
    }

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Integer id, Department department) {
        return departmentRepository.findById(id).isPresent() ?
                departmentRepository.save(department) : null;
    }

    @Override
    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public List<Department> getDepartmentByIds(List<Integer> ids) {
        return departmentRepository.findAllById(ids);
    }
}
