package com.rent.rentservice.util.session;

import com.rent.rentservice.user.domain.User;
import com.rent.rentservice.user.request.UserSessionInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static final String LOGIN_MEMBER_NICKNAME = "userNickname";
    private static final String LOGIN_MEMBER_EMAIL = "userEmail";
    private static final String LOGIN_MEMBER_IP= "ip";
    private static final String LOGIN_MEMBER_IDN= "userIdn";

    // 인스턴스화 방지
    private SessionUtil() {
    }

    // 세션 아웃 확인
    public static boolean checkValidSession(HttpSession session) {
        if(session == null) {
            return true;
        }
        return false;
    }

    public static String getLoginMemberNickname(HttpSession session) {
        return (String) session.getAttribute(LOGIN_MEMBER_NICKNAME);
    }

    public static String getLoginMemberEmail(HttpSession session) {
        return (String) session.getAttribute(LOGIN_MEMBER_EMAIL);
    }

    public static String getLoginMemberIp(HttpSession session) {
        return (String) session.getAttribute(LOGIN_MEMBER_IP);
    }

    public static Long getLoginMemberIdn(HttpSession session) {
        return (Long) session.getAttribute(LOGIN_MEMBER_IDN);
    }

    public static void setLoginInfo(HttpSession session, UserSessionInfo userSessionInfo) {
        session.setAttribute(LOGIN_MEMBER_IDN, userSessionInfo.getId());
        session.setAttribute(LOGIN_MEMBER_EMAIL, userSessionInfo.getEmail());
        session.setAttribute(LOGIN_MEMBER_NICKNAME, userSessionInfo.getNickName());
        session.setMaxInactiveInterval(60 * 60 * 3);
    }

    public static void logout(HttpSession session) {
        session.invalidate();
    }

    public static String getIp() {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String ip  = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
