package com.example.Sale_Information_System.Service;



import com.example.Sale_Information_System.pojo.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> getAllManufacturers();
    Manufacturer getManufacturerById(String manufacturerCode);
    void insert(Manufacturer manufacturer);
    void update(Manufacturer manufacturer);
    void delete(String manufacturerCode);

}
