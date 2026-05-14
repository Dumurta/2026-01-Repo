// ============================================================================
// RECUPERAÇÃO 01 - TESTBENCHES
// Validação das implementações em Verilog
// ============================================================================

// ============================================================================
// TESTBENCH - Exercício 01 (Veitch-Karnaugh)
// ============================================================================

module tb_ex01;
    reg a, b, c, d;
    wire f_sop_canon, f_pos_canon, f_sop_simp, f_pos_simp;
    wire f_nand, f_nor;
    
    ex01a_sop_canonical sop_canon(.f(f_sop_canon), .a(a), .b(b), .c(c), .d(d));
    ex01b_pos_canonical pos_canon(.f(f_pos_canon), .a(a), .b(b), .c(c), .d(d));
    ex01c_sop_simplified sop_simp(.f(f_sop_simp), .a(a), .b(b), .c(c), .d(d));
    ex01d_pos_simplified pos_simp(.f(f_pos_simp), .a(a), .b(b), .c(c), .d(d));
    ex01e_sop_nand sop_nand(.f(f_nand), .a(a), .b(b), .c(c), .d(d));
    ex01f_pos_nor pos_nor(.f(f_nor), .a(a), .b(b), .c(c), .d(d));
    
    initial begin
        $display("EXERCÍCIO 01 - Tabela Verdade");
        $display("a | b | c | d | SoP_Can | PoS_Can | SoP_Simp | PoS_Simp | NAND | NOR");
        $display("--+---+---+---+---------+---------+---------+---------+------+-----");
        
        for (integer i = 0; i < 16; i = i + 1) begin
            a = i[3];
            b = i[2];
            c = i[1];
            d = i[0];
            #1;
            $display("%d | %d | %d | %d |    %d    |    %d    |    %d    |    %d    |  %d   |  %d",
                     a, b, c, d, f_sop_canon, f_pos_canon, f_sop_simp, f_pos_simp, f_nand, f_nor);
        end
    end
endmodule

// ============================================================================
// TESTBENCH - Exercício 02
// ============================================================================

module tb_ex02;
    reg a, b, c;
    wire s_original, f_nand, f_pos, f_mux;
    
    ex02a_original orig(.s(s_original), .a(a), .b(b), .c(c));
    ex02b_nand_sop nand_version(.f(f_nand), .a(a), .b(b), .c(c));
    ex02c_pos_simplified pos_version(.f(f_pos), .a(a), .b(b), .c(c));
    ex02d_mux mux_version(.f(f_mux), .a(a), .b(b), .c(c));
    
    initial begin
        $display("\nEXERCÍCIO 02 - Tabela Verdade (diferentes formas)");
        $display("a | b | c | Original | NAND_SoP | PoS | MUX");
        $display("--+---+---+----------+----------+-----+-----");
        
        for (integer i = 0; i < 8; i = i + 1) begin
            a = i[2];
            b = i[1];
            c = i[0];
            #1;
            $display("%d | %d | %d |    %d     |    %d     |  %d  |  %d",
                     a, b, c, s_original, f_nand, f_pos, f_mux);
        end
    end
endmodule

// ============================================================================
// TESTBENCH - Exercício 03
// ============================================================================

module tb_ex03;
    reg a, b, c;
    wire f;
    
    ex03_nand_complex nand_complex(.f(f), .a(a), .b(b), .c(c));
    
    initial begin
        $display("\nEXERCÍCIO 03 - Expressão NAND Complexa");
        $display("a | b | c | f");
        $display("--+---+---+---");
        
        for (integer i = 0; i < 8; i = i + 1) begin
            a = i[2];
            b = i[1];
            c = i[0];
            #1;
            $display("%d | %d | %d | %d", a, b, c, f);
        end
    end
endmodule

// ============================================================================
// TESTBENCH - Exercício 04
// ============================================================================

module tb_ex04;
    reg [3:0] abcd;
    wire f, f_sop;
    
    ex04_quine_mccluskey qm(.f(f), .abcd(abcd));
    ex04_sop_equivalent sop_eq(.f(f_sop), .abcd(abcd));
    
    initial begin
        $display("\nEXERCÍCIO 04 - Quine-McCluskey (Mintermos: 0,4,5,12,10,14)");
        $display("a | b | c | d | QM | SoP_Eq");
        $display("--+---+---+---+----+-------");
        
        for (integer i = 0; i < 16; i = i + 1) begin
            abcd = i;
            #1;
            $display("%d | %d | %d | %d | %d  |   %d",
                     abcd[3], abcd[2], abcd[1], abcd[0], f, f_sop);
        end
    end
endmodule

// ============================================================================
// TESTBENCH - Exercício 05 (Conversões Numéricas)
// ============================================================================

module tb_ex05;
    initial begin
        $display("\nEXERCÍCIO 05 - Conversões e Cálculos Numéricos");
        $display("\na) FADABACA(16) / 100(4)");
        $display("   FADABACA(16) = 4208638666(10)");
        $display("   100(4) = 16(10)");
        $display("   Resultado: FA4A5AC(16)");
        $display("   Verificação: 0xFA4A5AC = %d", 32'hFA4A5AC);
        
        $display("\nb) 345(8) ÷ 23(4)");
        $display("   345(8) = 229(10)");
        $display("   23(4) = 11(10)");
        $display("   Quociente: 229 ÷ 11 = 20 (decimal) = 24(8) = 110(4)");
        $display("   Resto: 9 (decimal) = 11(8) = 21(4)");
        
        $display("\nc) C4(16) - 123(4) em um byte");
        $display("   C4(16) = 196(10)");
        $display("   123(4) = 27(10)");
        $display("   196 - 27 = 169(10)");
        $display("   169(10) = A9(16) = 10101001(2)");
        $display("   Verificação: 0xA9 = %d (decimal)", 8'hA9);
    end
endmodule

// ============================================================================
// Simulação Completa
// ============================================================================

module tb_complete;
    initial begin
        $display("================================================================================");
        $display("RECUPERAÇÃO 01 - TESTES COMPLETOS");
        $display("Aluno: Eduardo Murta (884985)");
        $display("================================================================================\n");
        
        $timeformat(-9, 0, " ns", 10);
    end
endmodule

// ============================================================================
