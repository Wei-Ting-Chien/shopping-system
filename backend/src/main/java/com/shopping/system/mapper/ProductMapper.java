package com.shopping.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shopping.system.model.Product;

// Resources / Mapper 資料夾下也有 Product Mapper，用來映射與解偶 ORM & DB
@Mapper 
public interface ProductMapper {
    // select by condition (接兩個參數 Keyword 或是 Category 來篩選)
	List<Product> selectByCondition(
	    @Param("keyword") String keyword, 
	    @Param("category") String category
	);
    
    // select by id
    Product selectById(Integer id);
    //新增資料，回傳 Void 或是 int
    void insert(Product product);
    
    //更新資料，回傳 void
    void update(Product product);

    //刪除資料(根據 id )
    void deleteById(Integer id);
}