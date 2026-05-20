// -------------- 
// --- Moore FSM 
// -------------- 
 
/* 
                               Moore FSM Diagram 
                             ___________________ 
                            /                    \ 
           1         1      v     0          1   1| // found 
   [start] ---> [id1] ---> [id11] --->> [id110] ---> [id1101] 
     ^  \0      0 |       1^  \        0 |       0| 
      \_/        /         \__/          |        | 
       \________/                        |        | 
        \                                |        | 
         \_______________________________/        | 
          \                                       | 
           \______________________________________/ 
*/ 
 
// constant definition 
`define found      1 
`define notfound 0 
 
// FSM by Moore 
module  moore_1101 ( y, x, clk, reset ); 
 output y; 
 input   x; 
 input   clk; 
 input   reset; 
 
 reg      y; 
 
 parameter        // state identifiers 
   start     = 3'b000, 
   id1       = 3'b001, 
   id11     = 3'b011, 
   id110   = 3'b010, 
   id1101 = 3'b110;  //  signal found 
 
   reg [2:0] E1; // current state variables 
   reg [2:0] E2; // next state logic output 
   // next state logic 
   always @( x or E1 ) 
    begin 
     case( E1 ) 
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
         E2 = id1101; 
        else 
         E2 = start; 
      id1101: 
        if ( x ) 
         E2 = id11; 
        else 
         E2 = start; 
     default:   // undefined state 
         E2 = 3'bxxx; 
     endcase 
    end // always at signal or state changing 
 
// state variables 
   always @( posedge clk or negedge reset ) 
    begin 
     if ( reset ) 
      E1 = E2;    // updates current state 
     else 
      E1 = 0;     // reset 
    end // always at signal changing 
 
// output logic 
   always @( E1 ) 
    begin 
     y = E1[2];   // first bit of state value (MOORE indicator) 
    end // always at state changing 
 
endmodule // moore_1101
/*
module test;
    reg clk, reset, x;
    wire y;
    
    // Instanciar a máquina de Mealy
    moore_1101 moore1 (y, x, clk, reset);
    
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
        $display(" Teste Máquina de Moore - Sequência 1101");
        $display("==========================================");
        $display("Tempo Clock  Reset   x   y  | Estado");
        $display("------------------------------------------");
        
        // Monitorar mudanças
        $monitor("%4d    %b      %b     %b   %b  | %2b", 
                $time, clk, reset, x, y, moore1.E1);
        
        // Inicialização
        reset = 0;  // Reset ativo
        x = 0;
        #10;
        #12 reset = 0; // solta o reset e deixa 0 até o fim
        
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
    
endmodule
*/
/* === TESTES ====
==========================================
 Teste Máquina de Moore - Sequência 1101
==========================================
Tempo Clock  Reset   x   y  | Estado
------------------------------------------
   0    0      0     0   0  | 000
   5    1      0     0   0  | 000
  10    0      0     0   0  | 000
  15    1      0     0   0  | 000
  20    0      0     0   0  | 000
  22    0      1     0   0  | 000
  25    1      1     0   0  | 000
  30    0      1     0   0  | 000
  32    0      1     1   0  | 000
  35    1      1     1   0  | 001
  40    0      1     1   0  | 001
  45    1      1     1   0  | 011
  50    0      1     1   0  | 011
  55    1      1     1   0  | 011
  60    0      1     1   0  | 011
  62    0      1     0   0  | 011
  65    1      1     0   0  | 010
  70    0      1     0   0  | 010
  72    0      1     1   0  | 010
  75    1      1     1   1  | 110
  80    0      1     1   1  | 110
  82    0      1     0   1  | 110
  85    1      1     0   0  | 000
  90    0      1     0   0  | 000
  92    0      1     1   0  | 000
  95    1      1     1   0  | 001
 100    0      1     1   0  | 001
 102    0      1     0   0  | 001
 105    1      1     0   0  | 000
 110    0      1     0   0  | 000
 112    0      1     1   0  | 000
 115    1      1     1   0  | 001
 120    0      1     1   0  | 001
 125    1      1     1   0  | 011
 130    0      1     1   0  | 011
 135    1      1     1   0  | 011
 140    0      1     1   0  | 011
 142    0      1     0   0  | 011
 145    1      1     0   0  | 010
 150    0      1     0   0  | 010
 152    0      1     1   0  | 010
 155    1      1     1   1  | 110
 160    0      1     1   1  | 110
 162    0      1     0   1  | 110
------------------------------------------
Fim do teste

*/