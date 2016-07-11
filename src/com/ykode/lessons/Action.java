package com.ykode.lessons;

/**
 * Created by lynxluna on 11/07/16.
 */
public interface Action <T> {
  void call(final T param);
}
