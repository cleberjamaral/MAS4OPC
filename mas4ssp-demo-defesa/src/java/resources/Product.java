/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este recurso gerencia a criação e a destruição de cada item
 * produto de um determinado lote. Este recurso gerencia a passagem
 * de cada objeto produto em cada equipamento do ambiente de produção.
 * O objeto produto deve saber onde está e para onde necessita ir, também
 * se deve armazenar neste objeto o resultado de cada operação realizada
 * no produto - se OK ou NOK. Desta forma o produto só passa ao próximo
 * equipamento quando a operação for OK, isto não significa que foi feita
 * com QUALIDADE ou SEM DEFEITOS, esta função será realizada pela
 * INSPEÇÃO. Caso a operação falhe (NOK) temos que parar a linha e tirar
 * o produto da produção - destruído. O lote desta forma fica com n-1
 * itens.
 *   
 * 2013-05-06 - MAS initial infrastructure for SSP 
 */

/**
 * 
 */
package resources;


import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author roloff
 *
 */
public class Product {
	
	int id;
	LinkedList<String> OpListResult;
	public Product(int p) {
		super();
		this.id = p;
		this.OpListResult=new LinkedList<String>();
	}	
	public Product() {
		// TODO Auto-generated constructor stub
		id=0;
		this.OpListResult=new LinkedList<String>();
	}
	
	public String value(){
		 return Integer.toString(id);
	}
	
	public void operate(String res){
		OpListResult.add(res);
	}
	
	public LinkedList<String> getOp(){
		return this.OpListResult;
	}
	
	public String inspec(){
		String ret=new String();
		Iterator<String> itr=this.OpListResult.iterator();
		while(itr.hasNext()){			
			ret=ret+itr.next()+"|";
		}
		return ret;
	}

}
