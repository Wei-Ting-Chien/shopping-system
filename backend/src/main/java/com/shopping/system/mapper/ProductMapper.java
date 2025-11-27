package com.shopping.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.shopping.system.model.Product;

// Resources / Mapper 資料夾下也有 Product Mapper，用來映射與解偶 ORM & DB
@Mapper // MyBatis 的 Mapper
public interface ProductMapper {
    // select by all
    List<Product> selectAll();
    
    // select by id
    Product selectById(Integer id);
}