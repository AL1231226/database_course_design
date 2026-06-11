package com.example.Sale_Information_System.Mapper;

import com.example.Sale_Information_System.pojo.Manufacturer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManufacturerMapper {
    List<Manufacturer> getAllManufacturers();
    Manufacturer getManufacturerById(String manufacturerCode);
    void insert(Manufacturer manufacturer);
    void update(Manufacturer manufacturer);
    void delete(String manufacturerCode);
    String getMaxManufacturerCode();

}
