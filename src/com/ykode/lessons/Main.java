package com.ykode.lessons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
  public static <T> List<T> map (final List<T> in, final Func<T, T> f) {
    final List<T> transformed = new ArrayList<T>(in.size());
    for(T item : in) {
      transformed.add(f.apply(item));
    }
    return transformed;
  }

  public static <T> void execute(final List <T> in, final Action<T> r) {
    for (T item : in) {
      r.call(item);
    }
  }

  public static <T, R> R reduce(final List<T> in, final Func2<R, T, R> f, final R init) {
    R acc = init;
    for (T x : in) {
      acc =  f.apply(acc, x);
    }
    return acc;
  }

  public static <T> List<T> filter (final List <T> in, final Func<T, Boolean> pred) {
    final List<T> filtered = new ArrayList<T>(in.size());
    for (T item : in) {
      if (pred.apply(item)) {
        filtered.add(item);
      }
    }
    return filtered;
  }

  public static void main(String[] args) {
    final CoffeeCup c = new CoffeeCup.Builder(2)
        .milk(1)
        .sugar(2)
        .whipCream(false)
        .build();

    System.out.println(c);

    final CoffeeCup cBlack = c.newBuilder()
        .milk(0)
        .sugar(0)
        .whipCream(false)
        .build();

    System.out.println(cBlack);

    final List<CoffeeCup> cups = Arrays.asList(new CoffeeCup.Builder(1).build(),
                                               new CoffeeCup.Builder(2).build(),
                                               new CoffeeCup.Builder(3).build());

    final List<CoffeeCup> sweetCups = new ArrayList<>(cups.size());

    for (final CoffeeCup cup : cups ) {
      sweetCups.add(cup.newBuilder().sugar(1).build());
    }

    for (final CoffeeCup cup : sweetCups) {
      System.out.println(cup);
    }

    final List<CoffeeCup> tooSweetCups = map(cups, new Func<CoffeeCup, CoffeeCup>() {
      @Override
      public CoffeeCup apply(final CoffeeCup in) {
        return in.newBuilder().sugar(3).build();
      }
    });

    for (final CoffeeCup cup : tooSweetCups) {
      System.out.println(cup);
    }

    // 16 calories
    final int calPerTsp = 16;
    float totalCalorie = 0.0f;
    for (final CoffeeCup cup : tooSweetCups) {
      totalCalorie += calPerTsp * cup.sugarSpoon;
    }

    System.out.println("Total calorie on too sweet cups: " + totalCalorie);

    final Func<CoffeeCup, CoffeeCup> addWhipFunc = new Func<CoffeeCup, CoffeeCup>() {
      @Override
      public CoffeeCup apply(final CoffeeCup in) {
        return in.newBuilder().whipCream(true).build();
      }
    };

    final Action<CoffeeCup> printAction = new Action<CoffeeCup>() {
      @Override
      public void call(final CoffeeCup cup) {
        System.out.println(cup);
      }
    };

    execute(map(cups, addWhipFunc), printAction);
    execute(map(tooSweetCups, addWhipFunc), printAction);
    execute(map(cups, x -> x.newBuilder().whipCream(true).sugar(1.5f).build()), System.out::println);

    final Func2<Float, CoffeeCup, Float> calorieTotalF = new Func2<Float, CoffeeCup, Float>() {
      static final int calPerSpoon = 16;
      @Override
      public Float apply(final Float cal, final CoffeeCup cup) {
        return cal + cup.sugarSpoon * calPerSpoon;
      }
    };

    System.out.println("Total calorie for sweet cup: " + reduce(sweetCups, calorieTotalF, 0.0f));
    System.out.println("Total calorie for too sweet cup: " + reduce(tooSweetCups, calorieTotalF, 0.0f));

    execute(filter(cups, x -> x.beanShot >=2), System.out::println);
  }
}
