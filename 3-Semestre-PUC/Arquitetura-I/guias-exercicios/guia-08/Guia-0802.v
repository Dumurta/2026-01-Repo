// Guia_0802
// Aluno: Eduardo Murta De Abreu - Matricula: 88485

module halfSubtractor (output d, output v, input a, input b);
    xor XOR1 (d, a, b);
    and AND1 (v, ~a, b);
endmodule

module fullSubtractor (output d, output v, input a, input b, input borrowIn);
    wire w1, w2, w3;
    halfSubtractor HS1 (w1, w2, a, b);
    halfSubtractor HS2 (d, w3, w1, borrowIn);
    or OR1 (v, w2, w3);
endmodule

module Guia_0802 (output [5:0] diff, output borrowOut, input [5:0] a, input [5:0] b);
    wire [4:0] v;
    fullSubtractor FS0 (diff[0], v[0], a[0], b[0], 1'b0);
    fullSubtractor FS1 (diff[1], v[1], a[1], b[1], v[0]);
    fullSubtractor FS2 (diff[2], v[2], a[2], b[2], v[1]);
    fullSubtractor FS3 (diff[3], v[3], a[3], b[3], v[2]);
    fullSubtractor FS4 (diff[4], v[4], a[4], b[4], v[3]);
    fullSubtractor FS5 (diff[5], borrowOut, a[5], b[5], v[4]);
endmodule

module test_Guia_0802;
    reg [5:0] x, y;
    wire [5:0] d;
    wire bout;
    Guia_0802 sub (d, bout, x, y);
    initial begin
        $display("\nTeste Subtrator 06 Bits:");
        x = 6'b001010; y = 6'b000100; 
        #1 $display("%b - %b = %b (Borrow: %b)", x, y, d, bout);
        x = 6'b000100; y = 6'b001010; 
        #1 $display("%b - %b = %b (Borrow: %b)", x, y, d, bout);
    end
endmodule