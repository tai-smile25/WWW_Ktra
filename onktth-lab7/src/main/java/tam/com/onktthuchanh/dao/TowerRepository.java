package tam.com.onktthuchanh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tam.com.onktthuchanh.entity.Tower;

import java.util.List;

@Repository
public interface TowerRepository extends JpaRepository<Tower, Integer> {

}
