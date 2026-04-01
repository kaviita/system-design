package org.booking.service;

import org.commons.entity.MovieShow;
import org.commons.entity.ShowSeat;
import java.math.BigDecimal;
import java.util.List;

public interface DiscountStrategy {

    BigDecimal applyDiscount(List<ShowSeat> seats, MovieShow show);

}
