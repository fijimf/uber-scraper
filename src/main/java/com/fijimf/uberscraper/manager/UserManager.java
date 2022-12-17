package com.fijimf.uberscraper.manager;

//import com.fijimf.deepfijomega.entity.user.AuthToken;
//import com.fijimf.deepfijomega.entity.user.Role;
//import com.fijimf.deepfijomega.entity.user.User;
//import com.fijimf.deepfijomega.exception.DuplicatedEmailException;
//import com.fijimf.deepfijomega.exception.DuplicatedUsernameException;
//import com.fijimf.deepfijomega.repository.AuthTokenRepository;
//import com.fijimf.deepfijomega.repository.RoleRepository;
//import com.fijimf.deepfijomega.repository.UserRepository;

import com.fijimf.uberscraper.db.user.model.AuthToken;
import com.fijimf.uberscraper.db.user.model.Role;
import com.fijimf.uberscraper.db.user.model.User;
import com.fijimf.uberscraper.db.user.repo.AuthTokenRepository;
import com.fijimf.uberscraper.db.user.repo.RoleRepository;
import com.fijimf.uberscraper.db.user.repo.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class UserManager implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthTokenRepository authTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final RandomStringGenerator rsg;
    public static final String EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+){0,4}@(?:[a-zA-Z0-9-]+\\.){1,6}[a-zA-Z]{2,6}$";
    public static final Predicate<String> emailMatches = Pattern.compile(EMAIL_REGEX).asMatchPredicate();

    @Autowired
    public UserManager(UserRepository userRepository, RoleRepository roleRepository, AuthTokenRepository authTokenRepository, PasswordEncoder passwordEncoder, RandomStringGenerator rsg) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authTokenRepository = authTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.rsg = rsg;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> ou = userRepository.findByUserName(s).next().blockOptional(Duration.ofSeconds(15));
        if (ou.isEmpty()) {
            throw new UsernameNotFoundException("Could not fine uer " + s);
        } else {
            return ou.get();
        }
    }

    public String createNewUser(String username, String password, String email, List<String> roles) {
        return createNewUser(username, password, email, roles, -1);
    }

    public String createNewUser(String username, String password, String email, List<String> roles, int expiryMinutes) {
        validateInputs(username, password, email, roles);
        User user = new User(username, passwordEncoder.encode(password), email);
        user.setActivated(false);
        user.setLocked(false);
        if (expiryMinutes > 0) {
            user.setExpireCredentialsAt(LocalDateTime.now().plusMinutes(expiryMinutes));
        }
        if (userRepository.findByEmail(email).next().blockOptional(Duration.ofSeconds(15)).isPresent()) {
            throw new RuntimeException(username + " is duplicated");
        }
        if (userRepository.findByUserName(username).next().blockOptional(Duration.ofSeconds(15)).isPresent()) {
            throw new RuntimeException(username + " is duplicated");
        }

        return loadRoles(roles).flatMap(l -> {
            user.setRoles(l);
            Mono<User> u = userRepository.save(user);
            return u.flatMap(uu ->
                    authTokenRepository.save(new AuthToken(uu.getId(), rsg.generate(12), LocalDateTime.now().plusHours(3))).map(AuthToken::getToken));
        }).block(Duration.ofSeconds(15));

    }

    private Mono<List<Role>> loadRoles(List<String> roles) {
        return roleRepository.findAll().filter(r->roles.contains(r.getAuthority())).collectList();
    }

    private void validateInputs(String username, String password, String email, List<String> roles) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("username must not be null or blank.");
        if (StringUtils.isBlank(password)) throw new IllegalArgumentException("password must not be null or blank.");
        if (StringUtils.isBlank(email)) throw new IllegalArgumentException("email must not be null or blank.");
        if (!emailMatches.test(email)) throw new IllegalArgumentException("email '" + email + "' is malformed");
        if (roles == null || roles.isEmpty()) throw new IllegalArgumentException("roles must not be null or empty.");
    }

    public Mono<User> activateUser(String s) {
        return authTokenRepository.findByToken(s).next().flatMap(token ->
                authTokenRepository.deleteById(token.getId()).flatMap(v -> {
                    if (token.getExpiresAt().isAfter(LocalDateTime.now())) {
                        return userRepository.findById(token.getUserId()).flatMap(u -> {
                            u.setActivated(true);
                            return userRepository.save(u);
                        });
                    } else {
                        return Mono.empty();
                    }
                })
        );
    }


    public Optional<String> forgottenPassword(String email) {
        String password = rsg.generate(15);
        return userRepository.findByEmail(email).map(u -> {
            if (u.isEnabled() && u.isCredentialsNonExpired()) {
                u.setPassword(passwordEncoder.encode(password));
                u.setExpireCredentialsAt(LocalDateTime.now().plusMinutes(10));
                userRepository.save(u);
            }
            return password;
        }).next().blockOptional(Duration.ofSeconds(15));
    }

    public Optional<String> forgottenUser(String email) {
        return userRepository.findByEmail(email).map(User::getUsername).next().blockOptional(Duration.ofSeconds(15));
    }

    public Optional<User> changePassword(String principal, String oldPassword, String newPassword) {
        return userRepository.findByUserName(principal).map(u -> {
            String savedCiphertext = u.getPassword();
            if (passwordEncoder.matches(oldPassword, savedCiphertext)) {
                u.setPassword(passwordEncoder.encode(newPassword));
                u.setExpireCredentialsAt(null);
                userRepository.save(u);
                return u;
            } else {
                throw new BadCredentialsException("Bad credentials for " + principal);
            }
        }).next().blockOptional(Duration.ofSeconds(15));
    }

}
