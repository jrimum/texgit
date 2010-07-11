package org.jrimum.texgit.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.jrimum.utilix.ObjectUtil;
import org.jrimum.utilix.TextStream;

@SuppressWarnings("serial")
public abstract class AbstractStringOfFields<G extends Field<?>> implements TextStream, List<G>, Cloneable{

	/**
	 * 
	 */
	private ArrayList<G> fields;

	/**
	 * 
	 */
	public AbstractStringOfFields() {

		fields = new ArrayList<G>();
	}
	
	/**
	 * 
	 */
	public AbstractStringOfFields(Integer size) {

		ObjectUtil.checkNotNull(size, "size");

		if (size > 0) {
			fields = new ArrayList<G>(size);
			for (int i = 1; i <= size; i++){
				fields.add(null);
			}
		} else {
			throw new IllegalArgumentException("A quantidade de campos [ "
					+ size + " ] deve ser um número natural > 0!");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected AbstractStringOfFields<G> clone() throws CloneNotSupportedException {
		
		//Clona apenas com uma referência a fields.
		AbstractStringOfFields<G> sof = (AbstractStringOfFields<G>) super.clone();
		
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
	 * @see org.jrimum.utilix.IReadWriteStream#read(java.lang.Object)
	 */
	public abstract void read(String lineOfField);

	/**
	 * <p>
	 * Escreve os campos na ordem em que estão dispostos na lista em uma única linha (string).
	 * </p>
	 * 
	 * @see org.jrimum.utilix.IReadWriteStream#write()
	 */
	public String write() {

		StringBuilder lineOfFields = new StringBuilder(StringUtils.EMPTY);

		ObjectUtil.checkNotNull(fields, "fields");

		for (G field : fields) {
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
	public boolean add(G e) {

		return fields.add(e);
	}

	/**
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, G element) {

		fields.add(index, element);
	}

	/**
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends G> c) {

		return fields.addAll(c);
	}

	/**
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends G> c) {

		return fields.addAll(index, c);
	}

	/**
	 * @see java.util.List#clear()
	 */
	public void clear() {

		fields.clear();
	}

	/**
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {

		return fields.contains(o);
	}

	/**
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {

		return fields.containsAll(c);
	}

	/**
	 * @see java.util.List#get(int)
	 */
	public G get(int index) {

		return fields.get(index);
	}

	/**
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {

		return fields.indexOf(o);
	}

	/**
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {

		return fields.isEmpty();
	}

	/**
	 * @see java.util.List#iterator()
	 */
	public Iterator<G> iterator() {

		return fields.iterator();
	}

	/**
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {

		return fields.indexOf(o);
	}

	/**
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<G> listIterator() {

		return fields.listIterator();
	}

	/**
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<G> listIterator(int index) {

		return fields.listIterator(index);
	}

	/**
	 * @see java.util.List#remove(int)
	 */
	public G remove(int index) {

		return fields.remove(index);
	}

	/**
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {

		return fields.remove(o);
	}

	/**
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {

		return fields.removeAll(c);
	}

	/**
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {

		return fields.retainAll(c);
	}

	/**
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public G set(int index, G element) {

		return fields.set(index, element);
	}

	/**
	 * @see java.util.List#size()
	 */
	public int size() {

		return fields.size();
	}

	/**
	 * @see java.util.List#subList(int, int)
	 */
	public List<G> subList(int fromIndex, int toIndex) {

		return fields.subList(fromIndex, toIndex);
	}

	/**
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {

		return fields.toArray();
	}

	/**
	 * @see java.util.List#toArray(T[])
	 */
	public <T> T[] toArray(T[] a) {

		return fields.toArray(a);
	}
}
