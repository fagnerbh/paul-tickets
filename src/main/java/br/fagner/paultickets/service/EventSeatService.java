package br.fagner.paultickets.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.fagner.paultickets.dao.EventSeatDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventSeatService {

    private final EventSeatDao eventSeatDao;

    public List<Map<String, Object>> listSectorsByEvent() {
        return eventSeatDao.getSeatListbyEventAndSector();
    }
}
