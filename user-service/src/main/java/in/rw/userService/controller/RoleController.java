package in.rw.userService.controller;

import in.rw.userService.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class RoleController {
    private final RoleService service;

    @Autowired
    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping("user-roles")
    public List<String> getAllRoles() {
        log.debug("GET /api/user-roles called");
        return service.getRoles();
    }
}
