// Guia_0804 - COMPARADOR DESIGUALDADE 06 BITS

module Guia_0804 (output diferente, input [5:0] a, input [5:0] b);
    wire [5:0] d;
    xor XOR0 (d[0], a[0], b[0]);
    xor XOR1 (d[1], a[1], b[1]);
    xor XOR2 (d[2], a[2], b[2]);
    xor XOR3 (d[3], a[3], b[3]);
    xor XOR4 (d[4], a[4], b[4]);
    xor XOR5 (d[5], a[5], b[5]);
    or OR1 (diferente, d[0], d[1], d[2], d[3], d[4], d[5]);
endmodule

module test_Guia_0804;
    reg [5:0] x, y;
    wire diff;
    Guia_0804 comp (diff, x, y);
    initial begin
        $display("\nTeste Desigualdade 06 Bits:");
        x = 6'b111000; y = 6'b111000;
        #1 $display("A:%b B:%b -> Diferente: %b", x, y, diff);
        x = 6'b111000; y = 6'b011000;
        #1 $display("A:%b B:%b -> Diferente: %b", x, y, diff);
    end
endmodule