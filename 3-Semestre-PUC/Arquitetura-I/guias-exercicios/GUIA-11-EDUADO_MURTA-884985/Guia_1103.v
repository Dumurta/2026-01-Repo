// Nome: Eduardo Murta De Abreu
// Matricula: 884985
// Guia_1103 - Moore, Sequência 1011 COM interseção

`timescale 1ns/1ps

module moore_1011_intersect (
  input  wire clk,
  input  wire reset,
  input  wire x,
  output wire y,
  output wire [3:0] janela_dbg
);
  reg [3:0] win;
  assign janela_dbg = win;

  // Moore: saída depende apenas da janela/estado atual consolidado
  assign y = (win == 4'b1011);

  always @(posedge clk or posedge reset) begin
    if (reset)
      win <= 4'b0000;
    else
      win <= {win[2:0], x};
  end
endmodule

// Testbench
module tb_guia_1103;
  reg clk=0, reset=0, x=0;
  wire y;
  wire [3:0] estado;

  moore_1011_intersect DUT(
    .clk(clk), .reset(reset), .x(x),
    .y(y), .janela_dbg(estado)
  );

  always #5 clk = ~clk;

  initial begin
    $display("==========================================");
    $display(" Teste Moore - Seq 1011 (COM Intersecao) ");
    $display("==========================================");
    $display("Tempo Clock  Reset   x   y  | Estado");
    $display("------------------------------------------");
    $monitor("%4t    %0d      %0d     %0d   %0d  | %b",
             $time, clk, reset, x, y, estado);
  end

  initial begin
    reset = 1; #7; reset = 0;
    #3 x=1; #7;
    #3 x=0; #7;
    #3 x=0; #7;
    #3 x=1; #7;
    #3 x=0; #7;
    #3 x=1; #7;
    #3 x=0; #7;
    #3 x=1; #7;
    #3 x=1; #7;
    #3 x=0; #7;
    #3 x=1; #7;
    #3 x=1; #7;
    #3 x=0; #7;
    #3 x=0; #7;
    #3 x=1; #7;

    #1 $monitoroff;
    $display("------------------------------------------");
    $finish;
  end
endmodule