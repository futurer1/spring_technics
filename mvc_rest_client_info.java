В конфигурации:
---------------

@Bean
public RestTemplate restTemplate() {
    // бин для запросов к Rest-сервису
    return new RestTemplate(); // org.springframework.web.client.RestTemplate
}

В компоненте (@Component):
--------------------------

@Autowired
private RestTemplate restTemplate;

Получение коллекции объектов:
-----------------------------

 public List<Employee> getEmployees() {

    // ResponseEntity (org.springframework.http.ResponseEntity) обертка над HttpResponse
    ResponseEntity<List<Employee>> responseEntity =
            restTemplate.exchange( //
                    URL,
                    HttpMethod.GET, // org.springframework.http.HttpMethod
                    null,
                    new ParameterizedTypeReference<List<Employee>>() {} // org.springframework.core.ParameterizedTypeReference
            );

    List<Employee> employees = responseEntity.getBody();
    return employees;
}

Получение одного объекта по id:
-------------------------------
  
public Employee getEmployee(int id) {

    Employee employee = restTemplate.getForObject(
            URL + "/" + id,
            Employee.class
    );

    return employee;
}

Сохранение объекта (отправка в виде JSON)
public int saveEmployee(Employee employee) {
    int id = employee.getId();

    if (id == 0) {

        // тело ответа ResponseEntity будет String в виде JSON формата
        ResponseEntity<Employee> responseEntity =
                restTemplate.postForEntity(
                        URL,
                        employee, // добавляется объект, который будет отправлен
                        Employee.class //
                );
        System.out.println("Employee was added to DB");
        System.out.println(responseEntity.getBody());

        id = responseEntity.getBody().getId();
    } else {

        // изменение уже имеющегося работника
        restTemplate.put(URL, employee);
        System.out.println("Employee ID = " + id + " was updated");
    }

    return id;
}

Удаление объекта по id
----------------------
  
public void delEmployee(int id) {
    restTemplate.delete(
            URL + "/" + id
    );
    System.out.println("Employee ID = " + id + " was deleted");
}

