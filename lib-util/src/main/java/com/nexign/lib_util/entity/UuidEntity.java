package com.nexign.lib_util.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@SuperBuilder
@MappedSuperclass
public class UuidEntity extends TimeStampEntity implements Serializable {

    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    public UuidEntity() {

    }

    public UuidEntity(final UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof UuidEntity other)) {
            return false;
        }

        return other.getUuid().equals(this.getUuid());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        Object id = this.getUuid();
        result = result * 59 + (id == null ? 43 : id.hashCode());
        return result;
    }

}
