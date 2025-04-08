package com.nexign.cdr_generator_app_service.entity;

import com.nexign.lib_util.entity.TimeStampEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;

/**
 * Действия, совершенные абонентом за тарифицируемый период
 */
@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "call_data_records")
public class CallDataRecord extends TimeStampEntity {

    /**
     * Входящий абонент
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incoming_caller_id", nullable = false)
    private Caller incomingCaller;

    /**
     * Исходящий абонент
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outcoming_caller_id", nullable = false)
    private Caller outcomingCaller;

    /**
     * Дата и время начала звонка
     */
    @Column(name = "start_call_time", nullable = false)
    private LocalDateTime startCallTime;

    /**
     * Дата и время окончания звонка
     */
    @Column(name = "finish_call_time", nullable = false)
    private LocalDateTime finishCallTime;

    /**
     * Признак отправления CDR в МС BRT
     */
    @Column(name = "is_sent", nullable = false)
    private boolean isSent = false;

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

