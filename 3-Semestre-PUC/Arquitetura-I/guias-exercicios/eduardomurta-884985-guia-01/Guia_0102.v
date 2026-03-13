/*
 Guia_0102.v
    884985 - Eduardo Murta De Abreu
*/
module Guia_0102;
// define data
 integer x = 0; // decimal
 reg [7:0] b = 8'b0111011; // binary (bits - little endian)
// actions
 initial
 begin : main
 $display ( "Guia_0102 - Eduardo" );
 $display ( "x = %d" , x );
 $display ( "b = %8b", b );
 x = b;
 $display ( "b = %d", x );
 end // main
endmodule // Guia_0102

/*
    a.
    x =           0
    b = 00010010
    b =          18
    
    b.
    x =           0
    b = 00010101
    b =          21
    
    c.
    x =           0
    b = 00010110
    b =          22
    
    d.
    x =           0
    b = 00101011
    b =          43
    
    e.
    x =           0
    b = 00111011
    b =          59
    OBS: todas as respostas ficaram iguais as contas.
*/