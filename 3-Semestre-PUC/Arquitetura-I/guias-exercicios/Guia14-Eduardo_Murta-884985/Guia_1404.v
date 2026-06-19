// -------------------------
// Questão 4
// Nome: Eduardo Murta
// Matricula: 884985
// -------------------------

module dff ( output q, output qnot,
             input  d, input clk,
             input  preset, input clear );
  reg q    = 1'b0;
  reg qnot = 1'b1;
  always @(posedge clk) begin
    if (clear)       begin q <= 1'b0; qnot <= 1'b1; end
    else if (preset) begin q <= 1'b1; qnot <= 1'b0; end
    else             begin q <= d;    qnot <= ~d;  end
  end
endmodule // dff

// Registrador CIRCULAR "twisted ring" (Johnson) p/ ESQUERDA (5 bits)
// carga unitária no primeiro estágio (q0)
// 
module twisted_ring_left5_preset1 (
  output q0, q1, q2, q3, q4,
  input  clk,
  input  clr,
  input  ld
);
  wire qn0, qn1, qn2, qn3, qn4;

  // anel torcido para a esquerda: q[i+1] <- q[i], q[0] <- ~q[4]
  // preset apenas em FF0 quando ld=1
  dff FF0 ( .q(q0), .qnot(qn0), .d(~q4),  .clk(clk), .preset(ld),  .clear(clr) );
  dff FF1 ( .q(q1), .qnot(qn1), .d(q0),   .clk(clk), .preset(1'b0),.clear(clr) );
  dff FF2 ( .q(q2), .qnot(qn2), .d(q1),   .clk(clk), .preset(1'b0),.clear(clr) );
  dff FF3 ( .q(q3), .qnot(qn3), .d(q2),   .clk(clk), .preset(1'b0),.clear(clr) );
  dff FF4 ( .q(q4), .qnot(qn4), .d(q3),   .clk(clk), .preset(1'b0),.clear(clr) );

endmodule

// -------------------------
// Testbench Q04
// -------------------------
module test_Q04;
  reg  clk, clr, ld;
  wire q0, q1, q2, q3, q4;

  twisted_ring_left5_preset1 UUT (
    .q0(q0), .q1(q1), .q2(q2), .q3(q3), .q4(q4),
    .clk(clk), .clr(clr), .ld(ld)
  );

  initial begin
    clk = 1'b0;
    forever #5 clk = ~clk;
  end

  task print_line;
    begin
      $display("| %b |  %b | %b |  %b  %b  %b  %b  %b  |",
               ld, clr, clk, q4, q3, q2, q1, q0);
    end
  endtask

  initial begin
    $display("Guia_1404 - Eduardo Murta - 884985");
    $display("Questao 4");
    $display("---------------------------");
    $display("| ld| clr|clk| q4 q3 q2 q1 q0 |");
    $display("---------------------------");

    ld=1'b0; clr=1'b1;
    @(posedge clk); #1 print_line;

    clr=1'b0; ld=1'b1;
    @(posedge clk); #1 print_line;   // preset q0=1: 00001
    ld=1'b0;

    repeat (11) begin
      @(posedge clk); #1 print_line;
    end

    $display("---------------------------");
    #5 $finish;
  end
endmodule
/*TESTES
Guia_1404 - Eduardo Murta - 884985
Questao 4
---------------------------
| ld| clr|clk| q4 q3 q2 q1 q0 |
---------------------------
| 0 |  1 | 1 |  0  0  0  0  0  |
| 1 |  0 | 1 |  0  0  0  0  1  |
| 0 |  0 | 1 |  0  0  0  1  1  |
| 0 |  0 | 1 |  0  0  1  1  1  |
| 0 |  0 | 1 |  0  1  1  1  1  |
| 0 |  0 | 1 |  1  1  1  1  1  |
| 0 |  0 | 1 |  1  1  1  1  0  |
| 0 |  0 | 1 |  1  1  1  0  0  |
| 0 |  0 | 1 |  1  1  0  0  0  |
| 0 |  0 | 1 |  1  0  0  0  0  |
| 0 |  0 | 1 |  0  0  0  0  0  |
| 0 |  0 | 1 |  0  0  0  0  1  |
---------------------------
*/
