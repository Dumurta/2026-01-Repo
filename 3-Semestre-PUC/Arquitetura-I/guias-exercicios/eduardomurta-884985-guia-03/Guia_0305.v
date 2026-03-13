/*
 Guia_0305.v
 884985 - Eduardo Murta De Abreu
*/
module Guia_0305;
// define data
 reg [7:0] a = 8'b00110001; // 49
 reg [7:0] b = 8'b00001011; // 11
 reg [7:0] c;

// actions
 initial
 begin : main
 $display ( "Guia_0305 - Eduardo Murta De Abreu" );
 c = a - b;
 $display ( "a - b = %8b (38 decimal)", c );
 end // main
endmodule // Guia_0305

/*
Saída no terminal:
Guia_0305 - Eduardo Murta De Abreu
a - b = 00100110 (38 decimal)
*/