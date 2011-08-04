package com.activetek.activemenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;

public class OList extends Observable implements List<String> {

	List<String> delegate;

	public OList(List<String> delegate) {
		this.delegate = delegate;
	}

	private OList() {
		this.delegate = new ArrayList<String>();
	}

	public static synchronized OList getInstance() {
		if (instance == null)
			instance = new OList();
		return instance;
	}

	private static OList instance;
	
	@Override
    public String toString() {
        return delegate.toString();
    }
	

	@Override
	public boolean add(String song) {
		boolean result = delegate.add(song);
        if (result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public void add(int position, String song) {
		delegate.add(position, song);
        setChanged();
        notifyObservers();
	}

	@Override
	public boolean addAll(Collection<? extends String> c) {
		boolean result = delegate.addAll(c);
        if(result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public boolean addAll(int position, Collection<? extends String> c) {
		boolean result = delegate.addAll(position, c);
        if(result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public void clear() {
		delegate.clear();
        setChanged();
        notifyObservers();
	}

	@Override
	public boolean contains(Object song) {
		return delegate.contains(song);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}

	@Override
	public String get(int position) {
		return delegate.get(position);
	}

	@Override
	public int indexOf(Object song) {
		return delegate.indexOf(song);
	}
	
	@Override
	public boolean equals(Object song) {
        return delegate.equals(song);
    }
	
	@Override
	public int hashCode() {
        return delegate.hashCode();
    }

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public Iterator<String> iterator() {
		return delegate.iterator();
	}

	@Override
	public int lastIndexOf(Object song) {
		return delegate.lastIndexOf(song);
	}

	@Override
	public ListIterator<String> listIterator() {
		return delegate.listIterator();
	}

	@Override
	public ListIterator<String> listIterator(int position) {
		return delegate.listIterator(position);
	}

	@Override
	public String remove(int position) {
		String result = delegate.remove(position);
        setChanged();
        notifyObservers();
        return result;
	}

	@Override
	public boolean remove(Object song) {
		boolean result = delegate.remove(song);
        if (result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = delegate.removeAll(c);
        if (result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean result = delegate.retainAll(c);
		if (result) {
			setChanged();
		}
		notifyObservers();
		return result;
	}

	@Override
	public String set(int position, String song) {
		String result = delegate.set(position, song);
		setChanged();
		notifyObservers();
		return result;
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public List<String> subList(int fromPosition, int toPosition) {
		return delegate.subList(fromPosition, toPosition);
	}

	@Override
	public Object[] toArray() {
		return delegate.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return delegate.toArray(a);
	}
	
}