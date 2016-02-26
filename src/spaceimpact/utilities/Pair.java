package spaceimpact.utilities;

/**
 * This class models a "pair" of elements.
 *
 * @param <A>
 *            The type of the first element of the pair
 * @param <B>
 *            The type of the first element of the pair
 */

public class Pair<A, B> {

	private final A a;
	private final B b;

	/**
	 * Pair constructor.
	 *
	 * @param firstParam
	 *            The first element of the pair (type A)
	 * @param secondParam
	 *            The second element of the pair (type B)
	 */
	public Pair(final A firstParam, final B secondParam) {
		this.a = firstParam;
		this.b = secondParam;
	}

	/**
	 * Getter of the first element (it's not a defensive copy!).
	 *
	 * @return The first element of the pair
	 */
	public A getFirst() {
		return this.a;
	}

	/**
	 * Getter of the second element (it's not a defensive copy!).
	 *
	 * @return The second element of the pair
	 */
	public B getSecond() {
		return this.b;
	}

	/**
	 * Convert the pair into a human-readable String.
	 *
	 * @return The resulting String
	 */
	@Override
	public String toString() {
		return "<" + this.a + "," + this.b + ">";
	}

}