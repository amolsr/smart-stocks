package com.smartstocks.product.config;

import com.smartstocks.product.repository.SearchTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    SearchTableRepository searchTableRepository;

    @Override
    @Transactional
    public void run(String... args) {
        searchTableRepository.createTrgmExtension();
    }
}
