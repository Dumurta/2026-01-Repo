// Mealy_1101.v - detector de sequência 1101 (Mealy)
// Versão corrigida: usa always @(*) para lógica combinacional,
// e atribuições não-bloqueantes (<=) no bloco sequencial.

`timescale 1ns/1ps
`define FOUND    1'b1
`define NOTFOUND 1'b0

module mealy_1101 (
  output reg y,       // Mealy output (combinacional em relação a E1 e x)
  input      x,
  input      clk,
  input      reset    // active-high synchronous/asynchronous reset (handled below)
);

  // state encoding (2 bits)
  localparam [1:0]
    START = 2'b00,
    ID1   = 2'b01,
    ID11  = 2'b11,
    ID110 = 2'b10;

  reg [1:0] E1; // current state
  reg [1:0] E2; // next state (combinational)

  // combinational block: next state and output (Mealy)
  always @(*) begin
    // defaults
    E2 = E1;
    y  = `NOTFOUND;

    case (E1)
      START: begin
        if (x) E2 = ID1; else E2 = START;
      end

      ID1: begin
        if (x) E2 = ID11; else E2 = START;
      end

      ID11: begin
        if (x) E2 = ID11; else E2 = ID110;
      end

      ID110: begin
        if (x) begin
          E2 = ID1;     // overlap: last '1' can be start of new pattern
          y  = `FOUND;  // Mealy output depends on E1 and x
        end else begin
          E2 = START;
          y  = `NOTFOUND;
        end
      end

      default: begin
        E2 = START;
        y  = `NOTFOUND;
      end
    endcase
  end

  // sequential block: update state on posedge clk or on active-high reset
  // Use non-blocking assignments (<=) to avoid races
  always @(posedge clk or posedge reset) begin
    if (reset)
      E1 <= START;    // asynchronous active-high reset to START
    else
      E1 <= E2;       // update state (non-blocking)
  end

endmodule
/*

module test;
    reg clk, reset, x;
    wire y;
    
    // Instanciar a máquina de Mealy
    mealy_1101 mealy1 (y, x, clk, reset);
    
    // Gerar clock
    initial begin
      clk   = 0;
      // pulso de reset curto
      reset = 1;
      #12;          // ~2 flancos, por exemplo
      reset = 0;    // libera a FSM para avançar

    end

    always #5 clk = ~clk;
    
    // Sequência de teste
    initial
    begin
        // Cabeçalho da tabela
        $display("==========================================");
        $display(" Teste Máquina de Mealy - Sequência 1101");
        $display("==========================================");
        $display("Tempo Clock  Reset   x   y  | Estado");
        $display("------------------------------------------");
        
        // Monitorar mudanças
        $monitor("%4d    %b      %b     %b   %b  | %2b", 
                $time, clk, reset, x, y, mealy1.E1);
        
        // Inicialização
        reset = 0;  // Reset ativo
        x = 0;
        #10;
        
        // Liberar reset e começar teste
        reset = 1;
        
        // Sequência de teste: 1 1 0 1 (deve detectar)
        #10 x = 1;  // bit 1
        #10 x = 1;  // bit 1  
        #20 x = 0;  // bit 0
        #10 x = 1;  // bit 1 - DEVE DETECTAR AQUI!
        
        // Continuar com mais bits
        #10 x = 0;
        #10 x = 1;
        #10 x = 0;
        #10 x = 1;
        
        // Segunda sequência 1101
        #10 x = 1;
        #10 x = 1;  
        #10 x = 0;
        #10 x = 1;  
        
        #10 x = 0;
        clk = 0;
        $monitoroff;    
        #10 $display("------------------------------------------");
        #10 $display("Fim do teste");
        $finish;
    end
    
endmodule*/

/* === TESTES ====

[Running] Mealy_1101.v
==========================================
 Teste Máquina de Mealy - Sequência 1101
==========================================
Tempo Clock  Reset   x   y  | Estado
------------------------------------------
   0    0      0     0   0  | xx
   5    1      0     0   0  | 00
  10    0      1     0   0  | 00
  12    0      0     0   0  | 00
  15    1      0     0   0  | 00
  20    0      0     1   0  | 00
  25    1      0     1   0  | 01
  30    0      0     1   0  | 01
  35    1      0     1   0  | 11
  40    0      0     1   0  | 11
  45    1      0     1   0  | 11
  50    0      0     0   0  | 11
  55    1      0     0   0  | 10
  60    0      0     1   1  | 10
  65    1      0     1   0  | 01
  70    0      0     0   0  | 01
  75    1      0     0   0  | 00
  80    0      0     1   0  | 00
  85    1      0     1   0  | 01
  90    0      0     0   0  | 01
  95    1      0     0   0  | 00
 100    0      0     1   0  | 00
 105    1      0     1   0  | 01
 110    0      0     1   0  | 01
 115    1      0     1   0  | 11
 120    0      0     1   0  | 11
 125    1      0     1   0  | 11
 130    0      0     0   0  | 11
 135    1      0     0   0  | 10
 140    0      0     1   1  | 10
 145    1      0     1   0  | 01
 150    1      0     0   0  | 00
------------------------------------------
Fim do teste
Mealy_1101.v:138: $finish called at 170000 (1ps)
[Done] exit with code=0 in 0.4 seconds

*/