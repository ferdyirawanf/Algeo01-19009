package matriks.tests;
import matriks.adt.*;

public class TestADT {
    public static void main(String[] args) {
        ADTMatriks M1 = new ADTMatriks(3,4);
        M1.BacaMatriks();
        M1.TulisMatrik();
        //M1.TulisMatrikSegitigaAtas();
        //M1.DeterminanReduksi();
        //M1.TulisDeterminan();
        //M1.DeterminanEkspansiKofaktor();
        M1.DeterminenXYZCramer();
    }
}
