# Bean references

Bean references allow defining sets of validation groups that can be applied
to detect a valid reference to an unique entity.

Usually a bean with a valid identity is a valid bean reference, in addition of
any number of extra references.

To define a valid bean reference add the annotation `BeanReference` to the
bean class with an array of validation groups to apply to the bean to validate
the valid reference.

```java
@BeanReference(RequireCode.class)
protected static class MyBean {
  // ...
  @NotNull(groups=RequireCode.class)
  @ValidCode(groups=Default.class,RequireCode.class)
  private String code;
  // ...
  public static interface RequireCode {
    // Validation group
  }
}
```

Multiple `BeanReference` annotations are allowed to define multiple bean
reference types:

```java
@BeanReference(RequireCode.class)
@BeanReference({ RequireYear.class, RequireMonth.class })
protected static class MyBean {
  // ...
}
```

## Bean references validation

For validating that a passed bean contains a valid reference annotation
`ValidBeanReference` is provided. This annotation validates that a valid
reference is provided. If the is an instance of `IdentityBean` and has a
identity same limitations of identity validations apply.

```java
void myMethod(
    @ValidBeanReference
    MyBean bean) {
  // ...
}
```

Utility class `BeanValidationUtils` provides method `isValidBeanReference()`
to check programmatically if a bean has a valid reference.
