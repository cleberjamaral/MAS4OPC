package resources;

import java.rmi.RemoteException;
import java.util.Calendar;

import br.org.scadabr.api.*;
import br.org.scadabr.api.constants.DataType;
import br.org.scadabr.api.constants.ErrorCode;
import br.org.scadabr.api.constants.QualityCode;
import br.org.scadabr.api.da.BrowseTagsOptions;
import br.org.scadabr.api.da.BrowseTagsParams;
import br.org.scadabr.api.da.BrowseTagsResponse;
import br.org.scadabr.api.da.ReadDataOptions;
import br.org.scadabr.api.da.ReadDataParams;
import br.org.scadabr.api.da.ReadDataResponse;
import br.org.scadabr.api.da.WriteDataOptions;
import br.org.scadabr.api.da.WriteDataParams;
import br.org.scadabr.api.da.WriteDataResponse;
import br.org.scadabr.api.vo.APIError;
import br.org.scadabr.api.vo.ItemInfo;
import br.org.scadabr.api.vo.ItemValue;

import javax.xml.rpc.ServiceException;

public class WebService {
	
	//declarando os objetos necessários para o servico para se conectar ao servidor - ScadaBR
	static APILocator locator = new APILocator();
	static ScadaBRAPI service = null;
	
	public static void testCall()
	{
		System.out.println("Web service test call confirm");
	}
	
	
	//TODO
	/*Inicia a conexao com a api SCADA*/
	
	public static void initWeb()
	{
//		//declarando os objetos necessários para o servico para se conectar ao servidor - ScadaBR
//				APILocator locator = new APILocator();
//				ScadaBRAPI service = null;
				
				try {
					//criando o servico e instanciando a API do ScadaBR
					service = (APISoapBindingStub) locator.getAPI();
					System.out.println("Web service received");
					
					//Buscar a lista de tags (variaveis do ScadaBR)
					////////////////////////////////////////////////
					
					BrowseTagsOptions browseTagsOptions = new BrowseTagsOptions();
					//número máximo de tags que buscará
					browseTagsOptions.setMaxReturn(100); // parâmetro opcional (valor padrão = 100)
					//configurando como receberemos as tags
					BrowseTagsParams browseTagsParams = new BrowseTagsParams();
					browseTagsParams.setOptions(browseTagsOptions);
					browseTagsParams.setItemsPath(null); // parâmetro opcional (para listar todas, basta enviar uma String vazia ou enviar null)
					//a lista que receber é salva tudo nesta variável 
					BrowseTagsResponse browseTagsResponse = null;

					try {
						//faz a ligação com o servidor ScadaBR via SOAP
					    browseTagsResponse = service.browseTags(browseTagsParams);
					    System.out.println("Tags response OK");
					} catch (RemoteException e) {
					    e.printStackTrace();
					}
					
					//agora vou pegar cada tag na lista que foi criada
					//////////////////////////////////////////////////
					
					ItemInfo[] itemList = browseTagsResponse.getItemsList();
					//salvarei tudo nesta string
					String response = "";
					//teste de erro no acesso as tags
					APIError[] errors = browseTagsResponse.getErrors();
					
					if(errors[0].getCode() != ErrorCode.OK) {
					    //se tiver um erro - informe
						response = "Error: " + errors[0].getDescription();
					} else {
						//senão vá guardando cada tag na varíavel tipo string
					    response = "Tags founded:\n";
					    for(int i = 0; i < itemList.length; i++) {
					    	//pega o nome de cada tag
					    	response += ("\n" + itemList[i].getItemName());
					    }
					    //imprime a lista de tags encontradas no console
					    System.out.println(response);
					}
					
					
				} catch (ServiceException e) {
					// Tratamento da exceção
					System.out.println("Web service exception - NOK");
					
				}

			}
	
	//TODO
	/*Escrever numero de PCB no loader e unloader, dependendo do tag que resceber.*/
	public static void writePCB(String tag, Object Value){
		String path = "";
		WriteDataOptions writeDataOptions = new WriteDataOptions();
		writeDataOptions.setReturnItemValues(false);

		ItemValue itemValue = new ItemValue();
		
		String pathWriteData = tag;
		
		itemValue.setItemName(pathWriteData); // Path da tag a receber a operação de escrita
		itemValue.setTimestamp(Calendar.getInstance());
		itemValue.setQuality(QualityCode.GOOD);
		itemValue.setDataType(DataType.INTEGER);
		
		Object novoValor = Value;
		
		itemValue.setValue(novoValor );
		ItemValue[] itemValueList = new ItemValue[1]; // Para alterar mais de uma tag, basta acrescentar mais objetos ItemValue na lista
		itemValueList[0] = itemValue;

		WriteDataParams writeDataParams = new WriteDataParams();
		writeDataParams.setItemsList(itemValueList);

		WriteDataResponse writeDataResponse = new WriteDataResponse();

		try {
			writeDataResponse = service.writeData(writeDataParams);
			System.out.println("Web service write function: OK");
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("Web service write function: NOK");
		}

		String responseWriteData = " ";

		path = "";
		
		APIError[] errorsWriteData = writeDataResponse.getErrors();
		if(errorsWriteData[0].getCode() != ErrorCode.OK) {
			//se acontecer algum erro - é porque a TAG não está com a opção SETABLE ativada - vá na edição da TAG e sete esta opção
			responseWriteData = "Error: " + errorsWriteData[0].getDescription();
		} else {
			responseWriteData = path + ":\t" + novoValor;
		}
		
		//Mostrando o resultado no console - olhe no ScadaBR se modificou
		System.out.println(responseWriteData);	
	}
	
	//TODO
	/*Escreve na tag de status*/
	public static void writeTag(String name, String estatus)
	{
		String tag = "";
		WriteDataOptions writeDataOptions = new WriteDataOptions();
		writeDataOptions.setReturnItemValues(false);

		ItemValue itemValue = new ItemValue();
		
		//TODO
		/* De acordo com o nome do artefato, seleciono a TAG do scada*/
		if (name.equals("L1"))tag = "Machines.Loader.Loader_MachineStatus";
		else if (name.equals("PP1"))tag = "Machines.PastePrinter.PP_MachineStatus";
		else if (name.equals("PaP1"))tag = "Machines.PickAndPlace.PaP_MachineStatus";
		else if (name.equals("U1"))tag = "Machines.Unloader.Unloader_MachineStatus";
		else if (name.equals("VS1"))tag = "Machines.AOI.AOI_MachineStatus";
		else if (name.equals("RO1"))tag = "Machines.Oven.Oven_MachineStatus";
		
		String pathWriteData = tag;
		
		itemValue.setItemName(pathWriteData); // Path da tag a receber a operação de escrita
		itemValue.setTimestamp(Calendar.getInstance());
		itemValue.setQuality(QualityCode.GOOD);
		itemValue.setDataType(DataType.INTEGER);
		
		Object Value = null;
		
		//TODO
		/*Trocar status para numero referente*/
		if(estatus.equals("STOPPED"))Value = 1;
		else if(estatus.equals("IDLE"))Value = 2;
		else if(estatus.equals("WAIT"))Value = 3;
		else if(estatus.equals("LOADED"))Value = 4;
		else if(estatus.equals("READY"))Value = 5;
		else if(estatus.equals("PAUSE"))Value = 6;
		
		//Object novoValor = Value;
		
		itemValue.setValue(Value );
		ItemValue[] itemValueList = new ItemValue[1]; // Para alterar mais de uma tag, basta acrescentar mais objetos ItemValue na lista
		itemValueList[0] = itemValue;

		WriteDataParams writeDataParams = new WriteDataParams();
		writeDataParams.setItemsList(itemValueList);

		WriteDataResponse writeDataResponse = new WriteDataResponse();

		try {
			writeDataResponse = service.writeData(writeDataParams);
			//System.out.println("write OK");
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("Web service write function: NOK");
		}

		String responseWriteData = " ";

		
		APIError[] errorsWriteData = writeDataResponse.getErrors();
		if(errorsWriteData[0].getCode() != ErrorCode.OK) {
			//se acontecer algum erro - é porque a TAG não está com a opção SETABLE ativada - vá na edição da TAG e sete esta opção
			responseWriteData = "Error: " + errorsWriteData[0].getDescription();
		} else {
			responseWriteData = "Tag value updated:\t" + Value;
		}
		
		//Mostrando o resultado no console - olhe no ScadaBR se modificou
		System.out.println(responseWriteData);	
	}
	
	//TODO
	/*Le a tag status da machine com o nome "name"*/
	public static String readTag(String name)
	{
		//Segue a mesma lógica, tem que criar um objeto readDataOptions e um readDataParams para formatar os dados
		ReadDataOptions readDataOptions = new ReadDataOptions();
		ReadDataParams readDataParams = new ReadDataParams();
		readDataParams.setOptions(readDataOptions);
		String tag = "";
		
		//TODO
		/* De acordo com o nome do artefato, seleciono a TAG do scada*/
		if (name.equals("L1"))tag = "Machines.Loader.Loader_MachineStatus";
		else if (name.equals("PP1"))tag = "Machines.PastePrinter.PP_MachineStatus";
		else if (name.equals("PaP1"))tag = "Machines.PickAndPlace.PaP_MachineStatus";
		else if (name.equals("U1"))tag = "Machines.Unloader.Unloader_MachineStatus";
		else if (name.equals("VS1"))tag = "Machines.AOI.AOI_MachineStatus";
		else if (name.equals("RO1"))tag = "Machines.Oven.Oven_MachineStatus";
		
		String[] itemPathList = {""+tag};
		readDataParams.setItemPathList(itemPathList); // lista com todas as tags que se deseja ler
		ReadDataResponse readDataResponse = new ReadDataResponse();

		try {
			//recebendo os dados do ScadaBR
		    readDataResponse = service.readData(readDataParams);
		    //System.out.println("recebido os dados lidos");
		} catch (RemoteException e) {
			
		    e.printStackTrace();
		}

		//Agora vou pegar cada item lido e escrever o valor para o Console
		ItemValue[] itemsValue = readDataResponse.getItemsList();
		String responseReadData = "";
		
		
		APIError[] errorsReadDataResponse = readDataResponse.getErrors();
		
				
		//só enviei uma tag para ler então só tem um item na minha lista
		if(errorsReadDataResponse[0].getCode() != ErrorCode.OK)
		    responseReadData = "Error: " + errorsReadDataResponse[0].getDescription();
		else
		    responseReadData = ""+itemsValue[0].getValue();
		
		String resposta = null;
		//TODO
		/*Trocar numero para status referente*/
		if(responseReadData.equals("1"))resposta = "STOPPED";
		else if(responseReadData.equals("2"))resposta = "IDLE";
		else if(responseReadData.equals("3"))resposta = "WAIT";
		else if(responseReadData.equals("4"))resposta = "LOADED";
		else if(responseReadData.equals("5"))resposta = "READY";
		else if(responseReadData.equals("6"))resposta = "PAUSED";
		
		//System.out.println(resposta);
		
		return resposta;
	
	}

}
 