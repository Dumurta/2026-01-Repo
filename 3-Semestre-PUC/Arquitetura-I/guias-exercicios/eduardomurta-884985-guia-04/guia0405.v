module questao5a_SoP (output s, input x, y);
    assign s = ~x & y;
endmodule

module questao5a_PoS (output s, input x, y);
    assign s = (x | y) & (~x | y) & (~x | ~y);
endmodule

module questao5b_SoP (output s, input x, y);
    assign s = (~x & ~y) | (~x & y) | (x & y);
endmodule

module questao5b_PoS (output s, input x, y);
    assign s = (~x | y);
endmodule

module questao5c_Sop (output s, input x, y, z);
    assign s = (~x & ~y & ~z) | (~x & ~y & z) | (~x & y & z) | (x & ~y & ~z) | (x & y & z);
endmodule// questao5c_SoP

module questao5c_PoS (output s, input x, y, z);
    assign s = (x | ~y | z) & (~x | y | ~z) & (~x | ~y | z);
endmodule // questao5c_PoS
