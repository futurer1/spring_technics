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
