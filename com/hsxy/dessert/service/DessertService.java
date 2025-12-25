package com.hsxy.dessert.service;

import com.hsxy.dessert.entity.Dessert;
import com.hsxy.dessert.entity.DessertDetail;
import com.hsxy.dessert.entity.SearchCondition;
import com.hsxy.dessert.mapper.DessertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DessertService {
    @Autowired
    DessertMapper dessertMapper;
    public List<DessertDetail> getAll(){
        List<DessertDetail> dessertDetails=dessertMapper.getAll();
                return dessertDetails;
    }

    public List<DessertDetail> search(SearchCondition condition) {
        List<DessertDetail> dessertDetails=dessertMapper.search(condition);
        return dessertDetails;
    }

    public int save(Dessert dessert){
        return dessertMapper.save(dessert);
    }

    public Dessert get(Integer id) {
        return dessertMapper.get(id);
    }

    public int edit(Dessert dessert) {
        return dessertMapper.edit(dessert);
    }

    public int remove(Integer id) {
        return dessertMapper.remove(id);
    }

    public List<DessertDetail> getReleaseNew(Integer row) {
        return dessertMapper.getRleaseNew(row);
    }
}
