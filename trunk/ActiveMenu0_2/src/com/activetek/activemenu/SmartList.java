package com.activetek.activemenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;

public class SmartList extends Observable implements List<MenuItem> {

	List<MenuItem> delegate;

	public SmartList(List<MenuItem> delegate) {
		this.delegate = delegate;
	}

	private SmartList() {
		this.delegate = new ArrayList<MenuItem>();
	}

	public static synchronized SmartList getInstance() {
		if (instance == null)
			instance = new SmartList();
		return instance;
	}

	private static SmartList instance;
	
	@Override
    public String toString() {
        return delegate.toString();
    }

	@Override
	public boolean add(MenuItem song) {
		boolean result = delegate.add(song);
        if (result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public void add(int position, MenuItem song) {
		delegate.add(position, song);
        setChanged();
        notifyObservers();
	}

	@Override
	public boolean addAll(Collection<? extends MenuItem> c) {
		boolean result = delegate.addAll(c);
        if(result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public boolean addAll(int position, Collection<? extends MenuItem> c) {
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
	public MenuItem get(int position) {
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
	public Iterator<MenuItem> iterator() {
		return delegate.iterator();
	}

	@Override
	public int lastIndexOf(Object song) {
		return delegate.lastIndexOf(song);
	}

	@Override
	public ListIterator<MenuItem> listIterator() {
		return delegate.listIterator();
	}

	@Override
	public ListIterator<MenuItem> listIterator(int position) {
		return delegate.listIterator(position);
	}

	@Override
	public MenuItem remove(int position) {
		MenuItem result = delegate.remove(position);
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
	public MenuItem set(int position, MenuItem song) {
		MenuItem result = delegate.set(position, song);
		setChanged();
		notifyObservers();
		return result;
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public List<MenuItem> subList(int fromPosition, int toPosition) {
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