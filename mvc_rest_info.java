@Configuration
--------------
// обозначение класса конфигом вместо applicationContext.xml

@ComponentScan
--------------
// где именно сканировать бины и пр., указание пакета
@ComponentScan(basePackages = "com.mikhail.spring.rest")

@EnableWebMvc
-------------
// включение обработчика аннотаций вместо <mvc:annotation-driven/> в .xml версии конфига

@EnableTransactionManagement
----------------------------
// включение управления транзакциями средствами Spring

@RestController
---------------
// обозначение класса контроллером REST

@Autowired
private EmployeeService employeeService;
// в контроллер имплементируется сервис

@Repository
-----------
// репозиторий, обозначает класс DAO, в котором ведется работа с БД

@Service
--------
// класс сервис, с которым работает контроллер  

@RequestBody
------------
// получение тела запроса и маппинг его в объект

@GetMapping
-----------
// маппинг метода контроллера с URL и HTTP запросом метода GET (получение работника по его id)
@GetMapping("/employees/{id}")
public Employee showEmployee(@PathVariable int id) {
    Employee emp = employeeService.getEmployee(id);

    if (emp == null) {
        throw new NoSuchEmployeeException("Employee is not find with ID = " + id);
    }

    return emp;
}
  
@PostMapping
------------
// маппинг метода контроллера с URL и HTTP запросом метода POST (сохранение нового работника)
@PostMapping("/employees")
public Employee addEmployee(@RequestBody Employee employee) {
    // происходит автоматическая конвертация JSON в объект класса Employee за счет Spring и библиотеки jackson
    employeeService.saveEmployee(employee);
    
    return employee;
}

@PutMapping
-----------
// маппинг метода контроллера с URL и HTTP запросом метода PUT (редактирование данных работника)
@PutMapping("/employees")
public Employee updateEmployee(@RequestBody Employee employee) {
    employeeService.saveEmployee(employee);

    return employee;
}

@DeleteMapping
--------------
// маппинг метода контроллера с URL и HTTP запросом метода DELETE (удаление работника) 

@DeleteMapping("/employees/{id}")
public String delEmployee(@PathVariable int id) {

    Employee emp = employeeService.getEmployee(id);

    if (emp == null) {
        throw new NoSuchEmployeeException("Employee is not find with ID = " + id);
    }

    employeeService.delEmployee(id);
    return "Employee ID = " + id + " was deleted.";
}
  
