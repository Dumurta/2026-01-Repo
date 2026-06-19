// Guia_1302 - Contador Assíncrono Crescente 6 bits com JK
// Nome: Eduardo Murta De Abreu
// Matricula: 884985

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

module q02_contador_crescente_6bits (
  input clk, input clear,
  output [5:0] out
);
  wire q0, q1, q2, q3, q4, q5;
  wire qn0, qn1, qn2, qn3, qn4, qn5;

  jkff ff0(.q(q0), .qnot(qn0), .j(1'b1), .k(1'b1), .clk(clk),  .preset(1'b0), .clear(clear));
  jkff ff1(.q(q1), .qnot(qn1), .j(1'b1), .k(1'b1), .clk(qn0),  .preset(1'b0), .clear(clear));
  jkff ff2(.q(q2), .qnot(qn2), .j(1'b1), .k(1'b1), .clk(qn1),  .preset(1'b0), .clear(clear));
  jkff ff3(.q(q3), .qnot(qn3), .j(1'b1), .k(1'b1), .clk(qn2),  .preset(1'b0), .clear(clear));
  jkff ff4(.q(q4), .qnot(qn4), .j(1'b1), .k(1'b1), .clk(qn3),  .preset(1'b0), .clear(clear));
  jkff ff5(.q(q5), .qnot(qn5), .j(1'b1), .k(1'b1), .clk(qn4),  .preset(1'b0), .clear(clear));

  assign out = {q5, q4, q3, q2, q1, q0};
endmodule

module q02_test;
  reg clk, clear;
  wire [5:0] out;

  q02_contador_crescente_6bits DUT(.clk(clk), .clear(clear), .out(out));

  initial begin clk = 1'b0; forever #5 clk = ~clk; end

  initial begin
    $display("Guia_1302 - Eduardo Murta De Abreu - 884985");
    $display("Questao 02 - Contador Assíncrono Crescente 6 bits com JK");
    $display("------------------------------------------");
    $display("|  t  | clk | clear |   out  | Decimal |");
    $display("------------------------------------------");
    $monitor("| %3t |  %b  |   %b   | %6b |   %2d    |",
             $time, clk, clear, out, out);
    clear = 1'b1; #10;
    clear = 1'b0; #2;
    #700;
    $display("------------------------------------------");
    $finish;
  end
endmodule
