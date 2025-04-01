package com.tescorporations.crud.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    /*
    * NOTA: para ejecutar esta funcion se ocupa el 04-setup-spring-security-demo-database-plaintext.sql
    * Por defecto spring security usa una tabla llamada users(usuario con contrasenias) y authorities( roles de los usurios)
    * UserDetailsManager: hace algo similar como con InMemoryUserDetailsManager pero esta vez sacando los usuarios,
    * contrasenias y roles de la bd
    * */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // define query to retrive a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_id, pw, active from members where user_id=?");

        // define query to retrive authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id, role from roles where user_id=?");

        return jdbcUserDetailsManager; //esto le dice a spring security que use autenticacion jdbc con nuestro dataSoruce
    }

    /*
    * Funcion para restringir el acceso a las rutas por tipo de rol
    * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // restricciones de endpoints por metodo, ruta o endpoint y rol
        http.authorizeHttpRequests( configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE") // ** se ocupa como comodin para indicar que tome las rutas siguientes
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
                );

        // use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());

        //deshabilitar Cross Site Request Forgery (CSRF), CSRF es una protección para sitios web con formularios
        // en general, no se requiere CSRF para api rest que usan POST, PUT, DELETE and/ or PATCH
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }


    /*
     * InMemoryUserDetailsManager: mantiene los detalles de usuario en memoria.
     * No almacena los usuarios en una base de datos, lo que es útil para pruebas o desarrollo.
     * */
    /*@Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123") // {noop} hace que la contraseña se almacene con texto plano
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }*/
}
