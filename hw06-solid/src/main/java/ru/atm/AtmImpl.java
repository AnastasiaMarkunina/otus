package ru.atm;

import java.util.*;
import java.util.stream.Collectors;

public class AtmImpl implements Atm {

    private final EnumMap<Banknote, Integer> cashStorage;

    public AtmImpl(Set<Banknote> acceptedMoney) {
        cashStorage = new EnumMap<>(Banknote.class);
        acceptedMoney.forEach(denom -> cashStorage.put(denom, 0));
    }

    @Override
    public List<Banknote> putMoney(List<Banknote> moneyInput) {
        Map<Boolean, List<Banknote>> partitioned =
                moneyInput.stream().collect(Collectors.partitioningBy(cashStorage::containsKey));

        partitioned.get(true).forEach(bill -> cashStorage.compute(bill, (key, count) -> count + 1));

        return partitioned.get(false).stream()
                .sorted(Comparator.comparing(Banknote::getValue).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Banknote> getMoney(Integer requestedAmount) {
        List<Banknote> payment = attemptPayment(requestedAmount);

        if (payment.isEmpty() && requestedAmount > 0) {
            throw new IllegalStateException("Not enough money");
        }

        payment.forEach(bill -> cashStorage.merge(bill, -1, Integer::sum));
        return payment;
    }

    private List<Banknote> attemptPayment(Integer amount) {
        int remaining = amount;
        List<Banknote> payment = new ArrayList<>();
        EnumMap<Banknote, Integer> copy = cashStorage.clone();

        for (Banknote bill : getSortedBills()) {
            while (remaining >= bill.getValue() && copy.get(bill) > 0) {
                payment.add(bill);
                remaining -= bill.getValue();
                copy.put(bill, copy.get(bill) - 1);
            }
        }

        return remaining == 0 ? payment : Collections.emptyList();
    }

    private List<Banknote> getSortedBills() {
        return cashStorage.keySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getBalance() {
        return cashStorage.entrySet().stream()
                .mapToInt(e -> e.getKey().getValue() * e.getValue())
                .sum();
    }
}
