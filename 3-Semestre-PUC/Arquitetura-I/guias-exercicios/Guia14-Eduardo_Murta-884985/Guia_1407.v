
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

// ---------------------------------------------------------
// Registrador CIRCULAR "twisted ring" (Johnson) p/ DIREITA (6 bits)
// carga inicial (LD) em TODOS os estágios
// ---------------------------------------------------------
module twisted_ring_right6_preset_all (
  output q0, q1, q2, q3, q4, q5,
  input  clk,
  input  clr,
  input  ld
);
  wire qn0, qn1, qn2, qn3, qn4, qn5;

  // anel torcido para a direita: q[i] <- q[i+1], q[5] <- ~q[0]
  // preset em TODOS os FFs quando ld=1
  dff FF0 ( .q(q0), .qnot(qn0), .d(q1),   .clk(clk), .preset(ld), .clear(clr) );
  dff FF1 ( .q(q1), .qnot(qn1), .d(q2),   .clk(clk), .preset(ld), .clear(clr) );
  dff FF2 ( .q(q2), .qnot(qn2), .d(q3),   .clk(clk), .preset(ld), .clear(clr) );
  dff FF3 ( .q(q3), .qnot(qn3), .d(q4),   .clk(clk), .preset(ld), .clear(clr) );
  dff FF4 ( .q(q4), .qnot(qn4), .d(q5),   .clk(clk), .preset(ld), .clear(clr) );
  dff FF5 ( .q(q5), .qnot(qn5), .d(~q0),  .clk(clk), .preset(ld), .clear(clr) );

endmodule

// -------------------------
// Testbench Q07
// -------------------------
module test_Q07;
  reg  clk, clr, ld;
  wire q0, q1, q2, q3, q4, q5;

  twisted_ring_right6_preset_all UUT (
    .q0(q0), .q1(q1), .q2(q2), .q3(q3), .q4(q4), .q5(q5),
    .clk(clk), .clr(clr), .ld(ld)
  );

  initial begin
    clk = 1'b0;
    forever #5 clk = ~clk;
  end

  task print_line;
    begin
      $display("| %b |  %b | %b |  %b  %b  %b  %b  %b  %b  |",
               ld, clr, clk, q5, q4, q3, q2, q1, q0);
    end
  endtask

  initial begin
    $display("Guia_1407 - Eduardo Murta - 884985");
    $display("Questao 7 (Extra)");
    $display("-------------------------------");
    $display("| ld| clr|clk| q5 q4 q3 q2 q1 q0 |");
    $display("-------------------------------");

    ld=0; clr=1;
    @(posedge clk); #1 print_line;
    clr=0;

    ld=1;
    @(posedge clk); #1 print_line;   // carrega todos: 111111
    ld=0;

    repeat (13) begin
      @(posedge clk); #1 print_line;
    end

    clr=1; @(posedge clk); #1 print_line;
    clr=0; @(posedge clk); #1 print_line;

    $display("-------------------------------");
    #5 $finish;
  end
endmodule
/*TESTES
Guia_1407 - Eduardo Murta - 884985
Questao 7 (Extra)
-------------------------------
| ld| clr|clk| q5 q4 q3 q2 q1 q0 |
-------------------------------
| 0 |  1 | 1 |  0  0  0  0  0  0  |
| 1 |  0 | 1 |  1  1  1  1  1  1  |
| 0 |  0 | 1 |  0  1  1  1  1  1  |
| 0 |  0 | 1 |  0  0  1  1  1  1  |
| 0 |  0 | 1 |  0  0  0  1  1  1  |
| 0 |  0 | 1 |  0  0  0  0  1  1  |
| 0 |  0 | 1 |  0  0  0  0  0  1  |
| 0 |  0 | 1 |  0  0  0  0  0  0  |
| 0 |  0 | 1 |  1  0  0  0  0  0  |
| 0 |  0 | 1 |  1  1  0  0  0  0  |
| 0 |  0 | 1 |  1  1  1  0  0  0  |
| 0 |  0 | 1 |  1  1  1  1  0  0  |
| 0 |  0 | 1 |  1  1  1  1  1  0  |
| 0 |  0 | 1 |  1  1  1  1  1  1  |
| 0 |  0 | 1 |  0  1  1  1  1  1  |
| 0 |  1 | 1 |  0  0  0  0  0  0  |
| 0 |  0 | 1 |  1  0  0  0  0  0  |
-------------------------------
*/
