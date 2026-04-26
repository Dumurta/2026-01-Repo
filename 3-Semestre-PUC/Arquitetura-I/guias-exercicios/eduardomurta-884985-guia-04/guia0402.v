module questao2a (output s1, output s2, input x, y);

    assign s1 = ~x & ~(x | y);

    assign s2 = ~x & ~y;

endmodule

module questao2b (output s1, output s2, input x, y);

    assign s1 = (~x | y) | (~x & y);

    assign s2 = ~x & y;

endmodule

module questao2c (output s1, output s2, input x, y);

    assign s1 = ~( x & y ) & ( ~x | y );

    assign s2 = ~x;

endmodule

module questao2d (output s1, output s2, input x, y);

    assign s1 = ~( ~x & ~y ) | ~( x | y );

    assign s2 = 1'b1;

endmodule

module questao2e (output s1, output s2, input x, y, z);

    assign s1 = (~y | ~x | z) & ~(y | x | ~z);

    assign s2 = (~y | ~x | z);

endmodule