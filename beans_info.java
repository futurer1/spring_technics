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
