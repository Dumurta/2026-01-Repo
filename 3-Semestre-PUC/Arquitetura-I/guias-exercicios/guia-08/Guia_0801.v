// Guia_0801
// Aluno: Eduardo Murta De Abreu - Matricula: 88485

module halfAdder (output s1, output s0, input a, input b);
    xor XOR1 (s0, a, b);
    and AND1 (s1, a, b);
endmodule

module fullAdder (output s1, output s0, input a, input b, input carryIn);
    wire w1, w2, w3;
    halfAdder HA1 (w1, w2, a, b);
    halfAdder HA2 (w3, s0, w2, carryIn);
    or OR1 (s1, w1, w3);
endmodule

module Guia_0801 (output [5:0] soma, output carryOut, input [5:0] a, input [5:0] b);
    wire [4:0] c; 
    fullAdder FA0 (c[0], soma[0], a[0], b[0], 1'b0);
    fullAdder FA1 (c[1], soma[1], a[1], b[1], c[0]);
    fullAdder FA2 (c[2], soma[2], a[2], b[2], c[1]);
    fullAdder FA3 (c[3], soma[3], a[3], b[3], c[2]);
    fullAdder FA4 (c[4], soma[4], a[4], b[4], c[3]);
    fullAdder FA5 (carryOut, soma[5], a[5], b[5], c[4]);
endmodule

module test_Guia_0801;
    reg [5:0] x, y;
    wire [5:0] s;
    wire cout;
    Guia_0801 adder (s, cout, x, y);
    initial begin
        $display("Guia_0801 - Eduardo Murta De Abreu - 88485");
        $display("Teste Somador 06 Bits:");
        x = 6'b000101; y = 6'b000011;
        #1 $display("%b + %b = %b (Carry: %b)", x, y, s, cout);
        x = 6'b111111; y = 6'b000001; 
        #1 $display("%b + %b = %b (Carry: %b)", x, y, s, cout);
    end
endmodule