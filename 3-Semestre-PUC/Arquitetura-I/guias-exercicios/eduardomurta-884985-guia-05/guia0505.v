module guia0505(output s, input a, input b);
    wire wa,wb,w3;
    nand NAND1 (wa, a, b);
    nand NAND2 (wb, a, wa);
    nand NAND3 (w3, b, wa);
    nand NAND4 (q, wb, w3);
    nand NAND5 (s, q, q);
endmodule //end guia0505

module test_f5;
    // ------------------------- definir dados
    reg x;
    reg y;
    wire s;
    //f5a moduloA ( a, x, y );
    //f5b moduloB ( b, x, y );

    guia0505 moduloA(s, x, y);

    // ------------------------- parte principal
    initial
    begin : main
    $display("Guia_0500 - Eduardo Murta De Abreu - 884985");
    $display(" x  y | s (~(a ^ b))");
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