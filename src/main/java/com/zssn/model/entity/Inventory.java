package com.zssn.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zssn.model.enumeration.Resource;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "survivor")
@JsonIgnoreProperties({"survivor"})
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource", nullable = false, length = 20)
    private Resource resource;

    @Column(name = "quantity", nullable = false, columnDefinition = "TINYINT")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "survivor")
    private Survivor survivor;
}
