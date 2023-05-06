package com.example.a70_lolkek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class Pill {
    public static ArrayList<Pill> pillBox = new ArrayList<>();

    private String name, dosage_type, best_before;
    private int tablets_amount;

    String[] types = {"таблетк", "капл", "ложк", "Шт", "шприц", "мл", "гр"};

    public Pill(String name, String dosage_type, String best_before, int tablets_amount)
    {
        this.name = name;
        this.dosage_type = dosage_type;
        this.best_before = best_before;
        this.tablets_amount = tablets_amount;
    }

    public String getName() {
        return name;
    }

    public String getBestBefore() {
        return best_before;
    }

    public String getAmount() {
        if (tablets_amount % 10 == 1 && tablets_amount != 11) {
            return tablets_amount + " " + dosage_type;
        } else if ((tablets_amount % 10 == 2 || tablets_amount % 10 == 3 || tablets_amount % 10 == 4) && tablets_amount != 12 && tablets_amount != 13 && tablets_amount != 14) {
            if (Objects.equals(dosage_type, "таблетка")) {
                return tablets_amount + " таблетки";
            } else if (Objects.equals(dosage_type, "капля")) {
                return tablets_amount + " капли";
            } else if (Objects.equals(dosage_type, "ложки")) {
                return tablets_amount + " ложки";
            } else {
                return tablets_amount + " шприца";
            }
        } else {
            if (Objects.equals(dosage_type, "таблетка")) {
                return tablets_amount + " таблеток";
            } else if (Objects.equals(dosage_type, "капля")) {
                return tablets_amount + " капель";
            } else if (Objects.equals(dosage_type, "ложки")) {
                return tablets_amount + " ложек";
            } else {
                return tablets_amount + " шприцов";
            }
        }
    }
//1 таблетка капля ложка шприц
//2-4 таблетки капли ложки шприца
//5-20 таблеток капель ложек шприцов
}
