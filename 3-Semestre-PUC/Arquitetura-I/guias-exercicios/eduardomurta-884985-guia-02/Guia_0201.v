/*
 Guia_0201.v
 884985 - Eduardo Murta De Abreu
*/
module Guia_0201;
// define data
 real x = 0; 
 real power2 = 1.0;
 integer y = 7;
 reg [7:0] b = 8'b10100000; 

// actions
 initial
 begin : main
 $display ( "Guia_0201 - Eduardo" );
 $display ( "b = 0.%8b", b );
 while (y >= 0)
    begin
        power2 = power2 / 2.0;
        if (b[y] == 1)
            begin
                x = x + power2;
            end
        y = y - 1;
    end
 $display ( "x = %f", x );
 end // main
endmodule // Guia_0201

/*
 Saída do terminal:
 Guia_0201 - Eduardo
 b = 0.10100000
 x = 0.625000
*/