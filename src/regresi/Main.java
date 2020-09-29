package com.regresi;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int i, j, k, l, x, y, variabel, ndata;
        float regresi, temp, pembagi, pembagi2;
        x = 0;
        y = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Masukkan jumlah variabel (variabel x sebanyak (n-1) dan y sebanyak 1): ");
        variabel = keyboard.nextInt();                                          // Input banyaknya variabel
        System.out.print("Masukkan jumlah data: ");
        ndata = keyboard.nextInt();                                             // Input banyak data

        float[][] data_x = new float[ndata][variabel-1];                        // Deklarasi matriks yang akan diisi semua x
        float[] data_y = new float[ndata];                                      // Deklarasi array yang akan diisi nilai y
        float[][] data = new float[variabel][variabel+1];                       // Deklarasi matriks data yaitu seperti augmented
        float[] taksiran = new float[variabel-1];                               // Deklarasi array yang diisi semua nilai taksiran

        // Input semua nilai taksiran dari user
        for (i = 0; i < variabel - 1; i++){
            System.out.print("Masukkan nilai taksiran X" + (i+1) + ": ");
            taksiran[i] = keyboard.nextFloat();
        }

        // Input semua nilai x dan y dari user
        for (i = 0; i < ndata; i++) {
            for (j = 0; j < variabel-1; j++) {
                System.out.print("Masukkan x" + (j + 1) + " data ke-" + (i + 1) + ": ");
                data_x[i][j] = keyboard.nextFloat();
            }
            System.out.print("Masukkan y data ke-" + (i + 1) + ": ");
            data_y[i] = keyboard.nextFloat();
        }

        // Mengisi elemen kolom ke-0 dan baris ke-0 dengan banyak data
        data[0][0] = ndata;

        // Mengisi semua elemen pada baris paling atas dan kolom paling kiri dengan jumlah X (sebanyak n data)
        // dan kolom paling kanan baris pertama dengan jumlah semua y (sebanyak ndata)
        for (j = 1; j < variabel; j++){
            float sumx = 0;
            float sumy = 0;
            for (i = 0; i < ndata; i++){
                sumx += data_x[i][j-1];                         // Menjumlah semua nilai variabel Xi
                sumy += data_y[i];                              // Menjumlah semua nilai variabel y
            }
            data[0][j] = sumx;                                  // Memasukkan nilai ke matriks data
            data[j][0] = sumx;                                  // Memasukkan nilai ke matriks data
            data[0][variabel] = sumy;                           // Memasukkan nilai ke matriks data
        }

        for (i = 1; i < variabel; i++) {
            for (j = 1; j < variabel; j++) {
                float sumx = 0;
                float sumy = 0;
                for (k = 0; k < ndata; k++){
                    sumx += data_x[k][j-1]*data_x[k][i-1];
                    sumy += data_y[k]*data_x[k][i-1];
                }
                data[i][j] = sumx;
                data[i][variabel] = sumy;
            }
        }


        // PROSES ELIMINASI GAUSS

        for (i = 0; i < variabel; i++) {
            search:                                                         // Proses mencari dari baris pertama kolom
            {                                                               // pertama elemen matriks yang bernilai
                for (j = i; j < variabel + 1; j++) {                        // bukan nol (pencarian dilakukan perkolom
                    for (k = i; k < variabel; k++) {                        // ke bawah terlebih dahulu)
                        if (data[k][j] != 0) {
                            x = k;
                            y = j;
                            break search;                                   // Jika terdapat elemen diagonal yang bernilai
                        }                                                   // nol maka pencarian berhenti dan akan
                    }                                                       // menimpan indeks baris dan kolom yang bernilai
                }                                                           // bukan nol muncul pertama kali
            }
            if (x != i && x > i) {
                tukar:
                {
                    for (k = i; k < variabel; k++) {
                        if (k != x) {
                            for (j = 0; j < variabel + 1; j++) {            // Melakukan penukaran baris
                                temp = data[k][j];
                                data[k][j] = data[x][j];
                                data[x][j] = temp;
                            }
                        }
                    }
                }
            }

            // Proses untuk memperoleh 1 utama dengan melakukan pembagian dengan elemen 1 utama
            if (y != variabel) {
                pembagi = data[i][y];
                for (j = 0; j < variabel + 1; j++) {
                    if (pembagi != 0 && data[i][j] != 0) {
                        data[i][j] = data[i][j] / pembagi;
                    }
                }
            }

            // Proses eliminasi agar elemen di bawah 1 utama bernilai nol semua
            for (j = i+1; j < variabel; j++){
                if (data[j][y] != 0 && y != variabel){
                    pembagi2 =data[j][y];
                    for (k = y; k < variabel+1; k++){
                        data[j][k] = data[j][k] - data[i][k]*pembagi2;
                    }
                }
            }
        }


        // PROSES ELIMINASI GAUSS JORDAN

        for (i = variabel-1; i > 0; i--){                                   // Pencarian terbalik dari baris bawah
            j = 0;
            while (j < variabel && data[i][j]==0){                          // Mencari 1 utama yang pertama kali dari bawah
                j = j+1;
            }
            if (j == variabel){                                             // Jika sampai ujung kanan kolom belum ditemukan
                continue;                                                   // Maka langsung ke baris di atasnya dan
            }                                                               // tidak diproses
            if (j < variabel){                                              // Jika ditemukan 1 utama pada kolom ke j maka
                for (l = 0; l < i; l++) {                                   // dilakukan proses eliminasi agar semua elemen
                    pembagi2 = data[l][j];                                  // di atas 1 utama juga bernilai nol
                    for (k = 0; k < variabel+1; k++) {
                        if (data[i][k] != 0) {
                            data[l][k] = data[l][k] - data[i][k] * pembagi2;
                        }
                    }
                }
            }
        }

        float[] Solusi_regresi = new float[variabel];                       // Deklarasi array baru untuk menampung hasil
        for (i = 0; i < variabel; i++){                                     // atau solusi dari eliminasi gauss jordan
            Solusi_regresi[i] = data[i][variabel];
        }

        String[] StrSolusi = new String[variabel];                          // Deklarasi array baru yang isinya sama dengan
        for (i = 0; i < variabel; i++){                                     // ArraySolusi namun bertype string
            StrSolusi[i] = String.valueOf(Solusi_regresi[i]);               // Casting float ke string
        }

        // Proses Output
        // Output persamaan regresi
        System.out.println("\nPersamaan regresi:");
        System.out.print("Y = ");
        if (Solusi_regresi[0] != 0){
            System.out.print(StrSolusi[0] + " ");                           // Print konstanta atau angka paling depan
        }
        for (i = 1; i < variabel; i++){                                     // Output koefisien dan variabel X
            if (Solusi_regresi[i] > 0){
                System.out.print("+ " + StrSolusi[i] + "X_" + i + " ");
            } else if (Solusi_regresi[i] < 0){
                System.out.print(StrSolusi[i] + "X_" + i + " ");
            }
        }
        System.out.println("\n");

        // Output hasil regresi yang ditaksir di suatu titik
        System.out.print("Hasil regresi yang di taksir dengan ");
        for (i = 0; i < variabel-1; i++){
            System.out.print("X" + (i + 1) + " = " + taksiran[i]);
            if (i < variabel - 2){
                System.out.print(", ");
            } else {
                System.out.print(" ");
            }
        }
        System.out.print("adalah ");

        float jumlah = 0;
        for (i = 1; i < variabel; i++){
            jumlah += Solusi_regresi[i]*(taksiran[i-1]);                // Substitusi agar diperoleh hasil
        }
        regresi = Solusi_regresi[0]+jumlah;                             // Menjumlahkan dengan konstanta atau angka pertama
        System.out.println(regresi);
    }
}
