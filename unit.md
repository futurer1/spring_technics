@WebMvcTest для эффективного тестирования вашего веб-слоя с помощью MockMvc

@DataJpaTest для эффективного тестирования вашего уровня персистентности

Также доступны аннотации для других нишевых частей вашего приложения:

```
@JsonTest для проверки сериализации и десериализации JSON
@RestClientTest чтобы протестировать RestTemplate
@DataMongoTest для тестирования кода, связанного с MongoDB

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@ActiveProfiles("dev")

@Test
@MockBean

@LocalServerPort
private int randomServerPort;

// https://www.youtube.com/watch?v=CVGmIp9Wv68&t=223s
@Testcontainers на классе
@Container
private PostgreSQLContainer pg = new PostgreSQLContainer("postgres.13.10");
```

[https://alexkosarev.name/2022/12/29/spring-in-a-nutshell-testing-rest-services/](https://alexkosarev.name/2022/12/29/spring-in-a-nutshell-testing-rest-services/)
```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

## Модульные тесты

```java
@ExtendWith(MockitoExtension.class) // Чтобы Mockito сам создал необходимые mock-объекты, необходимо добавить расширение Mockito к классу
class TasksRestControllerTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    MessageSource messageSource;

    @InjectMocks // Чтобы Mockito создал экземпляр тестируемого класса и передал mock-объекты в качестве аргументов его конструктора
    TasksRestController controller;
}
```

`Mock.doReturn` - указывает, что должно быть возвращено при вызове метода mock-объекта

```java
@ExtendWith(MockitoExtension.class)
class TasksRestControllerTest {

    @Test
    @DisplayName("GET /api/tasks возвращает HTTP-ответ со статусом 200 OK и списком задач")
    void handleGetAllTasks_ReturnsValidResponseEntity() {
        // given
        var tasks = List.of(new Task(UUID.randomUUID(), "Первая задача", false),
                new Task(UUID.randomUUID(), "Вторая задача", true));
        doReturn(tasks).when(this.taskRepository).findAll();

        // when
        var responseEntity = this.controller.handleGetAllTasks();

        // then
        assertNotNull(responseEntity); // проверка, что не null
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // код ответа 200 OK
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType()); // в ответе тип данных JSON
        assertEquals(tasks, responseEntity.getBody()); 
    }
}
```

проверка, что запрос на создание с валидными данными приводит к валидному результату
```java
@ExtendWith(MockitoExtension.class)
class TasksRestControllerTest {

    @Test
    void handleCreateNewTask_PayloadIsValid_ReturnsValidResponseEntity() { // название тестируемого метода, условие выполнения теста и ожидаемый результат
        // given
        var details = "Третья задача";

        // when
        var responseEntity = this.controller.handleCreateNewTask(new NewTaskPayload(details),
                UriComponentsBuilder.fromUriString("http://localhost:8080"), Locale.ENGLISH);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        if (responseEntity.getBody() instanceof Task task) {
            assertNotNull(task.id());
            assertEquals(details, task.details());
            assertFalse(task.completed());

            assertEquals(URI.create("http://localhost:8080/api/tasks/" + task.id()),
                    responseEntity.getHeaders().getLocation());

            verify(this.taskRepository).save(task);
        } else {
            assertInstanceOf(Task.class, responseEntity.getBody());
        }

        verifyNoMoreInteractions(this.taskRepository); // проверка отсутствия каких-либо обращений кроме тех, что ожидали
    }
}
```

проверка негативного сценария, приводящего к ошибке
```java
@ExtendWith(MockitoExtension.class)
class TasksRestControllerTest {

    @Test
    void handleCreateNewTask_PayloadIsInvalid_ReturnsValidResponseEntity() {
        // given
        var details = "   ";
        var locale = Locale.US;
        var errorMessage = "Details is empty";

        doReturn(errorMessage).when(this.messageSource)
                .getMessage("tasks.create.details.errors.not_set", new Object[0], locale);

        // when
        var responseEntity = this.controller.handleCreateNewTask(new NewTaskPayload(details),
                UriComponentsBuilder.fromUriString("http://localhost:8080"), locale);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(new ErrorsPresentation(List.of(errorMessage)), responseEntity.getBody());

        verifyNoInteractions(taskRepository);
    }
}
```

## Интеграционные тесты
Для интеграционных тестов потребуется работающий контекст приложения, что достигается добавлением аннотации `@SpringBootTest` к тестовому классу.
Для имитации HTTP-запросов потребуется экземпляр MockMvc, его можно сконфигурировать вручную, но Spring Boot предоставляет возможность сконфигурировать его автоматически при помощи аннотации `@AutoConfigureMockMvc`.

Очистка репозитория после каждого теста (`@AfterEach`):
```java
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class TasksRestControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    InMemTaskRepository taskRepository;

    @AfterEach
    void tearDown() {
        this.taskRepository.getTasks().clear();
    }
}
```

Делается HTTP-запрос через сервис MockMvc.perform и проверяется результат.
```java
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class TasksRestControllerIT {

    @Test
    void handleGetAllTasks_ReturnsValidResponseEntity() throws Exception {
        // given
        var requestBuilder = get("/api/tasks");
        this.taskRepository.getTasks()
                .addAll(List.of(new Task(UUID.fromString("71117396-8694-11ed-9ef6-77042ee83937"),
                                "Первая задача", false),
                        new Task(UUID.fromString("7172d834-8694-11ed-8669-d7b17d45fba8"),
                                "Вторая задача", true)));

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                        "id": "71117396-8694-11ed-9ef6-77042ee83937",
                                        "details": "Первая задача",
                                        "completed": false
                                    },
                                    {
                                        "id": "7172d834-8694-11ed-8669-d7b17d45fba8",
                                        "details": "Вторая задача",
                                        "completed": true
                                    }
                                ]
                                """)
                );

    }
}
```

Другие тесты для проверки изменений в репозитории
```java
@SpringBootTest
@AutoConfigureMockMvc
class TasksRestControllerIT {

    @Test
    void handleCreateNewTask_PayloadIsValid_ReturnsValidResponseEntity() throws Exception {
        // given
        var requestBuilder = post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "details": "Третья задача"
                        }
                        """);

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isCreated(),
                        header().exists(HttpHeaders.LOCATION),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "details": "Третья задача",
                                    "completed": false
                                }
                                """),
                        jsonPath("$.id").exists()
                );

        assertEquals(1, this.taskRepository.getTasks().size());

        final var task = this.taskRepository.getTasks().get(0);
        assertNotNull(task.id());
        assertEquals("Третья задача", task.details());
        assertFalse(task.completed());
    }

    @Test
    void handleCreateNewTask_PayloadIsInvalid_ReturnsValidResponseEntity() throws Exception {
        // given
        var requestBuilder = post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en")
                .content("""
                        {
                            "details": null
                        }
                        """);

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isBadRequest(),
                        header().doesNotExist(HttpHeaders.LOCATION),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "errors": ["Task details must be set"]
                                }
                                """, true)
                );

        assertTrue(this.taskRepository.getTasks().isEmpty());
    }
}
```

## Ссылки
- [Тестирование в Spring Framework](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#testing-introduction)
- [Про Junit](https://junit.org/junit5/docs/current/user-guide/)
- [Про Mockito](https://site.mockito.org/)
