package in.rw.userService.security;

import in.rw.userService.model.User;
import in.rw.userService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static in.rw.userService.LoggingMarkers.SECURITY;
import static java.text.MessageFormat.format;

@Slf4j
@Service
public class UserAuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.atInfo()
                .addMarker(SECURITY)
                .log(format("Attempting to login with user name: {0}", userName));

        String mobile = null;
        String email = null;
        if (!userName.contains("@")) {
            mobile = userName;
        } else email = userName;

        UserDetails userDetails;
        User user = userRepository.findUserByMobileOrEmail(mobile, email)
                .orElseThrow(() -> {
                    String errorMessage =
                            format("Bad credentials - User not found with user name: {0}!", userName);
                    UsernameNotFoundException e = new UsernameNotFoundException(errorMessage);
                    log.atError()
                            .setCause(e)
                            .addMarker(SECURITY)
                            .log(errorMessage);
                    return e;
                });

        return new UserAuthDetails(user);
    }
}
