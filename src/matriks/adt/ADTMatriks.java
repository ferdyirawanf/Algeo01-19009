package matriks.adt;

import java.util.Scanner;

public class ADTMatriks {
    /* ##### KONSTUKTOR definisi MATRIK ##### */
    int BrsEff;
    int KolEff;
    double[][] Mem; // atribut utama matik inputan
    double[][] MemS; // atribut matrik segitiga atas 
    int Kdet; //faktor pangkat di determinen
    Scanner input = new Scanner(System.in);


    /* ##### KONSTUKTOR membuat MATRIK ##### */
    public ADTMatriks(int NB, int NR){
        // Membuat matrik dengan ukuran banyaknya baris = NB
        // banyaknya kolom = NR, dan ukuran kontener selalu berbentuk persegi dari nilai NB, NB > NR atau NR, NR > NB
        //  dengan nilai efektif masing masing baris dan kolom adalah NBrsEff dan NKolEff
        int N;
        if(NB > NR){
            N = NB;
        }else{
            N = NR;
        }
        this.Mem = new double[N][N];
        this.MemS = new double[N][N];
        this.BrsEff = NB;
        this.KolEff = NR;

    }


    /* ##### SELEKTOR  MATRIK ##### */

    public double Elmt(int i, int j){
        /* Mengirimkan elemen matrik[i][j] */
        return Mem[i][j];
    }
    public int NBrsEff(){
        /* Mengirimkan banyaknya/ukuran baris yg terdefinisi */
        return BrsEff;
    }
    public int NKolEff(){
        /* Mengirimkan banyaknya/ukuran kolom yg terdefinisi */
        return KolEff;
    }
    public int GetFirstIdxBrs(){
        /* Mengirimkan indeks baris terkecil Matrik */
        return 0;
    }
    public int GetFirstIdxKol(){
        /* Mengirimkan indeks kolom terkecil Matrik */
        return 0;
    }
    public int GetLastIdxBrs(){
        /* Mengirimkan indeks kolom terkecil Matrik */
        return NBrsEff() -1;
    }
    public int GetLastIdxKol(){
        /* Mengirimkan indeks kolom terkecil Matrik */
        return NKolEff()- 1;
    }
    public boolean IsIdxValid(int i, int j){
        return (( GetFirstIdxBrs () <= i && i <= GetLastIdxBrs ()) && (GetFirstIdxKol () <= j && j <= GetLastIdxKol()));
    }

    /* ##### Baca/Tulis  MATRIK ##### */
    public void BacaMatriks(){
    /* I.S. IsIdxValid(NB,NK) */ 
	/* F.S. M terdefinisi nilai elemen efektifnya, berukuran NB x NK */
	/* Deangan membaca elemin kiri kekanan baru kebawah dengan input kebawah*/
        for (int i = 0; i < NBrsEff() ;i++ ) {
            for (int j = 0;j < NKolEff() ;j++ ) {
                Mem[i][j] = input.nextDouble();
            }
        }
    }
    public void TulisMatrik(){
    /* I.S. M terdefinisi */
	/* F.S. Nilai M(i,j) ditulis ke layar per baris per kolom, masing-masing elemen per baris dipisahkan sebuah spasi */
	/* Proses: Menulis nilai setiap elemen Matrik ke layar dengan traversal per baris dan per kolom */
	/* Contoh: menulis matriks 3x3 (ingat di akhir tiap baris, tidak ada spasi)
	1 2 3
	4 5 6
	8 9 10
	*/
        for (int i = 0; i < NBrsEff() ;i++ ) {
            for (int j = 0;j < NKolEff() ;j++ ) {
                int temp;
                temp = (int) MemS[i][j];
                if (j == GetLastIdxKol()){

                    if(temp == MemS[i][j] ){
                        System.out.println(temp);
                    }else{
                        System.out.println(MemS[i][j]);
                    }

                }else{
                    if(temp == MemS[i][j] ){
                        System.out.print( temp +" ");
                    }else{
                        System.out.print( MemS[i][j] +" ");
                    }
                }

            }

        }
    }
    public void MakeMatrikS(){
    /** Membuta salinan matrik Mem[N][N] ke MemS[N][N] sebagai persiapan matrik segitiga **/
    	for(int i = GetFirstIdxBrs(); i <= GetLastIdxBrs(); i++){
    		for (int j = GetFirstIdxKol(); j <= GetLastIdxKol(); j++){
				MemS[i][j] = Mem[i][j];
			}
		}
    }
    /* ********** KELOMPOK TEST TERHADAP MATRIKS ********** */
    public boolean IsBujurSangkar(){
    /* Mengirimkan true jika M adalah matriks dg ukuran baris dan kolom sama */
    	return NBrsEff()== NKolEff();
    }

    public void MakeSegitigaAtas(){
    /** Membuaat matrik MemS menjadi mattrik segitiga atas **/
    	if(IsBujurSangkar()){
    		MakeMatrikS(); // salnin matrik Men ke MenS
    		Kdet = 0;
    		for (int i=0; i < NBrsEff()-1 ;i++ ) {
    			if(MemS[i][i] == 0 ){
    				int idx = i+1;
    				while(MemS[idx][i]==0 && idx < NBrsEff()-1){
    					idx ++;	
    				}
    				if(MemS[idx][i] != 0 ){
	    				for (int j=0;j < NKolEff() ;j++ ) {
	    					double temp;
	    					temp = MemS[i][j];
	    					MemS[i][j] = MemS[idx][j];
	    					MemS[idx][j] = temp;
	    				}
	    			}
	    			Kdet ++;
    			}
    			double pengkali;
    			for (int j = i + 1; j < NBrsEff(); j++) {
    				pengkali =MemS[j][i]/MemS[i][i];
    				for (int k= i ;k < NKolEff() ;k++ ) {
    					MemS[j][k] = MemS[j][k] - (pengkali*MemS[i][k]);
    				}
    			}
    		}
    	}else{
    	    System.out.println("Matrik bukan matrik bujur sangkar");
        }
    }

    public void TulisMatrikSegitigaAtas(){
        MakeSegitigaAtas();
        for (int i = 0; i < NBrsEff() ;i++ ) {
            for (int j = 0;j < NKolEff() ;j++ ) {
            	int temp;
            	temp = (int) MemS[i][j];
                if (j == GetLastIdxKol()){
                	
                	if(temp == MemS[i][j] ){
                		System.out.println(temp);
                	}else{
                		System.out.println(MemS[i][j]);
                	}
      
                }else{
                	if(temp == MemS[i][j] ){
                		System.out.print( temp +" ");
                	}else{
                		System.out.print( MemS[i][j] +" ");
                	}
                }

            }

        }
    }






}

