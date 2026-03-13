/*
 Guia_0301.v
 884985 - Eduardo Murta De Abreu
*/
module Guia_0301;
// define data
 reg [5:0] a = 6'b001010;
 reg [7:0] b = 8'b00001001;
 reg [5:0] c = 6'b101001;
 reg [5:0] d_c1, d_c2;

// actions
 initial
 begin : main
 $display ( "Guia_0301 - Eduardo Murta De Abreu" );
 d_c1 = ~a;
 d_c2 = ~a + 1;
 $display ( "a = %6b -> C1(a) = %6b -> C2(a) = %6b", a, d_c1, d_c2 );
 
 d_c1 = ~c;
 d_c2 = ~c + 1;
 $display ( "c = %6b -> C1(c) = %6b -> C2(c) = %6b", c, d_c1, d_c2 );
 end // main
endmodule // Guia_0301

/*
Saída no terminal:
Guia_0301 - Eduardo Murta De Abreu
a = 001010 -> C1(a) = 110101 -> C2(a) = 110110
c = 101001 -> C1(c) = 010110 -> C2(c) = 010111
*/