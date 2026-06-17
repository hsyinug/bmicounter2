package com.example.bmicounter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BmiRepository extends JpaRepository<BmiRecord, Long> {
    // 這裡自動具備所有實體資料庫的 SQL 存取功能，免手寫 SQL 語法！
}