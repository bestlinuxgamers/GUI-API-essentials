package net.bestlinuxgamers.guiApiEssentials.geometry

import org.bukkit.inventory.ItemStack

/**
 * Komponente zum Darstellen eines statischen Quadrats.
 * @param length Seitenlänge des Quadrats.
 * @param item Item, aus der die Komponente bestehen soll.
 */
open class Square(length: Int, item: ItemStack?) : Rectangle(length, length, item)
