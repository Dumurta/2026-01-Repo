// Guia_0805

module Guia_0805 (output [5:0] saida, input [5:0] entrada);
    wire [5:0] c1;
    wire [5:0] soma_um;
    wire carry_lixo;

    not N0 (c1[0], entrada[0]);
    not N1 (c1[1], entrada[1]);
    not N2 (c1[2], entrada[2]);
    not N3 (c1[3], entrada[3]);
    not N4 (c1[4], entrada[4]);
    not N5 (c1[5], entrada[5]);

    Guia_0801 somador (saida, carry_lixo, c1, 6'b000001);
endmodule

module test_Guia_0805;
    reg [5:0] in;
    wire [5:0] out;
    Guia_0805 c2 (out, in);
    initial begin
        $display("\nTeste Complemento de 2:");
        in = 6'b000011;
        #1 $display("Entrada: %b -> C2: %b (-3)", in, out);
        in = 6'b111101;
        #1 $display("Entrada: %b -> C2: %b (+3)", in, out);
    end
endmodule