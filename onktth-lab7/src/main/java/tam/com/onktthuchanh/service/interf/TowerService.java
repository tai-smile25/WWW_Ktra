package tam.com.onktthuchanh.service.interf;

import tam.com.onktthuchanh.entity.Tower;

import java.util.List;

public interface TowerService {
    List<Tower> getAllTowers();
    public Tower getTowerById(Integer id);
}
