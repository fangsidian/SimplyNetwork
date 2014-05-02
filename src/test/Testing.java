package test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import dataManager.ReadExcel;
import dataManager.WriteOutcomes;
import jxl.read.biff.BiffException;
import architecture.Company;
import architecture.Network;
import architecture.Neuron;
import utilities.Matrix;



public class Testing {
	
	
	
	
	public Testing() {
		super();
	}

	public Matrix createMatrix1 (){
		BigDecimal aux[][] = new BigDecimal[3][3];
		aux[0][0] = new BigDecimal(4);
		aux[0][1] = new BigDecimal(23); 
		aux[0][2] = new BigDecimal(8); 
		aux[1][0] = new BigDecimal(7); 
		aux[1][1] = new BigDecimal(1); 
		aux[1][2] = new BigDecimal(34); 
		aux[2][0] = new BigDecimal(9); 
		aux[2][1] = new BigDecimal(12); 
		aux[2][2] = new BigDecimal(23); 
		Matrix m = new Matrix(aux);
		return m;
	}
	
	public Matrix createMatrix2 (){
		BigDecimal aux[][] = new BigDecimal[3][3];
		aux[0][0] = new BigDecimal(5);
		aux[0][1] = new BigDecimal(2); 
		aux[0][2] = new BigDecimal(9); 
		aux[1][0] = new BigDecimal(32); 
		aux[1][1] = new BigDecimal(17); 
		aux[1][2] = new BigDecimal(3); 
		aux[2][0] = new BigDecimal(16); 
		aux[2][1] = new BigDecimal(11); 
		aux[2][2] = new BigDecimal(42); 
		Matrix m = new Matrix(aux);
		return m;
	}
	
	public Matrix createVector (){
		BigDecimal aux[][] = new BigDecimal[3][1];
		aux[0][0] = new BigDecimal(7);
		aux[1][0] = new BigDecimal(8); 
		aux[2][0] = new BigDecimal(5); 
		Matrix m = new Matrix(aux);
		return m;
	}
	
	
	
	public void testMatrixClass (){ 
		
		Matrix m1 = createMatrix1();
		m1.printMatrix();
		System.out.print("\n");
		Matrix m2 = createMatrix2();
		m2.printMatrix();
		System.out.print("\n");
		Matrix v = createVector();
		v.printMatrix();
		System.out.print("\n");
		
		System.out.print("Probando el sumar matrices: \n");
		Matrix suma = Matrix.addition(m1, m2);
		suma.printMatrix();
		System.out.print("\n");

		
		System.out.print("Probando el restar matrices: \n");
		Matrix resta = Matrix.subtraction(m1, m2);
		resta.printMatrix();
		System.out.print("\n");
		
		
		System.out.print("Probando el multiplicar matrices: \n");
		Matrix multiplicar = Matrix.product(m1, m2);
		multiplicar.printMatrix();
		System.out.print("\n");
		
		
		System.out.print("Probando el multiplicar matrices (Matriz por Vector) \n");
		Matrix multiplicar2 = Matrix.product(m1, v);
		multiplicar2.printMatrix();
		System.out.print("\n");
		
		
		System.out.print("Probando el transponer matriz (con un vector) \n");
		Matrix transp = Matrix.transponer(v); 
		transp.printMatrix();
		System.out.print("\n");
		
		System.out.print("Probando el transponer matriz (matriz 3x3) \n");
		Matrix transp2 = Matrix.transponer(m1); 
		transp2.printMatrix();
		System.out.print("\n");
		
		System.out.print("Probando el setValuePos \n");
		m1.setValuePos(0, 2, new BigDecimal(99));
		m1.printMatrix();
	}
	
	
	

	public static void main(String[] args) {
		
		
		Testing a = new Testing();
		//a.testMatrixClass();
		
		/**PRUEBAS CLASE NETWORK*/
		try {
			ReadExcel excel = new ReadExcel();
			Company p = excel.readCompanyById("024"); //No se si dejarlo como clase estática
			Network n = Network.getInstance(p);
			
			//Pruebas Entrenamiento
			int numPatronesApren = 10;
			int numDatosPorNeurona = 10; //EL tamaño de la neurona no puede verse involucrado en la OBTENCIÓN de los datos
			// del excel, por lo que indicamos el numero de datos Rm o Ri q tendrá por parámetros y luego 
			//indicaremos el tamño de la neurona teniendo en cuenta este parámetro y si tiene bias o no.
			//Solo los inputs pueden tener bias. 
			boolean bias = true; 
			int inicio = 1; //desde donde comienza a seleccionar los patrones de aprend (podremos poner fechas más adelante)
			ArrayList<Neuron> in = n.createArrayNeuronsRm(numPatronesApren, inicio, numDatosPorNeurona, bias);
			n.setInputs(in);
			System.out.print("Size: "+ in.size());
			for (Neuron i: in){
				i.printNeuron();
			}
			
			ArrayList<Neuron> ou = n.createArrayNeuronsRi(numPatronesApren, inicio, numDatosPorNeurona);
			n.setDesiredOutputs(ou);
			System.out.print("Size: "+ ou.size());
			for (Neuron i: ou){
				i.printNeuron();
			}
			
			n.training();
			
			
			//Después del training vemos como se comporta la red, primero con los mismos inputs posteriormente cogeremos
			//los 10 siguientes inputs
			ArrayList<Neuron> salidas = n.calculateOutputs(in);
			WriteOutcomes writer = new WriteOutcomes("C:\\Users\\Esther\\Desktop\\Historial log\\actual\\Obtained outputs.txt", n);

			writer.writeWeightMatrix();
			Iterator<Neuron> it1 = ou.iterator();
			Iterator<Neuron> it2 = salidas.iterator(); 
			
			writer.writeStringAppend("\n Inputs/Outputs con los que se ha realizado el aprendizaje\n");
			while (it1.hasNext() && it2.hasNext()){
				writer.writeCompareOutputs(it1.next(), it2.next());
			}
			
			
//			for (Neuron e: ou){
//				
//			writer.writeNeuron(e);
//		
//			}
//			for (Neuron e: salidas){
//				writer.writeNeuron(e);
//			}
			
			
			
			//al probar los inputs, añadimos bias porq la matriz de pesos resultante fue entrenada con bias tb y sus dimensiones varían
			
			ArrayList<Neuron> in2 = n.createArrayNeuronsRm(10, 11, 10, true);
			n.setInputs(in2);
			ArrayList<Neuron> ou2 = n.createArrayNeuronsRi(10, 11, 10);
			n.setDesiredOutputs(ou2);
		ArrayList<BigDecimal> aux2 = new ArrayList<BigDecimal>();
//			for (Neuron e: ou2){
//				aux.add(e.getMaxValue());
//			}
			ArrayList<Neuron> salidas2 = n.calculateOutputs(in2);
			Iterator<Neuron> it3 = ou2.iterator();
			Iterator<Neuron> it4 = salidas2.iterator(); 
			
			writer.writeStringAppend("/n Probando con los siguientes 10 Inputs/Outputs");
			while (it3.hasNext() && it4.hasNext()){
				writer.writeCompareOutputs(it3.next(), it4.next());
			}
			writer.closeFile();
			
			
			
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
	}

}
