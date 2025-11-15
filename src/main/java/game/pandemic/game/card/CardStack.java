package game.pandemic.game.card;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Entity
public class CardStack<C extends Card> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Card.class)
    private List<C> cards;

    public CardStack() {
        this.cards = new LinkedList<>();
    }

    public CardStack(final List<CardStack<C>> stacks) {
        this.cards = stacks.stream()
                .map(stack -> stack.cards)
                .flatMap(List::stream)
                .toList();
    }

    public void push(final C card) {
        this.cards.add(card);
    }

    public C pop() {
        return this.cards.remove(this.cards.size() - 1);
    }

    public int size() {
        return this.cards.size();
    }

    public void shuffle() {
        Collections.shuffle(this.cards, new Random());
    }

    public List<CardStack<C>> divideIntoSubstacks(final int amount) {
        final List<CardStack<C>> substacks = new LinkedList<>();

        final int numberOfCardsPerStack = this.cards.size() / amount;
        final int numberOfRemainingCards = this.cards.size() % amount;

        for (int i = 0; i < amount; i++) {
            final CardStack<C> substack = new CardStack<>();
            for (int j = 0; j < numberOfCardsPerStack; j++) {
                substack.push(pop());
            }
            if (i < numberOfRemainingCards) {
                substack.push(pop());
            }
            substacks.add(substack);
        }
        return substacks;
    }
}
