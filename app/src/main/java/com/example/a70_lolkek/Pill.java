package com.example.a70_lolkek;

import java.util.ArrayList;
import java.util.Objects;

public class Pill {
    public static ArrayList<Pill> pillBox = new ArrayList<>();

    private String name, dosage_type, best_before;
    private int tablets_amount;

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

    public String getAmount(int tablets_amount_pill) {
        if (tablets_amount_pill % 10 == 1 && tablets_amount_pill != 11) {
            return dosage_type;
        } else if ((tablets_amount_pill % 10 == 2 || tablets_amount_pill % 10 == 3 || tablets_amount_pill % 10 == 4) && tablets_amount_pill != 12 && tablets_amount_pill != 13 && tablets_amount_pill != 14) {
            if (Objects.equals(dosage_type, "Таблетка")) {
                return " таблетки";
            } else if (Objects.equals(dosage_type, "Капля")) {
                return " капли";
            } else if (Objects.equals(dosage_type, "Ложка")) {
                return " ложки";
            } else if (Objects.equals(dosage_type, "Шприц")) {
                return " шприца";
            } else {
                return dosage_type;
            }
        } else {
            if (Objects.equals(dosage_type, "Таблетка")) {
                return " таблеток";
            } else if (Objects.equals(dosage_type, "Капля")) {
                return " капель";
            } else if (Objects.equals(dosage_type, "Ложка")) {
                return " ложек";
            } else if (Objects.equals(dosage_type, "Шприц")) {
                return " шприцов";
            } else {
                return dosage_type;
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

    @Override
    public String toString() {
        return this.name;
    }
}
