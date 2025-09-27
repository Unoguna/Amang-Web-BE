package back.domain.user.user.controller;

import back.domain.user.user.dto.UserDto;
import back.domain.user.user.dto.UserJoinReqBody;
import back.domain.user.user.dto.UserLoginReqBody;
import back.domain.user.user.dto.UserLoginResBody;
import back.domain.user.user.entity.User;
import back.domain.user.user.service.UserService;
import back.global.Rq.Rq;
import back.global.exception.ServiceException;
import back.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="ApiV1MemberController", description = "API 맴버 컨트롤러")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;
    private final Rq rq;

    @Transactional
    @PostMapping
    @Operation(summary = "회원가입")
    public RsData<UserDto> join(@Valid @RequestBody UserJoinReqBody reqBody) {
        userService.findByUsername(reqBody.username())
                .ifPresent(_user -> {
                    throw new ServiceException("409-1", "이미 존재하는 회원입니다.");
                });

        User user = userService.join(reqBody.username(), reqBody.password(), reqBody.nickname());

        return new RsData<>(
                "201-1",
                "%s님 환영합니다. 회원가입이 완료되었습니다.".formatted(user.getNickname()),
                new UserDto(user)
        );
    }

    @Transactional
    @PostMapping("/login")
    @Operation(summary = "로그인")
    public RsData<UserLoginResBody> login(
            @Valid @RequestBody UserLoginReqBody reqBody
    ){
        User user = userService.findByUsername(reqBody.username())
                .orElseThrow(()->new ServiceException("401-1", "존재하지 않는 회원입니다."));

        userService.checkPassword(
                user,
                reqBody.password()
        );

        String accessToken = userService.genAccessToken(user);

        rq.setCookie("apiKey", user.getApiKey());
        rq.setCookie("accessToken", accessToken);

        return new RsData<>(
                "200-1",
                "%s님 환영합니다.".formatted(user.getNickname()),
                new UserLoginResBody(
                        new UserDto(user),
                        user.getApiKey(),
                        accessToken
                )
        );
    }
}
