package ch.heig.amt.pokemon.api.filters;

import ch.heig.amt.pokemon.api.exceptions.ForbiddenException;
import ch.heig.amt.pokemon.api.util.UtilsJWT;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class isLoggedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("Authorization");
        Claims claims = null;
        Boolean validToken = true;
        try{
            claims = UtilsJWT.decodeJWT(token);
            request.setAttribute("username", claims.getSubject());
            request.setAttribute("idUser", claims.get("iduser"));
        } catch (Exception e){
            throw new ForbiddenException("Invalid auth token");
        }

        //call next filter in the filter chain
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
