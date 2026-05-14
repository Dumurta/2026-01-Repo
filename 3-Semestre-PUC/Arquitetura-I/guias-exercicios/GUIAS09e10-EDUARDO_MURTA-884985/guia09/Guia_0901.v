`include "clock.v"


module pulse (output reg signal, input clock);
    always @ (clock) begin
        signal = 1'b1; #3 signal = 1'b0; #3 signal = 1'b1; #3 signal = 1'b0;
    end
endmodule

module trigger (output reg signal, input on, clock);
    always @ (posedge clock & on) begin
        #60 signal = 1'b1; #60 signal = 1'b0;
    end
endmodule

module Guia_0901;
    wire clk_wire;
    clock CLK1 (clk_wire);
    reg p;
    wire p1, t1;

    pulse PULSE1 (p1, clk_wire);
    trigger TRIGGER1 (t1, p, clk_wire);

    initial begin
        p = 1'b0;
        $dumpfile("Guia_0901.vcd");
        $dumpvars(1, clk_wire, p1, p, t1);
        #060 p = 1'b1; #120 p = 1'b0; #180 p = 1'b1;
        #240 p = 1'b0; #300 p = 1'b1; #360 p = 1'b0;
        #376 $finish;
    end
endmodule