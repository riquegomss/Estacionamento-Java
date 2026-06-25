
import java.io.*;
import java.util.Scanner;

import javax.lang.model.type.PrimitiveType;

public class Estacionamento {	
	char 	ativo;
	String	codEst;
	String 	placa;
	String 	dataOperacao;
	char    tipoOperacao;
	String  modeloCor;
	String  codMarca;
	String  categoriaVeiculo;	
	String 	horaEntrada;
	String  horaSaida;
	float   valorPago;

	String vetCodMarca[] = {"BM", "VW", "FO", "MB", "CV", "FI", "AU", "TO", "HO", "HY"};
	String vetDescricaoMarca[] = {"BMW", "Volkswagen", "Ford", "Mercedes Benz", "Chevrolet", "Fiat", "Audi", "Toyota", "Honda", "Hyundai"};
	String vetCategorias[] = {"GI - (Grande e Importado)", "PI - (Pequeno e Importado)", "GN - (Grande e Nacional)", "PN - (Pequeno e Nacional)" };

	public long pesquisarRegistroVeiculo(String codEstPesq) {			
		// metodo para localizar um registro no arquivo em disco
		long posicaoCursorArquivo = 0;
		try {
			RandomAccessFile arqEst = new RandomAccessFile("EST.DAT", "rw");
			while (true) {
				posicaoCursorArquivo  = arqEst.getFilePointer();	// posicao do inicio do registro no arquivo
				ativo		     = arqEst.readChar();
				codEst           = arqEst.readUTF();
				placa  	         = arqEst.readUTF();
				dataOperacao     = arqEst.readUTF();
				tipoOperacao     = arqEst.readChar();
				modeloCor        = arqEst.readUTF();
				codMarca         = arqEst.readUTF();
				categoriaVeiculo = arqEst.readUTF();
				horaEntrada      = arqEst.readUTF();
				horaSaida        = arqEst.readUTF();
				valorPago        = arqEst.readFloat();


				if ( codEstPesq.equals(codEst) && ativo == 'S') {
					arqEst.close();
					return posicaoCursorArquivo;
				}
			}
		}catch (EOFException e) {
			return -1; // registro nao foi encontrado
		}catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
			return -1;
		}
	}

	public long pesquisarPlacaVeiculo(String placaEstChave) {
		char tipoOperacaoAux;
		String placaAux;
		char ativoAux;
		long posicaoCursorArquivo = 0;

		try {
			RandomAccessFile arqEst = new RandomAccessFile("EST.DAT", "rw");

			while (true) {

				posicaoCursorArquivo = arqEst.getFilePointer();

				ativoAux = arqEst.readChar();
				arqEst.readUTF();
				placaAux = arqEst.readUTF();
				arqEst.readUTF();
				tipoOperacaoAux = arqEst.readChar();
				arqEst.readUTF();
				arqEst.readUTF();
				arqEst.readUTF();
				arqEst.readUTF();
				arqEst.readUTF();
				arqEst.readFloat();

				if (placaEstChave.equals(placaAux) && ativoAux == 'S' && tipoOperacaoAux == 'E') {
					arqEst.close();
					return posicaoCursorArquivo;
				}
			}
		} catch (EOFException e) {
			return -1;
		} catch (IOException e) {
			return -1;
		}
	}

	public void salvarRegistroVeiculo() {	
		// metodo para incluir um novo registro no final do arquivo em disco
		try {
			RandomAccessFile arqEst = new RandomAccessFile("EST.DAT", "rw");	
			arqEst.seek(arqEst.length());  // posiciona o ponteiro no final do arquivo (EOF)
			arqEst.writeChar(ativo);
			arqEst.writeUTF(codEst);
			arqEst.writeUTF(placa);
			arqEst.writeUTF(dataOperacao);
			arqEst.writeChar(tipoOperacao);
			arqEst.writeUTF(modeloCor);
			arqEst.writeUTF(codMarca);
			arqEst.writeUTF(categoriaVeiculo);
			arqEst.writeUTF(horaEntrada);
			arqEst.writeUTF(horaSaida);
			arqEst.writeFloat(valorPago);		    
			arqEst.close();
			System.out.println("Dados gravados com sucesso !\n");
		}catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	public void desativarRegistroVeiculo(long posicao)	{    
		// metodo para alterar o valor do campo ATIVO para N, tornando assim o registro excluido
		try {
			RandomAccessFile arqEst = new RandomAccessFile("EST.DAT", "rw");			
			arqEst.seek(posicao);
			arqEst.writeChar('N');   // desativar o registro antigo
			arqEst.close();
		}catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	// ***********************   REGISTRAR ENTRADA DO VEÍCULO   *****************************
	public void entrarVeiculo() {
		String codEstChave = "000001";
		String placaChave = "";
		int maiorCodEst = 0;
		char confirmacao;
		long posicaoRegistro;
		int pesquisaMarca;
		String validacaoCategoria;
		boolean validacaoPlaca = true;


		do {


			try {
				RandomAccessFile arqEst = new RandomAccessFile("EST.DAT", "rw");
				while (true) {
					ativo		     = arqEst.readChar();
					codEst           = arqEst.readUTF();
					placa  	         = arqEst.readUTF();
					dataOperacao     = arqEst.readUTF();
					tipoOperacao     = arqEst.readChar();
					modeloCor        = arqEst.readUTF();
					codMarca         = arqEst.readUTF();
					categoriaVeiculo = arqEst.readUTF();
					horaEntrada      = arqEst.readUTF();
					horaSaida        = arqEst.readUTF();
					valorPago        = arqEst.readFloat();


					if ( Integer.parseInt(codEst) > maiorCodEst && ativo =='S') {
						maiorCodEst = Integer.parseInt(codEst);
					}
				}




			}catch (EOFException e) {

				if(maiorCodEst > 0) {
					maiorCodEst++;
					codEstChave = String.valueOf(maiorCodEst);
				}
				while(codEstChave.length() < 6) {
					codEstChave = '0' + codEstChave;
				}

			}catch (IOException e) {
				System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
				System.exit(0);

			}

			ativo = 'S';
			codEst = codEstChave;
			Main.leia.nextLine();
			System.out.println("\n ******************** ENTRADA DE VEICULOS NO ESTACIONAMENTO *******************");

			System.out.println("Codigo de Estacionamento.....................: " + codEstChave);

			do {
				System.out.print("Digite a placa do carro (FIM para encerrar)....: ");
				placaChave = Main.leia.nextLine().toUpperCase();
				if(placaChave.equals("FIM")) {
					break;
				}
				posicaoRegistro = pesquisarPlacaVeiculo(placaChave);
				validacaoPlaca = validarPlacaVeiculo(placaChave);

				if(posicaoRegistro != -1) {	
					System.out.println("Este veículo ainda está no estacionamento. Digite uma placa diferente.");
				}
			}while(posicaoRegistro != -1 || !validacaoPlaca);

			placa = placaChave;
			if(placaChave.equals("FIM")) {
				break;
			}

			do {
				System.out.print("Digite a data de operacao (DD/MM/AAAA).........: ");
				dataOperacao = Main.leia.nextLine().toUpperCase();;
			}while(!dataEhValida(dataOperacao));

			tipoOperacao = 'E';
			System.out.println("Tipo de Operacao (E - entrada / S - saida)...:" + tipoOperacao);

			do {
				System.out.print("Digite o modelo e a cor do veiculo.............: ");
				modeloCor = Main.leia.nextLine();
				if (modeloCor.length() <= 9) {
					System.out.println("Formato invalido minimo de 10 caracteres!");
				}
			} while (modeloCor.length() <= 9);

			do {
				System.out.println("************** MARCAS CADASTRADAS **************");
				for(byte i = 0; i < vetCodMarca.length; i++) {
					System.out.print( vetCodMarca[i]);
					if (i < vetCodMarca.length - 1) {
						System.out.print(" / ");
					}
				}
				System.out.println();
				System.out.print("Digite a marca do veiculo: ");					
				codMarca = Main.leia.nextLine().toUpperCase();;
				pesquisaMarca = pesquisarMarcaVeiculo(codMarca);
				if(pesquisaMarca == -1) {
					System.out.println("\nMarca nao encontrada. Digite uma marca que esteja no sistema.\n");
				}else {
					System.out.println(vetDescricaoMarca[pesquisaMarca]);
				}
			}while(pesquisaMarca == -1);

			do {
				System.out.println("************** CATEGORIAS CADASTRADAS **************");
				for(byte y = 0; y < vetCategorias.length; y++) {
					System.out.println(vetCategorias[y]);
				}
				System.out.print("\nDigite a categoria do veiculo..................: ");
				categoriaVeiculo = Main.leia.nextLine().toUpperCase();;
				validacaoCategoria = consistirCategoria(categoriaVeiculo);
				if(validacaoCategoria.equals("ERRO")) {
					System.out.println("Categoria invalida, digite uma categoria valida.");
				}else {
					System.out.println(validacaoCategoria);
				}

			}while(validacaoCategoria.equals("ERRO"));
			do {
				System.out.print("\nDigite a hora de entrada (hh:mm)...............: ");
				horaEntrada = Main.leia.nextLine();
				if(!horaEhValida(horaEntrada)) {
					System.out.println("Hora invalida digite uma hora valida (hh:mm)");
				}
			}while(!horaEhValida(horaEntrada));

			horaSaida = "";
			valorPago = 0;

			do {
				System.out.print("\nConfirma a gravacao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().toUpperCase().charAt(0);
				if(confirmacao != 'S' && confirmacao != 'N') {
					System.out.println("Comando invalido. Digite (S/N).");
				}
				if (confirmacao == 'S') {
					salvarRegistroVeiculo();
				}
			}while (confirmacao != 'S' && confirmacao != 'N');

			if (confirmacao == 'S' || confirmacao == 'N') {
				break;
			}

		}while ( !placa.equals("FIM"));	    
	}
	//*********************  PESQUISAR MARCA VEÍCULO  **************************

	public  int pesquisarMarcaVeiculo(String marcaExiste) {
		for(byte z = 0; z < vetCodMarca.length; z++) {
			if(marcaExiste.equals(vetCodMarca[z])) {
				return z;
			}	
		}
		return -1;
	}

	//************************  CONSISTIR CATEGORIA ****************************

	public String consistirCategoria(String categoria) {
		String descricao = "";
		if(categoria.equals("GI")) {
			descricao = "Grande e Importado";
			return descricao;
		}else if(categoria.equals("PI")) {
			descricao = "Pequeno e Importado";
			return descricao;
		}else if(categoria.equals("GN")) {
			descricao = "Grande e Nacional";
			return descricao;
		}else if(categoria.equals("PN")) {
			descricao = "Pequeno e Nacional";
			return descricao;
		}else {
			descricao = "ERRO";
			return descricao;
		}
	}

	//************************  ALTERAÇÃO - SAÍDA  *****************************
	public void registrarSaidaVeiculo() {
		String codEstChave;
		char confirmacao;
		long posicaoRegistro = 0;
		boolean validacaoHora;
		boolean verificacaoEntradaMenorSaida;


		do {
			do {
				Main.leia.nextLine();
				System.out.println("\n ***************  SAIDA DE VEICULOS  ***************** ");
				System.out.print("Digite o codigo do estacionamento (FIM para encerrar): ");
				codEstChave = Main.leia.nextLine().toUpperCase();
				if (codEstChave.equals("FIM")) {
					break;
				}
				posicaoRegistro = pesquisarRegistroVeiculo(codEstChave);
				if (posicaoRegistro == -1) {
					System.out.println("Veiculo nao cadastrado no arquivo, digite outro valor <ENTER>\n");
				}
				if(tipoOperacao == 'S') {
					System.out.println("Este veiculo ja saiu do estacionamento");
					posicaoRegistro = -1;
				}

			}while (posicaoRegistro == -1);

			if (codEstChave.equals("FIM")) {
				break;
			}

			tipoOperacao = 'S';

			System.out.println("Placa do veiculo..........................: " + placa);
			System.out.println("Data operacao ............................: " + dataOperacao);
			System.out.println("Tipo operacao (E - entrada / S saida).....: " + tipoOperacao);
			System.out.println("Modelo / cor..............................: " + modeloCor);
			System.out.println("Codigo da marca...........................: " + codMarca);
			System.out.println("Categoria do veiculo......................: " + categoriaVeiculo);
			System.out.println("Horario de entrada do veiculo.............: " + horaEntrada);

			do {
				System.out.print("Digite a hora de saida do veiculo (HH:MM)...:");
				horaSaida = Main.leia.nextLine();
				verificacaoEntradaMenorSaida = horaEntradaMenorHoraSaida(horaEntrada, horaSaida);
				validacaoHora = horaEhValida(horaSaida);
				if(!validacaoHora) {	
					System.out.println("Hora invalida digite uma hora valida (hh:mm)");
				}else if(!verificacaoEntradaMenorSaida) {
					System.out.println("O horario da saida deve ser maior que o de entrada, digite novamente.");
				}

			}while(!validacaoHora || !verificacaoEntradaMenorSaida);
			valorPago = calcularValorAPagar(horaEntrada, horaSaida);
			System.out.println("O valor pago sera de.....................:" + valorPago);

			do{
				System.out.println("\nConfirma a saida dente veiculo (S/N) ? ");
				confirmacao = Main.leia.next().toUpperCase().charAt(0);
				if(confirmacao != 'S' && confirmacao != 'N') {
					System.out.println("Comando invalido. Digite (S/N).");
				}
				if(confirmacao == 'S') {
					desativarRegistroVeiculo(posicaoRegistro);
					salvarRegistroVeiculo();
					System.out.println("Dados gravados com sucesso !\n");
				}

			}while(confirmacao != 'S' && confirmacao != 'N' );

		}while(!codEstChave.equals("FIM"));
	}



	//************************  EXCLUSAO  *****************************
	public void excluir() {
		String codEstChave;
		char confirmacao;
		long posicaoRegistro = 0;

		do {
			do {	
				Main.leia.nextLine();
				System.out.println(" ***************  EXCLUSAO DE REGISTROS  ***************** ");
				System.out.print("Digite o codigo do veiculo para excluir ( FIM para encerrar ): ");
				codEstChave = Main.leia.nextLine();
				if (codEstChave.equals("FIM")) {
					break;
				}

				posicaoRegistro = pesquisarRegistroVeiculo(codEstChave);
				if (posicaoRegistro == -1) {
					System.out.println("Veiculo nao cadastrado no arquivo, digite outro valor.");
				}
			}while (posicaoRegistro == -1);

			if (codEstChave.equals("FIM")) {
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;
			}

			System.out.println(" Placa do veiculo..........................: " + placa);
			System.out.println(" Data operacao ............................: " + dataOperacao);
			System.out.println(" Tipo operacao (E - entrada / S saida).....: " + tipoOperacao);
			System.out.println(" Modelo / cor..............................: " + modeloCor);
			System.out.println(" Codigo da marca...........................: " + codMarca);
			System.out.println(" Categoria do veiculo......................: " + categoriaVeiculo);
			System.out.println(" Hora de entrada do veiculo................: " + horaEntrada);
			System.out.println(" Hora de saida do veiculo..................: " + horaSaida);
			System.out.println(" Valor pago................................: " + valorPago);


			do {
				System.out.print("\nConfirma a exclusao deste cadastro (S/N) ? ");
				confirmacao = Main.leia.next().toUpperCase().charAt(0);
				if(confirmacao != 'S' && confirmacao != 'N') {
					System.out.println("Comando invalido. Digite (S/N).");
				}
				if (confirmacao == 'S') {
					desativarRegistroVeiculo(posicaoRegistro);
				}
			}while (confirmacao != 'S' && confirmacao != 'N');

		}while (!codEstChave.equals("FIM"));
	}

	//**************************** CALCULAR VALOR A PAGAR *********************************

	public float calcularValorAPagar(String entrada, String saida) {
		int horaEntrada;
		int minutoEntrada;
		int horaSaida;
		int minutoSaida;
		float valorAPagar = 0;


		horaEntrada = Integer.parseInt(entrada.substring(0,2));
		minutoEntrada = Integer.parseInt(entrada.substring(3,5));
		horaSaida = Integer.parseInt(saida.substring(0,2));
		minutoSaida = Integer.parseInt(saida.substring(3,5));

		if(horaEntrada <= 18) {
			if(categoriaVeiculo.equals("GI")) {
				valorAPagar = (horaSaida - horaEntrada + (minutoSaida - minutoEntrada) /60f) * 10;
			}else if(categoriaVeiculo.equals("PI")) {
				valorAPagar = (horaSaida - horaEntrada + (minutoSaida - minutoEntrada) /60)  * 8.2f;
			}else if(categoriaVeiculo.equals("GN")) {
				valorAPagar = (horaSaida - horaEntrada + (minutoSaida - minutoEntrada) /60f) * 9;
			}else if(categoriaVeiculo.equals("PN")) {
				valorAPagar = (horaSaida - horaEntrada + (minutoSaida - minutoEntrada) /60f) * 7;
			}
		}else {
			if(categoriaVeiculo.equals("GI")) {
				valorAPagar = (horaSaida - horaEntrada + (minutoSaida - minutoEntrada) /60f) * 8;
			}else if(categoriaVeiculo.equals("PI")) {
				valorAPagar = (horaSaida - horaEntrada + (minutoSaida - minutoEntrada) /60) * 6.5f;
			}else if(categoriaVeiculo.equals("GN")) {
				valorAPagar = (horaSaida - horaEntrada + (minutoSaida - minutoEntrada) /60) * 7.5f;
			}else if(categoriaVeiculo.equals("PN")) {
				valorAPagar = (horaSaida - horaEntrada + (minutoSaida - minutoEntrada) /60f) * 6;
			}
			System.out.println(categoriaVeiculo);
		}
		return valorAPagar;
	}

	//		//************************  CONSULTA  *****************************
	public void consultar() 	{
		RandomAccessFile arqEst;
		byte opcao;
		String codEstChave;
		long posicaoRegistro;
		String dataAux;

		do {
			do {
				System.out.println(" ***************  CONSULTA DE VEICULOS  ***************** ");
				System.out.println(" [1] EXIBIR TODOS OS REGISTROS ");
				System.out.println(" [2] EXIBIR SOMENTE OS VEICULOS QUE AINDA NAO SAIRAM DO ESTACIONAMENTO ");
				System.out.println(" [3] EXIBIR OS REGISTROS CADASTRADOS EM UMA DATA INFORMADA ");
				System.out.println(" [0] SAIR");
				System.out.print("\nDigite a opcao desejada: ");
				opcao = Main.leia.nextByte();
				if (opcao < 0 || opcao > 3) {
					System.out.println("opcao Invalida, digite novamente.\n");
				}
					
			}while (opcao < 0 || opcao > 3);

			switch (opcao) {
			case 0:
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;

			case 1:  // Exibi todos os registros
				Main.leia.nextLine();  // limpa buffer de memoria
				try {
					arqEst = new RandomAccessFile("EST.DAT", "rw");
					imprimirCabecalho();
					while (true) {
						ativo		     = arqEst.readChar();
						codEst           = arqEst.readUTF();
						placa  	         = arqEst.readUTF();
						dataOperacao     = arqEst.readUTF();
						tipoOperacao     = arqEst.readChar();
						modeloCor        = arqEst.readUTF();
						codMarca         = arqEst.readUTF();
						categoriaVeiculo = arqEst.readUTF();
						horaEntrada      = arqEst.readUTF();
						horaSaida        = arqEst.readUTF();
						valorPago        = arqEst.readFloat();
						if ( ativo == 'S') {
							imprimirVeiculo();
						}
					}
					//  arqEst.close();
				} catch (EOFException e) {
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codEstChave = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}
				break;

			case 2:  //Consultar os veiculos que ainda nao sairam do estacionamento	
				try {
					arqEst = new RandomAccessFile("EST.DAT", "rw");
					imprimirCabecalho();
					while (true) {
						ativo		     = arqEst.readChar();
						codEst           = arqEst.readUTF();
						placa  	         = arqEst.readUTF();
						dataOperacao     = arqEst.readUTF();
						tipoOperacao     = arqEst.readChar();
						modeloCor        = arqEst.readUTF();
						codMarca         = arqEst.readUTF();
						categoriaVeiculo = arqEst.readUTF();
						horaEntrada      = arqEst.readUTF();
						horaSaida        = arqEst.readUTF();
						valorPago        = arqEst.readFloat();
						if ( tipoOperacao == 'E' && ativo == 'S') {
							imprimirVeiculo();
						}
					}
					//  arqEst.close();
				} catch (EOFException e) {
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codEstChave = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}
				break;


			case 3:  // Consulta veiculos em uma data desejada
				do {
					System.out.print("Digite a data desejada (DD/MM/AAAA): ");
					dataAux = Main.leia.next();
				}while (!dataEhValida(dataAux));

				try {
					arqEst = new RandomAccessFile("EST.DAT", "rw");
					imprimirCabecalho();
					while (true) {
						ativo		     = arqEst.readChar();
						codEst           = arqEst.readUTF();
						placa  	         = arqEst.readUTF();
						dataOperacao     = arqEst.readUTF();
						tipoOperacao     = arqEst.readChar();
						modeloCor        = arqEst.readUTF();
						codMarca         = arqEst.readUTF();
						categoriaVeiculo = arqEst.readUTF();
						horaEntrada      = arqEst.readUTF();
						horaSaida        = arqEst.readUTF();
						valorPago        = arqEst.readFloat();
						if (dataAux.equals(dataOperacao) && ativo == 'S') {
							imprimirVeiculo();
						}
					}
					//  arqEst.close();
				} catch (EOFException e) {
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codEstChave = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}
				break;

			}	

		} while ( opcao != 0 );
	}

	public void imprimirCabecalho () {
		System.out.println("Cod Est    Placa           OP       Descricao                 Marca                 Categoria      Data           Hr Entr      Hr Saida      Vlr Pago");
		System.out.println("-------    ----------      --       --------------------      ---------------       ---------      ----------     -------      --------      --------");
	}

	public void imprimirVeiculo () {
		System.out.println(	formatarString(codEst, 7 ) + "    " +
				formatarString(placa , 10) + "      " +
				formatarString(Character.toString(tipoOperacao) , 2) + "       " +
				formatarString(modeloCor, 20 ) + "      " +
				formatarString(vetDescricaoMarca[pesquisarMarcaVeiculo(codMarca)], 15 ) + "       " +
				formatarString(categoriaVeiculo, 9 ) + "      " +
				formatarString(dataOperacao, 10 ) + "     " +
				formatarString(horaEntrada, 7 ) + "      " +
				formatarString(horaSaida, 8 ) + "      " +
				formatarString( String.valueOf(valorPago) , 8));
	}

	public void imprimirCabecalhoFaturamento() {
		System.out.println("PLACA         MODELO E COR                DATA              HORA ENTRADA       HORA SAIDA        VALOR PAGO");
		System.out.println("-----         --------------------        ----------        ------------       ----------        ----------");
	}

	public void imprimirFaturamento() {
		System.out.println(
				formatarString(placa,8) + "         " +
						formatarString(modeloCor,20) + "        " +
						formatarString(dataOperacao,10) + "        " +
						formatarString(horaEntrada,5) + "       " +
						formatarString(horaSaida,5) + "        " +
						formatarString(String.valueOf(valorPago),10));
	}

	public  String formatarString(String texto, int tamanho) {

		if (texto.length() > tamanho) {
			texto = texto.substring(0, tamanho);
		} else {
			while (texto.length() < tamanho) {
				texto = texto + " ";
			}
		}

		return texto;
	}

	public  boolean dataEhValida(String data) {
		byte dd;
		byte mm;
		int aaaa;

		if(data.length() != 10 || data.charAt(2) != '/' || data.charAt(5) != '/') {
			System.out.println("Data invalida, digite 10 caracteres no formato DD/MM/AAA.");
			return false;
		}

		try {
			dd = Byte.parseByte(data.substring(0,2));
			mm = Byte.parseByte(data.substring(3,5));
			aaaa = Integer.parseInt(data.substring(6));
		} catch(NumberFormatException ero) {
			System.out.println("Data inválida, digite dia, mes e ano numericos.");
			return false;
		}
		if(mm == 1 || mm == 3 || mm == 5 || mm == 7 || mm == 8 || mm == 10 || mm == 12) {
			if(dd < 1 || dd > 31) {
				System.out.println("Data inválida, para os meses 1, 3, 5, 7, 8, 10 e 12, digite o dia menor que 31.");
				return false;
			}
		}else if(mm != 2) {
			if(dd < 1 || dd > 30) {
				System.out.println("Data invalida, Para os meses 4, 6, 9 e 11, digite o dia menor que 30.");
				return false;
			}
		}else {
			if(aaaa % 400 == 0 || aaaa % 4 == 0 && aaaa % 100 != 0  ) {
				if(dd < 1 || dd > 29) {
					System.out.println("Data invalida! O ano é bissexto, digite um dia menor que 29 em fevereiro.");
					return false;
				}
			}else {
				if(dd < 1 || dd > 28) {
					System.out.println("Data inválida! O ano nao e bissexto, digite um dia menor que 28 para fevereiro.");
					return false;
				}
			}
		}
		if(mm < 1 || mm > 12) {
			System.out.println("Data inválida! O mes deverá ser maior que 1 e menor que 12.");
			return false;	
		}
		if(aaaa > 2026 ) {	
			System.out.println("Data inválida! O ano nao pode passar de 2026.");
			return false;
		}

		return true;	

	}

	public boolean horaEhValida(String hora) {

		try {
			int hh;
			int mm;

			if (hora.length() != 5) {
				return false;
			}

			if (hora.charAt(2) != ':') {
				return false;
			}

			hh = Integer.parseInt(hora.substring(0, 2));
			mm = Integer.parseInt(hora.substring(3, 5));

			if (hh >= 0 && hh <= 23 && mm >= 0 && mm <= 59) {
				return true;
			} else {
				return false;
			}
		}catch(Exception e) {
			return false;
		}
	}

	public boolean horaEntradaMenorHoraSaida(String horaEntradaEx, String horaSaidaEx) {
		int horaEnt;
		int minEnt;
		int horaSai;
		int minSai;
		int totalMinEnt;
		int totalMinSai;

		if (horaEntradaEx.length() != 5 || horaSaidaEx.length() != 5) {
			return false;
		}

		horaEnt = Integer.parseInt(horaEntradaEx.substring(0, 2));
		minEnt = Integer.parseInt(horaEntradaEx.substring(3, 5));

		horaSai = Integer.parseInt(horaSaidaEx.substring(0, 2));
		minSai = Integer.parseInt(horaSaidaEx.substring(3, 5));

		totalMinEnt = (horaEnt * 60) + minEnt;
		totalMinSai = (horaSai * 60) + minSai;
		if(totalMinSai <= totalMinEnt) {
			return false;
		}
		return true;	
	}

	public void emitirRelatorioFaturamento() {

		RandomAccessFile arqEst;
		String placaPesquisa;
		float totalFaturado = 0;
		boolean validacaoPlaca = true;


		try {

			Main.leia.nextLine();

			do {
				System.out.print("Digite a placa do veiculo (ENTER para todos): ");
				placaPesquisa = Main.leia.nextLine().toUpperCase();
				if(placaPesquisa.equals("")) {
					break;
				}
				validacaoPlaca = validarPlacaVeiculo(placaPesquisa);

			}while(!placaPesquisa.equals("") && !validacaoPlaca);



			arqEst = new RandomAccessFile("EST.DAT", "rw");

			imprimirCabecalhoFaturamento();

			while (true) {

				ativo             = arqEst.readChar();
				codEst            = arqEst.readUTF();
				placa             = arqEst.readUTF();
				dataOperacao      = arqEst.readUTF();
				tipoOperacao      = arqEst.readChar();
				modeloCor         = arqEst.readUTF();
				codMarca          = arqEst.readUTF();
				categoriaVeiculo  = arqEst.readUTF();
				horaEntrada       = arqEst.readUTF();
				horaSaida         = arqEst.readUTF();
				valorPago         = arqEst.readFloat();

				if (ativo == 'S' && tipoOperacao == 'S') {

					if (placaPesquisa.equals("") || placa.equalsIgnoreCase(placaPesquisa)) {
						imprimirFaturamento();
						totalFaturado += valorPago;
					}

				}
			}

		} catch (EOFException e) {

			System.out.println("\n                                                                                      TOTAL FATURADO : " + totalFaturado);

		} catch (IOException e) {

			System.out.println("Erro na abertura do arquivo.");
		}
	}

	public boolean validarPlacaVeiculo(String placaVeiculo) {
		String letras;
		boolean placaValidoNum = false;
		boolean placaValidoLet = false;
		Scanner leia = new Scanner(System.in);

		if(placaVeiculo.length() != 7) {
			System.out.println("A quantidade de caracteres e invalida. Digite um total de 7 caracteres.");
			return false;
		}

		try {
			Integer.parseInt(placaVeiculo.substring(3));
			placaValidoNum = true;
		}catch(NumberFormatException erro) {
			placaValidoNum = false;
		}
		if(placaValidoNum == false) {
			System.out.println("Erro! Os quatro ultimos digitos precisam ser numeros.");
			return false;
		}

		letras = placaVeiculo.substring(0,3);
		for(byte i = 0; i < letras.length(); i++) {
			try {
				Integer.parseInt(String.valueOf(letras.charAt(i)));
				placaValidoLet = false;
				break;
			}catch(NumberFormatException erro1) {
				placaValidoLet = true;
			}
		}
		if(placaValidoLet == false) {
			System.out.println("Erro! Os tres primeiros digitos devem ser apenas letras!");
			return false;
		}
		return true;
	}
}
