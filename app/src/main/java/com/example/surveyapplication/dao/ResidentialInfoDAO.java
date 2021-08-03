package com.example.surveyapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.surveyapplication.models.ResidentialInfo;
import com.example.surveyapplication.models.MaritalStatusCount;
import com.example.surveyapplication.models.Summary;

import java.util.List;

@Dao
public interface ResidentialInfoDAO {

    @Insert
    void insert(ResidentialInfo... ResidentialInfo);

    @Update
    void update(ResidentialInfo... ResidentialInfo);

    @Delete
    void delete(ResidentialInfo... ResidentialInfo);

    @Query("SELECT COUNT(id) FROM residential_info")
    int getResidentsCount();

    @Query("SELECT * FROM residential_info")
    List<ResidentialInfo> getAllResidents();

    @Query("SELECT * FROM residential_info WHERE id =:id")
    ResidentialInfo getResidentDetails(int id);

    @Query("SELECT COUNT(CASE WHEN gender_id = 1 THEN 1 END) as total_male, COUNT(CASE WHEN gender_id = 2 THEN 1 END) as total_female, COUNT(*) as total_population, district_id as district_id, province_id as province_id from residential_info GROUP BY province_id, district_id")
    List<Summary> getSummary();

    @Query("SELECT COUNT(CASE WHEN marital_status_id = 1 THEN 1 END) as unmarried_count, COUNT(CASE WHEN marital_status_id = 2 THEN 1 END) as married_count, COUNT(CASE WHEN marital_status_id = 3 THEN 1 END) as divorce_count, COUNT(CASE WHEN marital_status_id = 4 THEN 1 END) as widow_count from residential_info WHERE age > 14")
    MaritalStatusCount getMartialStatusCount();
}
