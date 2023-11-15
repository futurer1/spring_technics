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
// требует добавления в applicationContext.xml
<tx:annotation-driven transaction-manager="transactionManager" />

@Controller
-----------
// контроллер, специальный @Component 
// имплементирует в себя сервис @Service EmployeeService
@Autowired
private EmployeeService employeeService;

----------
|  VIEW  |
----------
// Передача параметров из view  в controller
<c:url var="editButton" value="/editEmployee">
    <c:param name="empId" value="${emp.id}"/>
</c:url>

<input type="button" value="edit" onclick="window.location.href = '${editButton}'"/>

// использование в самом контроллере
@RequestMapping("/editEmployee")
    public String editEmployee(@RequestParam("empId") int empId, Model model) {

        return "redirect:/"; // возможность делать редирект из одного метода контроллера в другой
}


// Передача модели из view в controller

<form:form action="saveEmployee" modelAttribute="employee">
    <form:hidden path="id"/>
    Name <form:input path="name"/>
    <input type="submit" value="ok"/>
</form:form>

// использование в самом контроллере

@RequestMapping("/saveEmployee")
public String saveEmployee(@ModelAttribute("employee") Employee employee) {

    if (bindingResult.hasErrors()) {
        return "employee-info";
    } else {
        employeeService.saveEmployee(employee);
        return "redirect:/"; // переадресация на /
    }
}
