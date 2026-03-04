package com.galitianu.casestudy.base.persistence.entity;


import com.galitianu.casestudy.base.utils.Versioned;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@ToString(of = "id")
@EqualsAndHashCode(of = {"id", "version"})
public abstract class BaseEntity implements Versioned {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", nullable = false, unique = true, updatable = false)
    protected UUID id;

    @Version
    protected long version;

    @Column(name = "created", columnDefinition = "TIMESTAMPTZ", nullable = false, updatable = false)
    protected ZonedDateTime created;

    @Column(name = "updated", columnDefinition = "TIMESTAMPTZ", nullable = false)
    protected ZonedDateTime updated;

    @Override
    public long getVersion() {
        return version;
    }

    @PrePersist
    protected void onPrePersist() {
        ZonedDateTime now = ZonedDateTime.now();
        created = now;
        updated = now;
    }

    @PreUpdate
    protected void onPreUpdate() {
        updated = ZonedDateTime.now();
    }
}
