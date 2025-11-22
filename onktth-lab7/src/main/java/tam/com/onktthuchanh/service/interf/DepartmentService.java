package tam.com.onktthuchanh.service.interf;

import jakarta.persistence.Id;
import tam.com.onktthuchanh.entity.Department;

import java.util.List;
public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentById(Integer id);
    List<Department> searchDepartment(String keyword);
    Department createDepartment(Department department);
    Department updateDepartment(Integer id, Department department);
    void deleteDepartment(Integer id);
    List<Department> getDepartmentByIds(List<Integer> ids);

}
