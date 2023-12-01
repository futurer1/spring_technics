Для работы с JSON:
------------------
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
```

У класса, повторяющего структуру JSON должна быть аннотация @Data


```java
try {
    ObjectMapper objectMapper = new ObjectMapper();
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    List<MyObject> myobjects = objectMapper.readValue(
        new File("/fold/somefile.json"),
        typeFactory.constructCollectionType(List.class, MyObject.class)
    );
} catch(Exception e) {
  
}


```

Для использования Lombok:
-------------------------
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```
