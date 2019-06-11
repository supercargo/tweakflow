import * as std from 'std.tf';

alias std.core.hash as hash;

library lib {
  f: (x) -> x;
}

library operator_spec {

  nil_nil:  nil == nil  == true;

  true_true:    true == true   == true;
  false_true:  false == true   == false;
  true_false:   true == false  == false;
  false_false: false == false  == true;

  l0_l1: 0 == 1        == false;
  l1_l0: 1 == 0        == false;
  l1_l1: 1 == 1        == true;

  d0_d1: 0.0 == 1.0    == false;
  d1_d0: 1.0 == 0.0    == false;
  d1_d1: 1.0 == 1.0    == true;

  l0_d0: 0 == 0.0    == true;
  h_l0_d0: hash(0) == hash(0.0) == true;

  l0_d1: 0 == 1.0    == false;
  l1_d0: 1 == 0.0    == false;
  l1_d1: 1 == 1.0    == true;
  h_l1_d1: hash(1) == hash(1.0) == true;

  d0_l0: 0.0 == 0    == true;
  d0_l1: 0.0 == 1    == false;
  d1_l0: 1.0 == 0    == false;
  d1_l1: 1.0 == 1    == true;

  nil_l0: nil == 0     == false;
  l0_nil:   0 == nil   == false;

  nil_d0: nil == 0.0   == false;
  d0_nil: 0.0 == nil   == false;

  inf_inf:    Infinity == Infinity  == true;
  ninf_inf:  -Infinity == Infinity  == false;
  inf_ninf:   Infinity == -Infinity == false;
  ninf_ninf: -Infinity == -Infinity == true;

  inf_d0:      Infinity == 0.0   == false;
  ninf_d0:    -Infinity == 0.0   == false;
  d0_inf:      0.0 == Infinity   == false;
  d0_ninf:     0.0 == -Infinity  == false;

  nan_nan:    NaN == NaN == false; # note that NaN is not equal to itself
  nan_d0:     NaN == 0.0 == false;
  d0_nan:     0.0 == NaN == false;

  foo_bar:    "foo" == "bar" == false;
  foo_foo:    "foo" == "foo" == true;
  l0_bar:         0 == "bar" == false;
  bar_l0:     "bar" == 0     == false;
  l0_s0:         0  == "0"   == false;
  s0_l0:        "0" == 0     == false;

  be_be:      0b == 0b       == true;
  b0_b0:      0b00 == 0b00   == true;
  b0_b0000:   0b00 == 0b0000 == false;
  b0_l0:      0b00 == 0      == false;
  b0001_b01:  0b0001 == 0b01 == false;
  b0001_b0100: 0b0001 == 0b0100 == false;
  b0001_b0001: 0b0001 == 0b0001 == true;

  f_f:        lib.f == lib.f == false; # note that there is no function equality

  a_a:        [] == []      == true;
  al0_a:     [0] == []      == false;
  al0_al0:   [0] == [0]     == true;
  al0_ad0:   [0] == [0.0]   == true;
  ab0_ab0:   [0b00] == [0b00] == true;
  ml0_md0:   {:a 0} == {:a 0.0} == true;
  mb0_mb0:   {:a 0b00} == {:a 0b00} == true;
  ml0_ml1:   {:a 0} == {:a 1} == false;
  anan_anan: [NaN]      == [NaN]      == false;
  af_af:     [lib.f]    == [lib.f]    == false;
  mf_mf:     {:a lib.f} == {:a lib.f} == false;
  mnan_mnan: {:a NaN}   == {:a NaN}   == false;

  dt_epoch:      1970-01-01T00:00:00Z == 1970-01-01T00:00:00Z == true;
  dt_epoch_dt:   1970-01-01T00:00:00Z == 1970-01-01T23:59:59Z == false;

  h_al0_ad0: hash([0]) == hash([0.0]) == true;

  al0_ad1:   [0] == [1.0]   == false;
  ad9_al9:   [9.0] == [9] == true;
  ad9_as9:   [9.0] == ["9"] == false;

  h_ad9_al9: hash([9.0]) == hash([9]) == true;

  a123_mixed: [1.0, 2, 3.0] == [1, 2.0, 3] == true;
  h_a123_mixed: hash([1.0, 2, 3.0]) == hash([1, 2.0, 3]) == true;


  m_m:        {} == {}          == true;
  mxl0_mxl0:  {:x 0} == {:x 0}  == true;
  mxl0_m:     {:x 0} == {}      == false;

  mab_mba:    {:a 1, :b 2} == {:b 2, :a 1}              == true;
  h_mab_mba:  hash({:a 1, :b 2}) == hash({:b 2, :a 1})  == true;

  mld:        {:a 1} == {:a 1.0}             == true;
  h_mld:      hash({:a 1}) == hash({:a 1.0}) == true;

  m_mixed:    {:a 1, :b 2.0} == {:a 1.0, :b 2}             == true;
  h_m_mixed:  hash({:a 1, :b 2.0}) == hash({:a 1.0, :b 2}) == true;

  nested_mixed: {:a [1.0, 2, 3.0, {:a 1.0, :b [0]}]}
                ==
                {:a [1, 2.0, 3, {:b [0.0], :a 1}]}
                ==
                true;

  h_nested_mixed: hash({:a [1.0, 2, 3.0, {:a 1.0, :b [0]}]})
                  ==
                  hash({:a [1, 2.0, 3, {:b [0.0], :a 1}]})
                  ==
                  true;



}