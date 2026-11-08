package com.stocksense.inventory.repository;

import com.stocksense.inventory.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {

    private final Map<Long , Product> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Product> findAll(){
        return new ArrayList<>(store.values());
    }

    public Optional<Product> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }

    public Product save(Product product){
        if(product.getId() == null){
            product.setId(idGenerator.getAndIncrement()); // new product get next id
        }
        store.put(product.getId(), product);
        return product;
    }

    public  boolean existsById(Long id){
        return store.containsKey(id);
    }

    public void deleteById(Long id){
        store.remove(id);
    }
}
