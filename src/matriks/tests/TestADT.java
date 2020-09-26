package matriks.tests;
import matriks.adt.*;

public class TestADT {
    public static void main(String[] args) {
        ADTMatriks M1 = new ADTMatriks(3,3);
        M1.BacaMatriks();
        M1.TulisMatrik();
        M1.TulisMatrikSegitigaAtas();
    }
}
