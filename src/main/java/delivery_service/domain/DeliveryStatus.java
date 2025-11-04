package delivery_service.domain;

import common.ddd.Entity;

interface DeliveryStatus extends Entity<DeliveryId> {

    DeliveryState getState();

    DeliveryTime getTimeLeft();
}