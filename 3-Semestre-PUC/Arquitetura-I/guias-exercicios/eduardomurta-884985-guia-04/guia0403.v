module questao3a (output s, input x, y, z);
    assign s = (~x & ~y & z) | (~x & y & z) | (x & ~y & ~z) | (x & y & ~z);
endmodule

module questao3b (output s, input x, y, z);
    assign s = (~x & ~y & z) | (~x & y & ~z) | (x & y & ~z) | (x & y & z);
endmodule

module questao3c (output s, input x, y, w, z);
    assign s = (~x & ~y & ~w & z) | (~x & ~y & w & ~z) | (~x & ~y & w & z) | (~x & y & w & ~z) | (~x & y & w & z) | (x & ~y & w & z) | (x & y & w & z);
endmodule

module questao3d (output s, input x, y, w, z);
    assign s = (~x & ~y & ~w & ~z) | (~x & ~y & w & ~z) | (~x & y & ~w & ~z) | (~x & y & w & ~z) | (x & ~y & w & ~z) | (x & y & ~w & ~z) | (x & y & w & ~z);
endmodule

module questao3e (output s, input x, y, w, z);
    assign s = (~x & ~y & ~w & ~z) | (~x & ~y & ~w & z) | (~x & y & w & z) | (x & ~y & ~w & z) | (x & y & w & z);
endmodule