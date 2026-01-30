package com.cg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	 

}
