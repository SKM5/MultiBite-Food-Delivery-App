package com.multibite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multibite.model.Bill;

@Repository
public interface BillRepo extends JpaRepository<Bill, Integer>{

}


