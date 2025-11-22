package tam.com.onktthuchanh.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // ========== PUBLIC - Không cần đăng nhập ==========
                        .requestMatchers("/", "/login", "/register",
                                "/css/**", "/js/**", "/images/**", "/uploads/**").permitAll()

                        // ========== DEPARTMENT - Phòng ban ==========
                        // ADMIN + MANAGER: Xem, thêm, sửa, xóa
                        .requestMatchers("/department/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // ========== EMPLOYEE - Nhân viên ==========
                        // ADMIN + MANAGER: Toàn quyền
                        // USER: Chỉ xem danh sách
                        .requestMatchers("/employee/all", "/employee/search", "/employee/view/**")
                        .hasAnyRole("ADMIN", "MANAGER", "USER")
                        .requestMatchers("/employee/create", "/employee/update/**", "/employee/delete/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // ========== TOWER - Tòa nhà ==========
                        // ADMIN: Toàn quyền
                        .requestMatchers("/tower/**")
                        .hasRole("ADMIN")

                        // ========== HOME PAGE ==========
                        .requestMatchers("/home")
                        .authenticated()

                        // ========== TẤT CẢ REQUEST CÒN LẠI ==========
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/department/create", "/department/update/**", "/department/delete/**",
                                "/employee/create", "/employee/update/**", "/employee/delete/**",
                                "/tower/create", "/tower/update/**", "/tower/delete/**"
                        )
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}