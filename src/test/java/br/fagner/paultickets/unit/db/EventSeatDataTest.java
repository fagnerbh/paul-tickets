package br.fagner.paultickets.unit.db;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.fagner.paultickets.dao.EventSeatDao;

@RunWith(SpringRunner.class)
@DataJpaTest
class EventSeatDataTest {
	
	@Autowired
    private EventSeatDao eventSeatDao;

	@Test
	void reserveSeat_byEvent_ExistsAvailableSeats() {
		fail("Not yet implemented");
	}

}
