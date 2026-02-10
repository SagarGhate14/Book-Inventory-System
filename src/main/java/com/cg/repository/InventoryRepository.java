package com.cg.repository;

import com.cg.entity.Inventory;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Inventory i WHERE i.id = :id")
    void deleteByInventoryId(@Param("id") int id);
	
	
	 Optional<Inventory> findByBook_BookId(int bookId);
}
