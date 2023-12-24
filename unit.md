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
