package ru.perveevm.reactive.util;

import ru.perveevm.reactive.model.Currency;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 */
public class CurrencyUtils {
    public static double convert(final Currency from, final Currency to, final double price) {
        return price * from.toUSDCoefficient / to.toUSDCoefficient;
    }
}
