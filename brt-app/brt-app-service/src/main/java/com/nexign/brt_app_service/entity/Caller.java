package com.nexign.brt_app_service.entity;

import com.nexign.lib_util.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "callers")
public class Caller extends BaseEntity {

    @Column(name = "msisdn", nullable = false)
    private String msisdn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id", nullable = false)
    private Operator operator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_name_id")
    private PersonName personName;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "incomingCaller", fetch = FetchType.LAZY)
    private List<CallDataRecord> incomingCallData;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "outcomingCaller", fetch = FetchType.LAZY)
    private List<CallDataRecord> outcomingCallData;

    @Fetch(FetchMode.SELECT)
    @OneToOne(mappedBy = "caller", cascade = CascadeType.ALL)
    private Resource resource;

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
