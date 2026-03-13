/*
 Guia_0101.v
 884985 - Eduardo Murta De Abreu
*/
module Guia_0101;
// define data
integer x = 366; // decimal
 reg [7:0] b = 0; // binary (bits - little endian)
// actions
 initial
 begin : main
 $display ( "Guia_0101 - Eduardo" );
 $display ( "x = %d" , x );
 $display ( "b = %8b", b );
 b = x;
 $display ( "b = %8b", b );
 end // main
endmodule // Guia_0101


/*
    a-
    x =          27
    b = 00000000
    b = 00011011
    
    b-
    x =          56
    b = 00000000
    b = 00111000

    c-
    x =         753
    b = 00000000
    b = 11110001

    d-
    x =         321
    b = 00000000
    b = 01000001

    e-
    x =         366
    b = 00000000
    b = 01101110

    OBS: notei que alguns resultados estao diferentes do que calculei, mas creio que minhas contas estao corretas.
*/
