package com.example.lab4.models;

import com.opencsv.bean.CsvIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "rate")
public class Rate {
    public Rate(){};

    public Rate(int ocena, ClassTeacher grupa, Date data, String komentarz){
        this.ocena = ocena;
        this.grupa = grupa;
        this.data= data;
        this.komentarz=komentarz;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "ocena")
    int ocena;

    @CsvIgnore
    @ManyToOne
    @JoinColumn(name = "grupa_id")
    private ClassTeacher grupa;

    @Column(name = "data")
    private Date data;

    @Column(name = "komentarz")
    private String komentarz;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public ClassTeacher getGrupa() {
        return grupa;
    }

    public void setGrupa(ClassTeacher grupa) {
        this.grupa = grupa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getKomentarz() {
        return komentarz;
    }

    public void setKomentarz(String komentarz) {
        this.komentarz = komentarz;
    }
}
