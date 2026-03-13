/*
 Guia_0104.v
 884985 - Eduardo Murta De Abreu
*/
module Guia_0104;
// define data
 integer x = 53; // decimal
 reg [7:0] b = 0; // binary
// actions
 initial
 begin : main
 $display ( "Guia_0104 - Tests" );
 $display ( "x = %d" , x );
 $display ( "b = %8b", b );
 b = x;
 $display ( "b = [%4b] [%4b] = %x %x", b[7:4], b[3:0], b[7:4], b[3:0] ); // agrupamento
 end // main
endmodule // Guia_0104

/*
a)
x =          21
b = 00000000
b = [0001] [0101] = 1 5

b)
x =          24
b = 00000000
b = [0001] [1000] = 1 8

c)
x =          37
b = 00000000
b = [0010] [0101] = 2 5

d)
x =          45
b = 00000000
b = [0010] [1101] = 2 d

e)
x =          53
b = 00000000
b = [0011] [0101] = 3 5

OBS: não entendi ao certo como é o funcionamento deste programa, então apenas inseri os valores pra que fossem agrupados de 4 em 4.
*/