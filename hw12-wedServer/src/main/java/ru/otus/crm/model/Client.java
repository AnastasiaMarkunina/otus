package ru.otus.crm.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.id = null;
        this.name = name;
        this.address = null;
        this.phones = new ArrayList<>();
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
        this.address = null;
        this.phones = new ArrayList<>();
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;

        if (address != null) {
            this.address = address;
            this.address.setClient(this);
        }

        if (phones != null) {
            this.phones = new ArrayList<>();
            for (Phone phone : phones) {
                phone.setClient(this);
                this.phones.add(phone);
            }
        }
    }

    public void addPhone(Phone phone) {
        if (phone != null) {
            phone.setClient(this);
            this.phones.add(phone);
        }
    }

    public void removePhone(Phone phone) {
        if (phone != null) {
            phone.setClient(null);
            this.phones.remove(phone);
        }
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        List<Phone> clonedPhones = new ArrayList<>();
        if (this.phones != null) {
            for (Phone phone : this.phones) {
                clonedPhones.add(phone.clone());
            }
        }

        Address clonedAddress = this.address != null ? this.address.clone() : null;

        var clonedClient = new Client(this.id, this.name, clonedAddress, clonedPhones);

        // Обновляем ссылки в клонированных объектах
        if (clonedAddress != null) {
            clonedAddress.setClient(clonedClient);
        }
        for (Phone phone : clonedPhones) {
            phone.setClient(clonedClient);
        }

        return clonedClient;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
