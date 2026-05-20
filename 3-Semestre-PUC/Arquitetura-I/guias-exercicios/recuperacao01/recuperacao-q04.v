module q4(output s, input a, input b, input c);
  assign s = (~a & ~c) | (b & ~c) | (a & c);
endmodule
 