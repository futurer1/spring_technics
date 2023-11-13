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

------------------------------------------
// Создание своих аннотаций для валидации
------------------------------------------

@CheckEmail(value = "abc.com", message = "email end abc.com")
private String email;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckEmailValidator.class) // указан класс CheckEmailValidator, который будет валидировать
public @interface CheckEmail {
    public String value() default "xyz.com";

    public String message() default "end email is not xyz.com";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}

public class CheckEmailValidator // класс валидатор
        // CheckEmail - аннотация, которая будет обрабатываться данным классом
        // String - тип данных, которые будут обрабатываться
        implements ConstraintValidator<CheckEmail, String> {
    /**
     * конечная часть email после символа @
     */
    private String endOfEmail;

    @Override
    public void initialize(CheckEmail checkEmail) {
        endOfEmail = checkEmail.value(); // значение поля из аннотации
    }

    @Override
    public boolean isValid(
            String enteredValue,
            ConstraintValidatorContext constraintValidatorContext
    ) {

        return enteredValue.endsWith(endOfEmail);
    }
}

        
