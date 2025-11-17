package delivery_service.domain;

import java.util.*;

public class DeliveryImpl implements Delivery, DroneObserver {

    private final DeliveryId id;
    private final DeliveryDetail deliveryDetail;
    private final MutableDeliveryStatus deliveryStatus;
    private final List<DeliveryObserver> observers;

    public DeliveryImpl(
            final DeliveryId deliveryId,
            final double weight,
            final Address startingPlace,
            final Address destinationPlace,
            final Calendar expectedShippingDate
    ) {
        this(deliveryId, weight, startingPlace, destinationPlace, Optional.of(expectedShippingDate));
    }

    public DeliveryImpl(
            final DeliveryId deliveryId,
            final double weight,
            final Address startingPlace,
            final Address destinationPlace,
            final Calendar expectedShippingDate,
            final DeliveryState deliveryState
    ) {
        this.id = deliveryId;
        this.deliveryDetail = new DeliveryDetailImpl(this.id, weight, startingPlace, destinationPlace,
                expectedShippingDate);
        this.deliveryStatus = new DeliveryStatusImpl(this.id);
        this.deliveryStatus.setDeliveryState(deliveryState);
        this.observers = new ArrayList<>();
        if (!deliveryState.equals(DeliveryState.DELIVERED)) {
            final Drone drone = new DroneImpl(this.deliveryDetail);
            drone.addDroneObserver(this);
            drone.startDrone();
        }
    }

    public DeliveryImpl(
            final DeliveryId deliveryId,
            final double weight,
            final Address startingPlace,
            final Address destinationPlace
    ) {
        this(deliveryId, weight, startingPlace, destinationPlace, Optional.empty());
    }

    private DeliveryImpl(
            final DeliveryId deliveryId,
            final double weight,
            final Address startingPlace,
            final Address destinationPlace,
            final Optional<Calendar> expectedShippingDate
    ) {
        this.id = deliveryId;
        this.deliveryDetail = expectedShippingDate
                .map(date -> new DeliveryDetailImpl(this.id, weight, startingPlace, destinationPlace, date))
                .orElseGet(() -> new DeliveryDetailImpl(this.id, weight, startingPlace, destinationPlace));
        this.deliveryStatus = new DeliveryStatusImpl(this.id);
        this.observers = new ArrayList<>();
        final Drone drone = new DroneImpl(this.deliveryDetail);
        drone.addDroneObserver(this);
        drone.startDrone();
    }

    @Override
    public DeliveryDetail getDeliveryDetail() {
        return this.deliveryDetail;
    }

    @Override
    public DeliveryStatus getDeliveryStatus() {
        return this.deliveryStatus;
    }

    @Override
    public void updateDeliveryState(final DeliveryState deliveryState) {
        this.deliveryStatus.setDeliveryState(deliveryState);
    }

    @Override
    public void addDeliveryObserver(final DeliveryObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public DeliveryId getId() {
        return this.id;
    }

    @Override
    public void notifyDeliveryEvent(final DeliveryEvent event) {
        System.out.println("Event " + event);
        if (event instanceof Shipped) {
            this.deliveryStatus.setDeliveryState(DeliveryState.SHIPPING);
            this.deliveryStatus.setTimeLeft(((Shipped) event).timeLeft());
        } else if (event instanceof TimeElapsed) {
            this.deliveryStatus.subDeliveryTime(((TimeElapsed) event).time());
        } else if (event instanceof Delivered) {
            this.deliveryStatus.setDeliveryState(DeliveryState.DELIVERED);
        }
        this.observers.forEach(obs -> obs.notifyDeliveryEvent(event));
    }
}
