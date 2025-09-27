package back.domain.user.user.controller;

import back.domain.user.user.dto.UserDto;
import back.domain.user.user.dto.UserJoinReqBody;
import back.domain.user.user.entity.User;
import back.domain.user.user.service.UserService;
import back.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Transactional
    @PostMapping
    public RsData<UserDto> join(@Valid @RequestBody UserJoinReqBody reqBody) {
        User user = userService.join(reqBody.username(), reqBody.password(), reqBody.nickname());

        return new RsData<>(
                "201-1",
                "%s님 환영합니다. 회원가입이 완료되었습니다.".formatted(user.getNickname()),
                new UserDto(user)
        );
    }

//    @Transactional
//    @PostMapping("/login")
//    public RsData<UserLoginResBody> login(
//            @Valid @RequestBody UserLoginReqBody reqBody
//    ){
//
//    }
}
