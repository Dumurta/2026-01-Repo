module guia0401a (output s,
input x, y, z);
    assign s = x & ~(~y | ~z);
endmodule // guia0401a

module guia0401b (output s,
input x, y, z);
    assign s = ~(~x + y) . ~z;
endmodule // guia0401b

module guia0401c (output s,
input x, y, z);
    assign s = ~(x & y) & z;
endmodule // guia0401c

module guia0401d (output s, input x, y, z);
    assign s = ~(x & ~y) & ~z;
endmodule //guia0401d

module guia0401e (output s, input x, y, z);
    assign s = (~x | y) & ~(~y | z);

endmodule // guia0401e