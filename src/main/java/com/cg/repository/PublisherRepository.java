package com.cg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Integer>{
	

}
