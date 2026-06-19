// Guia_1305 - Contador Síncrono Módulo 7 com Flip-flops T
// Nome: Eduardo Murta De Abreu
// Matricula: 884985

// Tabela de transições e equações T (T = Qt XOR Qt+1):
// Qt (Q2 Q1 Q0) | Qt+1 | T2 T1 T0
//    0  0  0    |  001 |  0  0  1
//    0  0  1    |  010 |  0  1  1
//    0  1  0    |  011 |  0  0  1
//    0  1  1    |  100 |  1  1  1
//    1  0  0    |  101 |  0  0  1
//    1  0  1    |  110 |  0  1  1
//    1  1  0    |  000 |  1  1  0
//    1  1  1    |   X  |  X  X  X  (don't care)
//
// Equações simplificadas (K-map com don't care em 111):
//   T0 = Q2' + Q1' + Q0
//   T1 = Q0 + Q2·Q1
//   T2 = Q1·(Q0 + Q2)

module tff (
  output reg q, output reg qnot,
  input t, input clk, input preset, input clear
);
  always @(posedge clk or posedge preset or posedge clear) begin
    if (clear)       begin q <= 1'b0; qnot <= 1'b1; end
    else if (preset) begin q <= 1'b1; qnot <= 1'b0; end
    else if (t)      begin q <= ~q;   qnot <= ~qnot; end
  end
endmodule

module q05_contador_mod7_sincrono (
  input clk, input clear,
  output [2:0] out
);
  wire q0, q1, q2;
  wire qn0, qn1, qn2;

  wire t0 = (~q2) | (~q1) | q0;
  wire t1 = q0 | (q2 & q1);
  wire t2 = q1 & (q0 | q2);

  tff ff0(.q(q0), .qnot(qn0), .t(t0), .clk(clk), .preset(1'b0), .clear(clear));
  tff ff1(.q(q1), .qnot(qn1), .t(t1), .clk(clk), .preset(1'b0), .clear(clear));
  tff ff2(.q(q2), .qnot(qn2), .t(t2), .clk(clk), .preset(1'b0), .clear(clear));

  assign out = {q2, q1, q0};
endmodule

module q05_test;
  reg clk, clear;
  wire [2:0] out;

  q05_contador_mod7_sincrono DUT(.clk(clk), .clear(clear), .out(out));

  initial begin clk = 1'b0; forever #5 clk = ~clk; end

  initial begin
    $display("Guia_1305 - Eduardo Murta De Abreu - 884985");
    $display("Questao 05 - Contador Síncrono Módulo 7 com Flip-flops T");
    $display("--------------------------------------------");
    $display("|  t  | clk | clear | out | Decimal |");
    $display("--------------------------------------------");
    $monitor("| %3t |  %b  |   %b   | %3b |    %1d    |",
             $time, clk, clear, out, out);
    clear = 1'b1; #10;
    clear = 1'b0; #2;
    #160;
    $display("--------------------------------------------");
    $finish;
  end
endmodule
