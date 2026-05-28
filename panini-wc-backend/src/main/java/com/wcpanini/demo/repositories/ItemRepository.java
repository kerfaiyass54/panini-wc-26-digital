package com.wcpanini.demo.repositories;


import com.wcpanini.demo.entities.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository
        extends MongoRepository<Item, String> {
}