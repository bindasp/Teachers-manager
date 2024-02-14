package com.example.lab4.models;

import com.opencsv.bean.CsvIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "groups")
public class ClassTeacher{

    public ClassTeacher(){};
    public ClassTeacher(String nazwa, int max) {
        this.nazwa = nazwa;
        this.max = max;
        this.srednia = 0.0;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    @Column(name = "nazwa", unique = true)
    private String nazwa;

    @Column(name = "max")
    private int max;
    @CsvIgnore
    @OneToMany(mappedBy = "grupa", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private List<Teacher> nauczyciele;

    @CsvIgnore
    @OneToMany(mappedBy = "grupa", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private List<Rate> oceny;

    @CsvIgnore
    @Transient
    private double srednia;

    public double getSrednia() {
        return srednia;
    }

    public void setSrednia(double srednia) {
        this.srednia = srednia;
    }

    public List<Teacher> getNauczyciele() {
        return nauczyciele;
    }

    public List<Rate> getOceny() {
        return oceny;
    }

    public void setOceny(List<Rate> oceny) {
        this.oceny = oceny;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setNauczyciele(List<Teacher> nauczyciele) {
        this.nauczyciele = nauczyciele;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getMax() {
        return max;
    }


}
