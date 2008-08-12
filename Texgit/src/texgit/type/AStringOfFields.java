package texgit.type;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;

import br.com.nordestefomento.jrimum.utilix.ITextStream;

public abstract class AStringOfFields<G extends IField<?>> implements ITextStream, List<G>, Cloneable{

	/**
	 * 
	 */
	private ArrayList<G> fields;

	/**
	 * 
	 */
	public AStringOfFields() {

		fields = new ArrayList<G>();
	}
	
	/**
	 * 
	 */
	public AStringOfFields(Integer size) {

		if(isNotNull(size, "size"))
			if(size > 0){
				fields = new ArrayList<G>(size);
				for(int i = 1; i <= size; i++ )
					fields.add(null);
			}
			else{
				throw new IllegalArgumentException("A quantidade de campos [ " + size + " ] deve ser um número natural > 0!");
			}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected AStringOfFields<G> clone() throws CloneNotSupportedException {
		
		//Clona apenas com uma referência a fields.
		AStringOfFields<G> sof = (AStringOfFields<G>) super.clone();
		
		//Clonagem real
		sof.fields = new ArrayList<G>();
		
		for(G gf : fields)
				sof.fields.add((G) gf.clone());
		
		return sof;
	}

	/**
	 * <p>
	 * A leitrua de uma string, ou melhor, a divisão de uma string em fields,
	 * não ocorre sem uma lógica. Assim este método deve ser implementado por
	 * cada subclasse.
	 * </p>
	 * 
	 * @see br.com.nordestefomento.jrimum.utilix.IReadWriteStream#read(java.lang.Object)
	 */
	public abstract void read(String lineOfField);

	/**
	 * <p>
	 * Escreve os campos na ordem em que estão dispostos na lista em uma única linha (string).
	 * </p>
	 * 
	 * @see br.com.nordestefomento.jrimum.utilix.IReadWriteStream#write()
	 */
	public String write() {

		StringBuilder lineOfFields = new StringBuilder(StringUtils.EMPTY);

		if (isNotNull(fields, "fields")){

			for (G field : fields)
				lineOfFields.append(field.write());
		}

		return lineOfFields.toString();
	}

	/**
	 * @return the fields
	 */
	public List<G> getFields() {
		return fields;
	}

	/**
	 * @see java.util.List#add(java.lang.Object)
	 */
	@Override
	public boolean add(G e) {

		return fields.add(e);
	}

	/**
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, G element) {

		fields.add(index, element);
	}

	/**
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends G> c) {

		return fields.addAll(c);
	}

	/**
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends G> c) {

		return fields.addAll(index, c);
	}

	/**
	 * @see java.util.List#clear()
	 */
	@Override
	public void clear() {

		fields.clear();
	}

	/**
	 * @see java.util.List#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {

		return fields.contains(o);
	}

	/**
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {

		return fields.containsAll(c);
	}

	/**
	 * @see java.util.List#get(int)
	 */
	@Override
	public G get(int index) {

		return fields.get(index);
	}

	/**
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	@Override
	public int indexOf(Object o) {

		return fields.indexOf(o);
	}

	/**
	 * @see java.util.List#isEmpty()
	 */
	@Override
	public boolean isEmpty() {

		return fields.isEmpty();
	}

	/**
	 * @see java.util.List#iterator()
	 */
	@Override
	public Iterator<G> iterator() {

		return fields.iterator();
	}

	/**
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	@Override
	public int lastIndexOf(Object o) {

		return fields.indexOf(o);
	}

	/**
	 * @see java.util.List#listIterator()
	 */
	@Override
	public ListIterator<G> listIterator() {

		return fields.listIterator();
	}

	/**
	 * @see java.util.List#listIterator(int)
	 */
	@Override
	public ListIterator<G> listIterator(int index) {

		return fields.listIterator(index);
	}

	/**
	 * @see java.util.List#remove(int)
	 */
	@Override
	public G remove(int index) {

		return fields.remove(index);
	}

	/**
	 * @see java.util.List#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {

		return fields.remove(o);
	}

	/**
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {

		return fields.removeAll(c);
	}

	/**
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {

		return fields.retainAll(c);
	}

	/**
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public G set(int index, G element) {

		return fields.set(index, element);
	}

	/**
	 * @see java.util.List#size()
	 */
	@Override
	public int size() {

		return fields.size();
	}

	/**
	 * @see java.util.List#subList(int, int)
	 */
	@Override
	public List<G> subList(int fromIndex, int toIndex) {

		return fields.subList(fromIndex, toIndex);
	}

	/**
	 * @see java.util.List#toArray()
	 */
	@Override
	public Object[] toArray() {

		return fields.toArray();
	}

	/**
	 * @see java.util.List#toArray(T[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {

		return fields.toArray(a);
	}
}
