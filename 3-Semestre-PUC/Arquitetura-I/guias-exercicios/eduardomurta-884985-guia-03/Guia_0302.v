/*
 Guia_0302.v
 884985 - Eduardo Murta De Abreu
*/
module Guia_0302;
// define data
 reg [5:0] a = 6'b100111; // 213(4)
 reg [7:0] b = 8'hd6;     // D6(16)
 reg [7:0] res_c1, res_c2;

// actions
 initial
 begin : main
 $display ( "Guia_0302 - Eduardo Murta De Abreu" );
 res_c1 = ~a;
 res_c2 = ~a + 1;
 $display ( "a(213_4) = %6b -> C1 = %6b -> C2 = %6b", a, res_c1[5:0], res_c2[5:0] );
 
 res_c1 = ~b;
 res_c2 = ~b + 1;
 $display ( "b(D6_16) = %8b -> C1 = %8b -> C2 = %8b", b, res_c1, res_c2 );
 end // main
endmodule // Guia_0302

/*
Saída no terminal:
Guia_0302 - Eduardo Murta De Abreu
a(213_4) = 100111 -> C1 = 011000 -> C2 = 011001
b(D6_16) = 11010110 -> C1 = 00101001 -> C2 = 00101010
*/