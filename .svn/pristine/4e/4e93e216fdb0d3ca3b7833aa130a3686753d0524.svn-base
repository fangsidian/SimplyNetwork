package dataManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import utilities.Matrix;
import architecture.Network;
import architecture.Neuron;

public class WriteExcel {
		private String name; //Nombre archivo
		private File outFile; //crear un nuevo archivo, si un archivo con el mismo nombre ya existe podríamos sin querer escribir contenido sobre el mismo
		private FileWriter write;
		private BufferedWriter bw;
		private PrintWriter wr;
		private Network network;
		private static final Logger log = Logger.getLogger(WriteExcel.class);
				
		
		public WriteExcel() {
			super();
			// TODO Auto-generated constructor stub
		}
		public WriteExcel (String name, Network ne) {
			super(); 
			this.name = name;
			this.outFile = new File (name);
			network = ne;
			openFile();
		}
		

		public void openFile()
		{
			try {
				write = new FileWriter(this.outFile);// objeto que reserva un espacio en memoria donde se guarda la información antes de ser escrita en un archivo.
				bw = new BufferedWriter(write); //Es el objeto que utilizamos para escribir directamente sobre el archivo de texto.
				wr = new PrintWriter(bw); //Objeto que tiene como función escribir datos en un archivo. 
			}
				catch (IOException e) {
					e.printStackTrace();
				}  
		}
		
		
		public void closeFile()
		{
			try {
				wr.close();
				bw.close();
			}
				catch (IOException e) {
					e.printStackTrace();
				} 
			
		}
		
		
		//Getters and Setters
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public File getOutFile() {
			return outFile;
		}
		public void setOutFile(File outFile) {
			this.outFile = outFile;
		}
		
		public FileWriter getWrite() {
			return write;
		}
		public void setWrite(FileWriter write) {
			this.write = write;
		}
		public BufferedWriter getBw() {
			return bw;
		}
		public void setBw(BufferedWriter bw) {
			this.bw = bw;
		}
		public PrintWriter getWr() {
			return wr;
		}
		public void setWr(PrintWriter wr) {
			this.wr = wr;
		}
		
//		public void writeStringAppend (String cadena){
//			wr.append(cadena);
//			wr.append("\n");
//		}
//		
//		public void writeBigDecimal (BigDecimal b){
//			wr.append(b.toString());
//			
//		}
//		
//		public void writeNeuron (Neuron n){
//			for (BigDecimal b: n.getValues()){
//				wr.append(b+ " ");	
//			}
//				wr.append(") \n");
//		}
//		
		
		//Escribe el input correspondiente al id q le pasamos por parámetro
		public void writeInput (int id){
			Neuron aux = network.getInput(id);
		
		    wr.append ("\n Input:  ;"+ id+"\n");
			for (BigDecimal b: aux.getValues()){
				String valueStr = b.toString();
				valueStr = valueStr.replace(".", ",");
				wr.append(valueStr+ ";");	
			}
			wr.append("\n");
		}
		
	
		
		
		
			
//		//Escribe el output correspondiente al id q le pasamos por parámetro
//		public void writeDesiredOutput (int id){
//			Neuron aux = network.getDesiredOutput(id);
//			wr.append ("\n");
//		    wr.append ("Desired Output "+ id+" (");
//			for (BigDecimal b: aux.getValues()){
//				wr.append(b+ " ");	
//			}
//			wr.append(") \n");
//		}
//		
//		
//		public void writeOutputs (){
//			for (Neuron aux: network.getOutputs()){
//				wr.append ("\n");
//			    wr.append ("Output: (");
//				for (BigDecimal b: aux.getValues()){
//					wr.append(b+ " ");	
//				}
//				wr.append(") \n");
//			}
//		}
//		
		public void writeInputs (){
			for (Neuron o: network.getInputs()){
				for (BigDecimal b: o.getValues()){
					String valueStr = b.toString();
					valueStr = valueStr.replace(".", ",");
					wr.append(valueStr+ ";");	
				}
				wr.append("\n");
			}
		}
		
		public void writeDesiredOutputs (){
			for (Neuron o: network.getDesiredOutputs()){
				for (BigDecimal b: o.getValues()){
					String valueStr = b.toString();
					valueStr = valueStr.replace(".", ",");
					wr.append(valueStr+ ";");		
				}
				wr.append("\n");
			}
		}
		
		
		
		public void writeWeightMatrix()
		{
		    Matrix aux = network.getMatrixWeight();
			 for (int i = 0; i < aux.getRow(); i++){
				 for (int j= 0; j < aux.getColumn(); j++){
					 String valueStr = aux.getValuePos(i, j).toString();
					 valueStr = valueStr.replace(".", ",");
					 wr.append(valueStr+ ";");
				 }
				 wr.append("\n");}
		}
		
		public void writeErrorProgress(){
			ArrayList<BigDecimal> aux = network.getErrorTraining();
			for (BigDecimal e: aux){
				log.trace("writeErrorProgres Bigdecimal: " + e);
				String valueStr = e.toString();
				valueStr = valueStr.replace(".", ",");
				log.trace("writeErrorProgres en cadena: " + valueStr);
				wr.append(valueStr+ "\n");	
			}
		}
		
		
//		public void writeErrorInf(int it)
//		{
//			wr.append ("\n");
//			wr.append("En iteracción "+ it+ " los errores parciales obtenidos son: \n");
//			for (BigDecimal b: network.getSaveError()){
//				wr.append (b + "\n");
//			}
//				wr.append ("\n");
//				wr.append ("Error ponderado = "+ network.getCurrentE());	
//
//		}
//		
//		
//		public void writeBasicInformation(){
//			
//			wr.append("Entrenamiento con "+ network.getInputs().size()+ " patrones. Los datos corresponden a la empresa "+ network.getCompany().getName() + "\n");
//			wr.append("Todos los datos han sido normalizados \n");
//			wr.append("Coeficiente de aprendizaje: "+ Network.LEARNING_CONSTANT+ "\n");
//			wr.append("Cota error: "+ Network.COTA+ "\n");
//			wr.append("Máximo número de iteracciones: "+ Network.ITERATION_MAX+ "\n");
//		}
//		
//		public void writeSolutionFound(int it){
//			wr.append("\n El entrenamiento ha llegado a una solución en la iteración "+ it+ "\n");
//			wr.append("Matriz de pesos resultante:\n" );
//			writeWeightMatrix();
//		}
//
//		
//		
//		public void writeCompareOutputs (Neuron d, Neuron o){
//			BigDecimal aux[] = d.getValues();
//			wr.append("Deseada: (");
//			for (BigDecimal b: d.getValues()){
//				wr.append(b+ " ");	
//			}
//			wr.append(") \nObtenida: (");
//			int i = 0;
//			for (BigDecimal b: o.getValues()){
//				wr.append(b+ " ");	
//				aux[i] = aux[i].abs().subtract(b.abs());
//				i++;
//			}
//			wr.append(") \nDiferencia: (");
//			for (BigDecimal b: aux){
//				wr.append(b+ " ");	
//			}
//			wr.append(") \n\n");
//			
//		}

	

}
