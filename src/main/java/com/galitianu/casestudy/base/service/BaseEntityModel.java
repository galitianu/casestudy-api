package com.galitianu.casestudy.base.service;


import com.galitianu.casestudy.base.utils.Versioned;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = {"id", "version"})
public class BaseEntityModel implements Versioned {

    protected UUID id;
    protected long version;
    protected ZonedDateTime created;
    protected ZonedDateTime updated;

    @Override
    public long getVersion() {
        return version;
    }

    public void reset() {
        id = null;
        version = 0;
        created = null;
        updated = null;
    }

    public <T extends BaseEntityModel> void copy(T other) {
        id = other.getId();
        version = other.getVersion();
        created = other.getCreated();
        updated = other.getUpdated();
    }

}
