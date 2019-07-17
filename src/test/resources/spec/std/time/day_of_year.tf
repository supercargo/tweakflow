import time as t from 'std.tf';
import assert, expect, expect_error, to, describe, it, subject, before, after from "std/spec";

alias t.day_of_year as day_of_year;

library spec {
  spec:
    describe("day_of_year", [


  of_default:
    expect(day_of_year(), to.be_nil());

  of_x_nil:
    expect(day_of_year(nil), to.be_nil());

  of_epoch:
    expect(day_of_year(t.epoch), to.be(1));

  of_jan_31st:
    expect(day_of_year(2019-01-31T), to.be(31));

  of_feb_1st:
    expect(day_of_year(2019-02-01T), to.be(31+1));

  of_dec_31st:
    expect(day_of_year(2019-12-31T), to.be(365));

  of_leap_dec_31st:
    expect(day_of_year(2016-12-31T), to.be(366));

  ]);
}