//recuperacao modulos verilogs Eduardo Murta De Abreu 884985
module q1a(output s, input a, input b, input c, input d);
  assign s = (~a & ~b & c & d)   |  // m3
             (~a &  b & ~c & ~d) |  // m4
             (~a &  b & c & ~d)  |  // m6
             (~a &  b & c & d)   |  // m7
             ( a & ~b & ~c & d)  |  // m9
             ( a & ~b & c & d)   |  // m11
             ( a &  b & ~c & ~d);   // m12
endmodule

module q1c(output s, input a, input b, input c, input d);
  assign s = (c & d)         |  // cd
             (~a & b & ~d)   |  // a'bd'
             (b & ~c & ~d)   |  // bc'd'
             (a & ~b & d);      // ab'd
endmodule

module q1d(output s, input A, input B, input C, input D);
  assign s = (A|B|C)    &  // D1
             (A|C|~D)   &  // D2
             (~A|~B|~D) &  // D3
             (~A|~C|D)  &  // D4
             (~A|B|D);     // D5
endmodule

module q1e(output s, input a, input b, input c, input d);
  wire an, bn, cn, dn;
  wire t1, t2a, t2, t3a, t3, t4a, t4;
  wire n1, n2;

  // Inverter cada termo
  nand(an, a, a);
  nand(bn, b, b);
  nand(cn, c, c);
  nand(dn, d, d);

  // NAND(c,d) = (cd)'
  nand(t1,  c,  d);

  // NAND(NAND(a',b), d') = (a'bd')'
  nand(t2a, an, b);
  nand(t2,  t2a, dn);

  // NAND(NAND(b,c'), d') = (bc'd')'
  nand(t3a, b,  cn);
  nand(t3,  t3a, dn);

  // NAND(NAND(a,b'), d) = (ab'd)'
  nand(t4a, a,  bn);
  nand(t4,  t4a, d);

  nand(n1, t1, t2);
  nand(n2, t3, t4);
  nand(s,  n1, n2);
endmodule

module q1f();
  //nao consegui fazer
endmodule

