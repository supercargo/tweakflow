import * as std from "std.tf";

alias std.core.hash as hash;

library lib {
  f: (x) -> x;
}

library operator_spec {

  nil_nil:  (nil === nil)  == true;

  true_true:   (true === true)   == true;
  false_true:  (false === true)   == false;
  true_false:   (true === false)  == false;
  false_false: (false === false)  == true;

  be_be:     (0b === 0b)   == true;
  b00_b00: (0b00 === 0b00) == true;
  b00_b01: (0b00 === 0b01) == false;

  l0_l1: (0 === 1)        == false;
  l1_l0: (1 === 0)        == false;
  l1_l1: (1 === 1)        == true;

  d0_d1: (0.0 === 1.0)    == false;
  d1_d0: (1.0 === 0.0)    == false;
  d1_d1: (1.0 === 1.0)    == true;

  dc0_dc1: (0d === 1d)    == false;
  dc1_dc0: (1d === 0d)    == false;
  dc1_dc1: (1d === 1d)    == true;

  l0_d0:  (0 === 0.0)    == false;
  l0_dc0: (0 === 0d)     == false;

  l0_d1: (0 === 1.0)    == false;
  l1_d0: (1 === 0.0)    == false;
  l1_d1: (1 === 1.0)    == false;
  l0_dc1: (0 === 1d)    == false;
  l1_dc0: (1 === 0d)    == false;
  l1_dc1: (1 === 1d)    == false;

  d0_l0: (0.0 === 0)    == false;
  d0_l1: (0.0 === 1)    == false;
  d1_l0: (1.0 === 0)    == false;
  d1_l1: (1.0 === 1)    == false;

  d0_dc0: (0.0 === 0d)    == false;
  d0_dc1: (0.0 === 1d)    == false;
  d1_dc0: (1.0 === 0d)    == false;
  d1_dc1: (1.0 === 1d)    == false;

  dc0_l0: (0d === 0)    == false;
  dc0_l1: (0d === 1)    == false;
  dc1_l0: (1d === 0)    == false;
  dc1_l1: (1d === 1)    == false;

  nil_l0: (nil === 0)     == false;
  l0_nil:  (0 === nil)   == false;

  nil_d0: (nil === 0.0)   == false;
  d0_nil: (0.0 === nil)   == false;

  inf_inf:    (Infinity === Infinity)  == true;
  ninf_inf:  (-Infinity === Infinity)  == false;
  inf_ninf:   (Infinity === -Infinity) == false;
  ninf_ninf: (-Infinity === -Infinity) == true;

  inf_d0:      (Infinity === 0.0)   == false;
  ninf_d0:    (-Infinity === 0.0)   == false;
  d0_inf:      (0.0 === Infinity)   == false;
  d0_ninf:     (0.0 === -Infinity)  == false;

  nan_nan:    (NaN === NaN) == false; # note that NaN is not identical to itself
  nan_d0:     (NaN === 0.0) == false;
  d0_nan:     (0.0 === NaN) == false;

  foo_bar:    ("foo" === "bar") == false;
  foo_foo:    ("foo" === "foo") == true;
  l0_bar:         (0 === "bar") == false;
  bar_l0:     ("bar" === 0)     == false;
  l0_s0:         (0  === "0")   == false;
  s0_l0:        ("0" === 0)     == false;

  f_f:        (lib.f === lib.f) == false; # note that there is no function equality or identity

  a_a:        ([] === [])      == true;
  al0_a:     ([0] === [])      == false;
  al0_al0:   ([0] === [0])     == true;
  al0_ad0:   ([0] === [0.0])   == false;
  al0_adc0:  ([0] === [0d])   == false;
  ad0_adc0:  ([0.0] === [0d])   == false;
  ad0_adc0_p1:  ([0.0] === [0.0d])   == false;
  anan_anan: ([NaN] === [NaN]) == false;
  af_af:     ([lib.f] === [lib.f]) == false;
  mnan_mnan: ({:a NaN} === {:a NaN}) == false;
  mf_mf:     ({:a lib.f} === {:a lib.f}) == false;

  dt_epoch:      (1970-01-01T00:00:00Z === 1970-01-01T00:00:00Z) == true;
  dt_epoch_dt:   (1970-01-01T00:00:00Z === 1970-01-01T23:59:59Z) == false;

  al0_ad1:   ([0] === [1.0])   == false;
  ad9_al9:   ([9.0] === [9]) == false;
  ad9_adc9:  ([9.0] === [9d]) == false;
  ad9_as9:   ([9.0] === ["9"]) == false;

  a123_mixed: ([1.0, 2, 3.0] === [1, 2.0, 3]) == false;
  a123_same: ([1.0, 2.0, 3.0] === [1.0, 2.0, 3.0]) == true;

  m_m:        ({} === {})          == true;
  mxl0_mxl0:  ({:x 0} === {:x 0})  == true;
  mxl0_m:     ({:x 0} === {})      == false;

  mab_mba:    ({:a 1, :b 2} === {:b 2, :a 1})    == true;

  mld:        ({:a 1} === {:a 1.0})             == false;
  mldc:       ({:a 1} === {:a 1d})             == false;

  m_mixed_d:    ({:a 1, :b 2.0} === {:a 1.0, :b 2}) == false;
  m_mixed_dc:   ({:a 1, :b 2d} === {:a 1.0, :b 2}) == false;

  nested_mixed: {:a [1.0, 2, 3.0, {:a 1.0, :b [0]}]}
                ===
                {:a [1, 2.0, 3, {:b [0.0], :a 1}]}
                ==
                false;

  nested_mixed_dc: {:a [1d, 2, 3d, {:a 1d, :b [0]}]}
                ===
                {:a [1, 2d, 3, {:b [0d], :a 1}]}
                ==
                false;

  nested_same:   (
                  {:a [1.0, 2, 3d, {:a 1.0, :b [0d]}]}
                  ===
                  {:a [1.0, 2, 3d, {:a 1.0, :b [0d]}]}
                 )
                 ==
                 true;

}