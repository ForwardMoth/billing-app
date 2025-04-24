package com.nexign.lib_util.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Setter
@Getter
@SuperBuilder
@MappedSuperclass
public class BaseEntity extends UuidEntity implements Serializable {

    @Column(name = "actual", nullable = false)
    private boolean actual = true;

    public BaseEntity() {
    }

    public BaseEntity(boolean actual) {
        this.actual = actual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof BaseEntity other)) {
            return false;
        }

        return other.actual == this.actual;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 59 + (actual ? 79 : 97);
        return result;
    }
}
