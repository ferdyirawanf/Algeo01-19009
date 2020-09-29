package com.coba;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int i, j, k, l, x, y, c, baris, kolom;
        float temp, pembagi, pembagi2;
        x = 0;
        y = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Masukkan jumlah baris matriks: ");
        baris = keyboard.nextInt();                                     // Input banyak baris yang diinginkan
        System.out.print("Masukkan jumlah kolom matriks: ");
        kolom = keyboard.nextInt();                                     // Input banyak kolom yang diinginkan

        float[][] matriks = new float[baris][kolom];                    // Deklarasi matriks koefisien x
        float[][] matriks_konstanta = new float[baris][1];              // Deklarasi matriks yang berisi konstanta
        float[][] matriks_augmented = new float[baris][kolom+1];        // Deklarasi matriks augmented

        // Proses input semua koefisien x (A)
        for (i = 0; i < baris; i++) {
            for (j = 0; j < kolom; j++) {
                System.out.print("Masukkan koefisien dari elemen baris ke-" + (i + 1) + " dan kolom ke-" + (j + 1) + ": ");
                matriks[i][j] = keyboard.nextInt();
            }
        }

        // Proses input semua konstanta (b)
        for (i = 0; i < baris; i++) {
            for (j = 0; j < 1; j++) {
                System.out.print("Masukkan konstanta baris ke-" + (i + 1) + ": ");
                matriks_konstanta[i][j] = keyboard.nextInt();
            }
        }

        // Proses menyalin semua koefisien x ke matriks augmented
        for (i = 0; i < baris; i++) {
            for (j = 0; j < kolom; j++) {
                matriks_augmented[i][j] = matriks[i][j];
            }
        }

        // Proses menyalin semua konstante ke kolom paling kanan matriks augmented
        for (i = 0; i < baris; i++) {
            for (j = 0; j < 1; j++) {
                matriks_augmented[i][j+kolom] = matriks_konstanta[i][j];
            }
        }


        // PROSES ELIMINASI GAUSS

        for (i = 0; i < baris; i++) {
            search:                                                 // Proses mencari dari baris pertama kolom
            {                                                       // pertama elemen matriks yang bernilai
                for (j = i; j < kolom + 1; j++) {                   // bukan nol (pencarian dilakukan perkolom/
                    for (k = i; k < baris; k++) {                   // ke bawah terlebih dahulu)
                        if (matriks_augmented[k][j] != 0) {
                            x = k;
                            y = j;
                            break search;                           // Jika terdapat elemen diagonal yang berniali
                        }                                           // nol maka pencarian berhenti dan akan
                    }                                               // menimpan indeks baris dan kolom yang bernilai
                }                                                   // bukan nol muncul pertama kali
            }
            if (x != i && x > i) {                                  // Jika indeks baris elemen bukan sama dengan i
                tukar:
                {
                    for (k = i; k < baris; k++) {
                        if (k != x) {
                            for (j = 0; j < kolom + 1; j++) {       // Dilakukan penukaran baris
                                temp = matriks_augmented[k][j];
                                matriks_augmented[k][j] = matriks_augmented[x][j];
                                matriks_augmented[x][j] = temp;
                            }
                        }
                    }
                }
            }

            // Proses untuk memperoleh 1 utama dengan melakukan pembagian dengan elemen 1 utama
            if (y != kolom) {
                pembagi = matriks_augmented[i][y];
                for (j = 0; j < kolom + 1; j++) {
                    if (pembagi != 0 && matriks_augmented[i][j] != 0) {
                        matriks_augmented[i][j] = matriks_augmented[i][j] / pembagi;
                    }
                }
            }

            // Proses eliminasi agar elemen di bawah 1 utama bernilai nol semua
            for (j = i+1; j < baris; j++){
                if (matriks_augmented[j][y] != 0 && y != kolom){
                    pembagi2 = matriks_augmented[j][y];
                    for (k = y; k < kolom+1; k++){
                        matriks_augmented[j][k] = matriks_augmented[j][k] - matriks_augmented[i][k]*pembagi2;
                    }
                }
            }
        }

        // Menampilkan matriks reduksi
        System.out.println("\nMatriks Gauss:");
        for (i = 0; i < baris; i++) {
            System.out.print("[");
            for (j = 0; j < kolom+1; j++) {
                System.out.print(matriks_augmented[i][j]);
                if (j < kolom) {
                    System.out.print(" ");
                }
            }
            System.out.print("]\n");
        }


        // PROSES GAUSS JORDAN

        for (i = baris-1; i > 0; i--){                              // Pencarian terbalik dari baris bawah
            j = 0;
            while (j < kolom && matriks_augmented[i][j]==0){        // Mencari 1 utama yang pertama kali dari bawah
                j = j+1;
            }
            if (j == kolom){                                        // Jika sampai ujung kanan kolom belum ditemukan
                continue;                                           // Maka langsung ke baris di atasnya dan
            }                                                       // tidak diproses
            if (j < kolom){                                         // Jika ditemukan 1 utama pada kolom ke j maka
                for (l = 0; l < i; l++) {                           // dilakukan proses eliminasi agar semua elemen
                    pembagi2 = matriks_augmented[l][j];             // di atas 1 utama juga bernilai nol
                    for (k = 0; k < kolom+1; k++) {
                        if (matriks_augmented[i][k] != 0) {
                            matriks_augmented[l][k] = matriks_augmented[l][k] - matriks_augmented[i][k] * pembagi2;
                        }
                    }
                }
            }
        }

        // Menampilkan matriks reduksi baris
        System.out.println("\nMatriks Gauss Jordan: ");
        for (i = 0; i < baris; i++) {
            System.out.print("[");
            for (j = 0; j < kolom+1; j++) {
                System.out.print(matriks_augmented[i][j]);
                if (j < kolom) {
                    System.out.print(" ");
                }
            }
            System.out.print("]\n");
        }

        // Proses Output SPL Gauss Jordan
        boolean diagonal = true;
        if (baris <= kolom){
            c = baris;
        } else{
            c = kolom;
        }
        i = 0;
        while (i < c && diagonal){                          // Proses mencari apakah semua diagonal utama bernilai 1
            if (matriks_augmented[i][i] == 0){                  // utama atau terdapat nol
                diagonal = false;                               // Jika ditemukan nol mengembalikan false dan berhenti
            } else{
                i++;
            }
        }
        if ((diagonal) && (kolom <= baris)){                    // Jika semua diagonal utama bernilai 1
            System.out.println("\nMatriks bersolusi tunggal/unik dengan solusi sebagai berikut:");
            for (i = 0; i < c; i++) {
                System.out.println("X_" + (i + 1) + " = " + matriks_augmented[i][kolom]);
            }
        } else {                                                // Jika diagonal utama ada yang nol
            tanda:
            {
                for (l = baris - 1; l >= 0; l--) {              // Proses mencari apa terdapat baris yang isinya nol
                    for (y = 0; y < kolom+1; y++){              // semua namun konstantanya bukan nol
                        if (matriks_augmented[l][y] !=  0){
                            break tanda;
                        }
                    }
                }
            }
            if (y == kolom){                                    // Jika ditemukan satu baris nol semua kecuali konstanta
                System.out.println("\nMatriks tidak memiliki solusi");
            } else {                                            // Jika tidak ada yang semuanya nol kecuali konstanta
                float[][] subInt = new float[kolom][kolom + 1]; // Deklarasi matriks untuk salinan matriks augmented
                for (i = 0; i < kolom; i++) {                   // namun posisinya sesuai dengan posisi 1 utama, misalnya
                    for (j = 0; j < kolom + 1; j++) {           // diagonal utama ditemukan di 0,3 maka disalin ke 3,3
                        subInt[i][j] = 0;                       // Assign semua elemen yang tersisa dengan 0
                    }
                }
                for (i = 0; i < baris; i++) {
                    boolean cari_0 = false;
                    for (j = 0; j < kolom + 1; j++) {
                        if (matriks_augmented[i][j] != 0) {     // Proses mencari posisi 1 utama
                            y = i;
                            x = j;
                            break;
                        }
                    }
                    for (k = 0; k < kolom; k++) {
                        for (j = 0; j < kolom + 1; j++) {
                            subInt[x][j] = matriks_augmented[y][j]; // Proses menyalin matriks seperti keternyan di atas
                        }
                    }
                }

                String[][] substitusi = new String[kolom][kolom + 1]; // Deklarasi matriks salinan subInt namun bertype string
                for (i = 0; i < kolom; i++) {
                    for (j = 0; j < kolom + 1; j++) {
                        substitusi[i][j] = "0";
                    }
                }
                for (i = 0; i < kolom; i++) {
                    for (j = 0; j < kolom + 1; j++) {
                        if (subInt[i][j] != 0) {
                            substitusi[i][j] = String.valueOf(subInt[i][j]);    // Casting dari float ke string
                        }
                    }
                }

                String[] parameter = {"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
                for (k = 0; k < 8; k++) {
                    for (i = 0; i < kolom; i++) {
                        if (substitusi[i][i] == "0") {
                            substitusi[i][i] = parameter[k];    // Jika matriks diagonal bernilai 0 maka diganti dengan parameter
                            k++;
                        }
                    }
                    if (i == kolom) {
                        break;
                    }
                }

                String[] result = new String[kolom];            // Deklarasi array yang akan menampung semua solusi x
                for (i = 0; i < kolom; i++) {
                    result[i] = "";                             // Assign dengan string kosong
                }
                for (i = 0; i < kolom; i++) {
                    if (subInt[i][i] == 1) {                    // Jika terdapat 1 utama/bukan nol
                        for (j = i + 1; j < kolom; j++) {
                            if (substitusi[i][j] != "0") {
                                result[i] += "-(" + substitusi[i][j] + substitusi[j][j] + ")";
                            }
                        }
                    } else {                                    // Jika diagonal utamanya nol
                        result[i] = substitusi[i][i];           // menyalin hasil parameter ke array hasil
                    }
                }
                System.out.println(("\nMatriks mempunyai solusi banyak/tak berhingga ditulis dalam parameter sebagai berikut:"));
                for (i = 0; i < kolom; i++) {
                    if (subInt[i][i] != 0) {
                        if (subInt[i][kolom] == 0){
                            if (result[i] == ""){
                                System.out.println("X_" + (i + 1) + " = 0");
                            }else {
                                System.out.println("X_" + (i + 1) + " = " + result[i]);
                            }
                        } else{
                            System.out.println("X_" + (i + 1) + " = " + substitusi[i][kolom] + result[i]);
                        }
                    } else {
                        System.out.println("X_" + (i + 1) + " = " + result[i]);
                    }
                }
            }
        }
    }
}
