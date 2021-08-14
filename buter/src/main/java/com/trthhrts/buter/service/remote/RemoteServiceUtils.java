package com.trthhrts.buter.service.remote;

import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@UtilityClass
public class RemoteServiceUtils {

    public static String getBearerTokenHeader() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return Optional.ofNullable(req.getCookies()).map(Arrays::stream).flatMap(stream -> stream
                .filter(cookie -> cookie.getName().equals("jwtToken"))
                .findFirst()
                .map(cookie -> "Bearer " + cookie.getValue())).orElse(req.getHeader("Authorization"));

    }
}
