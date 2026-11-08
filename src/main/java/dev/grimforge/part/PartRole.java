package dev.grimforge.part;

/**
 * The role a part plays in a tool, which decides the stats it adds.
 */
public enum PartRole {
    /**
     * Sets durability, mining speed, attack damage and tier.
     */
    HEAD,
    /**
     * Changes mining speed and adds some durability.
     */
    HANDLE,
    /**
     * Adds some durability.
     */
    EXTRA
}
