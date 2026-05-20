//Eduardo Murta De Abreu 884985 -guia 12


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

// RAM 1x4 com JK — sem vetores internos / sem replicação

module q01_ram1x4_jk (
  input  clk, input rw, input addr, input clr,
  input  [3:0] in,
  output [3:0] out
);
  // controles
  wire we = addr & rw;      // write enable
  wire re = addr & ~rw;     // read enable

  // bits individuais de entrada
  wire d0 = in[0];
  wire d1 = in[1];
  wire d2 = in[2];
  wire d3 = in[3];

  // J/K de cada bit
  wire j0 =  d0 & we;  wire k0 = (~d0) & we;
  wire j1 =  d1 & we;  wire k1 = (~d1) & we;
  wire j2 =  d2 & we;  wire k2 = (~d2) & we;
  wire j3 =  d3 & we;  wire k3 = (~d3) & we;

  // FFs e suas saídas
  wire q0, q1, q2, q3;
  wire qn0, qn1, qn2, qn3;

  jkff ff0(.q(q0), .qnot(qn0), .j(j0), .k(k0), .clk(clk), .preset(1'b0), .clear(clr));
  jkff ff1(.q(q1), .qnot(qn1), .j(j1), .k(k1), .clk(clk), .preset(1'b0), .clear(clr));
  jkff ff2(.q(q2), .qnot(qn2), .j(j2), .k(k2), .clk(clk), .preset(1'b0), .clear(clr));
  jkff ff3(.q(q3), .qnot(qn3), .j(j3), .k(k3), .clk(clk), .preset(1'b0), .clear(clr));

  // máscara de leitura bit a bit
  wire o0 = q0 & re;
  wire o1 = q1 & re;
  wire o2 = q2 & re;
  wire o3 = q3 & re;

  // saída agrupada
  assign out = {o3, o2, o1, o0};
endmodule


// Testbench Q1

module q01_noidx_test;
  reg clk, rw, addr, clr;
  reg  [3:0] in;
  wire [3:0] out;

  // DUT
  q01_ram1x4_jk DUT(.clk(clk), .rw(rw), .addr(addr), .clr(clr), .in(in), .out(out)
  );

  // clock 10 ns
  initial begin clk = 1'b0; forever #5 clk = ~clk; end

  // wires auxiliares para o $monitor (nada de expressões diretas)
  wire we = addr & rw;
  wire re = addr & ~rw;

  initial begin
    // Cabeçalho da tabela
    $display("Guia_1201 - Eduardo Murta De Abreu - 884985");
    $display("Questao 1 - RAM 1x4 com flip-flops JK");
    $display("-------------------------------------------------------");
    $display("|  t  | clk | addr | rw | clr | we | re |  in  |  out |");
    $display("-------------------------------------------------------");

  
    $monitor("| %3t |  %b  |   %b  | %b  |  %b  | %b  | %b  | %04b | %04b |",
             $time, clk, addr, rw, clr, we, re, in, out);

    // Sequência de testes
    // reset
    clr=1; addr=0; rw=0; in=4'b0000; #3; 
    clr=0;

    // write 0001 (addr=1,rw=1) → escreve na próxima borda de subida
    addr=1; rw=1; in=4'b0001; #12;

    // read (rw=0)
    rw=0; #10;

    // write 1010
    rw=1; in=4'b1010; #10;

    // read
    rw=0; #10;

    // desendereça → OUT=0000
    addr=0; #10;
    $monitoroff;
    $display("-------------------------------------------------------");
    $finish;
  end
endmodule
/*TESTES
Guia_1201 - Eduardo Murta De Abreu 884985
Questao 1 - RAM 1x4 com flip-flops JK
-------------------------------------------------------
|  t  | clk | addr | rw | clr | we | re |  in  |  out |
-------------------------------------------------------
|   0 |  0  |   0  | 0  |  1  | 0  | 0  | 0000 | 0000 |
|   3 |  0  |   1  | 1  |  0  | 1  | 0  | 0001 | 0000 |
|   5 |  1  |   1  | 1  |  0  | 1  | 0  | 0001 | 0000 |
|  10 |  0  |   1  | 1  |  0  | 1  | 0  | 0001 | 0000 |
|  15 |  1  |   1  | 0  |  0  | 0  | 1  | 0001 | 0001 |
|  20 |  0  |   1  | 0  |  0  | 0  | 1  | 0001 | 0001 |
|  25 |  1  |   1  | 1  |  0  | 1  | 0  | 1010 | 0000 |
|  30 |  0  |   1  | 1  |  0  | 1  | 0  | 1010 | 0000 |
|  35 |  1  |   1  | 0  |  0  | 0  | 1  | 1010 | 1010 |
|  40 |  0  |   1  | 0  |  0  | 0  | 1  | 1010 | 1010 |
|  45 |  1  |   0  | 0  |  0  | 0  | 0  | 1010 | 0000 |
|  50 |  0  |   0  | 0  |  0  | 0  | 0  | 1010 | 0000 |
-------------------------------------------------------*/