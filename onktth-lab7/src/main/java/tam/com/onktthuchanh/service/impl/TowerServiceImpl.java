package tam.com.onktthuchanh.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import tam.com.onktthuchanh.dao.TowerRepository;
import tam.com.onktthuchanh.entity.Tower;
import tam.com.onktthuchanh.service.interf.TowerService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class TowerServiceImpl implements TowerService {
    TowerRepository towerRepository;
    @Override
    public List<Tower> getAllTowers() {
        return towerRepository.findAll();
    }
    @Override
    public Tower getTowerById(Integer id) {
        return towerRepository.findById(id).orElse(null);
    }
}
