package online.beapp.balance.entities;

import androidx.annotation.NonNull;

public enum Currency {
    USD(0), LBP(1);

    private final int value;
    private Currency(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (value) {
            case 0:
                return "USD";
            case 1:
                return "LBP";
        }
        return "INVALID";
    }
}
