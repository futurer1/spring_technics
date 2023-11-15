DAO
---
// DAO (Data Access Object) - объект, через который идёт работа с БД через Hibernate
// DAO имплементируется в сервис @Service, для того, чтобы с ним потом работал @Controller

@Repository
-----------
// Сам DAO имеет аннотацию, означающую, что через него идёт работа с БД

// DAO имплементирует в себя SessionFactory
@Autowired
private SessionFactory sessionFactory;

// внутри методов идёт работа с этим SessionFactory
Session session = sessionFactory.getCurrentSession();

// получение данных
Query<Employee> query = session.createQuery("from Employee", Employee.class);
List<Employee> allEmployees = query.getResultList();

// или апдейт/удаление
Query<Employee> query = session.createQuery("delete from Employee where id =:employeeId");
query.setParameter("employeeId", empId); // bind значения на метку
query.executeUpdate();

@Service
--------
// отмечает класс, содержащий бизнес логику
// это тоже специализированный @Component

// сервис имплементирует в себя DAO
@Autowired
private EmployeeDAO employeeDAO;

  
@Transactional
public List<Employee> getAllEmployees() {
    return employeeDAO.getAllEmployees();
}

@Transactional
--------------
// передаёт Spring работу с транзакциями (открытие, закрытие, коммит)
// используется в сервисе

@Controller
-----------
// контроллер, специальный @Component 
// имплементирует в себя сервис @Service EmployeeService
@Autowired
private EmployeeService employeeService;
