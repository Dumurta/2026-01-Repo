module guia0508(output s, input a, input b);
    wire w1,q;
    // inverter A, fazer a nor de a invertido com b, e depois inverter o resultado
    nor NOR1(w1, a, a);
    nor NOR2(q, b, w1);
    nor NOR3(s, q,q);
endmodule //end guia0508

module test_f5;
    // ------------------------- definir dados
    reg x;
    reg y;
    wire s;
    guia0508 moduloA(s, x, y);
    // ------------------------- parte principal
    initial
    begin : main
    $display("Guia_0500 - Eduardo Murta De Abreu - 884985");
    $display(" x  y | s ");
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