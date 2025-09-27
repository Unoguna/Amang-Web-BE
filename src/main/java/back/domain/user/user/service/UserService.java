package back.domain.user.user.service;

import back.domain.user.user.entity.User;
import back.domain.user.user.repository.UserRepository;
import back.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthTokenService authTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User join(String username, String password, String nickname){
        password = passwordEncoder.encode(password);

        User user = new User(username, password, nickname);

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void checkPassword(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ServiceException("401-1", "비밀번호가 일치 하지 않습니다.");
        }
    }

    public String genAccessToken(User user) {
        return authTokenService.genAccessToken(user);
    }
}
