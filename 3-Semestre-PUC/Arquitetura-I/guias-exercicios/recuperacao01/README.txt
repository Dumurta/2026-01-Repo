================================================================================
RECUPERAÇÃO 01 - GUIA DE USO DOS ARQUIVOS
Arquitetura de Computadores I (ARQ1)
Aluno: Eduardo Murta (884985)
================================================================================

ARQUIVOS CRIADOS:
=================

1. recuperacao884985-EDUARDO_Murta.txt
   - Soluções completas para todos os exercícios
   - Análise detalhada do Mapa de Veitch-Karnaugh
   - Expressões canônicas SoP e PoS
   - Simplificações e transformações
   - Cálculos de sistemas de numeração com explicação passo a passo

2. recuperacao_verilog.v
   - Implementações em Verilog de todos os circuitos
   - Módulos para cada exercício
   - Suporta simulação em ferramentas como Vivado, ModelSim, Icarus Verilog

3. recuperacao_testbench.v
   - Testbenches para validar todas as implementações
   - Gera tabelas verdade para comparação
   - Testes automatizados

4. README_LOGISIM.txt (para criar)
   - Instruções para implementar os circuitos no Logisim

================================================================================
COMO USAR:
==========

A. PARA REVISAR AS SOLUÇÕES:
   - Abra recuperacao884985-EDUARDO_Murta.txt
   - Leia a análise de cada exercício
   - Todos os cálculos estão resolvidos e explicados

B. PARA SIMULAR EM VERILOG:
   
   Opção 1 - Usando Icarus Verilog (gratuito, linha de comando):
   
   $ iverilog -o recuperacao_sim recuperacao_verilog.v recuperacao_testbench.v
   $ vvp recuperacao_sim
   
   Opção 2 - Usando Vivado (Xilinx):
   - Criar novo projeto
   - Adicionar recuperacao_verilog.v e recuperacao_testbench.v
   - Executar simulação comportamental

   Opção 3 - Usando ModelSim (Altera/Intel):
   - Compilar verilog
   - Executar testbench

C. PARA IMPLEMENTAR NO LOGISIM:
   - Cada módulo pode ser convertido para circuito no Logisim
   - Use as portas lógicas disponíveis
   - A ordem das entradas segue a convenção: a, b, c, d

================================================================================
EXPLICAÇÃO DOS EXERCÍCIOS:
==========================

EXERCÍCIO 01 - MAPA DE VEITCH-KARNAUGH:
- Mintermos (saída 1): 3, 4, 6, 7, 9, B (11), C (12)
- Maxtermos (saída 0): 0, 1, 2, 5, 8, A (10), D (13), E (14), F (15)

a.) SoP Canônico: Soma de todos os mintermos como AND
b.) PoS Canônico: Produto de todos os maxtermos como OR
c.) Simplificação SoP: Agrupa 1s adjacentes (mintermos)
d.) Simplificação PoS: Agrupa 0s adjacentes (maxtermos)
e.) NAND: Converte SoP usando De Morgan
f.) NOR: Converte PoS usando De Morgan

EXERCÍCIO 02 - EXPRESSÕES MÚLTIPLAS:
a.) Circuito original: s = a AND (NOT c OR b)
b.) Implementar com apenas portas NAND (2 entradas)
c.) Forma PoS simplificada
d.) Equivalente usando multiplexadores

EXERCÍCIO 03 - LÓGICA NAND COMPLEXA:
nand(nand(nand(a,a),c), nand(nand(a,a),b), nand(nand(b,b),c))
- Simplificar nand(x,x) = NOT x
- Aplicar De Morgan iterativamente

EXERCÍCIO 04 - QUINE-McCLUSKEY:
Método sistemático de simplificação (alternativa ao Karnaugh)
- Encontrar prime implicants
- Selecionar cobertura mínima

EXERCÍCIO 05 - CONVERSÕES DE BASES:
a.) Hexadecimal / Base 4
b.) Octal / Base 4 (com quociente e resto)
c.) Subtração em byte (8 bits)

================================================================================
RESPOSTAS RÁPIDAS:
==================

EX 01:
  a/b) Ver tabela verdade no txt
  c) f = a'cd + ab'cd + abc'd' + a'bd
  d) f = (a+b+c+d)·(a+b+c'+d)·(a'+b+c+d)·(a'+b'+c+d')
  e) Implementação com NAND de 2 entradas
  f) Implementação com NOR de 2 entradas

EX 02:
  a) Tabela verdade (8 linhas, saída para a(b+c'))
  b) NAND SoP: f = NAND(NAND(a,b), NAND(a,NOT c))
  c) f = a(b + c')
  d) MUX equivalente funciona

EX 03:
  f = (a'c) + (a'b) + (b'c)

EX 04:
  Expressão SoP: f = c'd' + bd' + ac + a'b'cd

EX 05:
  a) FA4A5AC(16)
  b) Quociente = 24(8), Resto = 11(8)
  c) A9(16) = 10101001(2)

================================================================================
PARA IMPLEMENTAR NO LOGISIM:
============================

1. Abra o Logisim
2. Para cada módulo Verilog, crie um circuito equivalente:
   - Use portas AND, OR, NOT, NAND, NOR
   - Siga a estrutura do módulo
   - Conecte as entradas e saídas

3. Teste cada circuito com tabelas verdade
4. Compare os resultados com o arquivo recuperacao884985-EDUARDO_Murta.txt

EXEMPLO - Exercício 02a no Logisim:
- Entrada: a, b, c
- Circuito: 
  1. NOT gate: entrada c → saída (c')
  2. OR gate: entradas (c'), b → saída w1
  3. AND gate: entradas a, w1 → saída s
- Saída: s = a AND (b OR NOT c)

================================================================================
NOTAS IMPORTANTES:
==================

1. Verifique sempre as suas respostas com as tabelas verdade
2. As expressões SoP e PoS simplificadas devem produzir os mesmos resultados
3. As implementações com NAND/NOR devem ser funcionalmente equivalentes
4. Para Logisim, use o simulador integrado para testar
5. Documente seu trabalho com comentários nos circuitos

================================================================================
ESTRUTURA DE SIMULAÇÃO IVERILOG:
================================

Se usar Icarus Verilog, o comando será:

$ iverilog -g2009 -o recuperacao recuperacao_verilog.v recuperacao_testbench.v
$ vvp recuperacao

Isto gerará as tabelas verdade para todos os exercícios.

================================================================================
