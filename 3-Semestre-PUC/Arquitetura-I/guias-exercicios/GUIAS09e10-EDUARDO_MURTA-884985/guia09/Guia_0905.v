`include "clock.v"

module pulse5 (output reg signal, input clk);
    always @ (posedge clk) begin
        signal = 1'b1;
        #5 signal = 1'b0;
    end
endmodule

module Guia_0905;
    wire clk_wire;
    wire p_out;

    clock CLK1 (clk_wire);
    pulse5 PLS5 (p_out, clk_wire);

    initial begin
        $dumpfile("Guia_0905.vcd");
        $dumpvars(0, Guia_0905);
        #480 $finish;
    end
endmodule