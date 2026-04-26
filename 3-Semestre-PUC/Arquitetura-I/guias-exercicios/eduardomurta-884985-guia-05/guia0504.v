module guia0504(output s, input a, input b);
    wire q,sa,sb;
    nor NOR1(sa, a, a);
    nor NOR2(sb, b, b);

    nor NOR3(q, sa, sb);
    nor NOR4(s, q, q);
endmodule //end guia0504

module test_f5;
    // ------------------------- definir dados
    reg x;
    reg y;
    wire s;
    //f5a moduloA ( a, x, y );
    //f5b moduloB ( b, x, y );

    guia0504 moduloA(s, x, y);

    // ------------------------- parte principal
    initial
    begin : main
    $display("Guia_0500 - Eduardo Murta De Abreu - 884985");
    $display(" x  y | s ~(a & b)");
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