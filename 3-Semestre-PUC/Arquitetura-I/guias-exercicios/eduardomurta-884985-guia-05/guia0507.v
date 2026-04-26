module guia0507 (output s, input a, input b);
    wire w1,w2,w3,q;
    nand NAND1 (w1, a, b);
    nand NAND2 (w2, a, w1);
    nand NAND3 (w3, b, w1);
    nand NAND4 (s, w2,w3);
    /*
    encontrei essa solucao que e' uma XOR, mas pelo fato de B estar negado
    o comportamento da porta fica invertido, oq possibilita usar este circuito como solucao
    -----
    caso quisesse o comportamento padrao eu poderia ter feito a inversao de b antes de entrar com ele na XOR
    nand NAND0(W0, b,b)
    e precisaria substituir os valores de b que usei por W0
    */
    
endmodule //guia0507

module test_f5;
    // ------------------------- definir dados
    reg x;
    reg y;
    wire s;

    guia0507 moduloA(s, x, y);

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