package com.example.Sale_Information_System.Service.impl;

import com.example.Sale_Information_System.Mapper.ManufacturerMapper;
import com.example.Sale_Information_System.Service.ManufacturerService;
import com.example.Sale_Information_System.pojo.Manufacturer;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ManufacturerServiceimpl implements ManufacturerService {
    private final ManufacturerMapper manufacturerMapper;
    public ManufacturerServiceimpl(ManufacturerMapper manufacturerMapper) {
        this.manufacturerMapper = manufacturerMapper;
    }
    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerMapper.getAllManufacturers();

    }
    @Override
    public Manufacturer getManufacturerById(String manufacturerCode) {
        return manufacturerMapper.getManufacturerById(manufacturerCode);
    }
    @Override
    public void insert(Manufacturer manufacturer) {
        if (manufacturer.getManufacturerCode() == null || manufacturer.getManufacturerCode().isBlank()) {
            manufacturer.setManufacturerCode(generateManufacturerCode());
        }
        manufacturerMapper.insert(manufacturer);
    }

    private String generateManufacturerCode() {
        String maxCode = manufacturerMapper.getMaxManufacturerCode();
        if (maxCode == null) {
            return "MFR001";
        }
        int num = Integer.parseInt(maxCode.replaceAll("\\D", ""));
        return String.format("MFR%03d", num + 1);
    }
    @Override
    public void update(Manufacturer manufacturer) {
        manufacturerMapper.update(manufacturer);
    }
    @Override
    public void delete(String manufacturerCode) {
        manufacturerMapper.delete(manufacturerCode);
    }
}
