package matriks.adt;

import java.util.Scanner;

public class ADTMatriks {
    /* ##### KONSTUKTOR definisi MATRIK ##### */
    int BrsEff;
    int KolEff;
    double[][] Mem; // matrik utama/ matik inputan
    double[][] MemS; // matrik segitiga atas dan kasus determinan
    double[][] MemA; // matrik kasus matrik augmented
    double[] Cramer; // jika AX = b maka cramer adalah b
    int NMemS; //ukuran matrik persegi dari matrik MemS
    int Kdet; //faktor pangkat di determinen
    double Det; // peyimpanan nilai determinan matrik

    /** input.next.... untuk memint masukan keybord **/
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
        this.BrsEff = NB;
        this.KolEff = NR;
        this.Det=0; // inisiasi awal determinan = 0. akan fataj jika di akses sbl proses pencarian Deretminan
        this.Kdet= 0;

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

    /* ##### Baca/Tulis  MATRIK  matrik INPUTAN ##### */
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
                temp = (int) Mem[i][j];
                if (j == GetLastIdxKol()){

                    if(temp == Mem[i][j] ){
                        System.out.println(temp);
                    }else{
                        System.out.println(Mem[i][j]);
                    }

                }else{
                    if(temp == Mem[i][j] ){
                        System.out.print( temp +" ");
                    }else{
                        System.out.print( Mem[i][j] +" ");
                    }
                }

            }

        }
    }

    /* ********** KELOMPOK TEST TERHADAP MATRIKS ********** */
    public boolean IsBujurSangkar(){
    /* Mengirimkan true jika M adalah matriks dg ukuran baris dan kolom sama */
    	return NBrsEff()== NKolEff();
    }

    /* ********** KELOMPOK 'MAKE' MATRIKS ********** */
    public void MakeMatrikSBujursangkar(){
    	if(IsBujurSangkar()){// kasus matrik Determinan
    		MemS = new double[NBrsEff()][NKolEff()];
    	}else{// kasus matrik spl metode cramer
    		MemS = new double[NBrsEff()][NBrsEff()];

    	}
		NMemS = NBrsEff();
    }
    public void MakeMatrikS(){
    	MakeMatrikSBujursangkar();
		/* Membuta salinan matrik Mem[N][N] ke MemS[N][N] sebagai persiapan matrik segitiga **/
    	if(IsBujurSangkar()){ // kasus matrik mencari determinan 
	    	for(int i = GetFirstIdxBrs(); i <= GetLastIdxBrs(); i++){
	    		for (int j = GetFirstIdxKol(); j <= GetLastIdxKol(); j++){
					MemS[i][j] = Mem[i][j];
				}
			}
		}else{ // kasus mecari spl dengan metode cramer
			Cramer = new double[NBrsEff()];
			for(int i = GetFirstIdxBrs(); i <= GetLastIdxBrs(); i++){
	    		for (int j = GetFirstIdxKol(); j <= GetLastIdxKol(); j++){
	    			if ( j == GetLastIdxKol()){
	    				Cramer[i] = Mem[i][j];
	    			}else{
	    				MemS[i][j] = Mem[i][j];
	    			}
					
				}
			}

		}
    }
   
    public void MakeSegitigaAtas(){
		/* Membuaat matrik MemS menjadi mattrik segitiga atas **/
    	Kdet = 0;
    	for (int i=0; i < NMemS -1 ;i++ ) {
    			// mencari apakan di diaonal matrik ada nilai 0 jika ada ditukarkan keculai no terletak di diagonal akhir
    		if(MemS[i][i] == 0 ){
    			int idx = i+1; // indek baris bernilai nol
    			while(MemS[idx][i]==0 && idx < NMemS -1){
    				idx ++;	
    			}
    			if(MemS[idx][i] != 0 ){
	    			for (int j=0;j < NMemS ;j++ ) {
	    				double temp;
	    				temp = MemS[i][j];
	    				MemS[i][j] = MemS[idx][j];
	    				MemS[idx][j] = temp;
	    			}
	    		}
	    		Kdet ++;
    		}
    		double pengkali;
    		for (int j = i + 1; j < NMemS ; j++) {
    			pengkali =MemS[j][i]/MemS[i][i];
    			for (int k= i ;k < NMemS ;k++ ) {
    				MemS[j][k] = MemS[j][k] - (pengkali*MemS[i][k]);
    			}
    		}
    	}
    	
       
    }

    public void TulisMatrikSegitigaAtas(){
    	MakeMatrikS(); // salnin matrik Men ke MenS
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
    public double DeterminanCramer(){
    /* mengemhitung nilai determinan dari matri reduksi segitig atas
    kemuadian menirimkan nilai ke variabel kontruktor diatas danmengembalikan nilai dari Det */

		MakeSegitigaAtas();
    	double detReduksi = 1;
    	for (int i = 0; i< NBrsEff() ; i++ ) {
    		detReduksi = detReduksi*MemS[i][i];
    		}
    	if(Kdet%2 == 1 ){
    		detReduksi = -1*detReduksi;
    	}
    	Det = detReduksi;
    	return detReduksi;
    }

	public double DeterminanReduksi(){
		MakeMatrikS(); // salnin matrik Men ke MenS
		return DeterminanCramer();


	}

    public double DeterminanEkspansiKofaktor(){
    	double detMinor  ;
    	double detKofaktor=0;
		double[][] Mminor; //matrik minor
		int N;
		N = NBrsEff()-1; //ukuran matrikminor
		Mminor = new double[N][N];
    	for ( int i = 0; i < NKolEff() ;i++ ) {
    		if (Mem[0][i] != 0){
				//int IdxBrs = 0;

    			for (int j = 1; j < NBrsEff() ; j++ ) {
					int IdxKol = 0;
    				for (int k =0; k < NKolEff() ; k++) {
    					if( k != i && IdxKol< N){
    						// membuat matrik minor kofaktor
    						Mminor[j-1][IdxKol] = Mem[j][k];
							IdxKol ++;
    					}
    				}
    			}
				// pencarianan nilai determinan matrik minor
				int Kdetminor = 0;
				for (int l=0; l < N-1 ;l++ ) {
					// mencari apakan di diaonal matrik ada nilai 0 jika ada ditukarkan keculai no terletak di diagonal akhir
					if(Mminor[l][l] == 0 ){
						int idx = l+1; // indek baris bernilai nol
						while(Mminor[idx][l]==0 && idx < N-1){
							idx ++;
						}
						if(Mminor[idx][l] != 0 ){
							for (int m=0;m < N ;m++ ) {
								double temp;
								temp = Mminor[l][m];
								Mminor[l][m] = Mminor[idx][m];
								Mminor[idx][m] = temp;
							}
						}
						Kdetminor ++;
					}
					double pengkali;
					for (int m = l + 1; m < N; m++) {
						pengkali =Mminor[m][l]/Mminor[l][l];
						for (int n= l ;n < N ;n++ ) {
							Mminor[m][n] = Mminor[m][n] - (pengkali*Mminor[l][n]);
						}
					}
				}
				double temp = 1;
				for (int l = 0; l< N ; l++ ) {
					temp = temp*Mminor[l][l];
				}
				if(Kdetminor%2 ==0 ){
					detMinor = temp;
				}else{
					detMinor = -1*temp;
				}// didapatkan determiner matrik minor

    			// pengkalian dan menghitung total determinan
    			if(i % 2 == 0){
					detKofaktor += Mem[0][i]*detMinor;
    			}else{
					detKofaktor -=  Mem[0][i]*detMinor;
    			}

    		}
    		
    	}
    	Det = detKofaktor;
		return (detKofaktor);

    }

    public void DeterminenXYZCramer(){
    	double DetTotal;
    	DetTotal = DeterminanReduksi();
    	if(DetTotal != 0){

    		double [] DetXyz = new double [NMemS]; //kontener sementara det XYZ.....
    		for (int i = 0; i < NMemS ;i++ ) {
				MakeMatrikS(); // salnin matrik Men ke MenS
		    	// *** metode cramer dijalankan. *****
		    	for (int j = 0; j < NMemS ;j++ ) {
		    		MemS[j][i] = Cramer[j];
		    	}
		    	DetXyz[i] = DeterminanCramer();

		    }
			for (int i = 0; i < NMemS ;i++ ) {
				Cramer[i] = DetXyz[i] / DetTotal;
			}

		    for (int i = 0; i < NMemS ;i++ ) {
		    	// misal keluaran x1 = 1, x2 = 3, dst
		    	System.out.print("X_");
		    	System.out.print(i+1);
		    	System.out.print(" = ");
		    	System.out.print(Cramer[i]);
		    	if(i == NMemS - 1) {
					System.out.print(". ");
				}
		    	else{
					System.out.print(", ");
				}

		    }



	    }else{
	    	System.out.println("Tidak memiliki solusi");
	    }

    }

    public  void TulisDeterminan(){
    /* menulikan nilai determinan dilayar */
        int temp;
        temp = (int) Det;
        if(temp == Det ){
            System.out.println(temp);
        }else{
            System.out.println(Det);
            }

    }



}// add class


