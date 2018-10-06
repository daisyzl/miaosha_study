package com.imooc.miaosha_study.config;

import com.imooc.miaosha_study.domain.MiaoshaUser;
import com.imooc.miaosha_study.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    MiaoshaUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz=parameter.getParameterType();
        return clazz== MiaoshaUser.class;
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request=webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response=webRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken=request.getParameter(MiaoshaUserService.COOKI_NAME_TOKEN);
        String cookieToken=getCookieValue(request,MiaoshaUserService.COOKI_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
            return null;
        }
        String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return userService.getByToken(response,token);

    }

    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[]  cookies=request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(cookiName)){
                return cookie.getValue();
            }

        }
        return null;
    }


}
