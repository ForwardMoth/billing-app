package com.nexign.billing_real_time_app_service.entity;

import com.nexign.lib_util.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "call_data_records")
public class CallDataRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incoming_caller_id", nullable = false)
    private Caller incomingCaller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outcoming_caller_id", nullable = false)
    private Caller outcomingCaller;

    @Column(name = "start_call_time", nullable = false)
    private LocalDateTime startCallTime;

    @Column(name = "finish_call_time", nullable = false)
    private LocalDateTime finishCallTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }

        CallDataRecord caller = (CallDataRecord) o;
        return getId() != null && getId().equals(caller.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
