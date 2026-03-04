package com.galitianu.casestudy.base.api;

import com.galitianu.casestudy.base.utils.Versioned;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public abstract class BaseEntityDto implements Versioned {
    protected UUID id;
    protected long version;
    protected ZonedDateTime created;
    protected ZonedDateTime updated;
}
