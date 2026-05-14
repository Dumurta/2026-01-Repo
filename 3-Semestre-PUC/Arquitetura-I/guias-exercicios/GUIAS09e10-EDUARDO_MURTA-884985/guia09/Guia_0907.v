`include "clock.v"

module pulse7 (output reg signal, input clk);
    always @ (clk) begin
        if (clk == 1'b1) begin
            signal = 1'b1;
            #4 signal = 1'b0;
        end else begin
            signal = 1'b0;
        end
    end
endmodule

module Guia_0907;
    wire clk_wire;
    wire p_out;

    clock CLK1 (clk_wire);
    pulse7 PLS7 (p_out, clk_wire);

    initial begin
        $dumpfile("Guia_0907.vcd");
        $dumpvars(0, Guia_0907);
        #480 $finish;
    end
endmodule