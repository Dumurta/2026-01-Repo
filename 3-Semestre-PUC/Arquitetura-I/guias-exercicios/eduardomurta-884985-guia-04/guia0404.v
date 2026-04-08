module questao4a (output S, input X, Y, Z);
    assign S = (X | Y | ~Z) & (X | ~Y | Z) & (X | ~Y | ~Z) & (~X | ~Y | Z);
endmodule

module questao4b (output S, input X, Y, Z);
    assign S = (X | Y | Z) & (X | ~Y | Z) & (X | ~Y | ~Z) & (~X | Y | Z);
endmodule

module questao4c (output S, input X, Y, W, Z);
    assign S = (X | Y | W | Z) & (X | Y | W | ~Z) & (X | Y | ~W | Z) & (X | ~Y | W | ~Z) & (X | ~Y | ~W | Z) & (X | ~Y | ~W | ~Z) & (~X | Y | ~W | ~Z) & (~X | ~Y | W | ~Z);
endmodule

module questao4d (output S, input X, Y, W, Z);
    assign S = (X | Y | W | ~Z) & (X | Y | ~W | Z) & (X | ~Y | W | Z) & (X | ~Y | W | ~Z) & (~X | Y | W | Z) & (~X | ~Y | W | Z) & (~X | ~Y | W | ~Z);
endmodule

module questao4e (output S, input X, Y, W, Z);
    assign S = (X | Y | W | Z) & (X | Y | W | ~Z) & (X | Y | ~W | Z) & (X | Y | ~W | ~Z) & (X | ~Y | ~W | ~Z) & (~X | Y | ~W | ~Z) & (~X | ~Y | ~W | ~Z);
endmodule