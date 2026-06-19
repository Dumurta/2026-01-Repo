// Guia_1303 - Contador Assíncrono Decádico Crescente 5 bits com JK
// Nome: Eduardo Murta De Abreu
// Matricula: 884985

// Tabela de transições (módulo 10 crescente):
// Estado | Q3 Q2 Q1 Q0 | Próximo
//   0    |  0  0  0  0 |   1
//   1    |  0  0  0  1 |   2
//   2    |  0  0  1  0 |   3
//   3    |  0  0  1  1 |   4
//   4    |  0  1  0  0 |   5
//   5    |  0  1  0  1 |   6
//   6    |  0  1  1  0 |   7
//   7    |  0  1  1  1 |   8
//   8    |  1  0  0  0 |   9
//   9    |  1  0  0  1 |  10 (transitório 1010) -> clear -> 0

module jkff (
  output reg q, output reg qnot,
  input j, input k, input clk, input preset, input clear
);
  always @(posedge clk or posedge preset or posedge clear) begin
    if (clear)       begin q <= 1'b0; qnot <= 1'b1; end
    else if (preset) begin q <= 1'b1; qnot <= 1'b0; end
    else begin
      case ({j,k})
        2'b10: begin q <= 1'b1; qnot <= 1'b0; end
        2'b01: begin q <= 1'b0; qnot <= 1'b1; end
        2'b11: begin q <= ~q;   qnot <= ~qnot; end
        default: begin q <= q;  qnot <= qnot; end
      endcase
    end
  end
endmodule

module q03_contador_decadico_crescente_5bits (
  input clk, input clear,
  output [4:0] out
);
  wire q0, q1, q2, q3, q4;
  wire qn0, qn1, qn2, qn3, qn4;

  wire reset_mod10 = q3 & q1;
  wire clr_all = clear | reset_mod10;

  jkff ff0(.q(q0), .qnot(qn0), .j(1'b1), .k(1'b1), .clk(clk),  .preset(1'b0), .clear(clr_all));
  jkff ff1(.q(q1), .qnot(qn1), .j(1'b1), .k(1'b1), .clk(qn0),  .preset(1'b0), .clear(clr_all));
  jkff ff2(.q(q2), .qnot(qn2), .j(1'b1), .k(1'b1), .clk(qn1),  .preset(1'b0), .clear(clr_all));
  jkff ff3(.q(q3), .qnot(qn3), .j(1'b1), .k(1'b1), .clk(qn2),  .preset(1'b0), .clear(clr_all));
  jkff ff4(.q(q4), .qnot(qn4), .j(1'b1), .k(1'b1), .clk(qn3),  .preset(1'b0), .clear(clr_all));

  assign out = {q4, q3, q2, q1, q0};
endmodule

module q03_test;
  reg clk, clear;
  wire [4:0] out;

  q03_contador_decadico_crescente_5bits DUT(.clk(clk), .clear(clear), .out(out));

  initial begin clk = 1'b0; forever #5 clk = ~clk; end

  initial begin
    $display("Guia_1303 - Eduardo Murta De Abreu - 884985");
    $display("Questao 03 - Contador Assíncrono Decádico Crescente 5 bits com JK");
    $display("------------------------------------------");
    $display("|  t  | clk | clear |  out  | Decimal |");
    $display("------------------------------------------");
    $monitor("| %3t |  %b  |   %b   | %5b |   %2d    |",
             $time, clk, clear, out, out);
    clear = 1'b1; #10;
    clear = 1'b0; #2;
    #250;
    $display("------------------------------------------");
    $finish;
  end
endmodule
