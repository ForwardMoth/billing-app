package com.nexign.billing_real_time_app_service.entity;

import com.nexign.lib_util.entity.BaseEntity;
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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person_names")
public class PersonName extends BaseEntity {

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "personName", fetch = FetchType.LAZY)
    private List<Caller> caller;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }

        PersonName personName = (PersonName) o;
        return getId() != null && getId().equals(personName.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
