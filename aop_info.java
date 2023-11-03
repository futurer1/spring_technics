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
  
@Before("execution(public void getBook())") указан только метод, подходит для любого класса
@Before("execution(public void aop.UniLibrary.getBook())") указан точно пакет, класс и метод
@Before("execution(public void get*())") любое название метода, начинающееся на get...
@Before("execution(public void getBook(String))")  с указанием типа String одного параметра метода
@Before("execution(public void get*(*))")  любое название метода, начинающееся на get... и с любым одним параметром внутри
@Before("execution(public void get*(..))") любое название метода, начинающееся на get... и с любым кол-вом параметров (включая ноль) внутри
@Before("execution(public void getBook(aop.Book))") указан метод для любого класса с одним параметром типа класса Book из пакета aop
@Before("execution(public void getBook(aop.Book, ..))") как предыдущий, только + ещё сколько угодно доп. параметров у метода

Правило написания Pointcut:
execution(modifiers-pattern? return-type-pattern declaring-type-pattern? method-name-pattern(parameters-pattern) throws-pattern?)

Объявление отдельных именованных Pointcut-ов:
---------------------------------------------

@Pointcut("execution(* get*())") 
  private void allGetMethods() {  // будет доступен только в этом файле аспекта (т.к. private) по названию allGetMethods()
}

@Before("allGetMethods()")        // использование Pointcut в @Aspect
public void beforeGetLoggingAdvice() {
}

Комбинация из Pointcut-ов с условиями && || ! :
-----------------------------------------------
@Pointcut("execution(* aop.UniLibrary4.*(..))")
private void allMethodsFromUniLibrary4() {          // назвали один
}

@Pointcut("execution(public void aop.UniLibrary4.returnMagazine())")
private void returnMagazineFromUniLibrary4() {      // назвали второй
}

@Pointcut("allMethodsFromUniLibrary4() && !returnMagazineFromUniLibrary4()")  // использовали один с исключением второго
private void allMethodsExceptReturnMagazineFromUniLibrary() {
}

@Order
------
// выставление приоритета применения аспекта. Применяется к классу аспекта @Aspect. 
// Чем меньше число, тем выше приоритет. Это int значение.
@Order(1)
@Order(20)
@Order(-5)

Join Point
----------
// получение информации о том, откуда был вызван Advice. В параметрах метода можно 
// получить информацию о сигнатуре метода и значениях параметров бизнес-логики
MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
methodSignature                 // void aop.joinpoint.UniLibrary.addBook(String,Book)
methodSignature.getMethod()     // public void aop.joinpoint.UniLibrary.addBook(java.lang.String,aop.joinpoint.Book)
methodSignature.getReturnType() // void
methodSignature.getName()       // addBook

