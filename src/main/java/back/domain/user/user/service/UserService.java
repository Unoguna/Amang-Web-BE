package back.domain.user.user.service;

import back.domain.user.user.entity.User;
import back.domain.user.user.repository.UserRepository;
import back.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User join(String username, String password, String nickname){
        userRepository.findByUsername(username)
                .ifPresent(_user -> {
                    throw new ServiceException("409-1", "이미 존재하는 회원입니다.");
                });
        password = passwordEncoder.encode(password);

        User user = new User(username, password, nickname);

        return userRepository.save(user);
    }
}
