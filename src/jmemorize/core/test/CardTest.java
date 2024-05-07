/*
 * jMemorize - Learning made easy (and fun) - A Leitner flashcards tool
 * Copyright(C) 2004-2008 Riad Djemili and contributors
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 1, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package jmemorize.core.test;

import jmemorize.core.Card;
import jmemorize.core.CardSide;
import jmemorize.core.Category;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

public class CardTest extends TestCase
{
    private Category m_category;
    private Card     m_card;

    protected void setUp() throws Exception
    {
        m_category = new Category("root_category");
        m_card = new Card(new Date(), "test_card","bla");
    }
    
    public void testEditText()
    {
        m_category.addCard(m_card);
        m_card.setSides("test frontside", "test backside");
        m_card.setSides("test frontside2", "test backside2");
        
        CardSide frontSide = m_card.getFrontSide();
        CardSide backSide = m_card.getBackSide();
        
        assertEquals("test frontside2", frontSide.getText().getUnformatted());
        assertEquals("test backside2", backSide.getText().getUnformatted());
    }
    
    public void testAddImage()
    {
        List<String> images = new LinkedList<String>();
        images.add("foo.png");
        
        m_card.getFrontSide().setImages(images);
        assertEquals(images, m_card.getFrontSide().getImages());
        
        List<String> originalImages = new LinkedList<String>();
        originalImages.addAll(images);
        
        images.add("bar.png");
        assertEquals("image list should not be stored as reference to argument", 
            originalImages, m_card.getFrontSide().getImages());
    }

    public void testCardClonesBasic()
    {
        m_category.addCard(m_card);
        assertEquals(1, m_category.getCards().size());

        Card clonedCard = (Card)m_card.clone();
        
        assertEquals(1, m_category.getCards().size());
        assertEquals(null, clonedCard.getCategory());
    }

    public void testCardClonesText()
    {
        Card clonedCard = (Card)m_card.clone();
        clonedCard.setSides("other front", "other back");

        assertEquals("test_card", m_card.getFrontSide().toString());
        assertEquals("bla", m_card.getBackSide().toString());

        assertEquals("other front", clonedCard.getFrontSide().toString());
        assertEquals("other back", clonedCard.getBackSide().toString());
    }
    
    public void testCardClonesImages()
    {
        List<String> images = new LinkedList<String>();
        images.add("foo.png");
        m_card.getFrontSide().setImages(images);
        
        Card clonedCard = (Card)m_card.clone();
        assertEquals(images, clonedCard.getFrontSide().getImages());
        
        List<String> noImages = new LinkedList<String>();
        m_card.getFrontSide().setImages(noImages);
        
        assertEquals("clone should not reference originals image list",
            images, clonedCard.getFrontSide().getImages());
    }
    
    public void testCardWithoutProgressClonesImages()
    {
        List<String> images = new LinkedList<String>();
        images.add("foo.png");
        m_card.getFrontSide().setImages(images);
        
        Card clonedCard = (Card)m_card.cloneWithoutProgress();
        assertEquals(images, clonedCard.getFrontSide().getImages());
        
        List<String> noImages = new LinkedList<String>();
        m_card.getFrontSide().setImages(noImages);
        
        assertEquals("clone should not reference originals image list",
            images, clonedCard.getFrontSide().getImages());
    }

    public void testCardClonesStatss()
    {
        m_category.addCard(m_card);
        Card clonedCard = (Card)m_card.clone();

        assertEquals(m_card.getLevel(), clonedCard.getLevel());

        assertEquals(m_card.getDateCreated(), clonedCard.getDateCreated());
        assertEquals(m_card.getDateExpired(), clonedCard.getDateExpired());
        assertEquals(m_card.getDateTested(), clonedCard.getDateTested());
        assertEquals(m_card.getDateTouched(), clonedCard.getDateTouched());
        // assertFalse(m_childCard.getDateModified().equals(clonedCard.getDateModified()));

        assertEquals(m_card.getPassRatio(), clonedCard.getPassRatio());
        assertEquals(m_card.getTestsPassed(), clonedCard.getTestsPassed());
        assertEquals(m_card.getTestsTotal(), clonedCard.getTestsTotal());
        assertEquals(m_card.getLearnedAmount(true), clonedCard.getLearnedAmount(true));
        assertEquals(m_card.getLearnedAmount(false), clonedCard.getLearnedAmount(false));
    }
    @Test
    public void testRemoveCardFromFavorites() {


        m_card.markAsFavorite();


        m_card.removeFromFavorites();


        assertFalse(m_card.isFavorite());
    }
    @Test
    public void testMarkCardAsFavorite() {




        m_card.markAsFavorite();



        assertTrue(m_card.isFavorite());
    }
    @Test
    public void testGetAllFavoriteCards() {

        // Create some cards and mark them as favorite
        Card favoriteCard1 = new Card();
        favoriteCard1.markAsFavorite();

        Card favoriteCard2 = new Card();
        favoriteCard2.markAsFavorite();

        
        List<Card> favoriteCards = Card.getAllFavoriteCards();

        // Check if all favorite cards are retrieved
        assertEquals(2, favoriteCards.size());
        assertTrue(favoriteCards.contains(favoriteCard1));
        assertTrue(favoriteCards.contains(favoriteCard2));
    }
    @Test
    public void testAddTag() {
        Card card = new Card();
        card.addTag("tag1");
        assertTrue(card.hasTag("tag1"));

        // Adding the same tag should not result in duplicates
        card.addTag("tag1");
        assertEquals(1, card.getTags().size());
    }

    @Test
    public void testRemoveTag() {
        Card card = new Card();
        card.addTag("tag1");
        card.removeTag("tag1");
        assertFalse(card.hasTag("tag1"));

        // Removing a non-existing tag should have no effect
        card.removeTag("tag1");
        assertFalse(card.hasTag("tag1"));
    }

    @Test
    public void testGetTags() {
        Card card = new Card();
        card.addTag("tag1");
        card.addTag("tag2");
        List<String> tags = card.getTags();

        assertEquals(2, tags.size());

        // Modifying the returned list should not affect the original list of tags
        tags.add("tag3");
        assertFalse(card.hasTag("tag3"));
    }
    @Test
    public void testHasTag() {
        Card card = new Card();
        assertFalse(card.hasTag("tag1"));

        card.addTag("tag1");
        assertTrue(card.hasTag("tag1"));
    }

    @Test
    public void testUpdateModified() {
        Card card = new Card();
        Date originalModified = card.getDateModified();

        // Update modified should change the modified date
        card.addTag("tag1");
        assertNotEquals(originalModified, card.getDateModified());
    }

    @Test
    public void testFilterByTag() {
        Card card1 = new Card();
        card1.addTag("tag1");

        Card card2 = new Card();
        card2.addTag("tag2");

        List<Card> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);

        List<Card> filtered = Card.filterByTag(cards, "tag1");
        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(card1));

        // Filter by non-existing tag should return an empty list
        filtered = Card.filterByTag(cards, "tag3");
        assertTrue(filtered.isEmpty());
    }
}
