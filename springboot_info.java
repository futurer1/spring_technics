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


Spring Boot DevTools
--------------------
// автоматический перезапуск сервера после внесения изменений в проект Spring
// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>

// see https://stackoverflow.com/questions/33869606/intellij-15-springboot-devtools-livereload-not-working

Spring Data REST
----------------

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>

GET    employees                 получение списка
GET    employees/{employeeId}    получение одного
POST   employees                 добавление одного
PUT    employees/{id}            изменение одного (наличие primary key id в теле запроса не обязательно)
DELETE employees                 удаление одного
