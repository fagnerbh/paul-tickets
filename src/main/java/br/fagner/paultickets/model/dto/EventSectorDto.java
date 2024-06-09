package br.fagner.paultickets.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class EventSectorDto {
    private String eventId;

    private String sectorId;

    private int seatCount;

}
