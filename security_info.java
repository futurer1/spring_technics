конвертация пароля в BCrypt:
----------------------------
// https://www.browserling.com/tools/bcrypt

@EnableWebSecurity
------------------
// Отметка класса-конфигурации компонента security

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // аутентификация стандартными средствами 
        // Spring через пароль, логин и роли БД
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {  // сопоставление ролей и view
        http.authorizeRequests()
                .antMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER")
                .antMatchers("/hr_info").hasRole("HR")
                .antMatchers("/manager_info").hasRole("MANAGER")
                .and().formLogin().permitAll()
        ;
    }
}

// для того, чтобы пользоваться формой аутентификации
public class MySecurityInitializer
    extends AbstractSecurityWebApplicationInitializer {}

Внутри view ограничения для ролей
---------------------------------
<security:authorize access="hasRole('HR')">
<input type="button" value="Salary" onclick="window.location.href='hr_info'" />
for HR
</security:authorize>

  
