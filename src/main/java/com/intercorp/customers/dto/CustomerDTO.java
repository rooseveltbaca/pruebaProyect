package com.intercorp.customers.dto;
import lombok.Data;
import java.util.Date;

@Data
public class CustomerDTO {
    private String id;
    private String firstname;
    private String lastname;
    private Integer age;
    private Date birthday;
}
