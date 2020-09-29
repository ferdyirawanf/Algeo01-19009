package com.company;
import java.util.Scanner;

public class Main {

    static void TulisMatriks(int N, double[][] M){
        for (int i = 0; i < N ;i++ ) {
            for (int j = 0;j < N ;j++ ) {
                double temp;
                temp = M[i][j];
                if (j == N-1){

                    if(temp == M[i][j] ){
                        System.out.printf("%.4f\n",temp);
                    }else{
                        System.out.printf("%.4f\n",M[i][j]);
                    }

                }else{
                    if(temp == M[i][j] ){
                        System.out.printf("%.4f ",temp);
                    }else{
                        System.out.printf("%.4f ",M[i][j]);
                    }
                }

            }

        }
    }

    static void TulisMatriks1(int N,double[][]K){
        for(int i = 0;i<N;i++){
            for(int j = 0;j<2*N;j++){
                if(j<(2*N)-1){
                    System.out.printf("%.4f ",K[i][j]);
                }else{
                    System.out.printf("%.4f\n",K[i][j]);
                }
            }
        }
    }

    static void MatrikBalikan(int N,double[][]M){
        // deklarasi variable balikan
        boolean balikan = true;
        double [][] MInverse = new double [N][N];
        int Baris = N;
        int Kolom = N;
        int BarisAugmented = Baris;
        int KolomAugmented = 2*Baris;

        // create MatriksAugmented ukuran N x 2*N
        double [][] MatriksAugmented = new double[BarisAugmented][KolomAugmented];
        for(int i =0;i<BarisAugmented;i++){
            for(int j=0;j<KolomAugmented;j++){
                if(j<=N-1){
                    // untuk bagian yang bukan matriks identitas
                    MatriksAugmented[i][j] = M[i][j];
                }else{
                    // bagian matriks identitas
                    // untuk menyelaraskan indeks
                    int indeksDiagonal = j-N;
                    if( indeksDiagonal ==i){
                        MatriksAugmented[i][j] = 1;
                    }else{
                        MatriksAugmented[i][j] = 0;
                    }
                }
            }
        }
        System.out.println("\nMatriks Augmented yang digunakan  : ");
        TulisMatriks1(BarisAugmented,MatriksAugmented);
        // check apakah elemen awal '0'
        if(MatriksAugmented[0][0] == 0){
            balikan = false;
        }
        // Melakukan OBE Gauss-Jordan untuk mencari Matriks Balikan
        // inisialisasi indeks untuk penanda dilakukannya pengurangan saat OBE "pivot"
        int pivot = 0;
        // deklarasi faktor pengali untuk kalkulasi OBE Gauss-Jordan
        double Pengali;
        while((pivot<BarisAugmented) && balikan){
            // cek apakah masih memungkinkan satu utama
            if(MatriksAugmented[pivot][pivot] != 0){
                // pembuat satu utama
                double Pembagi = MatriksAugmented[pivot][pivot];
                for(int i = pivot;i < KolomAugmented;i++){
                    MatriksAugmented[pivot][i] /= Pembagi;
                }

                // Eliminasi untuk setiap kolom pada baris pivot
                for(int i=0;i<BarisAugmented;i++){
                    if(i != pivot){
                        Pengali = MatriksAugmented[i][pivot] / MatriksAugmented[pivot][pivot];

                        for(int j=0;j<KolomAugmented;j++){
                            MatriksAugmented[i][j] -= (MatriksAugmented[pivot][j] * Pengali);
                        }
                    }
                }
                pivot +=1;

            }else{
                balikan = false;
                break;
            }
        }

        if(balikan){
            // copy matriks
            for(int i=0;i<Baris;i++){
                for(int j=0;j<Kolom;j++){
                    int BarisInverse = i;
                    int KolomInverse = j+N;
                    MInverse[i][j]=MatriksAugmented[BarisInverse][KolomInverse];
                }
            }
            // Tulis Matriks Inverse
            System.out.println("\nMatriks Inverse: ");
            TulisMatriks(N,MInverse);

            // copy matriks
            for(int i=0;i<Baris;i++){
                for(int j=0;j<Kolom;j++){
                    M[i][j] = MInverse[i][j];
                }
            }


        }else{
            System.out.println("Matriks tersebut Matriks Singular");
        }

    }

    static void SPL(){
        int baris, kolom;

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Masukkan jumlah baris matriks: ");
        baris = keyboard.nextInt();
        System.out.print("Masukkan jumlah kolom matriks: ");
        kolom = keyboard.nextInt();

        double[][] matriks = new double[baris][kolom];
        double[][] matriks_konstanta = new double[baris][1];
        double[][] matriks_augmented = new double[baris][kolom+1];
        double[] result = new double[baris];



        if(baris==kolom){
            // input matriks
            for (int i = 0; i < baris; i++) {
                for (int j = 0; j < kolom; j++) {
                    System.out.print("Masukkan koefisien dari elemen baris ke-" + (i + 1) + " dan kolom ke-" + (j + 1) + ": ");
                    matriks[i][j] = keyboard.nextInt();
                }
            }

            for (int i = 0; i < baris; i++) {
                for (int j = 0; j < 1; j++) {
                    System.out.print("Masukkan konstanta baris ke-" + (i + 1) + ": ");
                    matriks_konstanta[i][j] = keyboard.nextInt();
                }
            }

            for (int i = 0; i < baris; i++) {
                for (int j = 0; j < kolom; j++) {
                    matriks_augmented[i][j] = matriks[i][j];
                }
            }

            for (int i = 0; i < baris; i++) {
                for (int j = 0; j < 1; j++) {
                    matriks_augmented[i][j + kolom] = matriks_konstanta[i][j];
                }
            }

            // Mencari balikan dari matriks_augmented
            MatrikBalikan(baris,matriks_augmented);

            // perkalian matriks
            for(int i=0;i<baris;i++){
                for(int j=0;j<1;j++){
                    double sum = 0;
                    for(int k=0;k<baris;k++){
                        sum += matriks_augmented[i][k]*matriks_konstanta[k][j];
                    }
                    result[i] = sum;
                }
            }

            // Melakukan print solusi spl
            System.out.println("\nSolusi Penyelesaian :");
            for(int i=0;i<baris;i++){
                for(int j=0;j<1;j++){
                    if(i==(baris-1)) {
                        System.out.printf("X_%d = %.4f\n",(i+1),result[i]);
                    }else{
                        System.out.printf("X_%d = %.4f, ",(i+1),result[i]);
                    }
                }
            }

        }else{
            System.out.println("SPL tidak dapat diselesaikan dengan Metode Matriks Balikan ");
        }

    }



    public static void main(String[] args) {
        SPL();
    }
}
