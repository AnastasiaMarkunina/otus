package ru.example.homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> customers = new TreeMap<>(Comparator.comparing(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = customers.firstEntry();
        return getCopyEntry(entry);
    }

    private Map.Entry<Customer, String> getCopyEntry(Map.Entry<Customer, String> entry) {
        if (entry == null) {
            return null;
        } else {
            return new Map.Entry<>() {

                @Override
                public Customer getKey() {
                    return new Customer(
                            entry.getKey().getId(),
                            entry.getKey().getName(),
                            entry.getKey().getScores());
                }

                @Override
                public String getValue() {
                    return entry.getValue();
                }

                @Override
                public String setValue(String value) {
                    return entry.setValue(value);
                }
            };
        }
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = customers.higherEntry(customer);
        return getCopyEntry(entry);
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}
