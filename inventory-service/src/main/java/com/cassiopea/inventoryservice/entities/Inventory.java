package com.cassiopea.inventoryservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id ;
    private String skuCode ;
    private Integer quantity ;
}
