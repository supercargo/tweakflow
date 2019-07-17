import time as t from 'std.tf';
import assert, expect, expect_error, to, describe, it, subject, before, after from "std/spec";

alias t.zone as zone;

library spec {
  spec:
    describe("zone", [


  of_default:
    expect(zone(), to.be_nil());

  of_x_nil:
    expect(zone(nil), to.be_nil());

  of_epoch:
    expect(zone(t.epoch), to.be("UTC"));

  of_offset:
    expect(zone(2019-01-01T00:00:00+02:00), to.be("UTC+02:00"));

  of_zone:
    expect(zone(2010-01-01T00:00:00.00+01:00@Europe/Berlin), to.be("Europe/Berlin"));


  ]);
}