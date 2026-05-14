`include "clock.v"


module pulse1 (output reg signal, input clock);
    always @ (posedge clock) begin
        signal = 1'b1; #4 signal = 1'b0; #4 signal = 1'b1; 
        #4 signal = 1'b0; #4 signal = 1'b1; #4 signal = 1'b0;
    end
endmodule

module pulse2 (output reg signal, input clock);
    always @ (posedge clock) begin
        signal = 1'b1; #5 signal = 1'b0;
    end
endmodule

module pulse3 (output reg signal, input clock);
    always @ (negedge clock) begin
        signal = 1'b1; #15 signal = 1'b0; #15 signal = 1'b1;
    end
endmodule

module pulse4 (output reg signal, input clock);
    always @ (negedge clock) begin
        signal = 1'b1; #20 signal = 1'b0; #20 signal = 1'b1; #20 signal = 1'b0;
    end
endmodule

module Guia_0902;
    wire clk_wire;
    clock CLK1 (clk_wire);
    wire p1, p2, p3, p4;

    pulse1 PLS1 (p1, clk_wire);
    pulse2 PLS2 (p2, clk_wire);
    pulse3 PLS3 (p3, clk_wire);
    pulse4 PLS4 (p4, clk_wire);

    initial begin
        $dumpfile("Guia_0902.vcd");
        $dumpvars(1, clk_wire, p1, p2, p3, p4);
        #480 $finish;
    end
endmodule