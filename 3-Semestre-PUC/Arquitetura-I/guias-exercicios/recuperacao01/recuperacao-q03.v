module q3(output s, input a, input b, input c);
  wire an, bn;
  wire w1, w2, w3;
  nand(an, a, a);
  nand(bn, b, b);
  nand(w1, an, c);
  nand(w2, an, b);
  nand(w3, bn, c);
  nand(s, w1, w2, w3);
endmodule