package com.blue.bluearchive.board.repository;

import com.blue.bluearchive.board.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // 추가적인 쿼리 메소드 선언 가능

}
