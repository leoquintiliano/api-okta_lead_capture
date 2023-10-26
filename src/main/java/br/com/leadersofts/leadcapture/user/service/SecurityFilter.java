package br.com.leadersofts.leadcapture.user.service;

import br.com.leadersofts.leadcapture.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if(Objects.nonNull(token)) {
            var login = this.tokenService.validateToken(token);
            UserDetails userDetails = this.userRepository.findByLogin(login);

            var authentication = new UsernamePasswordAuthenticationToken(userDetails,null,Objects.nonNull(userDetails) ? userDetails.getAuthorities() : new ArrayList<>());
        }
        filterChain.doFilter(request,response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        return Objects.isNull(authHeader) ? null : authHeader.replace("Bearer", "");
    }
}
