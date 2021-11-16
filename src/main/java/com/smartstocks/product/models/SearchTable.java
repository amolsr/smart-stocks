package com.smartstocks.product.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchTable {

    @Id
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String symbol;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String exchange;

    @Lob
    @Column(name = "shortname")
    @Type(type = "org.hibernate.type.TextType")
    private String shortName;

    @Lob
    @Column(name = "longname")
    @Type(type = "org.hibernate.type.TextType")
    private String longName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String prevName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String nameChangeDate;

}
