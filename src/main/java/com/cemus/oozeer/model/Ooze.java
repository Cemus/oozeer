package com.cemus.oozeer.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "oozes")
public class Ooze extends BaseEntity {
    @Column(name = "name")
    @NotBlank
    private @Nullable String name;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public @Nullable String getName() {
        return this.name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String name = this.getName();
        return (name != null) ? name : "<null>";
    }
}
