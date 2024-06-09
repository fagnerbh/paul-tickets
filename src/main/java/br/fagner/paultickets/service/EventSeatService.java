package br.fagner.paultickets.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.fagner.paultickets.dao.EventSeatDao;
import br.fagner.paultickets.model.dto.EventSectorDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventSeatService {

    private final EventSeatDao eventSeatDao;

    public List<EventSectorDto> listSectorsByEvent() {
        return eventSeatDao.getSeatListbyEventAndSector();
    }
}
