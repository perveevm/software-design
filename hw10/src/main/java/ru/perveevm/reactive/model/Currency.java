package ru.perveevm.reactive.model;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 */
public enum Currency {
    USD(1.0),
    EUR(1.1),
    RUB(0.009);

    public final double toUSDCoefficient;

    Currency(final double toUSDCoefficient) {
        this.toUSDCoefficient = toUSDCoefficient;
    }
}
