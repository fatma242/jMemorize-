/*
 * jMemorize - Learning made easy (and fun) - A Leitner flashcards tool
 * Copyright(C) 2004-2006 Riad Djemili
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
package jmemorize.core;


import java.util.LinkedList;
import java.util.List;

/**
 * @author djemili
 */
public class SearchTool
{
    private SearchTool(){}
    public static final int FRONT_SIDE = 0;
    public static final int FLIP_SIDE  = 1;
    public static final int BOTH_SIDES = 2;

    public static List<Card> search(String text, int side, boolean matchCase, List<Card> cards) {
        List<Card> foundCards = new LinkedList<>();
        String searchText = matchCase ? text : text.toLowerCase();

        for (Card card : cards) {
            String frontSide = card.getFrontSide().getText().getUnformatted();
            String flipSide = card.getBackSide().getText().getUnformatted();

            if (!matchCase) {
                frontSide = frontSide.toLowerCase();
                flipSide = flipSide.toLowerCase();
            }

            if ((side == FRONT_SIDE || side == BOTH_SIDES) && frontSide.contains(searchText)) {
                foundCards.add(card);
            }

            if ((side == FLIP_SIDE || side == BOTH_SIDES) && flipSide.contains(searchText)) {
                foundCards.add(card);
            }
        }

        return foundCards;
    }
    


}
