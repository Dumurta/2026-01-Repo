module guia0501 (output s, input a, input b);
    wire sa, sb, q;
    //inverter a pra ter ~a
    nand NAND1 (sa, a,a);

    //fazer o mesmo pra b
    nand NAND2(sb,b,b);

    //fazer a AND de dos resultados das nands e depois negar o resultado
    nand NAND3(q, sa,sb);
    nand NAND4(s, q,q);
endmodule //end guia0501

module test_f5;
    // ------------------------- definir dados
    reg x;
    reg y;
    wire s;
    //f5a moduloA ( a, x, y );
    //f5b moduloB ( b, x, y );

    guia0501 moduloA(s, x, y);

    // ------------------------- parte principal
    initial
    begin : main
    $display("Guia_0500 - Eduardo Murta De Abreu - 884985");
    // projetar testes do modulo
    $display(" x  y | s (~x & ~y)");
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