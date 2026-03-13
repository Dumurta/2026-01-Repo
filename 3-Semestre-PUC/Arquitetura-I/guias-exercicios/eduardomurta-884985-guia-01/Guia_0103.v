/*
    Guia_0103.v
    884985 - Eduardo Murta De Abreu
*/
module Guia_0103;
// define data
 integer x = 763; // decimal
 reg [7:0] b = 0; // binary
// actions
 initial
 begin : main
 $display ( "Guia_0103 - Tests" );
 $display ( "x = %d" , x );
 $display ( "b = %8b", b );
 b = x;
 $display ( "b = %B (2) = %o (8) = %x (16) = %X (16)", b, b, b, b );
 end // main
endmodule // Guia_0103

/*
a)
x =          49
b = 00000000
b = 00110001 (2) = 061 (8) = 31 (16) = 31 (16)

b)
x =          61
b = 00000000
b = 00111101 (2) = 075 (8) = 3d (16) = 3d (16)

c)
x =          77
b = 00000000
b = 01001101 (2) = 115 (8) = 4d (16) = 4d (16)

d)
x =         135
b = 00000000
b = 10000111 (2) = 207 (8) = 87 (16) = 87 (16)

e)
x =         763
b = 00000000
b = 11111011 (2) = 373 (8) = fb (16) = fb (16)


*/