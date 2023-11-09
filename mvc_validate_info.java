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
