package delivery_service.domain;

import java.time.Instant;
import java.util.Date;

public class DeliveryDetailImpl implements DeliveryDetail {

    private final DeliveryId id;
    private final double weight;
    private final Address startingPlace;
    private final Address destinationPlace;
    private final Date expectedShippingDate;

    public DeliveryDetailImpl(
            final DeliveryId id,
            final double weight,
            final Address startingPlace,
            final Address destinationPlace,
            final Date expectedShippingDate
    ) {
        this.id = id;
        this.weight = weight;
        this.startingPlace = startingPlace;
        this.destinationPlace = destinationPlace;
        this.expectedShippingDate = expectedShippingDate;
    }

    public DeliveryDetailImpl(
            final DeliveryId id,
            final double weight,
            final Address startingPlace,
            final Address destinationPlace
    ) {
        this.id = id;
        this.weight = weight;
        this.startingPlace = startingPlace;
        this.destinationPlace = destinationPlace;
        this.expectedShippingDate = Date.from(Instant.now());
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public Address getStartingPlace() {
        return this.startingPlace;
    }

    @Override
    public Address getDestinationPlace() {
        return this.destinationPlace;
    }

    @Override
    public Date getExpectedShippingDate() {
        return this.expectedShippingDate;
    }

    @Override
    public DeliveryId getId() {
        return this.id;
    }
}
