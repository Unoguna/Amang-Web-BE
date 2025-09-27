package back.global.Rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;

    public void setCookie(String name, String value) {
        if (value == null) value = "";

        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/"); // 쿠키를 도메인 전체에서 쓰겠다.
        cookie.setHttpOnly(true); // 쿠키를 스크립트로 접근 못하게(XSS 공격방어)
        cookie.setDomain("localhost"); // 쿠키가 적용될 도메인 지정
        cookie.setSecure(true); // https 에서만 쿠키전송
        cookie.setAttribute("SameSite", "Strict"); // 동일 사이트에서만 쿠키 전송(CSRF 공격방어)

        // 값이 없다면 해당 변수를 삭제하라는 뜻
        if (value.isBlank()) {
            cookie.setMaxAge(0);
        } else {
            cookie.setMaxAge(60 * 60 * 24 * 365); // 1년
        }

        resp.addCookie(cookie);
    }
}
