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

|======|
| FORM |
|======|


Связь объекта модели class Employee с формой через указание modelAttribute
--------------------------------------------------------------------------
// view:
<form:form action="showDetails" modelAttribute="employee">

Доступ к значениям через геттер объекта Employee.getName()
----------------------------------------------------------
// view:
Name <form:input path="name"/>

    
// model
private Map<String, String> departments;
departments = new HashMap<>();
departments.put("Information Technology", "IT");
departments.put("Hrenovye Gabotniki", "HR");
departments.put("Prodajniki", "sales");
// view
<form:select path="department">    
    <form:options items="${employee.departments}"/>
</form:select>

// model
private Map<String, String> carBrands;
carBrands = new HashMap<>();
carBrands.put("Geely Coolray", "Geely");
carBrands.put("Haval H9", "Haval");
carBrands.put("Dongfeng DF6", "Dongfeng");
// view
<form:radiobuttons path="carBrand" items="${employee.carBrands}" />

// model
private Map<String, String> languagesList;
languagesList = new HashMap<>();
languagesList.put("English", "EN");
languagesList.put("Deutch", "DE");
languagesList.put("French", "FR");
// view
<form:checkboxes path="languages" items="${employee.languagesList}"/>
