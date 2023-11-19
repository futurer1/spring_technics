// Создание проектов Spring Boot
// https://start.spring.io/

// документация
// https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties.server

@SpringBootApplication
----------------------
// отмечает главный класс в качестве приложения Spring Boot
// Включает в себя @Configuration, @EnableAutoConfiguration, @ComponentScan
// сканируются все пакеты из пространства имён, в котором находится класс, отмеченный этой аннотацией
// можно прописывать кастомные пакеты для сканирования в параметре @SpringBootApplication(scanBasePackages = "com.package.my")

@SpringBootApplication
public class SpringCourseSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCourseSpringbootApplication.class, args); // в контейнер кладется SpringCourseSpringbootApplication.class и запускается
    }
}

