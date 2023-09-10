package org.caranus.search.generic;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.logging.Logger;


public class GenericSearchImpl<K extends GenericElement<V>, V> implements GenericSearch<K, V>
{
	protected ElementToGet elementToGet;
	public enum ElementToGet {
		FIRST,
		LAST,
		ANY
	}

	protected boolean ascendingOrder;

	protected Logger logger = Logger.getLogger(GenericSearchImpl.class.getName());

	public GenericSearchImpl(ElementToGet elementToGet)
	{
		this.elementToGet = elementToGet;
		this.ascendingOrder = true;
	}

	private <T> int getObjectIndexFromList(List<T> list, Predicate<T> predicate){
		int index;
		try {
			index = list.indexOf(list.stream()
				  .filter(predicate)
				  .findFirst()
				  .get());
		} catch (NoSuchElementException ex){
			index = -1;
		}
		return index;
	}

	public int search(K element, List<K> elementList)
	{
		if (elementList.size() == 0)
		{
			return -1;
		}

		this.ascendingOrder = (element.compareTo(elementList.get(elementList.size() - 1)) < 0);
		return searchElement(element, elementList, 0, elementList.size());
	}

	@Override
	public int searchElement(K element, List<K> elementList, int low, int high)
	{
		return getObjectIndexFromList(elementList, a -> a.getValue().equals(element.getValue()));
	}

	public void showStatistics() {
		/*
		logger.info("Number of cached elements: " + this.getSize());
		logger.info("Number of evictions: " + cacheEvictions);
		logger.info("Average put time: " + String.format("%.8f", (double) this.getAveragePutTime() / 1_000_000_000) + " seconds.");
		logger.info("----------------------------------------------");
		 */
	}
}