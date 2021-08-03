package com.example.surveyapplication.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "residential_info")
public class ResidentialInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String serial_no;
    private String name_of_person;
    private String father_husband_name;
    private String mother_name;
    private String residential_address;
    private String mobile_number;
    private String date_of_birth;
    private int age;
    private int gender_id;
    private int marital_status_id;
    private int education_id;
    private int occupation_id;
    private int children;
    private int pregnant_status_id;
    private int province_id;
    private int district_id;
    private int tehsil_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getName_of_person() {
        return name_of_person;
    }

    public void setName_of_person(String name_of_person) {
        this.name_of_person = name_of_person;
    }

    public String getFather_husband_name() {
        return father_husband_name;
    }

    public void setFather_husband_name(String father_husband_name) {
        this.father_husband_name = father_husband_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getResidential_address() {
        return residential_address;
    }

    public void setResidential_address(String residential_address) {
        this.residential_address = residential_address;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender_id() {
        return gender_id;
    }

    public void setGender_id(int gender_id) {
        this.gender_id = gender_id;
    }

    public int getMarital_status_id() {
        return marital_status_id;
    }

    public void setMarital_status_id(int marital_status_id) {
        this.marital_status_id = marital_status_id;
    }

    public int getEducation_id() {
        return education_id;
    }

    public void setEducation_id(int education_id) {
        this.education_id = education_id;
    }

    public int getOccupation_id() {
        return occupation_id;
    }

    public void setOccupation_id(int occupation_id) {
        this.occupation_id = occupation_id;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public int getPregnant_status_id() {
        return pregnant_status_id;
    }

    public void setPregnant_status_id(int pregnant_status_id) {
        this.pregnant_status_id = pregnant_status_id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public int getTehsil_id() {
        return tehsil_id;
    }

    public void setTehsil_id(int tehsil_id) {
        this.tehsil_id = tehsil_id;
    }
}
