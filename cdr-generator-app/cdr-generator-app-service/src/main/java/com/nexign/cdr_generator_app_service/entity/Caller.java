package com.nexign.cdr_generator_app_service.entity;

import com.nexign.lib_util.entity.IdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

/**
 * Справочник абонентов
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "callers")
public class Caller extends IdEntity {

    /**
     * Список входящих звонков
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "incomingCaller", fetch = FetchType.LAZY)
    private List<CallDataRecord> incomingCallData;

    /**
     * Список исходящих звонков
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "outcomingCaller", fetch = FetchType.LAZY)
    private List<CallDataRecord> outcomingCallData;

    /**
     * Номер абонента
     */
    @Column(name = "msisdn", nullable = false)
    private String msisdn;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }

        Caller caller = (Caller) o;
        return getId() != null && getId().equals(caller.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
