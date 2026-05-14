`include "clock.v"

module pulse6 (output reg signal, input clk);
    always @ (negedge clk) begin
        signal = 1'b1;
        #2 signal = 1'b0;
    end
endmodule

module Guia_0906;
    wire clk_wire;
    wire p_out;

    clock CLK1 (clk_wire);
    pulse6 PLS6 (p_out, clk_wire);

    initial begin
        $dumpfile("Guia_0906.vcd");
        $dumpvars(0, Guia_0906);
        #480 $finish;
    end
endmodule