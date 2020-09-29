package com.interpolasi;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int i, j, k, l, x, y, titik;
        float estimasi, temp, pembagi, pembagi2;
        x = 0;
        y = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Masukkan jumlah titik: ");
        titik = keyboard.nextInt();                             // Input banyaknya titik yang diinginkan
        System.out.print("Masukkan nilai estimasi x = ");
        estimasi = keyboard.nextFloat();                        // Input nilai x sebagai estimasi

        float[] titik_x = new float[titik];                     // Deklarasi array yang akan diisi titik x dari inpput user
        float[] titik_y = new float[titik];                     // Deklarasi array yang akan diisi titik y dari input user
        float[][] titik_xy = new float[titik][titik+1];         // Deklarasi matriks (berbentuk matriks augmented) dari persamaan lanjar

        // Bagian input dari keyboard user
        for (i = 0; i < titik; i++) {
            System.out.print("Masukkan titik x ke-" + (i + 1) +  ": ");
            titik_x[i] = keyboard.nextFloat();
            System.out.print("Masukkan titik y ke-" + (i + 1) +  ": ");
            titik_y[i] = keyboard.nextFloat();
        }

        // Assign matriks titik_xy dengan angka 1 (agar kolom pertama/paling kiri bernilai 1 semua dan yang lain nanti diganti)
        for (i = 0; i < titik; i++) {
            for (j = 0; j < titik+1; j++) {
                titik_xy[i][0] = 1;
            }
        }

        // Menyalin semua titik x di array titik_x ke dalam matriks titik_xy kolom ke-dua
        for (i = 0; i < titik; i++) {
            for (j = 0; j < titik+1; j++) {
                titik_xy[i][1] = titik_x[i];
            }
        }

        // Menyalin semua titik y di array titik_y ke dalam matriks titik_xy kolom paling kanan
        for (i = 0; i < titik; i++) {
            for (j = 0; j < titik+1; j++) {
                titik_xy[i][titik] = titik_y[i];
            }
        }

        // Proses mengisi matriks titik_xy dari kolom ketiga sampai akhir
        for (j = 2; j < titik; j++){
            for (i = 0; i < titik; i++){
                titik_xy[i][j] = (float) Math.pow(titik_x[i], j);       // Mengisi titik_xy kolom ke-j dengan hasil x^j
            }                                                           // dengan j adalah indeks kolom
        }


        // PROSES ELIMINASI GAUSS

        for (i = 0; i < titik; i++) {
            search:                                                     // Proses mencari dari baris pertama kolom
            {                                                           // pertama elemen matriks yang bernilai
                for (j = i; j < titik + 1; j++) {                       // bukan nol (pencarian dilakukan perkolom/
                    for (k = i; k < titik; k++) {                       // ke bawah terlebih dahulu)
                        if (titik_xy[k][j] != 0) {
                            x = k;
                            y = j;
                            break search;                               // Jika terdapat elemen diagonal yang berniali
                        }                                               // nol maka pencarian berhenti dan akan
                    }                                                   // menimpan indeks baris dan kolom yang bernilai
                }                                                       // bukan nol muncul pertama kali
            }
            if (x != i && x > i) {                                      // Jika indeks baris elemen bukan sama dengan i
                tukar:
                {
                    for (k = i; k < titik; k++) {
                        if (k != x) {
                            for (j = 0; j < titik + 1; j++) {           // Dilakukan penukaran baris
                                temp = titik_xy[k][j];
                                titik_xy[k][j] = titik_xy[x][j];
                                titik_xy[x][j] = temp;
                            }
                        }
                    }
                }
            }

            // Proses untuk memperoleh 1 utama dengan melakukan pembagian dengan elemen 1 utama
            if (y != titik) {
                pembagi = titik_xy[i][y];
                for (j = 0; j < titik + 1; j++) {
                    if (pembagi != 0 && titik_xy[i][j] != 0) {
                        titik_xy[i][j] = titik_xy[i][j] / pembagi;
                    }
                }
            }

            // Proses eliminasi agar elemen di bawah 1 utama bernilai nol semua
            for (j = i+1; j < titik; j++){
                if (titik_xy[j][y] != 0 && y != titik){
                    pembagi2 =titik_xy[j][y];
                    for (k = y; k < titik+1; k++){
                        titik_xy[j][k] = titik_xy[j][k] - titik_xy[i][k]*pembagi2;
                    }
                }
            }
        }


        // PROSES ELIMINASI GAUSS JORDAN

        for (i = titik-1; i > 0; i--){                                  // Pencarian terbalik dari baris bawah
            j = 0;
            while (j < titik && titik_xy[i][j]==0){                     // Mencari 1 utama yang pertama kali dari bawah
                j = j+1;
            }
            if (j == titik){                                            // Jika sampai ujung kanan kolom belum ditemukan
                continue;                                               // Maka langsung ke baris di atasnya dan
            }                                                           // tidak diproses
            if (j < titik){                                             // Jika ditemukan 1 utama pada kolom ke j maka
                for (l = 0; l < i; l++) {                               // dilakukan proses eliminasi agar semua elemen
                    pembagi2 = titik_xy[l][j];                          // di atas 1 utama juga bernilai nol
                    for (k = 0; k < titik+1; k++) {
                        if (titik_xy[i][k] != 0) {
                            titik_xy[l][k] = titik_xy[l][k] - titik_xy[i][k] * pembagi2;
                        }
                    }
                }
            }
        }

        float[] ArraySolusi = new float[titik];                         // Deklarasi array baru untuk menampung hasil
        for (i = 0; i < titik; i++){                                    // atau solusi dari eliminasi gauss jordan
            ArraySolusi[i] = titik_xy[i][titik];
        }

        String[] StrSolusi = new String[titik];                         // Deklarasi array baru yang isinya sama dengan
        for (i = 0; i < titik; i++){                                    // ArraySolusi namun bertype string
            StrSolusi[i] = String.valueOf(ArraySolusi[i]);              // Casting float ke string
        }

        // Proses Output
        // Output Persamaan polinom interpolasi
        System.out.println("\nPersamaan polinom interpolasi:");
        System.out.print("P(x) = ");
        if (ArraySolusi[0] != 0){                                       // Input konstanta atau angka paling depan
            System.out.print(StrSolusi[0] + " ");                       // yang tidak diikuti x
        }
        for (i = 1; i < titik; i++){                                    // Output koefisien beserta variabel (x)
            if (ArraySolusi[i] > 0){
                System.out.print("+ " + StrSolusi[i] + "x^" + i + " ");
            } else if (ArraySolusi[i] < 0){
                System.out.print(StrSolusi[i] + "x^" + i + " ");
            }
        }
        System.out.println("\n");

        // Output Nilai
        System.out.print("Hasil dari interpolasi polinom di titik x = " + estimasi + " adalah ");
        float jumlah = 0;
        for (i = 0; i < titik; i++){
            jumlah += ArraySolusi[i]*(Math.pow(estimasi, i));           // Proses menghitung nilai dengan substitusi
        }                                                               // x atau angka estimasi yang diinginkan
        System.out.println(jumlah);
    }
}
