module guia0506(output s, input a, input b);
    wire wa,wb,w3;
    nor NOR1(wa, a, b);
    nor NOR2(wb, a, wa);
    nor NOR3(w3, b, wa);
    nor NOR4(q, wb,w3);
    nor NOR5(s, q, q);
endmodule //endguia0506

module test_f5;
    // ------------------------- definir dados
    reg x;
    reg y;
    wire s;
    //f5a moduloA ( a, x, y );
    //f5b moduloB ( b, x, y );

    guia0506 moduloA(s, x, y);

    // ------------------------- parte principal
    initial
    begin : main
    $display("Guia_0500 - Eduardo Murta De Abreu - 884985");
    $display(" x  y | s ((a ^ b)");
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