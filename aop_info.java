@EnableAspectJAutoProxy
-----------------------
// включение аспектов в классе конфига

@Aspect
-------
// пометка компонента @Component аспектом

Advice типы
-----------
@Before                - до метода с осн. логикой
@After returning       - после осн. логики, если всё выполнилось нормально
@After throwing        - после осн. логики, если выброшено исключение
@After / After finally - после завершения метода с осн. логикой
@Around                - до и после метода с осн. логикой

Pointcut 
--------
// указатель, на чем именно должен быть выполнен Advice  
  
@Before("execution(public void getBook())")
@Before("execution(public void aop.UniLibrary.getBook())")
@Before("execution(public void get*())")

Правило написания Pointcut:
execution(modifiers-pattern? return-type-pattern declaring-type-pattern? method-name-pattern(parameters-pattern) throws-pattern?)
  
