package tam.com.onktthuchanh.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import tam.com.onktthuchanh.dao.EmployeeRepository;
import tam.com.onktthuchanh.entity.Employee;
import tam.com.onktthuchanh.service.interf.EmployeeService;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;
    @Override
    public boolean existsByUsername(String username) {
        return employeeRepository.findByUsername(username).isPresent();
    }

    @Override
    public Employee registerUser(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee login(String username, String password) {
        Employee employee = employeeRepository.findByUsername(username).orElse(null);
        if(employee == null || !employee.getPassword().equals(password)){
            return null;
        }
        return employee;
    }
}
