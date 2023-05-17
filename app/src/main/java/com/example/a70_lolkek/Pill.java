package com.example.a70_lolkek;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Objects;

public class Pill {
    public static ArrayList<Pill> pillBox = new ArrayList<>();

    private String name, dosage_type, best_before;
    private int tablets_amount;

    public Pill(String name, String dosage_type, String best_before, int tablets_amount) {
        this.name = name;
        this.dosage_type = dosage_type;
        this.best_before = best_before;
        this.tablets_amount = tablets_amount;
    }

    public String getName() {
        return name;
    }

    public int getTabletsAmount() {
        return tablets_amount;
    }

    public static int getTabletsAmount(String name) {
        for (Pill pill : pillBox) {
            if (Objects.equals(pill.getName(), name)) {
                return pill.tablets_amount;
            }
        }
        return 0;
    }

    public String getBestBefore() {
        return best_before;
    }

    public LocalDate getBestBeforeDate() {
        String[] date_str = best_before.split("\\.");
        LocalDate date = LocalDate.of(Integer.parseInt(date_str[2]), Integer.parseInt(date_str[1]), Integer.parseInt(date_str[0]));
        return date;
    }

    public String getDosageType() {
        return dosage_type;
    }

    public String getAmount(int tablets_amount_pill) {
        if (tablets_amount_pill % 10 == 1 && tablets_amount_pill != 11) {
            return " " + dosage_type;
        } else if ((tablets_amount_pill % 10 == 2 || tablets_amount_pill % 10 == 3 || tablets_amount_pill % 10 == 4) && tablets_amount_pill != 12 && tablets_amount_pill != 13 && tablets_amount_pill != 14) {
            if (Objects.equals(dosage_type, "таблетка")) {
                return " таблетки";
            } else if (Objects.equals(dosage_type, "капля")) {
                return " капли";
            } else if (Objects.equals(dosage_type, "ложка")) {
                return " ложки";
            } else if (Objects.equals(dosage_type, "шприц")) {
                return " шприца";
            } else {
                return " " + dosage_type;
            }
        } else {
            if (Objects.equals(dosage_type, "таблетка")) {
                return " таблеток";
            } else if (Objects.equals(dosage_type, "капля")) {
                return " капель";
            } else if (Objects.equals(dosage_type, "ложка")) {
                return " ложек";
            } else if (Objects.equals(dosage_type, "шприц")) {
                return " шприцов";
            } else {
                return " " + dosage_type;
            }
        }
    }

    public static void changeAmount(String name, int amount) {
        for (Pill pill : pillBox) {
            if (Objects.equals(pill.getName(), name)) {
                pill.tablets_amount -= amount;
                break;
            }
        }
    }

    public static void deleteFromPills(String name) {
        pillBox.removeIf(item -> Objects.equals(name, item.getName()));
    }

    @Override
    public String toString() {
        return this.name;
    }
}