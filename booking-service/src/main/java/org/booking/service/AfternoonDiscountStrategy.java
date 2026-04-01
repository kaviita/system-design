package org.booking.service;

import org.commons.entity.MovieShow;
import org.commons.entity.ShowSeat;
import java.math.BigDecimal;
import java.util.List;

public class AfternoonDiscountStrategy implements DiscountStrategy {

    @Override
    public BigDecimal applyDiscount(List<ShowSeat> seats, MovieShow show) {
        int hour = show.getStartTime().getHour();
        // Afternoon defined as 12:00 PM to 4:00 PM
        if (hour >= 12 && hour < 16) {
            return new BigDecimal("0.80"); // 20% discount
        }
        return BigDecimal.ONE;
    }

}
