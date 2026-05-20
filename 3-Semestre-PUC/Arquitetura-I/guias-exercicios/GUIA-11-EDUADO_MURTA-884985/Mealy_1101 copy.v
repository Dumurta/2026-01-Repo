// -------------- 
// --- Mealy FSM 
// -------------- 
 
/* 
                        Mealy FSM Diagram 
                      ___________________ 
                    /                    \ 
              1    v     1           0  1 | // found 
   [start] ---> [id1] ---> [id11] ---> [id110] 
     ^  \0      0 |       1 /  ^        0 | // not found 
      \_/        /          \__/          | 
       \________/                         | 
        \                                 | 
         \________________________________/ 
*/ 
 
// constant definitions 
`define found      1 
`define notfound 0 
 
// FSM by Mealy 
module mealy_1101 ( y, x, clk, reset ); 
 output y; 
 input   x; 
 input   clk; 
 input   reset; 
 
 reg      y; 
 
 parameter      // state identifiers  
   start    = 2'b00, 
   id1      = 2'b01, 
   id11    = 2'b11, 
   id110  = 2'b10; 
 
   reg [1:0] E1; // current state variables 
   reg [1:0] E2; // next state logic output
   // next state logic 
   always @( x or E1 ) 
    begin 
     y = `notfound; 
     case ( E1 ) 
      start: 
        if ( x ) 
         E2 = id1; 
        else 
         E2 = start; 
      id1: 
        if ( x ) 
         E2 = id11; 
        else 
         E2 = start; 
      id11: 
        if ( x ) 
         E2 = id11; 
        else 
         E2 = id110; 
      id110: 
        if ( x ) 
         begin 
           E2 =  id1; 
           y  = `found; 
         end 
        else 
         begin 
           E2 =  start; 
           y  = `notfound; 
         end 
      default:   // undefined state 
           E2 =  2'bxx; 
     endcase 
    end // always at signal or state changing 
 
  // state variables 
  always @(posedge clk or negedge reset)
  begin
    if (!reset)
      E1 <= start;   // volta para o estado inicial
    else
      E1 <= E2;      // atualiza estado a cada clock
  end


endmodule // mealy_1101 

module main;
    reg clk, reset, x;
    wire y;
    
    // Instanciar a máquina de Mealy
    mealy_1101 mealy1 (y, x, clk, reset);
    
    // Gerar clock
    initial
    begin
        clk = 1;
        forever #5 clk = ~clk;
    end
    
    // Sequência de teste
    initial
    begin
        // Cabeçalho da tabela
        $display("==========================================");
        $display("   Teste Máquina de Mealy - Sequência 1101");
        $display("==========================================");
        $display("Tempo  Clck  Reset   x   y  | Estado");
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
        #10 $display("------------------------------------------");
        $display("Fim do teste");
        $finish;
    end
    
endmodule