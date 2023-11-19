public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
  
}

// Employee - С каким объектом будет работать репозиторий
// Integer - тип данных у поля primary key (id int) у класса Employee

@Autowired
private EmployeeRepository employeeRepository;

Методы EmployeeRepository
-------------------------
findAll    - получение всех работников
findById   - получение одного работника
save       - добавление нового работника
save       - изменение имеющегося работника
deleteById - удаление работника

Аннотация @Transactional у методов сервиса не нужна, т.к. Spring Data и Spring JPA автоматически обеспечать транзакционность.
  
  
