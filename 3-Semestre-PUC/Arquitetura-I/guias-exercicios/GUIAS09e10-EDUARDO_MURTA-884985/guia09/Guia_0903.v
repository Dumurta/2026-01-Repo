`include "clock.v"
module pulse_div4 (output reg signal, input clk);
    reg [1:0] counter = 2'b00;
    always @ (posedge clk) begin
        counter <= counter + 1;
        if (counter == 2'b11) begin
            signal <= 1'b1; #24 signal <= 1'b0;
        end
    end
endmodule

module Guia_0903;
    wire clk_wire;
    clock CLK1 (clk_wire);
    wire p_out;
    pulse_div4 PLS3 (p_out, clk_wire);

    initial begin
        $dumpfile("Guia_0903.vcd");
        $dumpvars(1, clk_wire, p_out);
        #500 $finish;
    end
endmodule