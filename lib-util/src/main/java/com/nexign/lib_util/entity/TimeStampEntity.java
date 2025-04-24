package com.nexign.lib_util.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Setter
@Getter
@SuperBuilder
@MappedSuperclass
public class TimeStampEntity extends IdEntity implements Serializable {

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    protected ZonedDateTime created;

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    protected ZonedDateTime updated;

    public TimeStampEntity() {
    }

    public TimeStampEntity(final ZonedDateTime created, final ZonedDateTime updated) {
        this.created = created;
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof TimeStampEntity other)) {
            return false;
        }

        if (!Objects.equals(this.getCreated(), other.getCreated())) {
            return false;
        }

        return Objects.equals(this.getUpdated(), other.getUpdated());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        Object createdObj = this.getCreated();
        result = result * 59 + (createdObj == null ? 43 : createdObj.hashCode());
        Object updatedObj = this.getUpdated();
        result = result * 59 + (updatedObj == null ? 43 : updatedObj.hashCode());
        return result;

    }

}
