import fun, data from 'std.tf';
import assert, expect, expect_error, to, describe, it, subject, before, after from "std/spec";

alias fun.thread as thread;

library spec {
  spec:
    describe("fun.thread", [

      it("of_empty", () -> 
        expect(thread(0, []), to.be(0))
      ),
    
      it("of_one_f", () -> 
        expect(thread(0, [(x) -> x+1]), to.be(1))
      ),
    
      it("of_three_f", () -> 
        expect(
          thread(2,
            [
              (x) -> x+1,
              (x) -> x*5,
              (x) -> x+4
            ]
          ),
          to.be(19) # (2+1)*5+4
        )
      ),
    
      it("of_f_nil", () -> 
        expect_error(
          () -> thread(1, nil),
          to.have_code("NIL_ERROR")
        )
      ),
    
      it("of_bad_f", () -> 
        expect_error(
          () -> thread(1, [() -> true]),
          to.have_code("UNEXPECTED_ARGUMENT")
        )
      ),

  ]);
}