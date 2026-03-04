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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
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
