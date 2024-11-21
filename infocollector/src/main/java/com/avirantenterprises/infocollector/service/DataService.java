package com.avirantenterprises.infocollector.service;

import com.avirantenterprises.infocollector.model.Data;

public interface DataService {
    void saveData(Data data);
    Iterable<Data> findAllData();
    Data getDataById(Long id);
    void deleteData(Long id);
}
