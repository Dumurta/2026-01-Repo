// Guia_0803 - COMPARADOR IGUALDADE 06 BITS

module Guia_0803 (output igual, input [5:0] a, input [5:0] b);
    wire [5:0] e;
    xnor XNOR0 (e[0], a[0], b[0]);
    xnor XNOR1 (e[1], a[1], b[1]);
    xnor XNOR2 (e[2], a[2], b[2]);
    xnor XNOR3 (e[3], a[3], b[3]);
    xnor XNOR4 (e[4], a[4], b[4]);
    xnor XNOR5 (e[5], a[5], b[5]);
    and AND1 (igual, e[0], e[1], e[2], e[3], e[4], e[5]);
endmodule

module test_Guia_0803;
    reg [5:0] x, y;
    wire eq;
    Guia_0803 comp (eq, x, y);
    initial begin
        $display("\nTeste Igualdade 06 Bits:");
        x = 6'b101010; y = 6'b101010;
        #1 $display("A:%b B:%b -> Igual: %b", x, y, eq);
        x = 6'b101010; y = 6'b101011;
        #1 $display("A:%b B:%b -> Igual: %b", x, y, eq);
    end
endmodule