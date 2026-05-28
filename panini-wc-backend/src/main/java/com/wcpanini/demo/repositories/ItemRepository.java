package com.wcpanini.demo.repositories;

import com.wcpanini.demo.entities.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemRepository extends ElasticsearchRepository<Item, String> {
}