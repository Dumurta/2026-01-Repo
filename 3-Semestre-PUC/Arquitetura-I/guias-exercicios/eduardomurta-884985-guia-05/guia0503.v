//Projetar e descrever em Verilog, usando apenas portas nativas nand
//módulo equivalente à expressão ~(~a | b).
module guia0503 (output s, input a, input b);
    wire q, sb;
    //deixa o A normal, inverte b com nand, faz a nand de a com b e dps a nand do resultado
    nand NAND1(sb, b, b);

    nand NAND2(q, a, sb);

    nand NAND3(s, q, q);
endmodule //end guia0503

module test_f5;
    // ------------------------- definir dados
    reg x;
    reg y;
    wire s;
    //f5a moduloA ( a, x, y );
    //f5b moduloB ( b, x, y );

    guia0503 moduloA(s, x, y);

    // ------------------------- parte principal
    initial
    begin : main
    $display("Guia_0500 - Eduardo Murta De Abreu - 884985");
    // projetar testes do modulo
    $display(" x  y | s ~(~a | b)");
    $monitor("%2b %2b | %2b", x, y, s);
    x = 1'b0; y = 1'b0;
    #1
    x = 1'b0; y = 1'b1;
    #1
    x = 1'b1; y = 1'b0;
    #1
    x = 1'b1; y = 1'b1;
    end
endmodule // test_f5