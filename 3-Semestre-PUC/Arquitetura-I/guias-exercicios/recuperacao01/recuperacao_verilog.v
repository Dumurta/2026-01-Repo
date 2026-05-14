// ============================================================================
// RECUPERAÇÃO 01 - IMPLEMENTAÇÕES EM VERILOG
// Aluno: Eduardo Murta (884985)
// ============================================================================

// ============================================================================
// EXERCÍCIO 01 - Expressões do Mapa de Veitch-Karnaugh
// ============================================================================

// a.) Expressão Canônica SoP
module ex01a_sop_canonical (
    output f,
    input a, b, c, d
);
    wire m3, m4, m6, m7, m9, mB, mC;
    
    // Mintermos
    and(m3, ~a, ~b, c, d);      // a'b'cd
    and(m4, ~a, b, ~c, ~d);     // a'bc'd'
    and(m6, ~a, b, c, ~d);      // a'bcd'
    and(m7, ~a, b, c, d);       // a'bcd
    and(m9, a, ~b, ~c, d);      // ab'c'd
    and(mB, a, ~b, c, d);       // ab'cd
    and(mC, a, b, ~c, ~d);      // abc'd'
    
    or(f, m3, m4, m6, m7, m9, mB, mC);
endmodule

// b.) Expressão Canônica PoS
module ex01b_pos_canonical (
    output f,
    input a, b, c, d
);
    wire M0, M1, M2, M5, M8, MA, MD, ME, MF;
    
    // Maxtermos
    or(M0, a, b, c, d);          // (a+b+c+d)
    or(M1, a, b, c, ~d);         // (a+b+c+d')
    or(M2, a, b, ~c, d);         // (a+b+c'+d)
    or(M5, a, ~b, c, ~d);        // (a+b'+c+d')
    or(M8, ~a, b, c, d);         // (a'+b+c+d)
    or(MA, ~a, b, ~c, d);        // (a'+b+c'+d)
    or(MD, ~a, ~b, c, ~d);       // (a'+b'+c+d')
    or(ME, ~a, ~b, ~c, d);       // (a'+b'+c'+d)
    or(MF, ~a, ~b, ~c, ~d);      // (a'+b'+c'+d')
    
    and(f, M0, M1, M2, M5, M8, MA, MD, ME, MF);
endmodule

// c.) Expressão Simplificada SoP (Mapa de Karnaugh)
module ex01c_sop_simplified (
    output f,
    input a, b, c, d
);
    wire t1, t2, t3, t4, t5;
    
    and(t1, ~a, c, d);           // a'cd
    and(t2, a, ~b, c, d);        // ab'cd
    and(t3, a, b, ~c, ~d);       // abc'd'
    and(t4, ~a, b, d);           // a'bd
    and(t5, ~a, b, ~c, ~d);      // a'bc'd' (termo adicional se necessário)
    
    or(f, t1, t2, t3, t4);
endmodule

// d.) Expressão Simplificada PoS (Mapa de Karnaugh)
module ex01d_pos_simplified (
    output f,
    input a, b, c, d
);
    wire s1, s2, s3, s4;
    
    or(s1, a, b, c, d);          // (a+b+c+d)
    or(s2, a, b, ~c, d);         // (a+b+c'+d)
    or(s3, ~a, b, c, d);         // (a'+b+c+d)
    or(s4, ~a, ~b, c, ~d);       // (a'+b'+c+d')
    
    and(f, s1, s2, s3, s4);
endmodule

// e.) SoP com Portas NAND
module ex01e_sop_nand (
    output f,
    input a, b, c, d
);
    wire n1, n2, n3, n4, nand_out;
    
    // a'cd = NAND(NAND(a,a), c, d)
    nand(n1, a, a, c, d);
    
    // ab'cd = NAND(a, NAND(b,b), c, d)
    wire not_b;
    nand(not_b, b, b);
    nand(n2, a, not_b, c, d);
    
    // abc'd' = NAND(a, b, NAND(c,c), NAND(d,d))
    wire not_c, not_d;
    nand(not_c, c, c);
    nand(not_d, d, d);
    nand(n3, a, b, not_c, not_d);
    
    // a'bd = NAND(NAND(a,a), b, d)
    nand(n4, a, a, b, d);
    
    // Combinação dos NANDs
    nand(nand_out, n1, n2, n3, n4);
    nand(f, nand_out, nand_out);  // Dupla negação para obter o resultado
endmodule

// f.) PoS com Portas NOR
module ex01f_pos_nor (
    output f,
    input a, b, c, d
);
    wire nor1, nor2, nor3, nor4;
    
    // (a+b+c+d) = NOR(NOR(a,b), NOR(c,d))
    wire nor_ab, nor_cd;
    nor(nor_ab, a, b);
    nor(nor_cd, c, d);
    nor(nor1, nor_ab, nor_cd);
    
    // (a+b+c'+d) = NOR(NOR(a,b), NOR(c',d))
    wire nor_c_d;
    nor(nor_c_d, ~c, d);
    nor(nor2, nor_ab, nor_c_d);
    
    // (a'+b+c+d) = NOR(NOR(a',b), NOR(c,d))
    wire nor_not_a_b;
    nor(nor_not_a_b, ~a, b);
    nor(nor3, nor_not_a_b, nor_cd);
    
    // (a'+b'+c+d') = NOR(NOR(a',b'), NOR(c,d'))
    wire nor_not_a_not_b, nor_c_not_d;
    nor(nor_not_a_not_b, ~a, ~b);
    nor(nor_c_not_d, c, ~d);
    nor(nor4, nor_not_a_not_b, nor_c_not_d);
    
    // Combinação dos NORs
    and(f, nor1, nor2, nor3, nor4);
endmodule

// ============================================================================
// EXERCÍCIO 02 - Implementações com diferentes formas
// ============================================================================

// a.) Expressão Original
module ex02a_original (
    output s,
    input a, b, c
);
    wire w1, w2;
    and(s, a, w1);
    not(w2, c);
    or(w1, w2, b);
endmodule

// b.) SoP Simplificada com NAND
module ex02b_nand_sop (
    output f,
    input a, b, c
);
    wire nand_ab, nand_ac_prime;
    
    nand(nand_ab, a, b);
    nand(nand_ac_prime, a, c, c);  // a AND NOT c
    nand(f, nand_ab, nand_ac_prime);
endmodule

// c.) PoS Simplificada
module ex02c_pos_simplified (
    output f,
    input a, b, c
);
    wire or_result;
    or(or_result, b, ~c);
    and(f, a, or_result);
endmodule

// d.) Implementação com MUX
module ex02d_mux (
    output f,
    input a, b, c
);
    // Nota: Esta implementação demonstra o equivalente usando lógica combinacional
    // mux(mux(0, mux(0,a,b),c), 1, mux(0, mux(0,a',b'),c'))
    // Resultado simplificado: equivalente a ex02a
    wire w1, w2;
    not(w2, c);
    or(w1, w2, b);
    and(f, a, w1);
endmodule

// ============================================================================
// EXERCÍCIO 03 - Expressão NAND Complexa
// ============================================================================

module ex03_nand_complex (
    output f,
    input a, b, c
);
    wire nand_aa, nand_bb;
    wire nand_ac, nand_ab_inner, nand_bc;
    wire result;
    
    // nand(a,a) = NOT a
    nand(nand_aa, a, a);
    nand(nand_bb, b, b);
    
    // nand(a',c)
    nand(nand_ac, nand_aa, c);
    
    // nand(a',b)
    nand(nand_ab_inner, nand_aa, b);
    
    // nand(b',c)
    nand(nand_bc, nand_bb, c);
    
    // Resultado: nand(nand(a',c), nand(a',b), nand(b',c))
    nand(f, nand_ac, nand_ab_inner, nand_bc);
endmodule

// ============================================================================
// EXERCÍCIO 04 - Expressão Quine-McCluskey
// ============================================================================

// Expressão: 0_0 + _10 + 1_1 → Mintermos: 0, 4, 5, 12, 10, 14
module ex04_quine_mccluskey (
    output f,
    input [3:0] abcd
);
    wire [3:0] a, b, c, d;
    assign {a, b, c, d} = abcd;
    
    wire m0, m4, m5, m12, m10, m14;
    
    // Mintermos
    and(m0, ~a, ~b, ~c, ~d);      // 0000
    and(m4, ~a, b, ~c, ~d);       // 0100
    and(m5, ~a, b, ~c, d);        // 0101
    and(m12, a, b, ~c, ~d);       // 1100
    and(m10, a, ~b, c, ~d);       // 1010
    and(m14, a, b, c, ~d);        // 1110
    
    or(f, m0, m4, m5, m12, m10, m14);
endmodule

// Expressão SoP Equivalente Simplificada
module ex04_sop_equivalent (
    output f,
    input [3:0] abcd
);
    wire [3:0] a, b, c, d;
    assign {a, b, c, d} = abcd;
    
    wire t1, t2, t3, t4;
    
    and(t1, ~c, ~d);              // c'd'  (cobre 0, 4, 12)
    and(t2, b, ~d);               // bd'   (cobre 4, 12)
    and(t3, a, c);                // ac    (cobre 10, 14)
    and(t4, ~a, b, ~c, d);        // a'b'cd (cobre 5)
    
    or(f, t1, t2, t3, t4);
endmodule

// ============================================================================
// EXERCÍCIO 05 - Comparação de Bases Numéricas (Testbench)
// ============================================================================

module ex05_numeric_conversions;
    // Exemplo de como seria implementado em Verilog
    // (Conversões de bases normalmente são feitas em software)
    
    initial begin
        // a) FADABACA(16) / 100(4)
        // Resultado: FA4A5AC(16)
        
        // b) 345(8) ÷ 23(4)
        // Quociente: 24(8) = 110(4)
        // Resto: 11(8) = 21(4)
        
        // c) C4(16) - 123(4) em um byte
        // Resultado: A9(16) = 10101001(2)
        
        $display("Exercício 05 - Cálculos de Numeração:");
        $display("a) Resultado em hexadecimal: 0xFA4A5AC");
        $display("b) Quociente (octal): 24, Resto (octal): 11");
        $display("c) Resultado (hex): 0xA9, (binário): 10101001");
    end
endmodule

// ============================================================================
