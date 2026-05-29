# Jackson utilities

## SPI based polymorphism

In Jackson, deserialization of polymorphic types can be problematic,
especially when implementations of a type can be provided by other libraries.
Class name based type identifiers can be used, but instantiation of arbitrary
classes based on user input can lead to security issues:

```
package org.example;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
interface MyInterface {}
```

On the other side using name based type identifiers requires known subtypes to
be declared in base type:

```
package org.example;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
    @JsonSubTypes.Type(FirstSubType.class),
    @JsonSubTypes.Type(SecondSubType.class),
    // ...
})
interface MyInterface {}
class FirstSubType implements MyInterface {}
@JsonTypeName("CustomName")
class SecondSubType implements MyInterface {}
```

The class `JacksonSpiTypeIdResolver` provides a SPI based mechanism to
discover new subtypes in runtime using the annotated type FQN as service:

```
package org.example;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonTypeIdResolver(JacksonSpiTypeIdResolver.class)
interface MyInterface {}
class FirstSubType implements MyInterface {}
@JsonTypeName("CustomName")
class SecondSubType implements MyInterface {}
```

Providing `META-INF/services/org.example.MyInterface` files with
implementations FQNs adds subtypes to the base type:

```
org.example.FirstSubType
org.example.SecondSubType
```

```java
assertInstanceOf(
    FirstSubType.class,
    mapper.readValue(
        "{ \"@type\" : \"FirstSubType\" }",
        MyInterface.class));
assertInstanceOf(
    SecondSubType.class,
    mapper.readValue(
        "{ \"@type\" : \"CustomName\" }",
        MyInterface.class));
```

The provider detects annotation inheritance, discarding subtypes that don't
extend target type:

```
package org.example;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonTypeIdResolver(JacksonSpiTypeIdResolver.class)
interface MyInterface {}
interface ExtendedInterface extends MyInterface {}
class FirstSubType implements MyInterface {}
@JsonTypeName("CustomName")
class SecondSubType implements ExtendedInterface {}

assertThrows(
    JsonProcessingException.class,
    mapper.readValue(
        "{ \"@type\" : \"FirstSubType\" }",
        ExtendedInterface.class));
assertInstanceOf(
    SecondSubType.class,
    mapper.readValue(
        "{ \"@type\" : \"CustomName\" }",
        ExtendedInterface.class));
```
