package org.booking.entity;

public enum SeatStatus {

    AVAILABLE,
    BOOKED,
    LOCKED,      // For temporary 5-10 min holding during checkout
    MAINTENANCE

}
