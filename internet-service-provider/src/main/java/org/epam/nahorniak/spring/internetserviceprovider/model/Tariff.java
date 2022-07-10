package org.epam.nahorniak.spring.internetserviceprovider.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@NamedNativeQuery(name = "Tariff.findTariffByCode", query = "SELECT * FROM tariff t WHERE t.code = ?",resultClass = User.class)
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;

    private String title;

    private double price;

    @ManyToMany
    @JoinTable(
            name = "tariff_services",
            joinColumns = @JoinColumn(name = "tariff_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<ServiceModel> services;

    public void addService(ServiceModel service){
        this.services.add(service);
        service.getTariffs().add(this);
    }

    public void removeService(ServiceModel service){
        this.services.remove(service);
        service.getTariffs().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return Objects.equals(id, tariff.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
