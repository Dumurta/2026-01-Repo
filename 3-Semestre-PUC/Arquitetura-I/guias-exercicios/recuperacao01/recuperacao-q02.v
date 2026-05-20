module f ( output s, input a, input b, input c ); 
         wire w1, w2; 
                 and  AND_1 (s,a,w1);  
                 not   NOT_1 (w2,c);   
                 or     OR__1 (w1,w2,b); 
endmodule // s = f (a,b,c)

