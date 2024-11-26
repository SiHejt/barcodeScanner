package kirin.barcodescanner.repository;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kirin.barcodescanner.oauth.SessionUser;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
	 

    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(Login.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }
 
    /**
     * 파라미터에 전달할 객체를 생성한다.

     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
 
        // 이미 세션이 있다면 그 세션을 돌려주고, 세션이 없으면 null을 돌려준다.
        HttpSession session = request.getSession(false);
 
        if (session == null) {
            return null;
        }
        return session.getAttribute("user");
    }
}