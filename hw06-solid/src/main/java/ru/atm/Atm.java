package ru.atm;

import java.util.List;

public interface Atm {
    List<Banknote> putMoney(List<Banknote> banknote);

    List<Banknote> getMoney(Integer sum);

    Integer getBalance();
}
