package tam.com.onktthuchanh.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tam.com.onktthuchanh.service.interf.TowerService;

@Controller
@RequestMapping("/tower")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class TowerController {
    TowerService towerService;
}
