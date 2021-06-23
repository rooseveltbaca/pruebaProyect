package com.intercorp.customers.service;
import com.intercorp.customers.dto.CustomerDTO;
import java.util.List;

public interface CustomerService {


    List<CustomerDTO> list();

    CustomerDTO listEntity(String id);

    Boolean add(CustomerDTO post);

    Boolean edit(String id,CustomerDTO post);

    Boolean delete(String id);

}
