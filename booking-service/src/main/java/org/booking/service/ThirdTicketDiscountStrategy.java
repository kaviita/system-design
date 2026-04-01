package org.booking.service;

import org.commons.entity.MovieShow;
import org.commons.entity.ShowSeat;

import java.math.BigDecimal;
import java.util.List;

public class ThirdTicketDiscountStrategy implements DiscountStrategy {

    @Override
    public BigDecimal applyDiscount(List<ShowSeat> seats, MovieShow show) {
        int ticketCount = seats.size();
        if (ticketCount % 3 == 0) {

        }
        // No discount if less than 3 tickets
        if (ticketCount < 3) {
            return BigDecimal.ONE;
        }
        // Calculate the number of "3rd tickets" (e.g., 6 tickets = 2 discounts)
        int discountCount = ticketCount / 3;

        // Discount Amount = (Base Price * 0.50) * number of eligible tickets
        BigDecimal basePrice = show.getBasePrice();
        BigDecimal discountPerTicket = basePrice.multiply(new BigDecimal("0.50"));

        return discountPerTicket.multiply(new BigDecimal(discountCount));
    }

}
