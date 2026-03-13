/*
 Guia_0303.v
 884985 - Eduardo Murta De Abreu
*/
module Guia_0303;
// define data
 reg signed [4:0] a = 5'b10111;
 reg signed [5:0] b = 6'b110101;
 integer v_pos;

// actions
 initial
 begin : main
 $display ( "Guia_0303 - Eduardo Murta De Abreu" );
 v_pos = ~(a - 1);
 $display ( "a = %5b (C2) -> Positivo = %d", a, v_pos );
 
 v_pos = ~(b - 1);
 $display ( "b = %6b (C2) -> Positivo = %d", b, v_pos );
 end // main
endmodule // Guia_0303

/*
Saída no terminal:
Guia_0303 - Eduardo Murta De Abreu
Guia_0303 - Eduardo Murta De Abreu
a = 10111 (C2) -> Positivo =           9
b = 110101 (C2) -> Positivo =          11
*/