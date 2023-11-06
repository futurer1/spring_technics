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
//CascadeType.DETACH
//CascadeType.MERGE
//CascadeType.REMOVE
//CascadeType.PERSIST
//CascadeType.REFRESH

@JoinColumn
-----------
// столбец, по которому идёт объединение
@JoinColumn(name = "details_id")
private Detail empDetail; // объект второго POJO класса, с которым делаем связь

// обратная связь со второго объекта (чтобы связь была Bi-directional)
@OneToOne(mappedBy = "empDetail", cascade = CascadeType.ALL)
private Employee emp;

  
