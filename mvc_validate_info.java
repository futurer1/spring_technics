@Size
-----
// длина введенных данных  
// model
@Size(min = 2, message = "name length must be more than 2 symbols")
private String name;

//view
<form:errors path="name"/> <!-- вывод информации из message с ошибкой валидации -->

//controller
@RequestMapping("/showDetails")
public String showEmpDetails(
        @Valid @ModelAttribute("employee") Employee emp,      // @Valid - значит применять валидацию
        BindingResult bindingResult                           // результаты валидации
) {
    if (bindingResult.hasErrors()) {
        //валидация не пройдена
    }
}

@NotBlank
---------
// не пустое значение, пробелы считатся пустым значением
@NotBlank(message = "surname is empty")
private String surname;

@Min
----
@Min(value = 500, message = "must be >= than 500")
private int salary;
        
@Max
----
@Max(value = 1000, message = "must be <= than 1000")
private int salary;

@Pattern
--------
// свой паттерн для валидации
@Pattern(regexp = "\\d{3}-\\d{2}-\\d{2}", message = "Phone is incorrect. Pattern: XXX-XX-XX")
private String phoneNumber;

