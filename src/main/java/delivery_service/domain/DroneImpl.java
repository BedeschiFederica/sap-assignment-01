package delivery_service.domain;

import java.util.ArrayList;
import java.util.List;

class DroneImpl extends Thread implements Drone {

    private static final int DURATION_MULTIPLIER = 5;
    private static final int HOUR_IN_SECONDS = 1000;// 3600;
    private static final int PERIOD_IN_HOURS = 1;

    private final List<DroneObserver> droneObservers;
    private final DeliveryDetail deliveryDetail;
    private final int deliveryDurationInHours;

    public DroneImpl(final DeliveryDetail deliveryDetail) {
        super(deliveryDetail.getId().id().replace("delivery", "drone"));
        this.deliveryDetail = deliveryDetail;
        this.droneObservers = new ArrayList<>();
        this.deliveryDurationInHours = DURATION_MULTIPLIER * ((int) this.deliveryDetail.weight());
    }

    @Override
    public void startDrone() {
        this.start(); // TODO: to start when expectedShippingTime
    }

    @Override
    public void run() {
        System.out.println(this.droneObservers);
        this.notifyDeliveryEvent(new Shipped(
                this.deliveryDetail.getId(),
                new DeliveryTime(this.deliveryDurationInHours / 24, this.deliveryDurationInHours % 24)
        ));
        for (int i = 0; i < this.deliveryDurationInHours; i++) {
            try {
                Thread.sleep(PERIOD_IN_HOURS * HOUR_IN_SECONDS);
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.notifyDeliveryEvent(new TimeElapsed(
                    this.deliveryDetail.getId(),
                    new DeliveryTime(0, PERIOD_IN_HOURS)
            ));
        }
        this.notifyDeliveryEvent(new Delivered(this.deliveryDetail.getId()));
    }

    @Override
    public void addDroneObserver(final DroneObserver observer) {
        this.droneObservers.add(observer);
    }

    private void notifyDeliveryEvent(final DeliveryEvent event) {
        this.droneObservers.forEach(obs -> obs.notifyDeliveryEvent(event));
    }
}
