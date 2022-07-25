package org.epam.nahorniak.spring.internetserviceprovider.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ServiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;

    private String title;

    @ManyToMany(mappedBy = "services")
    private Set<Tariff> tariffs;

}
