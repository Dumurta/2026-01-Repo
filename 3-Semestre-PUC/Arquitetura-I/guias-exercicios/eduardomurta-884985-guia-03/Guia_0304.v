/*
 Guia_0304.v
 884985 - Eduardo Murta De Abreu
*/
module Guia_0304;
// define data
 reg signed [7:0] a = 8'b00011011; // 27
 reg signed [7:0] b = 8'b00001101; // 13
 reg signed [7:0] res;

// actions
 initial
 begin : main
 $display ( "Guia_0304 - Eduardo Murta De Abreu" );
 res = a - b;
 $display ( "27 - 13 = %d (%8b)", res, res );
 end // main
endmodule // Guia_0304

/*
Saída no terminal:
Guia_0304 - Eduardo Murta De Abreu
27 - 13 = 14 (00001110)
*/