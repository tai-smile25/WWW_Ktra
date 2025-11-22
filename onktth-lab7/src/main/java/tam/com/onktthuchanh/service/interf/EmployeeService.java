package tam.com.onktthuchanh.service.interf;

import tam.com.onktthuchanh.entity.Employee;

public interface EmployeeService {
    public boolean existsByUsername(String username);
    public Employee registerUser(Employee employee);
    Employee login(String username, String password);

}
