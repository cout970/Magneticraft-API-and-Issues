package com.cout970.magneticraft.api.steel;

/**
 * Represents a Steel attribute.
 * An example Attribute would be if the steel has gold dust mixed in
 */
public interface ISteelAttribute {

    /**
     * Get the name of this attribute
     *
     * @return The name of the attribute
     */
    public String getName();

    /**
     * Gets the fully formatted text to display
     *
     * @param value The Value Of the Attribute
     * @return The Fully Formatted Text
     */
    public String getDisplayText(int value);

}
