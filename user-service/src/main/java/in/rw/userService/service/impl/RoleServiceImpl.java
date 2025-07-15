package in.rw.userService.service.impl;

import in.rw.userService.LoggingMarkers;
import in.rw.userService.model.Role;
import in.rw.userService.repository.RoleRepository;
import in.rw.userService.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        log.debug("RoleServiceImpl initialized...");
    }

    @Override
    public List<String> getRoles() {
        log.debug("Fetching all the roles");
        List<Role> roles = roleRepository.findAll();

        if (roles.isEmpty()) {
            String errorMessage = "No roles found!";
            log.atError()
                    .addMarker(LoggingMarkers.ERROR)
                    .log(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        }
        log.info("Roles are fetched successfully. Total role count = {}"
                , roles.size());
        return roles.stream()
                .peek(role -> log.debug("Role found: {}", role.getName()))
                .map(Role::getName)
                .toList();
    }
}
