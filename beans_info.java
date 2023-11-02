@Component
----------
// можно писать название в скобках, но если не написать, то будет применено правило

@Component("nameIdThisBean")
class Class1 { // name is -> nameIdThisBean
}

@Component
class Class1 { // name is -> class1
}

@Component
class CLass1 { // name is -> CLass1
}

@Autowired
----------
// внедрение зависимости на основе подходящего bean из Spring Container
// если конструктор один и он имеет параметр с зависимостью, то можно не писать @Autowired
// м.б. на конструкторе, методе, поле

@Qualifier
----------
// если для @Autowired подходит более одного bean, то разрулить 
// конфликт (чтобы не было Exception) можно через эту аннотацию

                // FIELD
@Autowired
@Qualifier("nameIdPreferredBean")
private Pet pet;

                // METHOD (or setter)
@Autowired
@Qualifier("nameIdPreferredBean")
public void setPet(Pet pet) {
    this.pet = pet;
}

                // CONSTRUCTOR
@Autowired
public Person(@Qualifier("nameIdPreferredBean") Pet pet) {
    this.pet = pet;
}

@Value
------
// возможность задать значение параметров без геттеров и сеттеров

                // Hard code
@Value("Vasya")
private String name;

                // use variable "person.name" from file someFile.properties
                // applicationContext.xml:    <context:property-placeholder location="classpath:myApp.properties"/>
@Value("${person.name}")
private String name;

@Scope
------
// жизненный цикл bean, определяет кол-во создаваемых бинов
// дополняет аннотацию @Component
@Scope("singleton") по умолчанию
@Scope("prototype")
@Scope("request")
@Scope("session")
@Scope("global_session")

@PostConstructor
----------------
// вызывается после создания bean
// deprecated в Java 9. удален в Java 11
  
@PreDestroy
-----------
// вызывается перед остановкой приложения (при условии, что Scope не "prototype")
// deprecated в Java 9. удален в Java 11

@Configuration
--------------
// объявляет класс конфигурацией. Бины описываются в классе
// не предполагает использование @Autowired. Зависимости прописываются вручную
// название метода является id bean

@Bean
-----
// аннотация перехватывает работу с бином и регулирует создание и получение бина

@ComponentScan
--------------
@ComponentScan("com.mikhail.spring.spring_introduction") // в каком пакете производить сканирование

// Способы получения контекста:
ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
