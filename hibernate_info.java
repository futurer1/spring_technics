// создание фабрики сессий
--------------------------
SessionFactory factory = new Configuration()
    .configure("hibernate.cfg.xml")
    .addAnnotatedClass(Employee.class)
    .buildSessionFactory();

// получение сессии из фабрики
------------------------------
Session session = factory.getCurrentSession();

// сохранение новой записи
--------------------------
session.beginTransaction();
session.save(employee);
session.getTransaction().commit();

// удаление записи
------------------
session.beginTransaction();
session.delete(employee);
session.getTransaction().commit();

// получение записи
-------------------
// по id
Employee myEmployee = session.get(Employee.class, 1);
// по нескольким параметрам
List<Employee> emps = session.createQuery("from Employee "
    + "where surname='Ivanov' and salary > 500") // surname - название поля в классе, а не столбца в таблице
    .getResultList();

// изменение записи
-------------------
session.createQuery("update Employee set salary = 1000 where firstName = 'Alexander'").executeUpdate();

// закрытие сессии и фабрики
----------------------------
session.close();
factory.close();




@Entity
-------
// отображение в виде таблицы

@Table
------
// обозначение связи POJO (Plain Old Java Object) класса с таблицей
@Entity
@Table(name="employees")

@Column
// столбец из таблицы
@Column(name = "department")

@Id
---
// обозначение Primary Key поля

@GeneratedValue
---------------
// стратегия автогенерации значения для Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

@OneToOne
---------
// тип отношения таблиц "один к одному"
// Uni-directional однонаправленная
// Bi-directional двунаправленная
@OneToOne(cascade = CascadeType.ALL) 
// каскадная операция с сущностью может быть разных видов
//CascadeType.ALL (включает в себя все типы)
//CascadeType.PERSIST
//CascadeType.MERGE
//CascadeType.REMOVE
//CascadeType.REFRESH
//CascadeType.DETACH

@JoinColumn
-----------
// столбец, по которому идёт объединение
@JoinColumn(name = "details_id")
private Detail empDetail; // объект второго POJO класса, с которым делаем связь

// обратная связь со второго объекта (чтобы связь была Bi-directional)
@OneToOne(mappedBy = "empDetail", cascade = CascadeType.ALL)
private Employee emp;

@OneToMany
----------
// тип отношения таблиц "один ко многим"
// сотрудников много, департамент один
// в таблице сотрудников есть foreign key (department_id) на таблицу департаментов 

Uni-directional
---------------
// у класса сотрудника отсутствует связь с департаментом
    
// у класса департамента указана связь с сотрудником
@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name = "department_id") // связь указывается со столбцом в таблице сотрудников
private List<Employee> emps; // сотрудников много

// удаление сотрудника не приводит к удалению департамента
// удаление департамента приводит к удалению всех сотрудников

@ManyToMany
-----------
// тип отношения таблиц "многие ко многим"

// секция (сущность Section) включает в себя детей, которые в ней занимаются
@JoinTable(
    name = "child_section", // название таблицы с индексами для связи many to many
    joinColumns = @JoinColumn(name = "section_id"), // название поля для target сущности Section и таблицы section
    inverseJoinColumns = @JoinColumn(name = "child_id") // название поля для связи с Child
)
private List<Child> children;

// доп. метод для добавления коллекции детей к секции
public void addChildToSection(Child child) {
    if (children == null) {
        children = new ArrayList<>();
    }
    children.add(child);
}

// ребенок (сущность Child) включает в себя секции, в которых он занимается
@JoinTable(
    name = "child_section", // название таблицы с индексами для связи many to many
    joinColumns = @JoinColumn(name = "child_id"), // название поля для target сущности Child и таблицы children
    inverseJoinColumns = @JoinColumn(name = "section_id") // название поля для связи с Section
)
private List<Section> sections;

// доп. метод для добавления коллекции секций к ребенку
public void addSectionToChild(Section section) {
    if (sections == null) {
        sections = new ArrayList<>();
    }
    sections.add(section);
}
