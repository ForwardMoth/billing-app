package com.nexign.brt_app_service.entity;

import com.nexign.lib_util.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.math.BigDecimal;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resources")
public class Resource extends BaseEntity {

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "is_forbid_calls", nullable = false)
    private boolean isForbidCalls = false;

    @Column(name = "minutes")
    private Integer minutes;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "caller_id", nullable = false)
    private Caller caller;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }

        Resource resources = (Resource) o;
        return getId() != null && getId().equals(resources.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
