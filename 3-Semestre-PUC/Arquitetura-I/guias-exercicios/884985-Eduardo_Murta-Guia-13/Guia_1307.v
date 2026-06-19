// Guia_1307 - Contador em Anel Torcido 6 bits com JK (Johnson Counter)
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

module q07_contador_anel_torcido_6bits (
  input clk, input clear,
  output [5:0] out
);
  wire q0, q1, q2, q3, q4, q5;
  wire qn0, qn1, qn2, qn3, qn4, qn5;

  jkff ff0(.q(q0), .qnot(qn0), .j(qn5), .k(q5),  .clk(clk), .preset(1'b0), .clear(clear));
  jkff ff1(.q(q1), .qnot(qn1), .j(q0),  .k(qn0), .clk(clk), .preset(1'b0), .clear(clear));
  jkff ff2(.q(q2), .qnot(qn2), .j(q1),  .k(qn1), .clk(clk), .preset(1'b0), .clear(clear));
  jkff ff3(.q(q3), .qnot(qn3), .j(q2),  .k(qn2), .clk(clk), .preset(1'b0), .clear(clear));
  jkff ff4(.q(q4), .qnot(qn4), .j(q3),  .k(qn3), .clk(clk), .preset(1'b0), .clear(clear));
  jkff ff5(.q(q5), .qnot(qn5), .j(q4),  .k(qn4), .clk(clk), .preset(1'b0), .clear(clear));

  assign out = {q5, q4, q3, q2, q1, q0};
endmodule

module q07_test;
  reg clk, clear;
  wire [5:0] out;

  q07_contador_anel_torcido_6bits DUT(.clk(clk), .clear(clear), .out(out));

  initial begin clk = 1'b0; forever #5 clk = ~clk; end

  initial begin
    $display("Guia_1307 - Eduardo Murta De Abreu - 884985");
    $display("Questao 07 - Contador em Anel Torcido 6 bits com JK");
    $display("-------------------------------------------");
    $display("|  t  | clk | clear |   out  |");
    $display("-------------------------------------------");
    $monitor("| %3t |  %b  |   %b   | %6b |",
             $time, clk, clear, out);
    clear = 1'b1; #10;
    clear = 1'b0; #2;
    #140;
    $display("-------------------------------------------");
    $finish;
  end
endmodule
