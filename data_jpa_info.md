```java
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
  
}

// Employee - С каким объектом будет работать репозиторий
// Integer - тип данных у поля primary key (id int) у класса Employee

@Autowired
private EmployeeRepository employeeRepository;

```

Методы EmployeeRepository
-------------------------
findAll    - получение всех работников
findById   - получение одного работника
save       - добавление нового работника
save       - изменение имеющегося работника
deleteById - удаление работника

Аннотация @Transactional у методов сервиса не нужна, т.к. Spring Data и Spring JPA автоматически обеспечать транзакционность.
  

Кастомный запрос к базе на основе JpaRepository
-----------------------------------------------
```java
public interface DataMessageDAO extends JpaRepository<DataMessage, Long> {
    @Query(value = "SELECT * FROM data_messages dm WHERE dm.chat_id = :chatId ORDER BY dm.create_date DESC LIMIT :limit",
            nativeQuery = true)
    Optional<List<DataMessage>> findLastMessagesByChatId(@Param("chatId") Long chatId, @Param("limit") Integer limit);
}
```


Transaction Propagation
-----------------------

| | Если вызывается из @Transactional sendReport() | Если вызывается из sendReport() без @Transactional, либо вызывается отдельно. |
| REQUIRED | используется существующая транзакция | транзакция создается |
| REQUIRED_NEW | создается отдельная вторая транзакция для внутреннего метода | транзакция создается |
| SUPPORTS | используется существующая транзакция 	транзакция не создается
| NOT_SUPPORTED | существующая транзакция не используется, код выполняется вне транзакции | транзакция не создается |
| NEVER | 	выбрасывает исключение 	транзакция не создается |
|MANDATORY | используется существующая транзакция 	выбрасывает исключение |
