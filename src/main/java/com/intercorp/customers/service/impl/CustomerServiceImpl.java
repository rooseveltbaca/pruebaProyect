package com.intercorp.customers.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.intercorp.customers.service.CustomerService;
import com.intercorp.customers.dto.CustomerDTO;
import com.intercorp.customers.firebase.FirebaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private FirebaseInitializer firebase;

    @Override
    public List<CustomerDTO> list() {
        List<CustomerDTO> response = new ArrayList<>();
        CustomerDTO customer;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                customer = doc.toObject(CustomerDTO.class);
                customer.setId(doc.getId());
                response.add(customer);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CustomerDTO listEntity(String id) {
        CustomerDTO customer= new CustomerDTO();
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = getCollection().document(id).get();

        try {
            if (documentSnapshotApiFuture.get().exists()) {
                DocumentSnapshot doc = documentSnapshotApiFuture.get();
                customer = doc.toObject(CustomerDTO.class);


            }
            return customer;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Boolean add(CustomerDTO customer) {
        Map<String, Object> docData = getDocData(customer);

        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document().create(docData);

        try {
            if(null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }


    @Override
    public Boolean edit(String id, CustomerDTO customer) {
        Map<String, Object> docData = getDocData(customer);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(id).set(docData);
        try {
            if(null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean delete(String id) {
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(id).delete();
        try {
            if(null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("customer");
    }

    private Map<String, Object> getDocData(CustomerDTO customer) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("firstname", customer.getFirstname());
        docData.put("lastname", customer.getLastname());
        docData.put("age", customer.getAge());
        docData.put("birthday", customer.getBirthday());
        return docData;

    }

}
