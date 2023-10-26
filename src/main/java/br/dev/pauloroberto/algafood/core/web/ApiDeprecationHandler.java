package br.dev.pauloroberto.algafood.core.web;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class ApiDeprecationHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/v1/")) {
            response.addHeader("X-AlgaFood-Deprecated",
                    "Esta versão da API está depreciada e deixará de existir. Use a versão mais atual.");
        }

//        if (request.getRequestURI().startsWith("/v1/")) {
//            response.setStatus(HttpStatus.GONE.value()); // 410
//            return false;
//        }

        return true;
    }

}
