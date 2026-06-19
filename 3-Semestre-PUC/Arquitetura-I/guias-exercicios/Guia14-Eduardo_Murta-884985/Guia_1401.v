// -------------------------
// Questão 1
// Nome: Eduardo Murta
// Matricula: 884985
// -------------------------

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


// Registrador de deslocamento p/ ESQUERDA (5 bits)
// carga de 1 bit (LD) no preset do primeiro estágio (q[0])

module shift_left5_preset1 (
  output q0, q1, q2, q3, q4,
  input  clk,
  input  clr,
  input  ld
);
  wire qn0, qn1, qn2, qn3, qn4;

  // deslocamento para a esquerda: q[i+1] <- q[i], q[0] <- 0
  // preset apenas em FF0 quando ld=1
  dff FF0 ( .q(q0), .qnot(qn0), .d(1'b0), .clk(clk), .preset(ld),  .clear(clr) );
  dff FF1 ( .q(q1), .qnot(qn1), .d(q0),   .clk(clk), .preset(1'b0),.clear(clr) );
  dff FF2 ( .q(q2), .qnot(qn2), .d(q1),   .clk(clk), .preset(1'b0),.clear(clr) );
  dff FF3 ( .q(q3), .qnot(qn3), .d(q2),   .clk(clk), .preset(1'b0),.clear(clr) );
  dff FF4 ( .q(q4), .qnot(qn4), .d(q3),   .clk(clk), .preset(1'b0),.clear(clr) );

endmodule


// Testbench Q01

module test_Q01;
  reg  clk, clr, ld;
  wire q0, q1, q2, q3, q4;

  shift_left5_preset1 UUT (
    .q0(q0), .q1(q1), .q2(q2), .q3(q3), .q4(q4),
    .clk(clk), .clr(clr), .ld(ld)
  );

  initial begin
    clk = 0;
    forever #5 clk = ~clk;
  end

  initial begin
    $display("Guia_1401 - Eduardo Murta - 884985");
    $display("Questao 1");
    $display("---------------------------");
    $display("| ld| clr|clk| q4 q3 q2 q1 q0 |");
    $display("---------------------------");
    $monitor("| %b |  %b | %b |  %b  %b  %b  %b  %b  |",
             ld, clr, clk, q4, q3, q2, q1, q0);

    // zera
    ld=0; clr=1; #7;
    clr=0;

    // injeta '1' no q[0] via preset do FF0
    ld=1; @(posedge clk);
    ld=0;

    // observa o deslocamento
    repeat (8) @(posedge clk);

    $monitoroff;
    #10;
    $display("---------------------------");
    #5 $finish;
  end
endmodule
/*TESTES
Guia_1401 - Eduardo Murta - 884985
Questao 1
---------------------------
| ld| clr|clk| q4 q3 q2 q1 q0 |
---------------------------
| 0 |  1 | 0 |  x  x  x  x  x  |
| 0 |  1 | 1 |  0  0  0  0  0  |
| 1 |  0 | 1 |  0  0  0  0  0  |
| 1 |  0 | 0 |  0  0  0  0  0  |
| 0 |  0 | 1 |  0  0  0  0  1  |
| 0 |  0 | 0 |  0  0  0  0  1  |
| 0 |  0 | 1 |  0  0  0  1  0  |
| 0 |  0 | 0 |  0  0  0  1  0  |
| 0 |  0 | 1 |  0  0  1  0  0  |
| 0 |  0 | 0 |  0  0  1  0  0  |
| 0 |  0 | 1 |  0  1  0  0  0  |
| 0 |  0 | 0 |  0  1  0  0  0  |
| 0 |  0 | 1 |  1  0  0  0  0  |
| 0 |  0 | 0 |  1  0  0  0  0  |
| 0 |  0 | 1 |  0  0  0  0  0  |
| 0 |  0 | 0 |  0  0  0  0  0  |
| 0 |  0 | 1 |  0  0  0  0  0  |
---------------------------
*/
