
// Questão 3
// Nome: Eduardo Murta
// Matricula: 884985


module dff ( output q, output qnot,
             input  d, input clk,
             input  preset, input clear );
  reg q, qnot;
  always @(posedge clk) begin
    if (clear)       begin q <= 1'b0; qnot <= 1'b1; end
    else if (preset) begin q <= 1'b1; qnot <= 1'b0; end
    else             begin q <= d;    qnot <= ~d;  end
  end
endmodule // dff


// Registrador CIRCULAR (ring) p/ DIREITA (5 bits)
// carga unitária no primeiro estágio (q0)

module ring_right5_preset1 (
  output q0, q1, q2, q3, q4,
  input  clk,
  input  clr,
  input  ld
);
  wire qn0, qn1, qn2, qn3, qn4;

  // rotação para a direita: q[i] <- q[i+1], q[4] <- q[0]
  // preset apenas em FF0 quando ld=1
  dff FF0 ( .q(q0), .qnot(qn0), .d(q1),   .clk(clk), .preset(ld),  .clear(clr) );
  dff FF1 ( .q(q1), .qnot(qn1), .d(q2),   .clk(clk), .preset(1'b0),.clear(clr) );
  dff FF2 ( .q(q2), .qnot(qn2), .d(q3),   .clk(clk), .preset(1'b0),.clear(clr) );
  dff FF3 ( .q(q3), .qnot(qn3), .d(q4),   .clk(clk), .preset(1'b0),.clear(clr) );
  dff FF4 ( .q(q4), .qnot(qn4), .d(q0),   .clk(clk), .preset(1'b0),.clear(clr) );

endmodule


// Testbench Q03

module test_Q03;
  reg  clk, clr, ld;
  wire q0, q1, q2, q3, q4;

  ring_right5_preset1 UUT (
    .q0(q0), .q1(q1), .q2(q2), .q3(q3), .q4(q4),
    .clk(clk), .clr(clr), .ld(ld)
  );

  initial begin
    clk = 0;
    forever #5 clk = ~clk;
  end

  task print_line;
    begin
      $display("| %b |  %b | %b |  %b  %b  %b  %b  %b  |",
               ld, clr, clk, q4, q3, q2, q1, q0);
    end
  endtask

  initial begin
    $display("Guia_1403 - Eduardo Murta - 884985");
    $display("Questao 3");
    $display("---------------------------");
    $display("| ld| clr|clk| q4 q3 q2 q1 q0 |");
    $display("---------------------------");

    ld=0; clr=1;
    @(posedge clk); #1 print_line;
    clr=0;

    ld=1;
    @(posedge clk); #1 print_line;   // carrega 1 em q0: 00001
    ld=0;

    repeat (7) begin
      @(posedge clk); #1 print_line;
    end

    $display("---------------------------");
    #5 $finish;
  end
endmodule
/*TESTES
Guia_1403 - Eduardo Murta - 884985
Questao 3
---------------------------
| ld| clr|clk| q4 q3 q2 q1 q0 |
---------------------------
| 0 |  1 | 1 |  0  0  0  0  0  |
| 1 |  0 | 1 |  0  0  0  0  1  |
| 0 |  0 | 1 |  1  0  0  0  0  |
| 0 |  0 | 1 |  0  1  0  0  0  |
| 0 |  0 | 1 |  0  0  1  0  0  |
| 0 |  0 | 1 |  0  0  0  1  0  |
| 0 |  0 | 1 |  0  0  0  0  1  |
| 0 |  0 | 1 |  1  0  0  0  0  |
| 0 |  0 | 1 |  0  1  0  0  0  |
---------------------------
*/
