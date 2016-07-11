package com.ykode.lessons;

/**
 * Created by lynxluna on 10/07/16.
 */
public class CoffeeCup {
  public final int     beanShot;
  public final float   sugarSpoon;
  public final float   milkCup;
  public final boolean withWhipCream;

  private CoffeeCup( final int beanShot, final float sugarSpoon,
                     final float milkCup, final boolean withWhipCream ) {
    this.beanShot = beanShot;
    this.sugarSpoon = sugarSpoon;
    this.milkCup = milkCup;
    this.withWhipCream = withWhipCream;
  }

  @Override
  public String toString() {
    return "Coffee Cup: " + beanShot + "shots, " + milkCup + " cup of milk, " + sugarSpoon + " spoons of sugar, and " +
        (withWhipCream ? "with" : "no") + " whip cream.";
  }

  public Builder newBuilder() {
    return new Builder(beanShot)
        .sugar(sugarSpoon)
        .milk(milkCup)
        .whipCream(withWhipCream);
  }

  public static class Builder {
    // required
    private final int beanShot;

    // optional
    private float sugarSpoon      = 0.0f;
    private float milkCup         = 0.0f;
    private boolean withWhipCream = false;

    public Builder(final int beanShot) {
      this.beanShot = beanShot;
    }

    public Builder sugar(final float sugarSpoon) {
      this.sugarSpoon = sugarSpoon;
      return this;
    }

    public Builder milk(final float milkCup) {
      this.milkCup = milkCup;
      return this;
    }

    public Builder whipCream(final boolean withWhipCream) {
      this.withWhipCream = withWhipCream;
      return this;
    }

    public final CoffeeCup build() {
      return new CoffeeCup(beanShot, sugarSpoon, milkCup, withWhipCream);
    }
  }
}
