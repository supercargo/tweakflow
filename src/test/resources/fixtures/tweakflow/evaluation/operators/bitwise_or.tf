import math, time from "std.tf";
alias math.max_long as max_long;

library lib {
  f: (x) -> x;
}

library operator_spec {

  nil_nil: (nil | nil)  == nil;

  l0_l0: (0 | 0)        == 0;
  l0_l1: (0 | 1)        == 1;
  l1_l0: (1 | 0)        == 1;
  l1_l1: (1 | 1)        == 1;

  dc0_dc0: (0d | 0d)        == 0;
  dc0_dc1: (0d | 1d)        == 1;
  dc1_dc0: (1d | 0d)        == 1;
  dc1_dc1: (1d | 1d)        == 1;

  l1_l2_l4_l8: (1|2|4|8) == 15;

  max_or: (max_long | 1) == max_long;

  d0_d4: (0.0 | 4.0)    == 4;
  d0_d1: (0.0 | 1.0)    == 1;
  d1_d0: (1.0 | 0.0)    == 1;
  d1_d1: (1.0 | 1.0)    == 1;

  l0_d0: (0 | 0.0)    == 0;
  l0_d1: (0 | 1.0)    == 1;
  l1_d0: (1 | 0.0)    == 1;
  l1_d1: (1 | 1.0)    == 1;

  d0_l0: (0.0 | 0)    == 0;
  d0_l1: (0.0 | 1)    == 1;
  d1_l0: (1.0 | 0)    == 1;
  d1_l1: (1.0 | 1)    == 1;

  dc0_l0: (0.0d | 0)    == 0;
  dc0_l1: (0.0d | 1)    == 1;
  dc1_l0: (1.0d | 0)    == 1;
  dc1_l1: (1.0d | 1)    == 1;

  nil_l0: (nil | 0)     == nil;
  l0_nil: (  0 | nil)   == nil;

  nil_d0: (nil | 0.0)   == nil;
  d0_nil: (0.0 | nil)   == nil;

  nil_bar: try     nil | "bar"    catch "error" == "error";
  foo_nil: try   "foo" | nil      catch "error" == "error";
  foo_bar: try   "foo" | "bar"    catch "error" == "error";
  l0_bar:  try       0 | "bar"    catch "error" == "error";
  bar_l0:  try   "bar" | 0        catch "error" == "error";
  b00_l0:  try    lib.f(0b00) | 0        catch "error" == "error";
  l0_b00:  try       0 | lib.f(0b00)     catch "error" == "error";

  f_f:     try   lib.f | lib.f    catch "error" == "error";

  dt_dt:   try   time.epoch | time.epoch    catch "error" == "error";

}