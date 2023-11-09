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
// при работе с формами @ModelAttribute в параметре метода даёт доступ к объекту Модели class Employee
@RequestMapping("/showDetails")
public String showEmpDetails(@ModelAttribute("employee") Employee emp) {
}
