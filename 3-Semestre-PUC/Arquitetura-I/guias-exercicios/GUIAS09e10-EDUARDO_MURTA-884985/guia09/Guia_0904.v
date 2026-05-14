`include "clock.v"

module pulse4 (output reg signal, input clk);
    always @ (posedge clk) begin
        signal = 1'b1;
        #4 signal = 1'b0;
        #4 signal = 1'b1;
        #4 signal = 1'b0;
    end
endmodule

module Guia_0904;
    wire clk_wire;
    wire p_out;

    clock CLK1 (clk_wire);
    pulse4 PLS4 (p_out, clk_wire);

    initial begin
        $dumpfile("Guia_0904.vcd");
        $dumpvars(0, Guia_0904);
        #480 $finish;
    end
endmodule