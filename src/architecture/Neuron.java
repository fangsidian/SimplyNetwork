package architecture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/*Clase que representa a un input/output u oculta, aunque en esta primera versión no
  no tendremos ocultas*/


public class Neuron {
	
	private int size;
	private BigDecimal[] values; 
	private static Logger log = Logger.getLogger(Neuron.class);
	private BigDecimal maxValue; //Valor máximo dentro de la neurona y con el q normalizaremos
	private boolean bias = false; 
	
	
	//Constructor
	public Neuron() {
		super();
	}

	public Neuron (BigDecimal[] values, int size, boolean bias){
		super();
		this.values = values;
		this.size = size;
		this.bias = bias;
	}
	
	public void printNeuron (){
		log.trace ("Valores de la neurona: ");
		System.out.print ("Valores de la neurona: ");
		for (BigDecimal d: values){
			log.trace(d);
			System.out.print(d+ " ");
		}
		System.out.print("\n");
	}	
		
	
	
	
	
			//numDays = Numero de datos que tiene una neurona (tamaño de la neurona, a excepcion de neuronas con bias)
			//CAMBIAR A LA CLASE NEURON
			//Son outputs, bias por defecto, osea a false
		//Iterator<BigDecimal> it = rms.iterator() o Iterator<BigDecimal> it = ris.iterator();
		public Neuron (Iterator<BigDecimal> it, int numDays, boolean bias)
		{
			int neuronSize = numDays, 
				i = 0,
				cont = 0;
			if (bias){ 				//Si tiene bias, su tamaño es +1
				log.trace("Esta neurona tiene bias");
				neuronSize++; 
				BigDecimal[] values = new BigDecimal[neuronSize];
				values[i] = new BigDecimal(1);
				i++;
				while(it.hasNext() && cont < numDays){
					BigDecimal b = it.next();
					values[i] = b;
					i++;
					cont++;
				}
				this.values = values; 
			}
			else{
				BigDecimal[] values = new BigDecimal[neuronSize];
				while(it.hasNext() && cont < numDays){
					BigDecimal b = it.next();
					values[i] = b;
					i++;
					cont++;
				}
				this.values = values; 
			}
			
			
			this.size = neuronSize;
			this.bias = bias; 
			
			//n.normalizeNeuron();
			constantProduct(Network.cntNormalization);
		}
			
		
	
	
		
	public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public boolean isBias() {
			return bias;
		}

		public void setBias(boolean bias) {
			this.bias = bias;
		}

	//Normalizar neuron to Maxime: todos los elementos del vector, divididos entre el mayor.
	//No la devuelve, sino que sobreescribe la clase	
	public void normalizeNeuron ()
	{
		BigDecimal[] aux = new BigDecimal[this.values.length];
		maxValue = this.getValues()[0];
		for (BigDecimal b: this.getValues())
		{
			if ((b.compareTo(maxValue)) == 1){
				maxValue = b;
			}
		}
		log.trace("Before normalize: ");
		for (BigDecimal b: this.getValues())
		{
			log.trace(b);
		}
		log.trace("After normalizeNeuron");
		log.trace("divisor value: " +maxValue);
		for (int i = 0; i< this.values.length; i++)
		{
			BigDecimal b = this.values[i];
			aux[i] = b.divide(maxValue, RoundingMode.HALF_UP); //in case the exact result has an infinite number of decimals
			log.trace(aux[i]);
		}
 
		this.setValues(aux);
	} 
		
	
public void undoNormalizationNeuron (){
	BigDecimal[] aux = new BigDecimal[this.values.length];
	log.trace("Normalized Neurons: ");
	for (BigDecimal b: this.getValues())
	{
		log.trace(b);
	}
	log.trace("After undo normalized");
	log.trace("multiplicando: " +maxValue);
	for (int i = 0; i< this.values.length; i++)
	{
		BigDecimal b = this.values[i];
		b = b.multiply (maxValue);
		b = b.setScale(Network.PRECISION, RoundingMode.HALF_UP);
		aux[i] = b;
		
		//in case the exact result has an infinite number of decimals
		log.trace(aux[i]);
	}

	this.setValues(aux);
		
		
		
	} 
	
	public BigDecimal getMaxValue() {
	return maxValue;
}

public void setMaxValue(BigDecimal maxValue) {
	this.maxValue = maxValue;
}

	public BigDecimal[] getValues() {
		return values;
	}

	public void setValues(BigDecimal[] values) {
		this.values = values;
	}

//una vez normalizadas las salidas, vamos a probar a añadir la componente menor negativa, en positivo al resto de valores
	//para así solo teener valores positivos
void addSmallestValueNegative (){
	log.info ("Add smallest value");
	BigDecimal aux[] = new BigDecimal[this.values.length];
	BigDecimal min = this.getValues()[0];
	for (BigDecimal b: this.getValues())
	{
		if ((b.compareTo(min)) == -1){ 
			min = b;
		}
	}
	min = min.negate();
	log.trace("add smallest value (positive): " +min);
	for (int i = 0; i< this.values.length; i++)
	{
		BigDecimal b = this.values[i];
		aux[i] = b.add(min);
		log.trace(b);
	}
	this.setValues(aux);
}

		
//modifica la clase multiplicando todos los valores por una cnt
public void constantProduct (BigDecimal cnt){
	log.info("Multiplicamos la neurona por la constante: "+ Network.cntNormalization);
	BigDecimal[] aux = new BigDecimal[this.size];
	System.out.println("tamaño de la neurona:"+ this.size+ "cheking: "+ this.values.length +"\n");
	if (bias){
		for (int i = 1; i<this.size; i++){
			aux[i] = this.values[i].multiply(cnt);
			System.out.println(aux[i]);
			aux[i] = aux[i].setScale(Network.PRECISION, RoundingMode.HALF_UP);
		}		
	}
	else{
		for (int i = 0; i<this.size; i++){
			aux[i] = this.values[i].multiply(cnt);
			aux[i] = aux[i].setScale(Network.PRECISION, RoundingMode.HALF_UP);
		}		
		
		this.setValues(aux);
	}
	log.trace("Valor neurona tras multiplicar por cnt");
	for (BigDecimal a: this.values){
		log.trace(a);
	}
	
}
	
}
