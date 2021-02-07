package org.acme;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQuery(name = "Fruits.findAll", query = "SELECT f FROM Fruit f")
public class Fruit {
    @Id
    @SequenceGenerator(name = "fruitsSequence", sequenceName = "known_fruits_id_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "fruitsSequence")
    public long id;
    
    public int size;
    public FruitType type;
    public double price;
}
