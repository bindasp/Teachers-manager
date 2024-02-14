package com.example.lab4.models;

import com.opencsv.bean.CsvIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "teachers" ,uniqueConstraints = {@UniqueConstraint(columnNames = {"imie", "nazwisko"})})
public class Teacher {

    public Teacher(){}

    public Teacher(String imie, String nazwisko, TeacherCondition stanNauczyciela,
            int rokUrodzenia, double wynagrodzenie, ClassTeacher grupa) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.stanNauczyciela = stanNauczyciela;
        this.rokUrodzenia = rokUrodzenia;
        this.wynagrodzenie = wynagrodzenie;
        this.grupa = grupa;
        groupName = grupa.getNazwa();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="imie")
    private String imie;

    @Column(name = "nazwisko")
    private String nazwisko;

    @Column(name="stan_nauczyciela")
    @Enumerated(EnumType.STRING)
    private TeacherCondition stanNauczyciela;

    @Column(name = "rok_urodzenia ")
    private int rokUrodzenia;

    @Column(name = "wynagrodzenie")
    private double wynagrodzenie;

    @CsvIgnore
    @ManyToOne
    @JoinColumn(name = "grupa_id")
    private ClassTeacher grupa;

    @Column(name = "groupName")
    private String groupName;

    public void setGrupa(ClassTeacher grupa) {
        this.grupa = grupa;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public ClassTeacher getGrupa() {
        return grupa;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setRokUrodzenia(int rokUrodzenia) {
        this.rokUrodzenia = rokUrodzenia;
    }


    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getId() {
        return id;
    }

    public TeacherCondition getStanNauczyciela() {
        return stanNauczyciela;
    }

    public int getRokUrodzenia() {
        return rokUrodzenia;
    }

    public double getWynagrodzenie() {
        return wynagrodzenie;
    }

    public void setWynagrodzenie(double wynagrodzenie) {
        this.wynagrodzenie = wynagrodzenie;
    }

    public void setStanNauczyciela(TeacherCondition stanNauczyciela) {
        this.stanNauczyciela = stanNauczyciela;
    }

}
