package br.fagner.paultickets.value;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
	
	RESERVED(0),
	CONFIRMED(1),
	CANCELED(2);
	
	private int status;

	private OrderStatusEnum(int status) {
		this.status = status;
	}

}
