package tam.com.onktthuchanh.dao;

import org.checkerframework.checker.index.qual.LTEqLengthOf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tam.com.onktthuchanh.entity.Department;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    List<Department> findAllByNameContainingIgnoreCase(String keyword);
}
