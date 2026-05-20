// -------------------------
// Guia_1203  - RAM 2x8 a partir de RAM 1x4 (JK)
// Nome: Eduardo Murta De Abreu
// Matricula: 884985
// -------------------------

// JK flip-flop 
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

// Q1) RAM 1x4 com JK — sem vetores internos / sem replicação
module q01_ram1x4_jk (
  input  clk, input rw, input addr, input clr,
  input  [3:0] in,
  output [3:0] out
);
  wire we = addr & rw;      // write enable
  wire re = addr & ~rw;     // read enable

  // bits de entrada
  wire d0 = in[0];
  wire d1 = in[1];
  wire d2 = in[2];
  wire d3 = in[3];

  // J/K de cada bit
  wire j0 =  d0 & we;  wire k0 = (~d0) & we;
  wire j1 =  d1 & we;  wire k1 = (~d1) & we;
  wire j2 =  d2 & we;  wire k2 = (~d2) & we;
  wire j3 =  d3 & we;  wire k3 = (~d3) & we;

  // FFs
  wire q0, q1, q2, q3;
  wire qn0, qn1, qn2, qn3;

  // instâncias POSICIONAIS
  jkff ff0(q0, qn0, j0, k0, clk, 1'b0, clr);
  jkff ff1(q1, qn1, j1, k1, clk, 1'b0, clr);
  jkff ff2(q2, qn2, j2, k2, clk, 1'b0, clr);
  jkff ff3(q3, qn3, j3, k3, clk, 1'b0, clr);

  // saída mascarada bit a bit
  wire o0 = q0 & re;
  wire o1 = q1 & re;
  wire o2 = q2 & re;
  wire o3 = q3 & re;

  assign out = {o3, o2, o1, o0};
endmodule

// Q2) RAM 1x8 a partir de duas RAM 1x4 (JK) em paralelo
module q02_ram1x8_from_1x4 (
  input  clk, input rw, input addr, input clr,
  input  [7:0] in,
  output [7:0] out
);
  wire [3:0] lo, hi;
  q01_ram1x4_jk jk_1(clk, rw, addr, clr, in[3:0], lo);
  q01_ram1x4_jk jk_2(clk, rw, addr, clr, in[7:4], hi);
  assign out = {hi, lo};
endmodule

// Q3) RAM 2x8 a partir RAM 1x4 (JK) 
module q03_ram2x8_from_1x4 (
  input  clk, input rw, input addr, input clr,
  input  [7:0] in,
  output [7:0] out
);
  // enables de escrita por linha
  wire we0 = rw & (addr == 1'b0);
  wire we1 = rw & (addr == 1'b1);

  // duas linhas 1x8 (cada uma construída com duas 1x4)
  wire [7:0] row0_out, row1_out;

  q02_ram1x8_from_1x4 ROW0(clk, we0, 1'b1, clr, in, row0_out);
  q02_ram1x8_from_1x4 ROW1(clk, we1, 1'b1, clr, in, row1_out);

  // leitura selecionada pela linha addr
  assign out = (addr == 1'b0) ? row0_out : row1_out;
endmodule

// ------------------------------------------------------------
// Testbench — tabela com $display (sem $monitor)
// ------------------------------------------------------------
module test;
  reg clk, rw, addr, clr;
  reg  [7:0] in;
  wire [7:0] out;

  q03_ram2x8_from_1x4 DUT(clk, rw, addr, clr, in, out);

  // clock 10 ns (nomeado para poder desligar, se quiser)
  initial begin : CLK_GEN
    clk = 1'b0; forever #5 clk = ~clk;
  end

  // impressão controlada
  task print_row;
    $display("| %3t |  %b  |  %b   | %b  |  %b  | %08b | %08b |",
             $time, clk, addr, rw, clr, in, out);
  endtask

  initial begin
    // Cabeçalho
    $display("Guia_1203 - Eduardo Murta De Abreu - 884985");
    $display("Questao 3 - RAM 2x8 a partir de RAM 1x4 (JK)");
    $display("-----------------------------------------------------");
    $display("|  t  | clk | addr | rw | clr |    in    |    out   |");
    $display("-----------------------------------------------------");

    // reset
    clr=1; addr=0; rw=0; in=8'h00; #3; print_row();
    clr=0;                        #2; print_row();

    // === escreve na linha 0 (addr=0) ===
    addr=0; rw=1; in=8'hA5; @(posedge clk); print_row(); // write row0
    rw=0;            @(negedge clk); print_row();        // read row0 -> A5

    // === escreve na linha 1 (addr=1) ===
    addr=1; rw=1; in=8'h3C; @(posedge clk); print_row(); // write row1
    rw=0;            @(negedge clk); print_row();        // read row1 -> 3C

    // === lê novamente a linha 0 (sem reescrever) ===
    addr=0;          @(negedge clk); print_row();        // read row0 -> A5



    $display("-----------------------------------------------------");
    disable CLK_GEN;
    $finish;
  end
endmodule
/*TESTES 
Guia_1203 - Eduardo Murta De Abreu - 884985
Questao 3 - RAM 2x8 a partir de RAM 1x4 (JK)
-----------------------------------------------------
|  t  | clk | addr | rw | clr |    in    |    out   |
-----------------------------------------------------
|   3 |  0  |  0   | 0  |  1  | 00000000 | 00000000 |
|   5 |  1  |  0   | 0  |  0  | 00000000 | 00000000 |
|  15 |  1  |  0   | 1  |  0  | 10100101 | 00000000 |
|  20 |  0  |  0   | 0  |  0  | 10100101 | 10100101 |
|  25 |  1  |  1   | 1  |  0  | 00111100 | 00000000 |
|  30 |  0  |  1   | 0  |  0  | 00111100 | 00111100 |
|  40 |  0  |  0   | 0  |  0  | 00111100 | 10100101 |
-----------------------------------------------------
*/