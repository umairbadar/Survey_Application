package com.example.surveyapplication.models;

public class Summary {

    private int total_male;
    private int total_female;
    private int total_population;
    private int district_id;
    private int province_id;
    private String province_name;

    public int getTotal_male() {
        return total_male;
    }

    public void setTotal_male(int total_male) {
        this.total_male = total_male;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getTotal_female() {
        return total_female;
    }

    public void setTotal_female(int total_female) {
        this.total_female = total_female;
    }

    public int getTotal_population() {
        return total_population;
    }

    public void setTotal_population(int total_population) {
        this.total_population = total_population;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }
}
