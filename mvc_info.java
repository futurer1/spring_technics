@Controller
-----------
// контроллер, специализированный компонент @Component
@Controller
public class MyController {
    public MyController() {
    }
    
    @RequestMapping({"/"}) // end point URL
    public String showFirstView() {
        return "first-view"; // связь с файлом view
    }
}

@RequestMapping
---------------
// связь с end point URL

@RequestParam
-------------
// параметр переданный в контроллер

public String showEmpDetails1(
    @RequestParam("employeeName") String empName,
    Model model
) {
}

@ModelAttribute
---------------
// Controller при работе с формами @ModelAttribute в параметре метода даёт доступ к объекту Модели class Employee
// controller:
@RequestMapping("/showDetails")
public String showEmpDetails(@ModelAttribute("employee") Employee emp) {
}

Связь объекта модели class Employee с формой через указание modelAttribute
--------------------------------------------------------------------------
// view:
<form:form action="showDetails" modelAttribute="employee">

Доступ к значениям через геттер объекта Employee.getName()
----------------------------------------------------------
// view:
Name <form:input path="name"/>

    
