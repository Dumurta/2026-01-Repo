// Nome: Eduardo Murta De Abreu
// Matricula: 884985
// Guia_1101 - FSM para reconhecer a primeira 1010 e parar

`timescale 1ns/1ps

module guia_1101 (
  input  wire clk,
  input  wire reset,
  input  wire x,
  output wire y,
  output wire [3:0] janela_dbg,
  output wire done_dbg
);
  reg [3:0] win;
  reg done;

  assign janela_dbg = win;
  assign done_dbg   = done;

  wire detect_now = (win == 4'b1010);
  assign y = detect_now & ~done;

  // Congelar após a primeira detecção
  wire freeze      = done | detect_now;
  wire [3:0] shift = {win[2:0], x};
  wire [3:0] win_next = freeze ? win : shift;

  always @(posedge clk or posedge reset) begin
    if (reset) begin
      win  <= 4'b0000;
      done <= 1'b0;
    end else begin
      win  <= win_next;
      done <= done | detect_now;
    end
  end
endmodule

// Testbench
module tb_guia_1101;
  reg clk=0, reset=0, x=0;
  wire y;
  wire [3:0] estado;
  wire done;

  guia_1101 DUT(
    .clk(clk), .reset(reset), .x(x),
    .y(y), .janela_dbg(estado), .done_dbg(done)
  );

  always #5 clk = ~clk;

  initial begin
    $display("==========================================");
    $display(" Teste - Sequencia 1010 (Reconhece 1x e para) ");
    $display("==========================================");
    $display("Tempo Clock  Reset   x   y  | Estado  Done");
    $display("------------------------------------------");
    $monitor("%4t    %0d      %0d     %0d   %0d  | %b   %0d",
             $time, clk, reset, x, y, estado, done);
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
    
    #1 $monitoroff;
    $display("------------------------------------------");
    $finish;
  end
endmodule