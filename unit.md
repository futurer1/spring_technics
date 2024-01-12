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

https://alexkosarev.name/2022/12/29/spring-in-a-nutshell-testing-rest-services/
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
