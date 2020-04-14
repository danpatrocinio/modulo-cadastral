package com.cadastral.filters;

import com.cadastral.repository.UsuarioRepository;
import com.cadastral.utils.Utils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.cadastral.utils.Utils.encode;

@Component
@Order(1)
public class AuthFilter implements Filter {

    public static final String LOGIN_PATH = "/api-cadastral/logar";
    public static final String H2_PATH = "/h2/";
    public static final String SOURCE_PATH = "/api-cadastral/source";
    private static final Map<String, Boolean> NO_FILTERED_RESOURCES;

    @Autowired
    UsuarioRepository usuarioRepository;

    static {
        NO_FILTERED_RESOURCES = new LinkedHashMap<>();
        NO_FILTERED_RESOURCES.put(LOGIN_PATH, true);
        NO_FILTERED_RESOURCES.put(H2_PATH, true);
        NO_FILTERED_RESOURCES.put(SOURCE_PATH, true);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String resourceRequest = req.getRequestURI();
        log("request: " + req.getMethod() + " " + resourceRequest);

        Boolean ignoreFilter = NO_FILTERED_RESOURCES.get(resourceRequest);

        boolean isOptionsRequest = req.getMethod().equals("OPTIONS");

        boolean isH2Database = resourceRequest != null && resourceRequest.contains("/h2");

        if (!isOptionsRequest && !isH2Database && (ignoreFilter == null || !ignoreFilter)) {
            try {

                if (unauthorize(req.getHeader("authorization"))) {
                    if (unauthorize(req.getHeader("Authorization"))) {
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                }

            } catch (Exception e) {
                log(e.getMessage());
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean unauthorize(String authorization) {

        if (authorization == null || !authorization.startsWith("Basic ")) {
            return true;
        }

        String token = authorization.substring(6).trim();

        if (token.isEmpty()) {
            return true;
        }

        log("credentials: " + token);
        String credentials = Utils.decode(token);
        if (credentials.isEmpty() || !credentials.contains(":")) {
            return true;
        }
        String username = credentials.split(":")[0];
        String senha = encode(credentials.split(":")[1]);

        return usuarioRepository.findByUsernameAndSenha(username, senha) == null;
    }

    @Override
    public void init(FilterConfig config) {
    }

    private void log(String msg) {
        Logger.getLogger(com.cadastral.filters.AuthFilter.class).info(msg);
    }

}
