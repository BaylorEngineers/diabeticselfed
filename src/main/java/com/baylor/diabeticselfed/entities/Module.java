package com.baylor.diabeticselfed.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "module")
public class Module {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_area_id")
    @JsonBackReference(value = "modules")
    private ContentArea contentArea;

}
