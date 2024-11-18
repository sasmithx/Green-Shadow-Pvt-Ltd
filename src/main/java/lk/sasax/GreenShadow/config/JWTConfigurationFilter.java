package lk.sasax.GreenShadow.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.sasax.GreenShadow.service.JWTService;
import lk.sasax.GreenShadow.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Configuration
@RequiredArgsConstructor
public class JWTConfigurationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserServiceImpl userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization"); // Get the value of the Authorization header

        // Validate Authorization header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7); // Extract the JWT token from the Authorization header
            String extractedUserName = jwtService.extractUsername(jwt); // Extract the username from the JWT

            // Check if username extracted from JWT is not null and there is no existing authenticated user in the security context
            if (extractedUserName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(extractedUserName); // Get the UserDetails

                // Check if the JWT token is valid for the extracted user details
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Create auth token and set it in the security context
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // Retrieve roles from userDetails (Assume roles are in GrantedAuthority)
                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                    boolean isManager = authorities.contains(new SimpleGrantedAuthority("Role_MANAGER"));
                    boolean isAdministrative = authorities.contains(new SimpleGrantedAuthority("Role_ADMINISTRATIVE"));
                    boolean isScientist = authorities.contains(new SimpleGrantedAuthority("Role_SCIENTIST"));

                    // Handle access based on role
                    if (isManager) {
                        // FULL access: All CRUD operations are allowed for MANAGER
                        System.out.println("MANAGER: Full access to perform all CRUD operations.");

                    } else if (isAdministrative) {
                        // ADMINISTRATIVE: Cannot edit crop data, field data, or monitor logs related to crop details
                        if (request.getMethod().equals("POST") && request.getRequestURI().contains("/api/v1/crops")) {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("{\"error\": \"You are not authorized to modify crop data\"}");
                            return;
                        }

                        if (request.getMethod().equals("GET") && request.getRequestURI().contains("/api/v1/logs")) {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("{\"error\": \"You are not authorized to access crop logs\"}");
                            return;
                        }

                        System.out.println("ADMINISTRATIVE: Access limited to certain resources.");

                    } else if (isScientist) {
                        // SCIENTIST: Cannot modify staff, vehicle, or equipment data
                        if (request.getMethod().equals("POST") && (request.getRequestURI().contains("/api/v1/staff") ||
                                request.getRequestURI().contains("/api/v1/vehicles") ||
                                request.getRequestURI().contains("/api/v1/equipment"))) {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("{\"error\": \"You are not authorized to modify staff, vehicle, or equipment data\"}");
                            return;
                        }

                        System.out.println("SCIENTIST: Access limited to data excluding staff, vehicle, or equipment.");

                    } else {
                        // If none of the roles match, respond with Unauthorized
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("{\"error\": \"You do not have permission to perform this action\"}");
                        return;
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
