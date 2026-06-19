// -------------------------
// Questão 5
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


// Conversor Paralelo-Série para 5 bits (PISO)
// LD=1: carrega entradas paralelas d4..d0
// LD=0: desloca à direita (LSB sai primeiro em s)

module piso5 (
  output q0, q1, q2, q3, q4,
  output reg s,
  input  d0, d1, d2, d3, d4,
  input  clk,
  input  clr,
  input  ld
);
  wire qn0, qn1, qn2, qn3, qn4;

  // MUX D de cada FF: se ld=1 carrega di; senão desloca (q[i] <- q[i+1])
  wire din0 = ld ? d0 : q1;
  wire din1 = ld ? d1 : q2;
  wire din2 = ld ? d2 : q3;
  wire din3 = ld ? d3 : q4;
  wire din4 = ld ? d4 : 1'b0;   // entra 0 no MSB ao deslocar

  dff FF0 ( .q(q0), .qnot(qn0), .d(din0), .clk(clk), .preset(1'b0), .clear(clr) );
  dff FF1 ( .q(q1), .qnot(qn1), .d(din1), .clk(clk), .preset(1'b0), .clear(clr) );
  dff FF2 ( .q(q2), .qnot(qn2), .d(din2), .clk(clk), .preset(1'b0), .clear(clr) );
  dff FF3 ( .q(q3), .qnot(qn3), .d(din3), .clk(clk), .preset(1'b0), .clear(clr) );
  dff FF4 ( .q(q4), .qnot(qn4), .d(din4), .clk(clk), .preset(1'b0), .clear(clr) );

  // saída serial: captura q0 (LSB) a cada borda
  always @(posedge clk) begin
    if (clr) s <= 1'b0;
    else     s <= q0;
  end

endmodule


// Testbench Q05

module test_Q05;
  reg        clk, clr, ld;
  reg        d0, d1, d2, d3, d4;
  wire       q0, q1, q2, q3, q4;
  wire       s;

  piso5 UUT (
    .q0(q0), .q1(q1), .q2(q2), .q3(q3), .q4(q4),
    .s(s),
    .d0(d0), .d1(d1), .d2(d2), .d3(d3), .d4(d4),
    .clk(clk), .clr(clr), .ld(ld)
  );

  initial begin
    clk = 1'b0;
    forever #5 clk = ~clk;
  end

  task print_line;
    begin
      $display("| %b |  %b | %b |  %b  %b  %b  %b  %b  | %b |",
               ld, clr, clk, q4, q3, q2, q1, q0, s);
    end
  endtask

  initial begin
    $display("Guia_1405 - Eduardo Murta - 884985");
    $display("Questao 5");
    $display("------------------------------------");
    $display("| ld| clr|clk| q4 q3 q2 q1 q0 | s |");
    $display("------------------------------------");

    ld=0; clr=1; {d4,d3,d2,d1,d0}=5'b00000;
    @(posedge clk); #1 print_line;
    clr=0;

    // carrega paralelo: d4..d0 = 10101
    {d4,d3,d2,d1,d0}=5'b10101; ld=1;
    @(posedge clk); #1 print_line;
    ld=0;

    repeat (7) begin
      @(posedge clk); #1 print_line;
    end

    $display("------------------------------------");
    #5 $finish;
  end
endmodule
/*TESTES
Guia_1405 - Eduardo Murta - 884985
Questao 5
------------------------------------
| ld| clr|clk| q4 q3 q2 q1 q0 | s |
------------------------------------
| 0 |  1 | 1 |  0  0  0  0  0  | 0 |
| 1 |  0 | 1 |  1  0  1  0  1  | 0 |
| 0 |  0 | 1 |  0  1  0  1  0  | 1 |
| 0 |  0 | 1 |  0  0  1  0  1  | 0 |
| 0 |  0 | 1 |  0  0  0  1  0  | 1 |
| 0 |  0 | 1 |  0  0  0  0  1  | 0 |
| 0 |  0 | 1 |  0  0  0  0  0  | 1 |
| 0 |  0 | 1 |  0  0  0  0  0  | 0 |
| 0 |  0 | 1 |  0  0  0  0  0  | 0 |
------------------------------------
*/
