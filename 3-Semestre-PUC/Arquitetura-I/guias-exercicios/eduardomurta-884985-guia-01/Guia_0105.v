/*
 Guia_0105.v
 884985 - Eduardo Murta De Abreu
*/
module Guia_0105;
// define data
integer x = 13; // decimal
 reg [7:0] b ; // binary
 reg [0:15][7:0] s = "PUC-Minas"; // char array[3] (3x8 bits - little Endian)
// actions
 initial
 begin : main
 $display ( "Guia_0105 - Tests" );
 $display ( "x = %d" , x );
 $display ( "b = %8b", b );
 $display ( "s = %s" , s );
 b = x;
 $display ( "b = [%4b] [%4b] = %h %h", b[7:4], b[3:0], b[7:4], b[3:0] );
 s[0] = "-";
 s[1] = 8'b01001101; // 'M'
 s[2] = 71; // 'G'
 $display ( "s = %s" , s );
 end // main
endmodule // Guia_0105

//tentei alterar o valor de reg pra caber "PUC-MINAS" mas o resultado foi esse:
/*
x =          13
b = xxxxxxxx
s =        PUC-Minas
b = [0000] [1101] = 0 d
s = -MG    PUC-Minas
não consegui entender bem qual deveria ser a saida correta para o programa, se seria o valor ASCII ou não.
*/