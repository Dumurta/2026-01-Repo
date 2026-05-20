// Nome: Eduardo Murta De Abreu
// Matricula: 884985
// Guia_1102 - Mealy
`timescale 1ns/1ps

module mealy_1001_no_intersect (
  input  wire clk,
  input  wire reset,
  input  wire x,
  output wire y,
  output wire [3:0] janela_dbg
);
  reg [3:0] win;
  assign janela_dbg = win;

  // Mealy: analisa a entrada combinacionalmente no mesmo ciclo
  wire [3:0] next_window = {win[2:0], x};
  wire detect_now = (next_window == 4'b1001);

  assign y = detect_now;

  always @(posedge clk or posedge reset) begin
    if (reset)
      win <= 4'b0000;
    else if (detect_now)
      win <= 4'b0000; 
    else
      win <= next_window;
  end
endmodule

// Testbench
// 
module tb_guia_1102;
  reg clk=0, reset=0, x=0;
  wire y;
  wire [3:0] estado;

  mealy_1001_no_intersect DUT(
    .clk(clk), .reset(reset), .x(x),
    .y(y), .janela_dbg(estado)
  );

  always #5 clk = ~clk;

  initial begin
    $display("==========================================");
    $display(" Teste Mealy - Seq 1001 (SEM Intersecao) ");
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
    #3 x=0; #7;
    #3 x=1; #7;
    #3 x=0; #7;
    #3 x=1; #7;

    #1 $monitoroff;
    $display("------------------------------------------");
    $finish;
  end
endmodule