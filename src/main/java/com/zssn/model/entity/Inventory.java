package com.zssn.model.entity;


import com.zssn.model.enumeration.Resource;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource", nullable = false, length = 20)
    private Resource resource;

    @Column(name = "quantity", nullable = false, columnDefinition = "TINYINT")
    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "survivor")
    private Survivor survivor;
}
